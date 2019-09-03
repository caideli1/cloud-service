package com.cloud.user.service.impl;

import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.utils.StringUtils;

import com.cloud.model.appUser.AppUserAddressInfo;

import com.cloud.user.dao.SysRefusalCycleDao;
import com.cloud.user.model.SysRefusalCycle;
import com.cloud.user.model.UserContactRepairEntity;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.product.OrderPartRateAmount;
import com.cloud.model.product.OrderPartRes;
import com.cloud.model.product.OrderRepayPart;
import com.cloud.model.user.*;
import com.cloud.user.dao.UserAddressDao;
import com.cloud.user.dao.UserContactRepairDao;
import com.cloud.user.dao.UserDao;
import com.cloud.user.model.UserContactRepairReq;
import com.cloud.user.service.UserInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yoga
 * @Description: TODO
 * @date 2019/1/183:40 PM
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAddressDao userAddressDao;
    @Autowired
    private UserContactRepairDao userContactRepairDao;

    @Autowired
    private SysRefusalCycleDao sysRefusalCycleDao;

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userDao.getUserInfoById(id);
    }

    @Override
    public List<UserInfo> getUserInfoList(String id, String phone, String aadhaar, String startTime, String endTime, Integer status) {
        String newEndTime = endTime;
        if (!StringUtils.isBlank(newEndTime)) {
            newEndTime = endTime.concat(" 23:59:59");
        }
        return userDao.getUserInfoList(id, phone, aadhaar, startTime, newEndTime, status);
    }

    @Override
    public UserLoan getUserLoanById(Integer id) {
        return userDao.getUserLoanById(id);
    }

    @Override
    public List<UserLoan> getUserLoanList(String loanNumber, String userId, String phone, Integer handlerType, Integer loanStatus, String startTime, String endTime) {
        String newEndTime = endTime;
        if (!StringUtils.isBlank(newEndTime)) {
            newEndTime = endTime.concat(" 23:59:59");
        }
        return userDao.getUserLoanList(loanNumber, userId, phone, handlerType, loanStatus, startTime, newEndTime);
    }

    @Override
    public List<UserAddress> getUserAddressList(long userId, int type) {
        return userDao.getUserAddressList(userId, type);
    }

    @Override
    public List<Long> getUserIdByAddressItem(String state, String district) {
        return userDao.getUserIdByAddressItem(state.toLowerCase().trim(),district.toLowerCase().trim());
    }

    @Override
    public List<UserInfo> getUserAccountByAadhaarAccount(String aadhaarAccount) {
        return userDao.getUserAccountByAadhaarAccount(aadhaarAccount);
    }

    @Override
    public List<UserInfoExpand> getUserAccountByPancardAccount(String pancardAccount) {
        return userDao.getUserAccountByPancardAccount(pancardAccount);
    }

    @Override
    public int getCompanyNameCountWhenSamePhone(String orderNo) {
        return userDao.getCompanyNameCountWhenSamePhone(orderNo);
    }

    @Override
    public int getCompanyPhoneCountWhenSameName(String orderNo) {
        return userDao.getCompanyPhoneCountWhenSameName(orderNo);
    }

    @Override
    public int getRelativePhoneAsApplyPhoneCount(long userId) {
        return userDao.getRelativePhoneAsApplyPhoneCount(userId);
    }

    @Override
    public int getRelativePhoneAsContactPhoneCount(long userId) {
        return userDao.getRelativePhoneAsContactPhoneCount(userId);
    }

    @Override
    public List<AppUserAddressInfo> getUserAddressListByOrderNum(String userId) {
        return userAddressDao.queryUserAddressByUserId(userId);}

    public Map<String, Object> getUserCreditExtension(long userId) {
        return userDao.getUserCreditExtension(userId);
    }

    @Override
    public Map<String, Object> getUserDueInfo(long userId) {
        Map<String, Object> map = userDao.getUserTotalDueAmount(userId);
        return map;
    }

    @Override
    public List<Map<String, Object>> getUserDueInfoList(long userId, String orderNo) {
        return userDao.getUserDueInfoList(userId, orderNo);
    }

    @Override
    public List<Map<String, Object>> getUserOrderExtensionInfoList(String orderNo) {
        return userDao.getUserOrderExtensionInfoList(orderNo);
    }

    @Override
    public Map<String, Object> userLoanAmountComposition(String orderNo) {
        Map<String, Object> amountCompositionMap = userDao.userLoanAmountComposition(orderNo);

        List<OrderPartRateAmount> rateInfoList = userDao.userLoanRepayCompositionRateInfoList(orderNo);
        BigDecimal totalRateAmount = rateInfoList.stream()
                .map((item -> item.amountAndGst()))
                .reduce(new BigDecimal(0), (lhs, rhs) -> lhs.add(rhs));

        Long extensionCount = (Long) amountCompositionMap.getOrDefault("extension_count", 0);
        Long productTerm = (Long) amountCompositionMap.getOrDefault("product_term", 0);

        // 展期利息费用
        BigDecimal loanAmount  = (BigDecimal) amountCompositionMap.getOrDefault("loan_amount", 0);
        BigDecimal extensionInterestAmount = loanAmount
                .multiply(CommonConfig.CASH_LOAN_EXTENSION_INTEREST)
                .multiply(new BigDecimal(productTerm));

        // 展期费用
        BigDecimal extensionAmount = totalRateAmount
                .add(extensionInterestAmount)
                .multiply(new BigDecimal(extensionCount))
                .setScale(0 , BigDecimal.ROUND_HALF_UP);
        amountCompositionMap.put("extension_amount", extensionAmount);
        return amountCompositionMap;
    }

    @Override
    public OrderPartRes userLoanRepayComposition(String orderNo) {
        List<OrderPartRateAmount> rateInfoList = userDao.userLoanRepayCompositionRateInfoList(orderNo);
        if (CollectionUtils.isEmpty(rateInfoList)) {
            return OrderPartRes.builder().otherAmountList(new ArrayList<>()).rateInfoList(new ArrayList<>()).build();
        }
        OrderRepayPart orderRepayPart = userDao.userLoanRepayComposition(orderNo);
        orderRepayPart.setRateInfoList(rateInfoList);
        BigDecimal totalGstAmount = rateInfoList.stream().map((item -> item.getGstAmount())).reduce(new BigDecimal(0), (lhs, rhs) -> lhs.add(rhs));
        orderRepayPart.setTotalGstAmount(totalGstAmount);
        BigDecimal totalRateAmount = rateInfoList.stream().map((item -> item.amountAndGst())).reduce(new BigDecimal(0), (lhs, rhs) -> lhs.add(rhs));

        // 展期利息费用
        BigDecimal loanAmount  = orderRepayPart.getLoanAmount();
        BigDecimal extensionInterestAmount = loanAmount
                .multiply(CommonConfig.CASH_LOAN_EXTENSION_INTEREST)
                .multiply(new BigDecimal(orderRepayPart.getProductTerm()));

        // 展期费用
        BigDecimal extensionAmount = totalRateAmount
                .add(extensionInterestAmount)
                .multiply(new BigDecimal(orderRepayPart.getExtensionCount()))
                .setScale(0 , BigDecimal.ROUND_HALF_UP);

        orderRepayPart.setExtensionAmount(extensionAmount);

        OrderPartRes orderPartRes = orderRepayPart.toOrderPartRes();
        return orderPartRes;
    }

    @Override
    public List<UserContactRepairEntity> getContactList(long userId, int contactType) {
        List<UserContactRepairEntity> repairEntityList = userContactRepairDao.getContactRepairList(userId, contactType);
        if (CollectionUtils.isNotEmpty(repairEntityList)) {
            List<UserContactRepairEntity> originContactList = userContactRepairDao.getContact(userId, contactType);
            repairEntityList.addAll(originContactList);
        }
        return repairEntityList;
    }

    @Override
    public int repairUserContact(UserContactRepairReq repairReq) {
        UserContactRepairEntity entity = UserContactRepairEntity.builder()
                .userId(repairReq.getUserId())
                .contactMobile(repairReq.getContactMobile())
                .contactName(repairReq.getContactName())
                .contactType(repairReq.getContactType())
                .repairDate(DateUtil.getDate())
                .build();
        return userContactRepairDao.insert(entity);
    }
    @Override
    public Map<String,String> getUserIdAndRelation(Map<String, Object> params) {
        String mobile= MapUtils.getString(params,"mobile");
        String userId = MapUtils.getString(params,"userId");
        String publicContactsUserIds="";
        String ontherUserIds="";
        List<String>   userIdList= userDao.queryRelationUserIdByMobile(mobile);
        List<String> userIdList2=userDao.queryRelationUserIdByUserId(userId);


        for (int i=0;i<userIdList.size();i++){
            if (i!=userIdList.size()-1){
                ontherUserIds=ontherUserIds+ userIdList.get(i)+",";

            }else
            {
                ontherUserIds=ontherUserIds+userIdList.get(i);
            }

        }
        for (int i=0;i<userIdList2.size();i++){
            if (i!=userIdList2.size()-1){
                publicContactsUserIds=publicContactsUserIds+ userIdList2.get(i)+",";

            }else
            {
                publicContactsUserIds=publicContactsUserIds+userIdList2.get(i);
            }

        }
        Map<String,String> returnMap=new HashMap<String,String>();
        returnMap.put("publicContactsUserIds",publicContactsUserIds);
        returnMap.put("ontherUserIds",ontherUserIds);

        return returnMap;
    }

    @Override
    public UserContact getContactById(String contractId) {
        return userDao.getContactById(contractId);
    }
    @Override
    public AppUserLoanInfo getUserLoanByOrderNo(String orderNo) {
        return userDao.getUserLoanByOrderNo(orderNo);
    }


    @Override
    public List<SysRefusalCycle> queryAllSysRefusalCycle() {
        return sysRefusalCycleDao.selectAll();
    }

    @Override
    public int updateSysRefusalCycle(SysRefusalCycle sysRefusalCycle) {
        return sysRefusalCycleDao.updateCycleDayCountById(sysRefusalCycle);
    }

    @Override
    public String getCompanyNameByOrderNum(String orderNum) {
        return userDao.queryCompanyNameByOrderNum(orderNum);
    }

    @Override
    public List<String> getFirstAndSecondContactPhoneByOrderNum(String orderNum) {
        return userDao.queryFirstAndSecondContactPhoneByOrderNum(orderNum);
    }

    @Override
    public String getUserWhatsappAccountByOrderNum(String orderNum) {
        return userDao.queryUserWhatsappAccountByOrderNum(orderNum);
    }

    @Override
    public List<Long> listGetUserIdsByPhones(List<String> phones) {
        return userDao.listQueryUserIdsByPhones(phones);
    }

    @Override
    public String getAadharrAddressByOrderNum(String orderNum) {
        return userDao.queryAadharrAddressByOrderNum(orderNum);
    }

    @Override
    public List<String> listGetUserRelatedPhonesByPhone(List<String> phoneList, String excludedPhone) {
        List<String> a = userDao.listQueryUserPhoneAsContactByPhone(phoneList, excludedPhone);
        List<String> b = userDao.listQueryUserPhoneAsContactByPhone(phoneList, excludedPhone);
        a.addAll(b);
        return a;
    }
}
