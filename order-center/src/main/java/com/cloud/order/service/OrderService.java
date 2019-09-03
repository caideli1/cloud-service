package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.common.Page;
import com.cloud.model.product.OrderHistoryDto;
import com.cloud.order.model.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderProcessModel service
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
public interface OrderService {

    /**
     * 查询订单
     *
     * @param params
     * @return
     * @author
     */
    Page<OrderModel> findOrder(Map<String, Object> params);

    /**
     * 保存 风控  模块
     * @param orderNo 订单编号
     * @param node  节点数
     * @param  rejectReason   拒绝原因
     * @return
     */
    boolean saveCheckResult(String orderNo, Integer node, String rejectReason);



    /**
     * 查询所有订单
     *
     * @param params 前端传入参数
     * @return 分页信息
     * @author zhujingtao
     */
    PageInfo<OrderTableModel> queryAllOrderList(Map<String, Object> params);

    /**
     * 放款池列表
     * @param applyNum
     * @param userPhone
     * @param term
     * @param loanAmount
     * @param startFinalAuditTime
     * @param endFinalAuditTime
     * @return
     */
    PageInfo<OrderTableModel> queryLendingPoolOrderList(String applyNum, String userPhone, Integer term,
                                                        BigDecimal loanAmount, String startFinalAuditTime, String endFinalAuditTime,
                                                        Integer page,Integer pageSize);

    /**
     * 领取任务
     *
     * @param params 前端传入参数
     * @return JsonResult
     * @author baijianye
     */
    JsonResult takeOrder(Map<String, Object> params);
 IndexDueIngModel   queryIndexDueIng(DateParam dateParam);

    /**
     * 分配任务
     *
     * @param params
     * @return JsonResult
     * @author baijieye
     */
    JsonResult assignOrder(Map<String, Object> params);

    /**
     * 获取审批人员
     *
     * @return
     * @author baijieye
     */
    JsonResult getAuditor(int type);

    /**
     * 初审通过
     *
     * @param params
     * @author baijieye
     */
    void firstJudgment(Map<String, Object> params) throws Exception;
    IndexFirstParam queryIndexFirst();


   IndexCustomerNewedModel  queryIndexCustomerNewed(DateParam dateParam);

   IndexDueTypeModel  queryIndexDueType(DateParam dateParam);
    /**
     * 初审拒绝
     *
     * @param params
     * @author baijieye
     */
    void firstRefuse(Map<String, Object> params) throws Exception;

    /**
     * 终审通过
     *
     * @param params
     * @author baijieye
     */
    Map<String, Object> finalJudgment(Map<String, Object> params) throws Exception;

    /**
     * 存入放款池 caideli 2019/08/01
     * @param orderId
     * @param reason
     * @param isWarning
     * @param tagIds
     * @param auditorName
     * @return
     * @throws Exception
     */
    void lendingPool(String orderId, String reason,Integer isWarning,String tagIds,String auditorName) throws Exception;

    /**
     * 放款池放款 caideli 2019/08/01
     * @param orderId
     * @param auditorName
     * @return
     * @throws Exception
     */
    void lendingPoolLoan(String orderId,String auditorName) throws Exception;


    List<String> getOrderNoByUserId(Long userId);

    /**
     * 终审拒绝
     *
     * @param params
     * @author baijieye
     */
    void finalRefuse(Map<String, Object> params) throws Exception;

    /**
     * 获取标签
     *
     * @param - type
     * @return
     * @author baijieye
     */
    JsonResult getTags();



    /**
     * 获取订单审批记录
     *
     * @param orderId
     * @return
     * @author baijieye
     */
    JsonResult getOrderCheckLog(String orderId, String name);

    /**
     * 获取 同用户的信息
     * @param orderNo
     * @return
     */
    JsonResult  getSameUserInfoByOrderNo(String orderNo);

    OrderTableModel queryUserOrderById(Integer id);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     * @author baijieye
     */
    JsonResult getOrderDetail(String orderId);

