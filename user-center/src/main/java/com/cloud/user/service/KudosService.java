package com.cloud.user.service;

import java.util.List;

import com.cloud.model.cibil.LoanCIBILEntity;
import com.cloud.model.cibil.LoanCibilResponseEntity;
import com.cloud.user.model.PartKudosAccountNonSummary;
import com.cloud.user.model.Account;
import com.cloud.user.model.AccountNonsummarySegmentFields;
import com.cloud.user.model.Address;
import com.cloud.user.model.Applicant;
import com.cloud.user.model.Applicationdata;
import com.cloud.user.model.CibilInfo;
import com.cloud.user.model.Cibilbureauresponse;
import com.cloud.user.model.Creditreport;
import com.cloud.user.model.EmailContactSegment;
import com.cloud.user.model.Employmentsegment;
import com.cloud.user.model.End;
import com.cloud.user.model.Enquiry;
import com.cloud.user.model.Header;
import com.cloud.user.model.Identifier;
import com.cloud.user.model.Idsegment;
import com.cloud.user.model.Inquiry;
import com.cloud.user.model.Namesegment;
import com.cloud.user.model.Scoresegment;
import com.cloud.user.model.Step;
import com.cloud.user.model.Telephone;
import com.cloud.user.model.Telephonesegment;
import com.cloud.user.model.UserAddress;

public interface KudosService {
	//保存cibil数据
	int insertKudosCibil(LoanCibilResponseEntity le);
	//查询cibil是否存在
//	List<LoanCibilResponseEntity> queryCibilCount(String orderId);
	List<CibilInfo> queryCibilInfoCount(String orderNo);
	List<Creditreport> queryCreditreportCount(String orderNo);
	public String kudosCibil(LoanCIBILEntity cibilEntity,String kudosType,String orderId,String urlValue);

	List<PartKudosAccountNonSummary> getRiskKudosAccountNonSummary(String orderNo);

	//cibil拆分  存储
//	int insertContextData(ContextData contextData);
	int insertApplicant(Applicant applicant,String orderNo);
	int insertCreditreport(Creditreport creditreport,String orderNo);
	int insertHeader(Header header,String orderNo);
	int insertNameSegment(Namesegment namesegment,String orderNo);
	int insertIdsegment(List<Idsegment> idsegment,String orderNo);
	int insertTelephonesegment(Telephonesegment telephonesegment,String orderNo);
	int insertEmailContactSegment(EmailContactSegment emailContactSegment,String orderNo);
	int inesrtScoresegment(Scoresegment scoresegment, String orderNo);
	int insertAddressList(List<Address> address,String orderNo);
	int insertAddress(Address address,String orderNo);
	int insertAccountNonsummarySegmentFields(AccountNonsummarySegmentFields accountNonsummarySegmentFields,String orderNo);
	int insertAccount(Account account,String orderNo);
	int insertEnquiry(List<Enquiry> enquiry,String orderNo);
	int insertEnd(End end,String orderNo);
	int insertApplicationdata(Applicationdata applicationdata, String orderNo);
	int insertStep(List<Step> step,String orderNo);
	int insertCibilbureauresponse(Cibilbureauresponse cibilbureauresponse,String orderNo);
	int insertTelephone(List<Telephone> telephone,String orderNo);
	int insertIdentifier(List<Identifier> identifierList,String orderNo);
	int insertCibilInfo(CibilInfo cibilInfo);
	int insertEmploymentsegment(Employmentsegment employmentsegment,String orderNo);
	//查询
	Inquiry getInquiryInfo(String orderNo);
	List<UserAddress> getUserAddressInfo(String orderNo);
	Scoresegment getScoresegmentInfo(String orderNo);
	Header getHeaderInfo(String orderNo);
	Namesegment getNameSegmentInfo(String orderNo);
	List<Idsegment> getIdsegmentInfo(String orderNo);
	List<Idsegment> getIdsegmentInfo(String orderNo, String idType);
	Telephonesegment getTelephoneSegmentInfo(String orderNo);
	List<EmailContactSegment> getEmailContactSegmentInfo(String orderNo);
	List<Address> getAddressInfo(String orderNo);
	List<Employmentsegment> getEmploymentSegmentInfo(String orderNo);
	AccountNonsummarySegmentFields getAccountNonsummarySegmentFieldsInfo(String orderNo);
	List<Enquiry> getEnquiryInfo(String orderNo);
	Creditreport getCreditreportInfo(String orderNo);
	//测试
	String testinsert(String orderNo);



}
