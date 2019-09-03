package com.cloud.order.dao;

import com.cloud.model.FinanceLoanContract;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.check.CheckLogModel;
import com.cloud.model.product.OrderHistoryDto;
import com.cloud.model.user.UserAddress;
import com.cloud.model.user.UserBankCard;
import com.cloud.order.model.*;
import com.cloud.order.model.kudos.BorrowerInfoApiEntity;
import com.cloud.order.model.kudos.LoanRequestApiEntity;
import com.cloud.order.model.kudos.PGOrderIdParamEntity;
import com.cloud.order.model.kudos.UploadDocumentApiEntity;
import com.cloud.order.model.resp.AppMyCurrentLoanInfoRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderDao interface
 *
 * @author zhujingtao
 * @date 2019/2/19
 */
@Mapper
public interface OrderDao {
    /**
     * 模板文件
     *
     * @param start  开始页码
     * @param length 结束页码
     * @return 返回数据集
     */
    List<OrderModel> findAllOrder(@Param("start") int start, @Param("length") int length);

    @Select("select  count(1)  from  user_order where DATE_FORMAT(create_time,'%Y-%m-%d')=DATE_FORMAT(#{date},'%Y-%m-%d')")
    Integer getOrderCountByDate(@Param("date") Date date);


    @Select(" select   order_num  from    user_order  where  user_id = #{userId}")
    List<String> getOrderNoByUserId(@Param("userId") Long userId);


    /**
     * 模板文件
     *
     * @param params 传参
     * @return 数量
     */
    int count(Map<String, Object> params);


    /**
     * 通过用户名称获取对应的用户ID
     *
     * @param username
     * @return
     * @author baijieye
     */
    int findIdByName(@Param("username") String username);

    /**
     * 通过id寻找用户名字
     *
     * @param auditorId
     * @return
     */
    String findNameById(@Param("id") int auditorId);


    /**
     * 查询审核人员
     *
     * @return
     * @author baijieye
     */
    List<Map<String, Object>> getAuditor(@Param("type") int type);

    /**
     * 新增一条审批记录
     *
     * @param params
     * @return
     * @author baijieye
     */
    int insertOrderCheckLog(Map<String, Object> params);

    /**
     * 更新订单状态通过订单id
     *
     * @param params
     * @author baijieye
     */
    int updateOrderStatusById(Map<String, Object> params);


    /**
     * 获取标签信息
     *
     * @param type
     * @return
     * @author baijieye
     */
    List<Map<String, Object>> getTags();


    /**
     * 获取订单审批记录
     *
     * @param orderId
     * @return
     * @author baijieye
     */
    List<OrderCheckLogModel> getOrderCheckLogByOrderId(@Param("orderId") String orderId);

    @Select(" select   descrption  from  check_tag    where  id  in (${ids})  ")
    List<String> getTagsByIds(@Param("ids") String ids);

    OrderTableModel queryUserOrderById(@Param("id") Integer id);

    /**
     * 获取订单流程信息
     *
     * @param orderId
     * @return OrderProcessModel 订单流程实体类
     */
    OrderProcessModel getOrderById(@Param("orderId") String orderId);

    /**
     * 通过用户ID 获取用户信息
     *
     * @param orderId 用户ID
     * @return 返回用户的信息
     * @author zhujingtao
     */
    UserInfoParam getUserInfo(@Param("orderId") String orderId);

    /**
     * 通过用户ID 获取银行 信息
     *
     * @param orderNo
     * @return 银行信息
     * @author zhujingtao
     */
    UserBankCard   getBankInfo(@Param("orderNo") String orderNo);

    /**
     * 通过用户ID 获取图片URL
     *
     * @param orderId 用户ID
     * @return 图片URL
     * @author zhujingtao
     */
    ImgInfo getImgInfoByOrderId(@Param("orderId") String orderId);

    /**
     * 通过用户Id 获取工作信息
     *
     * @param orderNo
     * @return 工作信息
     * @author zhujingtao
     */
    WorkAddressInfo getWorkInfoByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 通过用户Id 获取学生信息
     *
     * @param orderNo    用户ID
     * @param createTime 创建时间
     * @return 工作信息
     * @author zhujingtao
     */
   StudentInfo getStudentInfoByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 、
     * 查询所有的订单 列表
     *
     * @param params 前端传参
     * @return 订单列表
     * @author zhujingtao
     */
    List<OrderTableModel> queryAllOrderList(Map<String, Object> params);

    /**
     * 放款池列表
     * @param params
     * @return
     */
    List<OrderTableModel> queryLendingPoolOrderList(Map<String, Object> params);

    /**
     * 查询待终审订单 列表
     *
     * @param params 前端传参
     * @return 订单列表
     * @author zhujingtao
     */
    List<OrderTableModel> queryEndCheckOrders(Map<String, Object> params);

    /**
     * 查询待初审订单列表
     *
     * @param params 前端传参
     * @return 订单列表
     * @author zhujingtao
     */
    List<OrderTableModel> queryFirstCheckOrders(Map<String, Object> params);

