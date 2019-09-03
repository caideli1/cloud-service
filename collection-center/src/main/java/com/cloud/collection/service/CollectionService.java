package com.cloud.collection.service;

import com.cloud.collection.model.*;
import com.cloud.collection.model.req.CollectionDetailsAuditReq;
import com.cloud.common.dto.JsonResult;
import com.cloud.collection.model.CollectionRecord;
import com.cloud.model.collection.CollectionRecordVo;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 催收模块服务接口
 *
 * @author zhujingtao
 */
public interface CollectionService {
    /**
     * 查询催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收记录集合
     * @author zhujingtao
     */
    PageInfo<CollectionRecordVo> queryCollectionList(Map<String, Object> parameter);
    /**
     * 查询催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收记录集合
     * @author zhujingtao
     */
    List<CollectionRecordVo> queryCollectionReport(Map<String, Object> parameter);
    /**
     * 显示我的催收的所有的总金额 催收订单的未还总额=所有本金+所有逾期；涉及减免通过的金额，不减。
     * @param name
     * @return
     */
    BigDecimal sumMyCollectionAllAmount(String name);

    /**
     * 查找我的催收列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    PageInfo<CollectionRecordVo> queryMyCollectionList(Map<String, Object> parameter);

    /**
     * 查找资产处置列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    PageInfo<CollectionRecordVo> queryEndPropertyList(Map<String, Object> parameter);

    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    PageInfo<CollectionRecordVo> queryInterestReductionList(Map<String, Object> parameter);

    /**
     * 查找主管分配催收
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    PageInfo<CollectionRecordVo> queryAssginList(Map<String, Object> parameter);

    /**
     * 插入罚息申请
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数
     * @author zhujingtao
     */
    Integer insertInterestReduction(Map<String, Object> parameter);

    /**
     * 根据罚息Id 查找 反馈记录列表
     *
     * @param collectionId 催收ID
     * @return 反馈记录列表
     * @author zhujingtao
     */
    List<CollectionAccruedRecordModel> queryAccruedListByCollectionId(String collectionId,String tagId);

    /**
     * 插入催收反馈 信息
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数  失败则返回 0
     * @author zhujingtao
     */
    Integer insertAccrued(Map<String, Object> parameter);

    /**
     * 修改审批记录
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    JsonResult updateInterestReduction(Map<String, Object> parameter);

    /**
     * 查找减免记录列表 信息
     *
     * @param collectionId 合同ID
     * @return 减免记录列表
     * @author zhujingtao
     */
    List<CollectionInterestReductionModel> queryInterestReduction(String collectionId);


    /**
     * 插入指派信息
     *
     * @param parameter 前端传入参数
     * @return 插入成功 条目数
     * @author zhujingtao
     */
    JsonResult insertAssginCollection(Map<String, Object> parameter);


    /**
     * 获取信息详情
     *
     * @param collectionId 催收ID
     * @return
     * @author zhujingtao
     */
    JsonResult getDetails(String collectionId);

    /**
     * 处置订单列表
     *
     * @param ids 订单ID集合
     * @return
     * @author zhujingtao
     */
    JsonResult updateHandleStatus(String ids);

    /**
     * 获取 除了拒绝状态的 罚息申请单
     *
     * @param collectionId
     * @return 数量
     */
    Integer queryNotRefuseInterestReductionCount(String collectionId);


    /**
     * 获取审批人员
     *
     * @return
     * @author zhujingtao
     */

    JsonResult queryCollectionMan();

    /**
     * 通过ID 获取订单号
     *
     * @param collectionId
     * @return
     */
    String getOrderNoById(Integer collectionId);

    /**
     * 查询罚息减免统计报表
     *
     * @param page
     * @param limit
     * @param collectorId
     * @param appointCaseDate
     * @return
     */
    PageInfo<ReportCollReductionModel> queryCollReductionReport(Integer page, Integer limit, Integer collectorId, String appointCaseDate);

    /**
     * 查询具体催收员的减免罚息统计
     *
     * @param page
     * @param limit
     * @param collectorId
     * @param appointCaseDate
     * @return
     */
    PageInfo<ReportCollReductionModel> queryReductionByCollectorId(Integer page, Integer limit, Integer collectorId, String appointCaseDate);

    List<CollectionTag> queryAllTags();


    /**
     * 主管审核-申请订单查看列表
     *
     * @param collectionDetailsAuditReq
     * @return
     */
    PageInfo<CollectionRecordVo>  queryCollDetailsAuditList(Map<String,Object> collectionDetailsAuditReq);

    /**
     * 主管审核-插入申请订单详情
     *
     * @param paramsMap
     * @return
     */
    Integer insertCollDetailsAudit(Map<String, Long> paramsMap);

    /**
     * 通过催收ID  查找 分配记录
     *
     * @param collectionId
     * @return
     */
    List<CollectionAssginRecordModel> queryAssginRecordModelByCollectionId(String collectionId);

    /**
     * 主管审核-更新申请订单详情查看
     *
     * @param detailAuditIds
     * @param auditStatus
     * @return
     */
    Integer updateCollDetailsAudit(String detailAuditIds, Integer auditStatus, Integer auditorId);

    boolean hasCollDetailsAuditPermission(long sysUserId, String orderNo);

    /**
     * 通过 电话号码  查询 订单状态列表
     * @param mobile
     * @return
     */
    List<CollectionOrderStatusMode> queryOrderStatusList(String  mobile);


    /**
     * 根据逾期id查询催收记录
     * @param id
     * @return
     */
    CollectionRecord getCollectionByDueId(Integer id);

    /**
     * 查询催收标签
     * @param ids
     * @return
     */
    List<CollectionTag>  queryAllTagsByIds(String ids);

    /**
     * 根据逾期id查询催收反馈信息
     * @param dueId
     * @return
     */
    List<CollectionAccruedRecordModel> queryAccruedRecordByDueId(Integer dueId);

    /**
     * @Author : caideli
     * @Email : 1595252552@qq.com
     * @Date : 19:29  2019/8/8
     * Description :根据条件获取罚息减免记录
     */
    CollectionInterestReductionModel getOneCollectionInterestReductionByParams(Map<String,Object> params);

}
