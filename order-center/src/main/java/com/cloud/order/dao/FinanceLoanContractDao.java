package com.cloud.order.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.cloud.model.FinanceLoanContract;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FinanceLoanContractDao {
	
	
	@Insert("insert into finance_loan_contract(contract_no, loan_id,contract_url,contract_type,create_time,create_user,apply_no) "
			+ "values(#{contractNo},#{loanId}, #{contractUrl}, #{contractType}, #{createTime}, #{createUser}, #{applyNo})")
	int insert(FinanceLoanContract financeLoanContract);

	@Select("SELECT contract_url FROM finance_loan_contract WHERE contract_no = #{loanNo} AND contract_type = 'A'")
	String getFinanceLoanContractFinanceContract(@Param("loanNo") String loanNo);

	@Select("SELECT contract_url FROM finance_loan_contract WHERE contract_no = #{loanNo} AND contract_type = 'B'")
	String getFinanceLoanContractFinanceLoan(@Param("loanNo") String loanNo);

	@Select("SELECT contract_url FROM finance_loan_contract WHERE contract_type = CONCAT('C-', #{extensionId})")
	String getFinanceLoanContractExtension(@Param("extensionId") long extensionId);
}