    /**
     * 查询待处理订单列表
     *
     * @param params 前端传参
     * @return 订单列表
     * @author zhujingtao
     */
    List<OrderTableModel> queryUnclaimedOrder(Map<String, Object> params);

    /**
     * 查询我的订单列表
     *
     * @param params 前台传参
     * @return 订单列表
     * @author zhujingtao
     */
    List<OrderTableModel> queryMyOrders(Map<String, Object> params);

    /**
     * 获取待分配订单 数量
     *
     * @return 待分配订单数量
     */
    int getAllocatedOrderCount();


    /**
     * 获取平台审批统计报表
     *
     * @param params
     * @return
     */
    List<SystemCountModel> getSystemCount(Map<String, Object> params);

    /**
     * 获取个人审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    List<PersonCountModel> getPersonCount(Map<String, Object> params);

    @Select("select  *  from   user_order where order_num=#{orderNo}")
    OrderTableModel queryOrderTableByOrderNo(@Param("orderNo") String orderNo);

    @Select("select  *  from  user_order  where user_id=#{userId}    and   id  in  (select   max(id) from  user_order  GROUP BY  user_id  ) ")
    OrderTableModel queryLastOrderTableByUserId(@Param("userId")Long userId);
    /**
     * 获取个人统计详情
     *
     * @param params 传入值
     * @return 详情列表
     * @author baijieye
     */
    List<PersonCountDetailModel> getPersonCountDetail(Map<String, Object> params);

    /**
     * 批量插入分配记录
     *
     * @param list
     * @return 插入数量
     * @author baijieye
     */
    int insertOrderAssignLogUseList(List<Map<String, Object>> list);

    /**
     * 批量跟新分配记录
     *
     * @param list
     * @return 跟新数量
     * @author baijieye
     */
    void updateUserOrderUseList(List<Map<String, Object>> list);

    /**
     * 获取是否可以操作
     *
     * @param orderId name
     * @return 0 否 1是
     * @author baijieye
     */
    int getOperation(@Param("orderId") String orderId, @Param("name") String name);


    AppUserOrderInfo getUserOrderByOrderNum(@Param("orderNum") String orderNum);


    int getOrderCountInSameCompanyName(@Param("orderNo") String orderNo);


    int getOrderCountInSameCompanyPhone(@Param("orderNum") String orderNum);

    AppUserOrderInfo getUserOrderByUserId(@Param("orderNum") String orderNum);


    /**
     * 查询当前订单的状态
     *
     * @param orderId
     * @return
     */
    int findCheckStatus(String orderId);

    /**
     * 获取认证信息
     *
     * @param orderNo
     * @return
     */
    ConformFile getConformFile(@Param("orderNo") String orderNo);

    /**
     * 获取adda卡 等认证信息
     *
     * @param orderId 订单
     * @return
     */
    VoterAuthInfo  getVoterAuthInfoByOrderNo(@Param("orderNo") String orderNo);


    /**
     * kudos 1
     * ssp
     */
    LoanRequestEntity queryUserInformation(@Param("orderNo") String orderId);

    /**
     * kudos  cibil api
     * ssp
     */
    LoanCIBILEntity queryCibilInformation(@Param("orderNo") String orderNo);

    /**
     * kudos adstype
     * ssp
     */
    UserAddress queryUserAds(@Param("userId") String userId, @Param("adsType") String adsType);

    /**
     * kudos 接口2
     */
    BorrowerInfoApiEntity queryBorrowerInfo(@Param("orderNo") String orderNo);

    /**
     * 获取合同、借据
     */
    FinanceLoanContract queryContract(@Param("orderNo") String orderNo, @Param("contractType") String contractType);

    /**
     * 获取订单编号
     */
    OrderTableModel queryOrderNo(@Param("orderNo") String orderNo);

    /**
     * 通过订单Id 寻找订单编号
     */
    String findOrderNumByOrderId(@Param("orderId") String orderId);

    /**
     * 通过订单ID 寻找指派次数
     *
     * @param orderId 订单ID
     * @return 指派次数
     */
    int findAssignCountByOrderId(@Param("orderId") String orderId);

    OrderTableModel getFirstOrderCreatedByUserIds(@Param("userIds") List<Long> userIds);

    //ordernum to id
    int queryOrderNumToId(@Param("orderNum") String orderNum);

    /**
     * 跟据订单ID  跟新审核时间
     *
     * @param orderId
     * @return
     */
    Integer updateOrderAuditTimeByOrderId(@Param("orderId") String orderId);

    /**
     * @param orderId
     * @return
     */
    LoanRequestApiEntity queryLoanRequestInfo(@Param("orderNo") String orderNo);
    PGOrderIdParamEntity queryOrderIdParam(@Param("orderNo") String orderNo);

    UploadDocumentApiEntity queryUpdocumentApiInfo(@Param("orderNo") String orderNo);

    String queryUserIdByOrderNo(@Param("orderNo") String orderNo);

    @Select("  select sum(loan_amount) from report_order_details")
    BigDecimal getReportOrderDetailsLoanAmount();

