package com.cloud.collection.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.collection.constant.*;
import com.cloud.collection.dao.*;
import com.cloud.collection.model.*;
import com.cloud.collection.model.req.CollectionDetailsAuditReq;
import com.cloud.collection.service.CollectionService;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.StringUtils;
import com.cloud.collection.model.CollectionRecord;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.common.LoanStatusEnum;
import com.cloud.model.user.LoginSysUser;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.service.feign.risk.RiskClient;
import com.cloud.service.feign.user.UserClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 催收模块服务实现类
 *
 * @author zhujingtao
 */
@Slf4j
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired(required = false)
    private CollectionDao collectionDao;

    @Autowired(required = false)
    private UserClient userClient;

    @Autowired(required = false)
    private FinanceRepayDao repayDao;

    @Autowired
    private RiskClient riskClient;

    @Autowired(required = false)
    private ReportCollReductionDao reportCollReductionDao;

    @Autowired(required = false)
    private CollectionDetailsAuditDao collectionDetailsAuditDao;

    @Autowired(required = false)
    private SysUserPermissionDao sysUserPermissionDao;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收记录集合
     * @author zhujingtao
     */
    @Override
    public PageInfo<CollectionRecordVo> queryCollectionList(Map<String, Object> parameter) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");
        parameter=this.processCollectionParam(parameter);
        PageHelper.startPage(page, pageSize);
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryCollectionList(parameter);
        //查询对应的列表集合
        PageInfo<CollectionRecordVo> pageInfo = new PageInfo<>(collectionRecordList);
        return pageInfo;
    }

    @Override
    public List<CollectionRecordVo> queryCollectionReport(Map<String, Object> parameter) {
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryCollectionList(parameter);
        return collectionRecordList;
    }

    /**
     * 处理参数 传参
     *
     * @param param
     * @return
     */
    private  Map<String, Object> processCollectionParam(Map<String, Object> param) {
        String state=MapUtils.getString(param, "state");
        String distinct=MapUtils.getString(param, "distinct");
        //处理地址 用户ID
        if (StringUtils.isNotBlank(state)||StringUtils.isNotBlank(distinct)){
        List<Long> addressUserIdList=    userClient.getUserIdByAddressItem(state,distinct);
        if (CollectionUtils.isNotEmpty(addressUserIdList) ){
            param.put("addressUserIdList",addressUserIdList);
        }
        }

        //传入电话 进行 查询 并将传参 加入
        String phone = MapUtils.getString(param, "mobile");
        List<Long> collectionIdList = new ArrayList<>();
        if (StringUtils.isBlank(phone)) {
            phone = MapUtils.getString(param, "phone");
        }
        if (StringUtils.isNotBlank(phone)) {
            List<String> orderNoList = riskClient.queryOrderNoByPhone(phone);
            List<Long> collectionIds = collectionDao.findCollectionIdByComment(phone);
            if (CollectionUtils.isNotEmpty(collectionIds)) {
                param.put("collectionIdPhoneList",collectionIdList);
            }
            if (CollectionUtils.isNotEmpty(orderNoList)){
                param.put("orderNoPhoneList", orderNoList);
            }
        }
        //获取关键字检索项
        String context = MapUtils.getString(param, "context");
        if (StringUtils.isNotBlank(context)) {
            List<Long> collectionIds = collectionDao.findCollectionIdByComment(context);
            if (CollectionUtils.isNotEmpty(collectionIds)) {
                collectionIdList.addAll(collectionIds);
            }
        }
        //传入 催收ID
        if(CollectionUtils.isNotEmpty(collectionIdList)){
            param.put("collectionIdContextList",collectionIdList);
        }
        return param;

    }

    /**
     * 查找我的催收列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    @Override
    public PageInfo<CollectionRecordVo> queryMyCollectionList(Map<String, Object> parameter) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");
        Long collectionUserId = collectionDao.findIdByName((String) parameter.get("name"));
        parameter.put("collectionUserId", collectionUserId);
        parameter=this.processCollectionParam(parameter);
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryMyCollectionList(parameter);
        PageInfo<CollectionRecordVo> pageInfo = new PageInfo<>(collectionRecordList);
        return pageInfo;
    }

    @Override
    public BigDecimal sumMyCollectionAllAmount(String name) {

        Long collectionUserId = collectionDao.findIdByName(name);
        if (collectionUserId != null && collectionUserId > 0) {
            Map<String, Object> parameter = new HashMap<>(1);
            parameter.put("collectionUserId", collectionUserId);
            return collectionDao.sumMyCollectionAllAmount(parameter);
        } else {
            return BigDecimal.valueOf(0);
        }


    }

    /**
     * 查找资产处置列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    @Override
    public PageInfo<CollectionRecordVo> queryEndPropertyList(Map<String, Object> parameter) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");
        parameter=this.processCollectionParam(parameter);
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryEndPropertyList(parameter);
        PageInfo<CollectionRecordVo> pageInfo = new PageInfo<>(collectionRecordList);
        return pageInfo;
    }

    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    @Override
    public PageInfo<CollectionRecordVo> queryInterestReductionList(Map<String, Object> parameter) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");
        parameter=this.processCollectionParam(parameter);
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryInterestReductionList(parameter);
        collectionRecordList.stream().forEach(collectionRecordModel -> collectionRecordModel.setDueStart(DateUtil.getDate(collectionRecordModel.getDueStart(), -1)));
        PageInfo<CollectionRecordVo> pageInfo = new PageInfo<>(collectionRecordList);
        return pageInfo;
    }

    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    @Override
    public PageInfo<CollectionRecordVo> queryAssginList(Map<String, Object> parameter) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");
        parameter=this.processCollectionParam(parameter);
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<CollectionRecordVo> collectionRecordList = collectionDao.queryAssginList(parameter);
        PageInfo<CollectionRecordVo> pageInfo = new PageInfo<>(collectionRecordList);
        return pageInfo;
    }

    /**
     * 插入罚息申请
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数
     * @author zhujingtao
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertInterestReduction(Map<String, Object> parameter) {
        Integer count = collectionDao.insertInterestReduction(parameter);

        collectionDao.updateCollectionStation(parameter.get("collectionId").toString(),1);
        return count;
    }

    /**
     * 根据罚息Id 查找 反馈记录列表
     *
     * @param collectionId 催收ID
     * @return 反馈记录列表
     * @author zhujingtao
     */
    @Override
    public List<CollectionAccruedRecordModel> queryAccruedListByCollectionId(String collectionId,String  tagId) {

        List<CollectionAccruedRecordModel> collectionAccruedRecordModelList = collectionDao.queryAccruedListByCollectionId(collectionId);


        //非空判定
        if (CollectionUtils.isEmpty(collectionAccruedRecordModelList)) {
            return collectionAccruedRecordModelList;
        }
        if (StringUtils.isNotBlank(tagId)){
            collectionAccruedRecordModelList=  collectionAccruedRecordModelList.stream().filter(collectionAccruedRecordModel ->
                    collectionAccruedRecordModel.getTagIds().contains(tagId)).collect(Collectors.toList());
        }

        for (CollectionAccruedRecordModel collectionAccruedRecordModel : collectionAccruedRecordModelList) {

            if (StringUtils.isNotBlank(collectionAccruedRecordModel.getTagIds())) {
                List<CollectionTag> CollectionTags = collectionDao.queryAllTagsByIds(collectionAccruedRecordModel.getTagIds());
                if (CollectionUtils.isNotEmpty(CollectionTags)) {
                    collectionAccruedRecordModel.setTagList(CollectionTags);
                }

            }

        }


        return collectionAccruedRecordModelList;
    }

    /**
     * 插入催收反馈 信息
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数  失败则返回 0
     * @author zhujingtao
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAccrued(Map<String, Object> parameter) {
        int num = 0;
        int updateNum = 0;

        //插入成功条目数
        parameter.put("collectionId", Long.parseLong((String) parameter.get("collectionId")));
        num = collectionDao.insertAccrued(parameter);
        String tagIds = MapUtils.getString(parameter, "tagIds");
        if (tagIds != null && !tagIds.trim().equals("")) {
            String[] ids = tagIds.split(",");
            parameter.put("tagId", ids[0]);
            List<CollectionTag> collectionTags = collectionDao.queryAllTags(parameter);
            if (CollectionUtils.isNotEmpty(collectionTags)) {
                collectionDao.updateFollowUpStatusByCollectionId(collectionTags.get(0).getTagName(), (Long) parameter.get("collectionId"),new Date());
            }
        }
        //判定是否插入成功  若是成功 则跟新主表信息
        if (num > 0) {
            updateNum = collectionDao.updateCollectionRecordForAccruedCount((Long) parameter.get("collectionId"));
        }
        //若是条目数相等 则返回 相关条目数
        if (num == updateNum) {
            return num;
        }
        //修改条目数不相等 返回 0
        return 0;
    }

    /**
     * 修改审批记录
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数  失败则返回 0
     * @author zhujingtao
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult updateInterestReduction(Map<String, Object> parameter) {
        if (MapUtils.getString(parameter, "ids") == null || (MapUtils.getString(parameter, "ids").equals(""))) {
            return JsonResult.errorMsg("少于一条记录");
        }
        if (MapUtils.getString(parameter, "applyStatus") == null || (MapUtils.getString(parameter, "applyStatus").equals(""))) {
            return JsonResult.errorMsg("未传拒绝或通过标识");
        }
        //判定是否 是通过拒绝标识
        String applyStatus = "applyStatus";
        if (Integer.parseInt((String) parameter.get(applyStatus)) != CollectionApplyStatus.REFUSE.num
                && Integer.parseInt((String) parameter.get(applyStatus)) != CollectionApplyStatus.PASS.num) {
            return JsonResult.errorMsg("不是通过或拒绝标识");
        }
        Date now = new Date();
        parameter.put("auditTime", now);
        if (Objects.equals(CollectionApplyStatus.PASS.num, Integer.parseInt((String) parameter.get(applyStatus)))) {
            parameter.put("expireTime", DateUtil.getDate(now, 1));
        } else {
            parameter.put("expireTime", null);
        }
        int num = collectionDao.updateInterestReduction(parameter);
        parameter.put("collectionId", parameter.get("ids"));
        //判定是否修改成功  若是成功 则跟新主表信息
        if (num > 0) {
            collectionDao.updateApplyStationForCollectionRecord(parameter);
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("审批失败");
        }
    }

    /**
     * 查找减息申请表
     *
     * @param collectionId 催收订单ID
     * @return
     * @author zhujingtao
     */
    @Override
    public List<CollectionInterestReductionModel> queryInterestReduction(String collectionId) {
        return collectionDao.queryInterestReduction(collectionId,null);
    }


    /**
     * 插入催收指派信息
     *
     * @param parameter 前端传入集合
     * @return 插入条目数  0 为失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult insertAssginCollection(Map<String, Object> parameter) {
        //封装 返回控制参数
        if (StringUtils.isBlank(MapUtils.getString(parameter, "collectionIds"))) {
            return JsonResult.errorMsg("无选择指派催收单");
        }
        if (StringUtils.isBlank(MapUtils.getString(parameter, "collectionUserId") ) ) {
            return JsonResult.errorMsg("无指派催收人");
        }
        //将前端传入的催收ID 传入
        String collectionIds = MapUtils.getString(parameter,"collectionIds");
        Long managerId;
        //获取分配人ID
        if (StringUtils.isBlank(
                MapUtils.getString(parameter,"managerName"))){
            parameter.put("managerName", "admin");
        }
        managerId = collectionDao.
                findIdByName(MapUtils.getString(parameter,"managerName"));
        List<CollectionRecord> collectionRecords = collectionDao
                .queryAllCollectionRecordByIds(collectionIds);
        List<CollectionAssginRecordModel> assginRecordModels = new ArrayList<>();

        Long collectionUserId = Long.parseLong(MapUtils.getString(parameter,"collectionUserId"));
        //获取催收人员名称
        String collectionName;
        collectionName = collectionDao.findNameById(collectionUserId);
        if (StringUtils.isBlank(collectionName)) {
            return JsonResult.errorMsg("对应的ID未有对应的人员名称");
        }

        if (CollectionUtils.isNotEmpty(collectionRecords)) {
            //分组转换
            Map<Integer, List<CollectionRecord>> groupMap = collectionRecords.stream()
                    .collect(Collectors.groupingBy(CollectionRecord::getAppointCaseType));
            //s1  //遍历 返回参数 是否为空值
            Set<Map.Entry<Integer, List<CollectionRecord>>> keySet = groupMap.entrySet();
            //遍历map集合存储 对应的list
            for (Map.Entry<Integer, List<CollectionRecord>> key : keySet) {
                if (CollectionUtils.isNotEmpty(key.getValue())) {
                    for (CollectionRecord collectionRecord : key.getValue()) {
                        assginRecordModels.add(
                                CollectionAssginRecordModel.builder().collectionId(collectionRecord.getId())
                                        .collectionUserId(collectionUserId)
                                        .managerId(managerId).appointCaseType(key.getKey())
                                        .collectionName(collectionName).build());
                    }

                }
            }
        }
        int num = collectionDao.insertAssginCollection(assginRecordModels);
        if (num > 0) {
            collectionDao.updateCollectionStation(collectionIds, 2);
            List<CollectionInterestReductionModel> collectionInterestReductionModels = collectionDao.queryInterestReduction(collectionIds,CollectionApplyStatus.APPROVALPENDING.num);
            if (CollectionUtils.isNotEmpty(collectionInterestReductionModels)) {
                parameter.put("ids", collectionIds);
                parameter.put("applyStatus", CollectionApplyStatus.ABANDON.num);
                collectionDao.updateInterestReduction(parameter);
            }
        } else {
            return JsonResult.errorMsg("insert Collection record  less then one ");
        }

        //催收审核详情查看相关记录失效
        List<CollectionDetailsAuditModel> detailsAuditModelList = collectionDetailsAuditDao.queryCollDetailAuditByCollIds(collectionIds);
        List<CollectionDetailsAuditModel> resultAuditModelList = getUnValidAuditModelList(assginRecordModels, detailsAuditModelList);
        log.info("getUnValidAuditModelList return:{}", JSON.toJSONString(resultAuditModelList));
        if (CollectionUtils.isNotEmpty(resultAuditModelList)) {
            collectionDetailsAuditDao.updateBatch(resultAuditModelList);
        }
        return JsonResult.ok();
    }

    /**
     * 获取信息详情
     *
     * @param collectionId 催收ID
     * @return
     * @author zhujingtao
     */
    @Override
    public JsonResult getDetails(String collectionId) {
        //定义返回参数
        Map<String, Object> dataMap = new HashMap<>(16);
        Map<String, Object> loanBaseInfo;
        Map<String, Object> userInfo;
        Map<String, Object> collectionAmountInfo;
        Map<String, Object> imgInfo;
        Map<String, Object> conformInfo;
        //后台用户信息
        Map<String, Object> backUserInfo = null;
        String firstContact;
        String secondContact;
        String userId;
        try {

            loanBaseInfo = collectionDao.queryLoanBaseInfo(collectionId);
            if (loanBaseInfo.get("userAadhaarNo") == null) {
                loanBaseInfo.put("userAadhaarNo", "");
            }

            String orderNo = collectionDao.getOrderNoById(Integer.valueOf(collectionId));
            //判定老数据 迁移难则 代码层控制展示

            userInfo = collectionDao.queryUserInfo(orderNo);
            userId = MapUtils.getString(userInfo, "userId");
            firstContact = collectionDao.queryUserContactRepairedByUserIdAndContactType(userId, 1);
            secondContact = collectionDao.queryUserContactRepairedByUserIdAndContactType(userId, 2);
            if (StringUtils.isBlank(firstContact)) {
                firstContact = collectionDao.queryContactNameByContactId(MapUtils.getString(userInfo, "firstContactId"));
            }
            if (StringUtils.isBlank(secondContact)) {
                secondContact = collectionDao.queryContactNameByContactId(MapUtils.getString(userInfo, "secondContactId"));
            }

            userInfo.put("firstContact", firstContact);
            userInfo.put("secondContact", secondContact);
            collectionAmountInfo = collectionDao.queryCollectionAmount(collectionId);
            imgInfo = collectionDao.getImgInfo(orderNo);
            conformInfo = collectionDao.getConformFile(orderNo);


            //添加认证信息的 接口图片
            if (null != conformInfo) {
                for (Map.Entry<String, Object> entry : conformInfo.entrySet()) {
                    if (!StringUtils.isBlank((String) entry.getValue()) && entry.getKey().equals("workCard")) {
                        String[] workCards = ((String) entry.getValue()).split(",");
                        String workeCard = "";
                        for (int i = 0; i < workCards.length; i++) {
                            if (i == 0) {
                                workeCard = aliOssUtil.getUrl(workCards[0]);
                            } else {
                                workeCard = workeCard + "," + aliOssUtil.getUrl(workCards[i]);
                            }

                            entry.setValue(workeCard);
                        }
                    } else if (!StringUtils.isBlank((String) entry.getValue()) && entry.getKey().equals("bankStatementUrl")) {
                        String[] bankStatementUrls = ((String) entry.getValue()).split(",");
                        String bankStatementUrl = "";
                        for (int i = 0; i < bankStatementUrls.length; i++) {
                            if (i == 0) {
                                bankStatementUrl = aliOssUtil.getUrl(bankStatementUrls[0]);
                            } else {
                                bankStatementUrl = bankStatementUrl + "," + aliOssUtil.getUrl(bankStatementUrls[i]);
                            }
                            entry.setValue(bankStatementUrl);
                        }
                    }
                }
            }
            //添加 图片信息 的接口图片
            if (null != imgInfo) {
                for (Map.Entry<String, Object> entry : imgInfo.entrySet()) {
                    if (!StringUtils.isBlank((String) entry.getValue())) {
                        String signedUrl = aliOssUtil.getUrl((String) entry.getValue());
                        entry.setValue(signedUrl);
                    }
                }
            }

            //后台用户信息
            backUserInfo = getBackUserInfo(collectionId);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorException(e.getMessage());
        }

        //增加空记录判定
        if (loanBaseInfo == null || loanBaseInfo.isEmpty()) {
            dataMap.put("loanBaseInfo", "");
        } else {
            dataMap.put("loanBaseInfo", loanBaseInfo);
        }
        if (userInfo == null || userInfo.isEmpty()) {
            dataMap.put("userInfo", "");
        } else {
            dataMap.put("userInfo", userInfo);
        }
        if (collectionAmountInfo == null || collectionAmountInfo.isEmpty()) {
            dataMap.put("collectionAmountInfo", "");
        } else {
            dataMap.put("collectionAmountInfo", collectionAmountInfo);
        }
        if (imgInfo == null || imgInfo.isEmpty()) {
            dataMap.put("imgInfo", "");
        } else {
            dataMap.put("imgInfo", imgInfo);
        }
        if (conformInfo == null || conformInfo.isEmpty()) {
            dataMap.put("conformInfo", "");
        } else {
            dataMap.put("conformInfo", conformInfo);
        }

        dataMap.put("backUserInfo", backUserInfo);

        return JsonResult.ok(dataMap);
    }

    private Map<String, Object> getBackUserInfo(String collectionId) {
        Map<String, Object> backUserInfo = new HashMap<>(2);
        Integer backRoleType = getBackRoleType();
        Integer checkDetailAduitStatus = getCheckDetailAduitStatus(collectionId);

        backUserInfo.put("backRoleType", backRoleType);
        backUserInfo.put("checkDetailAduitStatus", checkDetailAduitStatus);

        return backUserInfo;
    }

    private Integer getBackRoleType() {
        Long userId = null;
        LoginSysUser loginSysUser = AppUserUtil.getLoginSysUser();
        if (null != loginSysUser) {
            userId = loginSysUser.getId();
        }
        Set<String> sysPermissions = sysUserPermissionDao.findUserPermissionsByUserId(userId)
                .stream().map(i -> i.getPermission()).collect(Collectors.toSet());
        Integer backRoleType = BackRoleTypeEnum.ORDINARY.getType();

        for (String permission : sysPermissions) {
            if (Objects.equals("permission:all", permission) || Objects.equals("order:ergency:manage", permission)) {
                backRoleType = BackRoleTypeEnum.MANAGER.getType();
                break;
            }
            if (Objects.equals("audit:primary", permission)) {
                backRoleType = BackRoleTypeEnum.PRIMARY.getType();
                break;
            }
            if (Objects.equals("audit:high", permission)) {
                backRoleType = BackRoleTypeEnum.FINAL.getType();
                break;
            }
        }

        return backRoleType;
    }

    private Integer getCheckDetailAduitStatus(String collectionId) {
        Integer checkDetailAduitStatus = CheckDetailAduitStatusEnum.UN_APPLY.getStatus();

        Example example = new Example(CollectionDetailsAuditModel.class);
        example.createCriteria().andEqualTo("collectionId", Integer.valueOf(collectionId))
                .andEqualTo("isDeleted", CheckDetailIsDeletedEnum.UN_DELETED.getCode());
        example.setOrderByClause("id desc limit 1");

        CollectionDetailsAuditModel collectionDetailsAuditModel = collectionDetailsAuditDao.selectOneByExample(example);
        if (null != collectionDetailsAuditModel) {
            checkDetailAduitStatus = collectionDetailsAuditModel.getAuditStatus();
        }

        return checkDetailAduitStatus;
    }


    /**
     * 处置订单列表
     *
     * @param ids 订单ID集合
     * @return
     * @author zhujingtao
     */
    @Override
    public JsonResult updateHandleStatus(String ids) {
        //限定是否为空传入
        String empty = "";
        if (ids == null || ids.equals(empty)) {
            return JsonResult.errorMsg("请选择处置的订单");
        }
        int num;
        try {
            num = collectionDao.updateHandleStatus(ids);
            if (num < 1) {
                return JsonResult.errorMsg("处置成功订单小于一条");
            } else {
                String loanNums = "";
                List<CollectionRecord> collectionRecords = collectionDao.queryAllCollectionRecordByIds(ids);
                if (CollectionUtils.isNotEmpty(collectionRecords)) {
                    for (int i = 0; i < collectionRecords.size(); i++) {
                        if (i == 0) {
                            loanNums = loanNums + collectionRecords.get(i).getLoanNum();
                        } else {
                            loanNums = loanNums + "," + collectionRecords.get(i).getLoanNum();
                        }
                    }
                    collectionDao.updateUserLoanStatusByLoanNums(loanNums, LoanStatusEnum.HANDLED.getCode());

                }
            }
        } catch (Exception e) {
            return JsonResult.errorException(e.getMessage());
        }
        return JsonResult.ok();
    }

    /**
     * 获取 除了拒绝状态的 罚息申请单
     *
     * @param collectionId
     * @return 数量
     */
    @Override
    public Integer queryNotRefuseInterestReductionCount(String collectionId) {
        return collectionDao.queryNotRefuseInterestReductionCount(collectionId);
    }

    @Override
    public List<CollectionAssginRecordModel> queryAssginRecordModelByCollectionId(String collectionId) {
        return collectionDao.queryAssginRecordModelByCollectionId(collectionId);
    }


    /**
     * 获取审批人员
     *
     * @return
     * @author zhujingtao
     */
    @Override
    public JsonResult queryCollectionMan() {
        try {
            List<Map<String, Object>> data = collectionDao.queryCollectionMan();
            return JsonResult.ok(data);
        } catch (Exception e) {

            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    @Override
    public String getOrderNoById(Integer collectionId) {
        return collectionDao.getOrderNoById(collectionId);
    }

    @Override
    public List<CollectionTag> queryAllTags() {
        return collectionDao.queryAllTags(null);
    }

    @Override
    public PageInfo<ReportCollReductionModel> queryCollReductionReport(Integer page, Integer limit, Integer collectorId, String appointCaseDate) {
        PageHelper.startPage(page, limit);
        List<ReportCollReductionModel> reportCollReductionModelList = reportCollReductionDao.queryCollReductionModelList(collectorId, appointCaseDate);
        return new PageInfo<>(reportCollReductionModelList);

    }

    @Override
    public PageInfo<ReportCollReductionModel> queryReductionByCollectorId(Integer page, Integer limit, Integer collectorId, String appointCaseDate) {
        PageHelper.startPage(page, limit);
        ReportCollReductionModel query = new ReportCollReductionModel();

        try {
            if (null != collectorId && collectorId.intValue() > 0) {
                query.setCollectorId(collectorId);
            }
            if (StringUtils.isNotBlank(appointCaseDate)) {
                query.setAppointCaseDate(DATE_FORMATTER.parse(appointCaseDate));
            }
            query.setStatus(ReportCollReductionEnum.ENABLE.getStatus());

            List<ReportCollReductionModel> reportCollReductionModelList = reportCollReductionDao.select(query);
            return new PageInfo<>(reportCollReductionModelList);
        } catch (Exception e) {
            log.warn("查询罚息减免列表异常", e);
        }

        return new PageInfo<>();
    }

    @Override
    public PageInfo<CollectionRecordVo> queryCollDetailsAuditList(Map<String,Object> auditReq) {
        this.processCollectionParam(auditReq);
        PageHelper.startPage(MapUtils.getInteger(auditReq,"page"), MapUtils.getInteger(auditReq,"limit") );
        List<CollectionRecordVo> collectionDetailAuditModelList = collectionDetailsAuditDao.queryCollDetailsAuditList(auditReq);
        return new PageInfo<>(collectionDetailAuditModelList);
    }

    @Override
    public Integer insertCollDetailsAudit(Map<String, Long> paramsMap) {
        if (null == paramsMap) {
            log.info("paramMap null");
            return null;
        }

        Integer applyCheckUserId = paramsMap.get("applyCheckUserId").intValue();
        Long collectionId = paramsMap.get("collectionId");
        if (null == collectionId || null == applyCheckUserId) {
            log.info("param null, collectionId:{}, applyCheckUserId:{}", collectionId, applyCheckUserId);
            return null;
        }

        CollectionDetailsAuditModel model = new CollectionDetailsAuditModel();
        model.setCollectionId(collectionId);
        model.setApplyCheckUserId(applyCheckUserId);
        model.setApplyDate(new Date());

        return collectionDetailsAuditDao.insertSelective(model);
    }

    @Override
    public Integer updateCollDetailsAudit(String detailAuditIds, Integer auditStatus, Integer auditorId) {
        if (StringUtils.isBlank(detailAuditIds)) {
            log.info("no update record, auditorId:{}", auditorId);
            return null;
        }
        if (null == auditStatus || null == auditorId) {
            log.info("param null, auditStatus:{}, auditorId:{}", auditStatus, auditorId);
            return null;
        }

        return collectionDetailsAuditDao.updateCollDetailsAudit(detailAuditIds, auditStatus, auditorId);
    }

    @Override
    public boolean hasCollDetailsAuditPermission(long sysUserId, String orderNo) {
        return collectionDetailsAuditDao.existCollDetailsAudit(sysUserId, orderNo) > 0;
    }

    /**
     * 获取失效的订单查看审核记录
     *
     * @param assginRecordModels
     * @param detailsAuditModelList
     * @return
     */
    private List<CollectionDetailsAuditModel> getUnValidAuditModelList(List<CollectionAssginRecordModel> assginRecordModels, List<CollectionDetailsAuditModel> detailsAuditModelList) {
        List<CollectionDetailsAuditModel> resultAuditModelList = new ArrayList<>();
        if (CollectionUtils.isEmpty(assginRecordModels) || CollectionUtils.isEmpty(detailsAuditModelList)) {
            log.info("no collDetailAudit record update");
            return resultAuditModelList;
        }
        Map<Long, CollectionAssginRecordModel> assginRecordModelMap = assginRecordModels.stream().collect(Collectors.toMap(model -> model.getCollectionId(), model -> model));
        resultAuditModelList = detailsAuditModelList.stream().filter(model -> isChangedCollUser(model, assginRecordModelMap)).collect(Collectors.toList());
        resultAuditModelList.stream().forEach(model -> model.setIsDeleted(CheckDetailIsDeletedEnum.DELETED.getCode()));
        return resultAuditModelList;
    }

    private boolean isChangedCollUser(CollectionDetailsAuditModel model, Map<Long, CollectionAssginRecordModel> assginRecordModelMap) {
        if (null == assginRecordModelMap || assginRecordModelMap.isEmpty()) {
            return false;
        }

        Long collectionId = model.getCollectionId();
        CollectionAssginRecordModel assginRecordModel = assginRecordModelMap.get(collectionId);
        if (null == assginRecordModel) {
            return false;
        }

        if (Objects.equals(model.getApplyCheckUserId(), assginRecordModel.getCollectionUserId())) {
            return false;
        }

        return true;
    }

    @Override
    public List<CollectionOrderStatusMode> queryOrderStatusList(String mobile) {

        List<CollectionOrderStatusMode> collectionOrderStatusModes = new ArrayList<>();
        //电话未传  返回空列表
        if (StringUtils.isBlank(mobile)) {
            return collectionOrderStatusModes;
        }
        //查询逾期表 并设置状态
        //其中 判定逻辑  结清 为 dueEnd 不为空
        Map<String, Object> param = new HashMap<>();
        //判定 催收调用   可以获取到展期字段
        param.put("isCollection", 100);
        param.put("phone", mobile);
        List<FinanceDueOrderModel> financeDueOrderModels = repayDao.queryLateOrderList(param);
        financeDueOrderModels.stream().forEach(financeDueOrderModel -> {
            collectionOrderStatusModes.add(CollectionOrderStatusMode.builder().browAmount(financeDueOrderModel.getLoanAmount())
                    .name(financeDueOrderModel.getName())
                    .orderNo(financeDueOrderModel.getOrderNo().replaceAll("MN", "").replaceAll("PD", ""))
                    .mobile(financeDueOrderModel.getMobile())
                    .loanPeriod(financeDueOrderModel.getLoanPeriod())
                    //订单状态 判定展期  结清  未结清
                    .orderStatus(
                            financeDueOrderModel.getDueEnd() == null ?
                                    "unpaid" : financeDueOrderModel.getIsExtension() == 1 ? "extension" : "paid"
                    )
                    .build());
            for (CollectionOrderStatusMode collectionOrderStatusMode : collectionOrderStatusModes) {
                if (collectionOrderStatusMode.getOrderStatus().equals("extension")) {
                    FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(collectionOrderStatusMode.getOrderNo());
                    if (financeRepayModel.getActualAmount() != null && financeRepayModel.getActualAmount().doubleValue() != 0.0) {
                        collectionOrderStatusMode.setOrderStatus("paid");
                    }
                }
            }


        });
        return collectionOrderStatusModes;
    }

    @Override
    public CollectionInterestReductionModel getOneCollectionInterestReductionByParams(Map<String, Object> params) {

        return collectionDao.getOneCollectionInterestReductionByParams(params);
    }

    @Override
    public CollectionRecord getCollectionByDueId(Integer id) {
        return collectionDao.getCollectionByDueId(id);
    }

    @Override
    public List<CollectionTag> queryAllTagsByIds(String ids) {
        List<CollectionTag> collectionTagList = collectionDao.queryAllTagsByIds(ids);
        if (CollectionUtils.isEmpty(collectionTagList)) {
            return Collections.emptyList();
        }

        return collectionTagList;
    }

    @Override
    public List<CollectionAccruedRecordModel> queryAccruedRecordByDueId(Integer dueId) {
        List<CollectionAccruedRecordModel> accruedRecordModelList = collectionDao.queryAccruedRecordByDueId(dueId);
        if (CollectionUtils.isEmpty(accruedRecordModelList)) {
            return Collections.emptyList();
        }

        return accruedRecordModelList;
    }
}
