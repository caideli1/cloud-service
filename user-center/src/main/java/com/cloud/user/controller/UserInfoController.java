package com.cloud.user.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppUserAddressInfo;

import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.service.feign.collection.CollectionClient;
import com.cloud.model.appUser.UserPanCardModel;
import com.cloud.user.model.UserContactRepairEntity;
import com.cloud.model.product.OrderPartRes;
import com.cloud.model.user.*;
import com.cloud.user.model.UserContactRepairReq;
import com.cloud.user.service.UserBankCardService;
import com.cloud.user.service.UserInfoService;
import com.cloud.user.service.UserPanCardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * @author yoga
 * @Description: UserInfoController
 */
@Slf4j
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPanCardService userPanCardService;

    @Autowired(required = false)
    private CollectionClient collectionClient;

    @Autowired(required = false)
    private UserBankCardService userBankCardService;

    /**
     * @Description: userInfo详细信息
     * @author yoga
     */
    @PreAuthorize("hasAuthority('user:info:detail') or hasAuthority('permission:all')")
    @GetMapping("/userInfo/detail")
    public JsonResult userInfoDetail(@RequestParam Long id) {
        UserInfo userInfo = userInfoService.getUserInfoById(id);
        return JsonResult.ok(userInfo, null);
    }

    /**
     * @Description: userInfo列表
     * @author yoga
     */
    @PreAuthorize("hasAuthority('user:info:list') or hasAuthority('permission:all')")
    @GetMapping("/userInfo/list")
    public JsonResult userInfoList(@RequestParam(required = false) String id,
                                   @RequestParam(required = false) String phone,
                                   @RequestParam(required = false) String aadhaar,
                                   @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime,
                                   @RequestParam(required = false) Integer status,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<UserInfo> userInfoList = userInfoService.getUserInfoList(id, phone, aadhaar, startTime, endTime, status);
        PageInfo pageInfo = new PageInfo<>(userInfoList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    /**
     * @Description: userLoan列表
     * @author yoga
     */
    @PreAuthorize("hasAuthority('loan:info:list') or hasAuthority('permission:all')")
    @GetMapping("/userLoan/list")
    public JsonResult userLoanList(@RequestParam(required = false) String loanNumber,
                                   @RequestParam(required = false) String userId,
                                   @RequestParam(required = false) String phone,
                                   @RequestParam(required = false) String startTime,
                                   @RequestParam(required = false) String endTime,
                                   @RequestParam(required = false) Integer handlerType,
                                   @RequestParam(required = false) Integer loanStatus,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<UserLoan> userLoanList = userInfoService.getUserLoanList(loanNumber, userId, phone, handlerType, loanStatus, startTime, endTime);
        PageInfo pageInfo = new PageInfo<>(userLoanList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    /**
     * @Description: userLoan借据详细信息
     * @author yoga
     */
    @PreAuthorize("hasAuthority('loan:info:detail') or hasAuthority('permission:all')")
    @GetMapping("/userLoan/detail")
    public JsonResult userLoanDetail(@RequestParam Integer id) {
        UserLoan userLoan = userInfoService.getUserLoanById(id);
        return JsonResult.ok(userLoan, null);
    }

    /**
     * @Author : caideli
     * @Email : 1595252552@qq.com
     * @Date : 19:17  2019/8/8
     * Description :获取减免项
     */
    @PreAuthorize("hasAuthority('loan:info:detail') or hasAuthority('permission:all')")
    @GetMapping("/userLoan/ReliefItems")
    public JsonResult ReliefItems(@RequestParam String orderNo) {
        return collectionClient.ReliefItems(orderNo);
    }

    /**
     * @Description: user地址信息
     * @author yoga
     */
    @GetMapping("/userAddress/list")
    public JsonResult userAddressList(@RequestParam() long userId,
                                      @RequestParam() int type,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<UserAddress> userAddressList = userInfoService.getUserAddressList(userId, type);
        PageInfo<UserAddress> pageInfo = new PageInfo<>(userAddressList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }


    @GetMapping("/users-anon/getBasicInfoById")
    public UserInfo getUserInfoByUserId(@RequestParam Long userId) {
        return userInfoService.getUserInfoById(userId);
    }

    @GetMapping("/users-anon/getUserAddressById")
    public List<UserAddress> getUserAddressList(@RequestParam Long userId, @RequestParam Integer type) {
        return userInfoService.getUserAddressList(userId, type);
    }
    @GetMapping("/users-anon/getUserIdByAddressItem")
    public List<Long> getUserIdByAddressItem(@RequestParam(required = false) String state, @RequestParam(required = false)  String district) {
        return userInfoService.getUserIdByAddressItem(state, district);
    }
    @GetMapping("/users-anon/getUserAccountByAadhaarAccount")
    public List<UserInfo> getUserAccountByAadhaarAccount(@RequestParam String aadhaarAccount) {
        return userInfoService.getUserAccountByAadhaarAccount(aadhaarAccount);
    }
    @GetMapping("/users-anon/getUserLoanByOrderNo")
    public AppUserLoanInfo getUserLoanByOrderNo(@RequestParam String orderNo){
        return   userInfoService.getUserLoanByOrderNo(orderNo);
    }

    @GetMapping("/users-anon/getUserAccountByPancardAccount")
    List<UserInfoExpand> getUserAccountByPancardAccount(@RequestParam String pancardAccount) {
        return userInfoService.getUserAccountByPancardAccount(pancardAccount);
    }

    /**
     * @Description: 同单位名称匹配不同单位电话个数
     */
    @GetMapping("/users-anon/companyPhoneCountInSameName")
    public int getCompanyPhoneCountWhenSameName(@RequestParam String orderNo) {
        return userInfoService.getCompanyPhoneCountWhenSameName(orderNo);
    }

    /**
     * @Description: 同单位电话匹配不同单位名称个数
     */
    @GetMapping("/users-anon/companyNameCountInSamePhone")
    public int getCompanyNameCountWhenSamePhone(@RequestParam String orderNo) {
        return userInfoService.getCompanyNameCountWhenSamePhone(orderNo);
    }

    /**
     * @Description: 亲属联系人号码被用作申请号码个数
     */
    @GetMapping("/users-anon/relativePhoneAsApplyPhoneCount")
    public int getRelativePhoneAsApplyPhoneCount(@RequestParam long userId) {
        return userInfoService.getRelativePhoneAsApplyPhoneCount(userId);
    }

    /**
     * @Description: 亲属联系人号码被用作联系人号码个数
     */
    @GetMapping("/users-anon/relativePhoneAsContactPhoneCount")
    public int getRelativePhoneAsContactPhoneCount(@RequestParam long userId) {
        return userInfoService.getRelativePhoneAsContactPhoneCount(userId);
    }

    @GetMapping("/users-anon/getUserAddressByOrderNum")
    public List<AppUserAddressInfo> getUserAddressList(@RequestParam String userId) {
        return userInfoService.getUserAddressListByOrderNum(userId);
    }

    /**
     * @Description: user授信信息
     * @author yoga
     */
    @GetMapping("/userCreditInfo")
    public JsonResult userCreditExtension(@RequestParam long userId) {
        Map<String, Object> result = userInfoService.getUserCreditExtension(userId);
        return JsonResult.ok(result);
    }

    /**
     * @Description: user逾期信息列表
     * @author yoga
     */
    @GetMapping("/userDueInfo/list")
    public JsonResult userDueInfoList(@RequestParam long userId,
                                      @RequestParam(required = false) String orderNo) {
        List<Map<String, Object>> result = userInfoService.getUserDueInfoList(userId, orderNo);
        return JsonResult.ok(result);
    }

    /**
     * @Description: user逾期信息
     * @author yoga
     */
    @GetMapping("/userDueInfoTotal")
    public JsonResult userDueInfoTotal(@RequestParam long userId) {
        Map<String, Object> result = userInfoService.getUserDueInfo(userId);
        return JsonResult.ok(result);
    }

    /**
     * @Description: 贷款信息详情 - 展期信息
     * @author yoga
     */
    @GetMapping("/userOrderExtensionInfo/list")
    public JsonResult userOrderExtensionInfoList(@RequestParam String orderNo) {
        List<Map<String, Object>> result = userInfoService.getUserOrderExtensionInfoList(orderNo);
        return JsonResult.ok(result);
    }


    /**
     * @Description: 金额成分
     * @author yoga
     */
    @GetMapping("/userLoan/amountPart")
    public JsonResult userLoanAmountComposition(@RequestParam String orderNo) {
        Map<String, Object> result = userInfoService.userLoanAmountComposition(orderNo);
        return JsonResult.ok(result);
    }

    /**
     * @Description: 应还金额明细
     * @author yoga
     */
    @GetMapping("/userLoan/repayPart/detail")
    public JsonResult userLoanRepayComposition(@RequestParam String orderNo) {
        OrderPartRes result = userInfoService.userLoanRepayComposition(orderNo);
        return JsonResult.ok(result);
    }

    /**
     * 用户联系人变更记录
     */
    @GetMapping("/userInfo/{userId}/contact/history")
    public JsonResult userContactRepairHistory(@PathVariable long userId, @RequestParam int contactType) {
        List<UserContactRepairEntity> contactList = userInfoService.getContactList(userId, contactType);
        return JsonResult.ok(contactList);
    }

    /**
     * 用户联系人修复
     */
    @PostMapping("/userInfo/contact/repair")
    public JsonResult userContactRepair(@RequestBody @Valid UserContactRepairReq repairReq, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("参数错误：- " + errors.toString());
        }
        userInfoService.repairUserContact(repairReq);
        return JsonResult.ok("修复成功");
    }

    /**
     *
     */
    @PostMapping("/users-anon/getUserIdAndRelation")
    public Map<String, String> getUserIdAndRelation(@RequestBody Map<String, Object> params) {
        return userInfoService.getUserIdAndRelation(params);

    }

    @GetMapping("/users-anon/getContactById")
    public UserContact getContactById(@RequestParam("contractId") String contractId) {
        return userInfoService.getContactById(contractId);
    }

    @GetMapping("/users-anon/getPancardByUserId")
    UserPanCardModel getPancardByUserId(@RequestParam Long userId){
        return userPanCardService.getUserPanCardByUserId(userId);
    }

    @ApiOperation("更新银行卡信息")
    @PostMapping("/users-anon/updateBankCardInfo")
    void updateBankCardInfo(@RequestParam String bankAccount, @RequestParam String ifsc, @RequestParam String verifyReturnName) {
        userBankCardService.updateBankCardInfo(bankAccount, ifsc, verifyReturnName);
    }
}