    List<ReportOrderDetailsModel> getReportOrderDetails(Map<String, Object> params);
    List<ReportOrderDetailsModel> getReportOrderDetailsByDates(List<Map<String, Object>> params);
    List<Map<String, Object>> getReportOrderDate(Map<String, Object> params);
    /**
     * 通过日期 统计 展期 数量
     *
     * @param date
     * @return
     */
    int getExtensionCountByDate(@Param("date") Date date);

    List<FinanceExtensionReportParam> getExtensionCount();
    /**
     * 查询  审核列表 集合
     *
     * @param params 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<ReportOrderAuditModel> getReportOrderAudit(Map params);

    /**
     * 查询  审核列表 集合
     *
     * @param params 前端传入参数
     * @return
     * @author zhujingtao
     */
    List<ReportOrderAuditModel> getSystemHistoryReport(Map params);


    /**
     * 通过订单 查询  公司名称 和电话号码
     *
     * @param orderNo
     * @return
     * @author zhujingtao
     */

    Map<String, Object> getSameCompanyNameAndMobileByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 通过电话或公司名称查寻相同信息
     *
     * @param params
     * @return
     * @author zhujingtao
     */
    List<OrderHistoryDto> getSameCompanyOrderInfoByCompanyMobileOrName(Map<String, Object> params);


    /**
     * 通過訂單編號 查詢
     *
     * @param orderNos
     * @return
     */
    List<OrderHistoryDto> getSameCompanyOrderInfoByOrderNo(@Param("orderNos") String orderNos);

    /**
     * 通過訂單編號 查詢
     *
     * @param userIds
     * @return
     */
    List<OrderHistoryDto> getSameCompanyOrderInfoByUserIds(@Param("userIds") String userIds);

    OrderTableModel getOrderInfoByOrderNo(@Param("orderNo") String orderNo);


    @Select("select  * from  user_order where  order_num in ( ${orderNos})")
    List<OrderTableModel>getOrderByOrderNos(@Param("orderNos") String orderNos);
    /**
     * 查询 需要统计 得账户信息
     *
     * @param param
     * @return
     */
    List<ReportOrderAuditParam> queryNowOrderAudit(Map<String, Object> param);

    @Select("select  *  from  user_order where  " +
            "user_id in(  " +
            " select user_id  from  user_order   " +
            "where order_num =#{orderNo}) and  check_status !=1")
    List<OrderTableModel> getSamUserIdOrderInfoByOrderNo(@Param("orderNo") String orderNo);

    @Update("update  user_order  set    check_status =#{checkStatus}   where  order_num=#{orderNum} ")
    int  updateMechineCheckToOrderByOrderNum(@Param("orderNum") String orderNum,@Param("checkStatus") int checkStatus );

    @Update("update  user_order  set    is_stock =#{isStock}   where  id=#{id} ")
    int  updateIsStockById(@Param("id") String id,@Param("isStock") int isStock);

    AadhaarAuth getAadhaarAuthByOrderNo(@Param("orderNo")String orderNo);

    PanCardAuth   getPanCardAuthByOrderNo(@Param("orderNo") String orderNo);


    List<OrderTableModel> getOrderListByUserId(@Param("userId") Long userId);

    int insertMechineCheck(List<CheckLogModel> checkLogModels);

    AppMyCurrentLoanInfoRes appQueryCurrentOrderInfo(Long userId);

    AppUserOrderInfo getUserOrderByOrderId(@Param("orderId") String orderId);

    @Select("SELECT COUNT(*) FROM user_order WHERE id = #{userId}")
    int countQueryUserOrderByUserId(Long userId);

    @Select("SELECT count(*) FROM user_loan WHERE user_id = #{userId} AND loan_status in (0, 1, 4)")
    int countQueryNoRepayLoanByUserId(Long userId);

    @Select("SELECT ul.loan_status FROM user_loan ul JOIN user_info_history uih ON ul.loan_number=uih.order_no JOIN user_address ua ON ua.id=uih.home_address_id WHERE ua.address_detail=#{address}")
    List<Integer> listQueryLoanStatusBySameAadhaarAddress(String address);

    @Select("SELECT fdo.order_no FROM finance_due_order fdo JOIN user_info_history uih ON fdo.order_no=uih.order_no JOIN user_address ua ON ua.id=uih.home_address_id WHERE ua.address_detail=#{address}")
    List<String> listQueryDueOrderNumBySameAadhaarAddress(String address);

    List<Integer> listQueryLoanStatusByOrderNumList(List<String> orderNumList);

    List<String> listQueryPaiedDueOrderNumInOrderNumList(List<String> orderNumList);

    List<String> listQueryCurrentDueOrderNumByPhoneList(List<String> phoneList);

    @Select("SELECT loan_number FROM user_loan WHERE loan_status = 2 AND user_id = #{userId} ORDER BY create_time DESC LIMIT 1;")
    String queryUserLastRePayedLoanOrderNum(Long userId);

    @Select("SELECT due_days FROM finance_due_order WHERE order_no = #{orderNum}")
    List<Integer> listQueryDueDaysListByOrderNum(String orderNum);
    
    List<String> listQueryApplyPhonesInPhones(List<String> phoneList);
}