    /**
     * 获取待终审订单 列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    PageInfo<OrderTableModel> queryEndCheckOrders(Map<String, Object> params);


    IndexOrderPassModel queryIndexOrderPass(DateParam dateParam);

    /**
     * 获取待初审订单 列表
     *
     * @param params 前端传值
     * @return 订单列表
     */
    PageInfo<OrderTableModel> queryFirstCheckOrders(Map<String, Object> params);

    /**
     * 获取待领取订单列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    PageInfo<OrderTableModel> queryUnclaimedOrder(Map<String, Object> params);

    /**
     * 获取我的订单列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    PageInfo<OrderTableModel> queryMyOrders(Map<String, Object> params);

    /**
     * 获取 待分配订单数量
     *
     * @return 待分配订单
     * @author zhujingtao
     */
    int getAllocatedOrderCount();

    /**
     * 获取平台审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    JsonResult getSystemHistoryReport(Map<String, Object> params);

    JsonResult getSystemNowReport();

    /**
     * 获取平台审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    JsonResult getSystemCount(Map<String, Object> params);

    /**
     * 获取个人审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    JsonResult getPersonHistoryReport(Map<String, Object> params);

    /**
     * 获取 今日 个人列表
     * @param params
     * @return
     */
    JsonResult getPersonNowReport(Map<String, Object> params);
    /**
     * 获取个人审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    JsonResult getPersonCount(Map<String, Object> params);

    /**
     * 获取个人审批统计详情报表
     *
     * @param params
     * @return
     */
    JsonResult getPersonCountDetail(Map<String, Object> params);


    AppUserOrderInfo getUserOrderByOrderNum(String orderNum);


    /**
     * 同一公司名称申请人数
     */
    int getOrderCountInSameCompanyName(String orderNo);


    /**
     * 同一单位电话匹配申请人数
     */
    int getOrderCountInSameCompanyPhone(String orderNum);

    AppUserOrderInfo getUserOrderByUserId(String userId);



    /**
     * 借据pdf
     */
    String getLoanPdf(String loanNo);


    /**
     * 合同pdf
     */
    String getLoanOrderContractPdf(String loanNo);

    /**
     * 展期合同pdf
     */
    String getExtensionContractPdf(long extensionId);

    /**
     * 通过OrderID 查找OrderNUm
     */
    String findOrderNumByOrderId(String orderId);

    OrderTableModel getFirstOrderCreatedByUserIds(List<Long> userIds);

    /**
     * ordernum to id
     * */
    int queryOrderNumToId(String orderNum);

    List<OrderHistoryDto>  getSamePhoneCompanyByOrderNo(Map<String, Object> params);

    List<OrderHistoryDto>  getSameCompanyNameByOrderNo(Map<String, Object> params);
    List<OrderHistoryDto> getSameDeviceInfoByOrderNo(Map<String, Object> params);

    List<OrderHistoryDto> getSameDeviceInfoByRelationMan(Map<String,Object> params);
    List<OrderTableModel>  getOrderByOrderNos( String orderNos);

    boolean finalJudgeForLoanOrder(Integer userId);

    List<OrderTableModel> getOrderListByUserId(Long userId);

    boolean updateMechineCheckToOrderByOrderNum(@RequestParam String orderNo, @RequestParam Integer  checkStatus);

    List<OrderOntherRefuse>  queryAllOrderOntherRefuse(String orderNo);

    int countGetUserOrderByUserId(Long userId);

    int countGetNoRepayLoanByUserId(Long userId);

    List<Integer> listGetLoanStatusBySameAadhaarAddress(String address);

    List<String> listGetDueOrderNumBySameAadhaarAddress(String address);

    List<Integer> listGetLoanStatusByOrderNumList(List<String> orderNumList);

    List<String> listGetRepayedDueOrderNumInOrderNumList(List<String> orderNumList);

    List<String> listGetCurrentDueOrderNumByPhoneList(List<String> phoneList);

    String getUserLastRePayedLoanOrderNum(Long userId);

    List<Integer> listGetDueDaysListByOrderNum(String orderNum);

    List<String> listGetApplyPhonesInPhones(List<String> phoneList);
}
