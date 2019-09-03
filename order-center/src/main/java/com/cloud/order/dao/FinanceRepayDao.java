package com.cloud.order.dao;

import com.cloud.order.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * FinanceRepayDao interface
 *
 * @author zhujingtao
 * @date 2019/2/26
 */
@Mapper
public interface FinanceRepayDao {

    @Select("select  sum(loan_amount)  from   finance_due_order  where  due_end=0")
    BigDecimal queryNotFinanceDueAmount();



    /**
     * 查找 催收员  记录
     * @param params
     * @return
     */
	List<ReportCollectionManModel> queryReportCollectionManModel(Map<String, Object> params);

    /**
     * 查找已还款记录
     *
     * @param params 前端入参
     * @return 还款记录列表
     * @author zhujingtao
     */
    List<FinanceRepayModel> queryRepaymentPaidList(Map<String, Object> params);


    /**
     * 更新还款状态
     *
     * @param financeRepayModel
     * @return 修改的条目数
     * @author ZiAng
     */
    int updateRepaymentByLoanNum(FinanceRepayModel financeRepayModel);

    /**
     * 查找未还款记录
     *
     * @param params 前端入参
     * @return 还款记录列表
     * @author zhujingtao
     */
    List<FinanceRepayModel> queryRepaymentUnpaidList(Map<String, Object> params);


    /**
     * 查找还款失败
     *
     * @param params 前端入参
     * @return 还款记录列表
     * @author zhujingtao
     */
    List<FinanceRepayModel> queryRepaymentFailedList(Map<String, Object> params);

    /**
     * 查找逾期记录
     *
     * @param params 前端入参
     * @return 还款记录列表
     * @author zhujingtao
     */
    List<FinanceDueOrderModel> queryLateOrderList(Map<String, Object> params);

    /**
     * 新增还款快照记录
     *
     * @param financeRepaySnapshotModel
     * @return
     */
    int saveRepaySnapshot(FinanceRepaySnapshotModel financeRepaySnapshotModel);

    /**
     * 查询还款记录
     *
     * @param loanNumber 借据编号
     * @return
     */
    FinanceRepayModel queryRepayByLoanNum(String loanNumber);

    /**
     * 修改账户信息
     *
     * @param params 传入修改参数
     * @return 修改记录数
     */
    int updateAccountManager(Map<String, Object> params);


    /**
     * 通过支付顺序 查询是否有相关有效的启用 数据
     *
     * @param payOrder
     * @return
     */
    int selectAccountByPayOrder(@Param("payOrder") Integer payOrder);

    /**
     * 插入账户管理
     *
     * @param params 前端用户值
     * @return 插入数量
     */
    int insertAccountManager(Map<String, Object> params);

    /**
     * 查询所有的名称集合
     *
     * @return 名称集合
     */
    List<String> selectAllAccountName();

    /**
     * 查询所有的 账户列表
     *
     * @param params 名称
     * @return 账户列表
     */
    List<FinanceAccountManagerModel> selectAccountList(Map<String, Object> params);

    @Select("select  *  from finance_extension  where order_no=#{orderNo}  and  id in (select  max(id) from finance_extension group by  order_no)")
    FinanceExtensionModel queryFinanceExtensionByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 展期 数据 列表查询
     *
     * @param params
     * @return
     */
    List<FinanceRepayModel> queryFinanceExtensionList(Map<String, Object> params);


    FinanceAccountManagerModel getLoanAccountInfo();

    FinanceRepayModel queryRepaymentByOrderNo(@Param("orderNo") String orderNo);

    @Select("select  finance_due_order.order_no,\n" +
            "finance_due_order.customer_no,\n" +
            "finance_due_order.`name`,\n" +
            "finance_due_order.mobile,\n" +
            "finance_due_order.loan_amount,\n" +
            "finance_due_order.due_days,\n" +
            "finance_due_order.due_amount,\n" +
            "finance_due_order.due_start,\n" +
            "   case  when  finance_due_order.due_end=0 then null  else finance_due_order.due_end  end  dueEnd,\n" +
            "finance_due_order.id,\n" +
            "finance_due_order.is_extension,\n" +
            "finance_due_order.rate,\n" +
            "finance_due_order.max_due_days,\n" +
            "finance_due_order.create_time,\n" +
            "finance_due_order.finished_type   from  finance_due_order  where  order_no =#{orderNo}")
    List<FinanceDueOrderModel> queryLateOrderByOrderNO(@Param("orderNo") String orderNo);

    /**
     * 根据条件获取一条还款记录 created by caideli 2019/8/9
     *
     * @param params
     * @return
     */
    FinanceRepayModel getOneFinanceRepayByParams(Map<String, Object> params);
}
