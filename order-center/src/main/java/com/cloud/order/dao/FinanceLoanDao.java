package com.cloud.order.dao;

import java.util.List;
import java.util.Map;

import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.order.model.FinanceDueOrderModel;
import com.cloud.order.model.FinanceExtensionModel;
import org.apache.ibatis.annotations.*;


import java.util.Date;

import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.user.UserLoan;
import com.cloud.order.model.FinanceLoanReportRecordModel;
import com.cloud.order.model.FinanceRepayModel;


/**
 * 放款记录dao
 * @author bjy
 * @date 2019/2/26 0026  17:03
 */
@Mapper
public interface FinanceLoanDao {

	FinanceDueOrderModel getFinanceDueOrderByOrderNo(@Param("orderNo")String orderNo,@Param("dueIsEnd")String dueIsEnd);

	FinancePayLogModel getFinancePayLogModelByOrderNo(Map<String, Object> params);
	
	@Select("select * from finance_pay_log where serial_number = #{serialNumber}")
    FinancePayLogModel getFinancePayLogModelBySerialNumber(@Param("serialNumber")String serialNumber);
	
	@Select("select * from finance_loan where order_no = #{orderNo}  and pay_status = #{payStatus}")
	FinanceLoanModel getFinanceLoanModelByOrderNo(@Param("orderNo")String orderNo,@Param("payStatus")int payStatus);
	
	@Select("select * from user_loan where loan_number = #{orderNo}")
	UserLoan getUserLoanByOrderNo(String orderNo);

	//map 传参   mobile手机号     iousStatus 借据状态
    List<UserLoan> getUncloseIous(Map<String,Object> map);

    List<UserLoan> queryOfflineRepayOrderList(Map<String,Object> parameter);

	//交易记录的amount需要修改是因为  还款的时候 每次进入还款页面需要把最新的交易金额更新下
	@Update("update finance_pay_log set amount=#{amount},origin_amount=#{originAmount},pay_status=#{payStatus},create_time=#{createTime}," +
            "repay_date=#{repayDate},order_status=#{orderStatus},failure_reason=#{failureReason},loan_channel=#{loanChannel}, serial_number = #{serialNumber} where id = #{id}")
	int updateFinancePayLogModel(FinancePayLogModel financePayLogModel);

	//放款记录更新银行卡是因为 放款重提的时候 会获取最新的银行卡
	@Update("update finance_loan set bank_no=#{bankNo},ifsc_code=#{ifscCode},pay_status=#{payStatus},order_status=#{orderStatus}," +
            "create_time=#{createTime} ,failure_reason=#{failureReason},comment=#{comment},modify_bankcard_count=#{modifyBankcardCount}," +
            "notice_modify_bankcard_date=#{noticeModifyBankcardDate},loan_channel=#{loanChannel} where id = #{id}")
	int updateFinanceLoanModel(FinanceLoanModel financeLoanModel);

	int saveLoanInfo(AppUserLoanInfo req);

    /**
     * 查询放款记录
     *
     * @param parameter 放款记录查询的参数
     * @return 放款记录
     */
    List<FinanceLoanModel> getFinanceLoan(Map<String, Object> parameter);

    /**
     * 查询放款失败记录
     *
     * @param parameter 放款失败记录查询的参数
     * @return 放款失败的记录
     */
    List<FinanceLoanModel> getFinanceLoanFailure(Map<String, Object> parameter);

    /**
     * 根据主键查询失败的放款记录
     * @param id
     * @return
     */
    FinanceLoanModel getFinanceLoanById(@Param("id") String id);

    /**
     * 保存交易记录
     *
     * @param payLogModel
     * @return 交易记录的id
     */
    long savePayLog(FinancePayLogModel payLogModel);

    /**
     * 保存放款记录快照
     *
     * @param loan
     */
    void saveLoanSnapshot(FinanceLoanModel loan);

    /**
     * 更新放款记录的订单状态
     *
     * @param id
     * @param num
     */
    void updateLoanStatus(@Param("id") String id, @Param("orderStatus") int num);

    /**
     * 交易记录失败更改交易记录
     *
     * @param payLogId
     * @param localizedMessage
     */
    void updatePayLogFail(@Param("id") long payLogId, @Param("failureReason") String localizedMessage);

    /**
     * 交易记录失败更改放款记录
     *
     * @param id
     * @param localizedMessage
     */
    void updateLoanFail(@Param("id") String id, @Param("failureReason") String localizedMessage);

