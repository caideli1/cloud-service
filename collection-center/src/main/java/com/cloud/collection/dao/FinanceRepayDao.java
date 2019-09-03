package com.cloud.collection.dao;

import com.cloud.collection.model.FinanceDueOrderModel;
import com.cloud.collection.model.FinancePayLogModel;
import com.cloud.collection.model.FinanceRepayModel;
import com.cloud.collection.model.LaterDetailsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    /**
     * 查找逾期记录
     *
     * @param params 前端入参
     * @return 还款记录列表
     * @author zhujingtao
     */
    List<FinanceDueOrderModel> queryLateOrderList(Map<String, Object> params);

    FinanceRepayModel queryRepaymentByOrderNo(@Param("orderNo") String orderNo);

    @Select("select  finance_due_order.order_no,\n" +
            "finance_due_order.customer_no,\n" +
            "finance_due_order.`name`,\n" +
            "finance_due_order.mobile,\n" +
            "finance_due_order.loan_amount,\n" +
            "finance_due_order.due_days,\n" +
            "finance_due_order.due_amount,\n" +
            "finance_due_order.due_start,\n" +
            "   case  when  finance_due_order.due_end=0 then null  else finance_due_order.due_end  end,\n" +
            "finance_due_order.id,\n" +
            "finance_due_order.is_extension,\n" +
            "finance_due_order.rate,\n" +
            "finance_due_order.max_due_days,\n" +
            "finance_due_order.create_time,\n" +
            "finance_due_order.finished_type   from  finance_due_order  where  id =#{dueId}")
    FinanceDueOrderModel queryLateOrderByDueId(@Param("dueId") String dueId);

    @Select("   select   distinct due.order_no ,due.is_extension isExtension   ," +
            "due.due_start dueStart, case  when  due.due_end=0 then null else due.due_end end  dueEnd ," +
            " extension.extension_start  extensionStartDate ,extension.extension_end extensionEndDate" +
            " ,due.loan_amount  loanAmount   from  " +
            " finance_due_order     due  " +
            " left  join     " +
            "finance_extension      extension  " +
            "on   due.order_no = extension.order_no   and   due.due_end=extension.create_at " +
            "where due.order_no=#{orderNo}   and   due.id  in   (select  max(id)  from finance_due_order  group by  order_no ) ")
    LaterDetailsModel queryLaterDetailsByOrderNo(@Param("orderNo") String orderNo);

    @Select("select  *  from    finance_pay_log  where pay_status=1  and " +
            "order_no=#{orderNo} " +
            " and  DATE_FORMAT(create_time,'%Y-%m-%d')= #{payDate}" +
            "and loan_type=#{loanType}")
    List<FinancePayLogModel> queryPayLogByOrderNoAndPayDateAndLoanType(@Param("orderNo") String orderNo, @Param("payDate") String payDate, @Param("loanType") Integer loanType);

    /**
     * 根据条件获取一条还款记录 created by caideli 2019/8/9
     * @param params
     * @return
     */
    FinanceRepayModel getOneFinanceRepayByParams(Map<String,Object> params);
}
