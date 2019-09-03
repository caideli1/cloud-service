package com.cloud.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.cibil.KudosParamEntity;
import com.cloud.model.cibil.LoanCibilResponseEntity;
import com.cloud.user.model.AccountNonsummarySegmentFields;
import com.cloud.user.model.Address;
import com.cloud.user.model.Applicant;
import com.cloud.user.model.CibilInfo;
import com.cloud.user.model.ContextData;
import com.cloud.user.model.Creditreport;
import com.cloud.user.model.EmailContactSegment;
import com.cloud.user.model.End;
import com.cloud.user.model.Enquiry;
import com.cloud.user.model.Header;
import com.cloud.user.model.Idsegment;
import com.cloud.user.model.Inquiry;
import com.cloud.user.model.Namesegment;
import com.cloud.user.model.Scoresegment;
import com.cloud.user.model.Telephonesegment;
import com.cloud.user.model.UserAddress;

@Mapper
public interface KudosDao {
	int insertKudosCibil(LoanCibilResponseEntity le);
	LoanCibilResponseEntity queryCibilScore(@Param("orderId") String orderId);
//	List<LoanCibilResponseEntity> queryCibilCount(@Param("orderId") String orderId);
	
	List<CibilInfo> queryCibilInfoCount(@Param("orderNo") String orderNo);
	KudosParamEntity queryKudosParams(@Param("name") String name,@Param("code") String code,@Param("value") String value);
	
	Inquiry getInquiryInfo(@Param("orderNo") String orderNo);
	List<UserAddress> getUserAddressInfo(@Param("orderNo") String orderNo);
	
	List<Creditreport> queryCreditreportCount(@Param("orderNo") String orderNo);
	//cibil  拆分
//	int insertContextData(ContextData contextData);
	int insertApplicant(Applicant applicant);
	int insertHeader(Header header);
	int insertNameSegment(Namesegment namesegment);
	int insertIdsegment(Idsegment idsegment);
	int insertTelephonesegment(Telephonesegment telephonesegment);
	int insertEmailContactSegment(EmailContactSegment emailContactSegment);
	int inesrtScoresegment(Scoresegment scoresegment);
	int insertAddress(Address address);
	int insertAccountNonsummarySegmentFields(AccountNonsummarySegmentFields accountNonsummarySegmentFields);
	int insertEnquiry(Enquiry enquiry);
	int insertEnd(End end);
}