    /**
     * 查询交易记录关联的放款记录的id
     *
     * @param id
     * @return
     */
    String getFinanceLoanIdByPayLogId(@Param("id") String id);

    /**
     * 交易成功更新放款记录
     *
     * @param loanId
     */
    void updateLoanSuccess(@Param("id") String loanId);

    /**
     * 交易成功更新交易记录信息
     *
     * @param payLogId
     */
    void updatePayLogSuccess(@Param("id") String payLogId);

    /**
     * 查询交易记录信息
     *
     * @param parameter
     * @return
     */
    List<Map<String, Object>> getPayLog(Map<String, Object> parameter);

    /**
     * 插入还款记录
     *
     * @param loanId 放款记录id
     */
    void insertFinanceRepayByLoanId(@Param("id") String loanId);


    /**
     * 查询需要插入的统计的日期
     *
     * @return
     */
    List<Map<String, Object>> getCountDate();

    /**
     * 添加或者替换掉统计记录
     *
     * @param list
     */
    void replaceFinanceReportRecord(List<Map<String, Object>> list);

    /**
     * 新建放款记录
     *
     * @param loanModel
     */
    void saveFinanceLoan(FinanceLoanModel loanModel);

    /**
     * 查询放款记录统计信息
     *
     * @param params
     * @return
     */
    List<FinanceLoanReportRecordModel> getFinanceLoanCount(Map<String, Object> params);

    /**
     * 交易记录处理中
     *
     * @param -orderNo
     * @param localizedMessage
     */
    void updatePayLogProcess(@Param("serialNumber") String serialNumber, @Param("failureReason") String localizedMessage);

    List<UserLoan> getUserLoadByCondition(@Param("userId") Long userId,
                                          @Param("userContactRelations") List<String> userContactRelations);

    List<UserLoan> getUserOverdueLoanByAadhaarAccount(@Param("aadhaarAccount") String aadhaarAccount);


    /**
     * 同一公司名称放款人数
     */
    int getLoanCountInSameCompanyName(@Param("userId") long userId);

    /**
     * 同一公司名称正在逾期人数
     */
    int getDueUserCountInSameCompanyName(@Param("userId") long userId);


    /**
     * 同一单位电话放款人数
     */
    int getLoanCountInSameCompanyPhone(@Param("userId") long userId);


    /**
     * 同一单位电话的申请人正在逾期人数
     */
    int getDueUserCountInSameCompanyPhone(@Param("orderNum") String orderNum);

    /**
     * 查询交易记录
     * @param params
     * @return
     */
    List<FinancePayLogModel> getFinancePayLogModelList(Map<String, Object> params);

    /**
     * 通过用户ID 和原因 查询放款记录
     * @param userId
     * @param reason
     * @return
     */
    List<Map<String,Object>> findFailFianceLoanByUserIdAndReason(@Param("userId")Long userId,
                                                                 @Param("reason") String reason ,
                                                                 @Param("reason2") String reason2
                                                                 );


    @Select("select   *  from   finance_pay_log   where  loan_type  in(1,2,7) and  pay_status=1  and  order_no=#{orderNo}")
    List<FinancePayLogModel> getLoanPayLogByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 修改 错误贷款列表信息
     * @param id
     * @param comment
     * @param bankCode
     * @return
     */
    int updateFailFianceLoanRemarkById(@Param("id")Integer id,
                                       @Param("bankCode") String bankCode,
                                       @Param("comment") String comment);


    List<String> querySerialNumByStatus(@Param("payStatus") Integer payStatus, @Param("startQueryDate") Date startQueryDate, @Param("endQueryDate") Date endQueryDate);

    List<String> querySerialNumByStatusAndloanType(@Param("payStatus") Integer payStatus, @Param("loanTypes") int[] loanTypes,
        @Param("startQueryDate") Date startQueryDate, @Param("endQueryDate") Date endQueryDate);

    /**
     * 获取最新最近的一条展期记录
     * created by caideli 2019/8/12
     * @param params
     * @return
     */
    FinanceExtensionModel getOneNewFinanceExtension(Map<String,Object> params);

    /**
     * 统计某用户的放款笔数
     * @param customerNo
     * @param payStatus
     * @return
     */
    Integer countFinanceLoanByUserId(Long customerNo, Integer payStatus);


    List<String> listQueryPayedPhonesInPhones(List<String> phoneList);
}
