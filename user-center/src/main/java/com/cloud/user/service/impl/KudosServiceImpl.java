package com.cloud.user.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.user.model.PartKudosAccountNonSummary;
import com.cloud.user.utils.KudosModelUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cloud.model.cibil.LoanCIBILEntity;
import com.cloud.model.cibil.LoanCibilResponseEntity;
import com.cloud.user.dao.CibilAccountDao;
import com.cloud.user.dao.CibilAccountNonsummarySegmentFieldsDao;
import com.cloud.user.dao.CibilAddressDao;
import com.cloud.user.dao.CibilApplicantDao;
import com.cloud.user.dao.CibilApplicationdataDao;
import com.cloud.user.dao.CibilCibilbureauresponseDao;
import com.cloud.user.dao.CibilCreditreportDao;
import com.cloud.user.dao.CibilEmailContactSegmentDao;
import com.cloud.user.dao.CibilEmploymentsegmentDao;
import com.cloud.user.dao.CibilEndDao;
import com.cloud.user.dao.CibilEnquiryDao;
import com.cloud.user.dao.CibilHeaderDao;
import com.cloud.user.dao.CibilIdentifierDao;
import com.cloud.user.dao.CibilIdsegmentDao;
import com.cloud.user.dao.CibilInfoDao;
import com.cloud.user.dao.CibilNamesegmentDao;
import com.cloud.user.dao.CibilScoresegmentDao;
import com.cloud.user.dao.CibilStepDao;
import com.cloud.user.dao.CibilTelephoneDao;
import com.cloud.user.dao.CibilTelephonesegmentDao;
import com.cloud.user.dao.KudosDao;
import com.cloud.user.model.Account;
import com.cloud.user.model.AccountNonsummarySegmentFields;
import com.cloud.user.model.Address;
import com.cloud.user.model.Applicant;
import com.cloud.user.model.Applicants;
import com.cloud.user.model.Applicationdata;
import com.cloud.user.model.Bureauresponsexml;
import com.cloud.user.model.CibilInfo;
import com.cloud.user.model.Cibilbureauresponse;
import com.cloud.user.model.ContextData;
import com.cloud.user.model.Creditreport;
import com.cloud.user.model.DCResponse;
import com.cloud.user.model.Dscibilbureau;
import com.cloud.user.model.EmailContactSegment;
import com.cloud.user.model.Employmentsegment;
import com.cloud.user.model.End;
import com.cloud.user.model.Enquiry;
import com.cloud.user.model.Field;
import com.cloud.user.model.Header;
import com.cloud.user.model.Identifier;
import com.cloud.user.model.Idsegment;
import com.cloud.user.model.Inquiry;
import com.cloud.user.model.Namesegment;
import com.cloud.user.model.Response;
import com.cloud.user.model.Scoresegment;
import com.cloud.user.model.Step;
import com.cloud.user.model.Telephone;
import com.cloud.user.model.Telephonesegment;
import com.cloud.user.model.UserAddress;
import com.cloud.user.service.KudosService;
import com.cloud.user.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KudosServiceImpl implements KudosService {
	@Autowired
	private KudosDao kudosDao;
	@Autowired
	private CibilHeaderDao headerDao;
	@Autowired
	private CibilNamesegmentDao cibilNamesegmentDao;
	@Autowired
	private CibilIdsegmentDao cibilIdsegmentDao;
	@Autowired
	private CibilTelephonesegmentDao cibilTelephonesegmentDao;
	@Autowired
	private CibilEmailContactSegmentDao cibilEmailContactSegmentDao;
	@Autowired
	private CibilScoresegmentDao cibilScoresegmentDao;
	@Autowired
	private CibilAddressDao cibilAddressDao;
	@Autowired
	private CibilAccountNonsummarySegmentFieldsDao cibilAccountNonsummarySegmentFieldsDao;
	@Autowired
	private CibilEnquiryDao cibilEnquiryDao;
	@Autowired
	private CibilEndDao cibilEndDao;
	@Autowired
	private CibilApplicationdataDao cibilApplicationdataDao;
	@Autowired
	private CibilStepDao cibilStepDao;
	@Autowired
	private CibilCibilbureauresponseDao cibilCibilbureauresponseDao;
	@Autowired
	private CibilApplicantDao cibilApplicantDao;
	@Autowired
	private CibilTelephoneDao cibilTelephoneDao;
	@Autowired
	private CibilIdentifierDao cibilIdentifierDao;
	@Autowired
	private CibilInfoDao cibilinfoDao;
	@Autowired
	private CibilEmploymentsegmentDao cibilEmploymentsegmentDao;
	@Autowired
	private CibilCreditreportDao cibilCreditreportDao;
	@Autowired
	private CibilAccountDao cibilAccountDao;

	@Autowired(required = false)
	private RedisUtil redisUtil;

	@Override
	public int insertKudosCibil(LoanCibilResponseEntity le){
		int cibil = 0;
		try {
			cibil= kudosDao.insertKudosCibil(le);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cibil;
	}
//	@Override
//	public int insertContextData(ContextData contextData){
//		return kudosDao.insertContextData(contextData);
//	}

	@Override
	public int insertApplicant(Applicant applicant,String orderNo){
		applicant.setOrderNo(orderNo);
		applicant.setCreateTime(new Date());
		return cibilApplicantDao.insert(applicant);
	}
	@Override
	public int insertCreditreport(Creditreport creditreport,String orderNo){
		creditreport.setOrderNo(orderNo);
		creditreport.setCreateTime(new Date());
		return cibilCreditreportDao.insert(creditreport);
	}
	@Override
	public int insertHeader(Header header,String orderNo){
		header.setOrderNo(orderNo);
		header.setCreateTime(new Date());
		return headerDao.insert(header);
	}
	@Override
	public int insertNameSegment(Namesegment namesegment,String orderNo){
		namesegment.setOrderNo(orderNo);
		namesegment.setCreateTime(new Date());
		return cibilNamesegmentDao.insert(namesegment);
	}
	@Override
	public int insertIdsegment(List<Idsegment> idsegment,String orderNo){
		idsegment.stream().forEach(segment -> segment.setOrderNo(orderNo));
		idsegment.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilIdsegmentDao.insertList(idsegment);
	}
	@Override
	public int insertTelephonesegment(Telephonesegment telephonesegment,String orderNo){
		telephonesegment.setOrderNo(orderNo);
		telephonesegment.setCreateTime(new Date());
		return cibilTelephonesegmentDao.insert(telephonesegment);
	}
	@Override
	public int insertEmailContactSegment(EmailContactSegment emailContactSegment,String orderNo){
		emailContactSegment.setOrderNo(orderNo);
		emailContactSegment.setCreateTime(new Date());
		return cibilEmailContactSegmentDao.insert(emailContactSegment);
	}
	@Override
	public int inesrtScoresegment(Scoresegment scoresegment,String orderNo){
		scoresegment.setOrderNo(orderNo);
		scoresegment.setCreateTime(new Date());
		return cibilScoresegmentDao.insert(scoresegment);
	}
	@Override
	public int insertAddressList(List<Address> address,String orderNo){
		address.stream().forEach(segment -> segment.setOrderNo(orderNo));
		address.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilAddressDao.insertList(address);
	}
	@Override
	public int insertAddress(Address address,String orderNo){
		address.setOrderNo(orderNo);
		address.setCreateTime(new Date());
		return cibilAddressDao.insert(address);
	}
	@Override
	public int insertAccount(Account account,String orderNo){
		account.setOrderNo(orderNo);
		account.setCreateTime(new Date());
		return cibilAccountDao.insert(account);
	}
	@Override
	public int insertAccountNonsummarySegmentFields(AccountNonsummarySegmentFields accountNonsummarySegmentFields,String orderNo){
		accountNonsummarySegmentFields.setOrderNo(orderNo);
		accountNonsummarySegmentFields.setCreateTime(new Date());
		return cibilAccountNonsummarySegmentFieldsDao.insert(accountNonsummarySegmentFields);
	}
	@Override
	public int insertEnquiry(List<Enquiry> enquiry,String orderNo){
		enquiry.stream().forEach(segment -> segment.setOrderNo(orderNo));
		enquiry.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilEnquiryDao.insertList(enquiry);
	}
	@Override
	public int insertEnd(End end,String orderNo){
		end.setOrderNo(orderNo);
		end.setCreateTime(new Date());
		return cibilEndDao.insert(end);
	}
	@Override
	public int insertApplicationdata(Applicationdata applicationdata,String orderNo){
		applicationdata.setOrderNo(orderNo);
		applicationdata.setCreateTime(new Date());
		return cibilApplicationdataDao.insert(applicationdata);
	}
	@Override
	public int insertStep(List<Step> step,String orderNo){
		step.stream().forEach(segment -> segment.setOrderNo(orderNo));
		step.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilStepDao.insertList(step);
	}
	@Override
	public int insertCibilbureauresponse(Cibilbureauresponse cibilbureauresponse,String orderNo){
		cibilbureauresponse.setOrderNo(orderNo);
		cibilbureauresponse.setCreateTime(new Date());
		return cibilCibilbureauresponseDao.insert(cibilbureauresponse);
	}
	@Override
	public int insertTelephone(List<Telephone> telephone,String orderNo){
		telephone.stream().forEach(segment -> segment.setOrderNo(orderNo));
		telephone.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilTelephoneDao.insertList(telephone);
	}
	@Override
	public int insertIdentifier(List<Identifier> identifierList,String orderNo){
		identifierList.stream().forEach(segment -> segment.setOrderNo(orderNo));
		identifierList.stream().forEach(segment -> segment.setCreateTime(new Date()));
		return cibilIdentifierDao.insertList(identifierList);
	}
	@Override
	public int insertCibilInfo(CibilInfo cibilInfo){
		return cibilinfoDao.insert(cibilInfo);
	}
	@Override
	public int insertEmploymentsegment(Employmentsegment employmentsegment,String orderNo){
		employmentsegment.setOrderNo(orderNo);
		employmentsegment.setCreateTime(new Date());
		return cibilEmploymentsegmentDao.insert(employmentsegment);
	}
	@Override
	public String testinsert(String orderNo){
//		String cibilxml = "<?xml version=\"1.0\"?><DCResponse><Status>Success</Status><Authentication><Status>Success</Status><Token>b2748ecc-22b4-4ede-a48a-800ff3192475</Token></Authentication><ResponseInfo><ApplicationId>438510889</ApplicationId><SolutionSetInstanceId>ae698b61-99a3-4610-83d3-1f3c9764fa76</SolutionSetInstanceId><CurrentQueue></CurrentQueue></ResponseInfo><ContextData><Field key=\"Applicant\"><Applicant><Accounts><Account><AccountNumber/></Account></Accounts><Addresses><Address><StateCode>22</StateCode><ResidenceType>02</ResidenceType><PinCode>492013</PinCode><City>Raipur</City><AddressType>02</AddressType><AddressLine5>NA</AddressLine5><AddressLine4>NA</AddressLine4><AddressLine3>NA</AddressLine3><AddressLine2>Vasant Nagar, near diksha bhoomi</AddressLine2><AddressLine1>Sunder Nagar, kargil chowk</AddressLine1></Address></Addresses><Telephones><Telephone><TelephoneType>01</TelephoneType><TelephoneNumber>7798688426</TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone><Telephone><TelephoneType></TelephoneType><TelephoneNumber></TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone></Telephones><Identifiers><Identifier><IdType>01</IdType><IdNumber>BOIPC7021J</IdNumber></Identifier><Identifier><IdType/><IdNumber/></Identifier></Identifiers><CompanyName>Ace HVAC engineers </CompanyName><EmailAddress>hiteshchavhan1995@gmail.com</EmailAddress><Gender>M</Gender><DateOfBirth>15021995</DateOfBirth><ApplicantLastName>Dilip</ApplicantLastName><ApplicantMiddleName>Chavhan</ApplicantMiddleName><ApplicantFirstName>Hitesh</ApplicantFirstName><ApplicantType>Main</ApplicantType><DsCibilBureau><DsCibilBureauData><Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7798688426</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Hitesh</ConsumerName1> <ConsumerName2>Chavhan</ConsumerName2> <ConsumerName3>Dilip</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>15021995</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>BOIPC7021J</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7798688426</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>Sunder Nagar, kargil chowk</AddressLine1><AddressLine2>Vasant Nagar, near diksha bhoomi</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>22</StateCode><PinCode>492013</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request></DsCibilBureauData><DsCibilBureauStatus><Trail></Trail></DsCibilBureauStatus><Response><CibilBureauResponse><BureauResponseRaw>TUEF127798688426                 0000NB67098899_C2C                100290588437517052019095358PN03N010106HITESH0205DILIP0307CHAVHAN07081502199508012ID03I010102010210BOIPC7021JID03I020102060212503202371400PT03T01011096072029070202910302019001YPT03T020112917798688426030200EC03C010127HITESHCHAVHAN1995@GMAIL.COMEM03E01010205020831032019030204SC10CIBILTUSC2010204020210030817052019040500718250220260211270205PA03A010115HOUSE NO. 836-G0212SUNDER NAGAR0306RAIPUR06022207064920130802010902011008030320199001YPA03A020110PIN_4920130506RAIPUR06022207064920130802010902011008101020189001YPA03A030133FLAT NO. 325, GODAVARI APARTMENT,0235KARVENAGAR, PUNE MAHARASHTRA 41105206022707064110520802021008081020189001YPA03A040139HOUSE NO. 646, SUNDER NAGAR, STREET NO.02095, RAIPUR0602220706492001080202100808102018TL04T0010213NOT DISCLOSED040210050110808300120190908140220191108280220191205100001305100002806000000300801022019310801012019TL04T0020213NOT DISCLOSED0402050501108082512201810082401201911083103201912043500130102812000XXX000000300801032019310801122018350200TL04T0030213NOT DISCLOSED0402050501108080812201810082401201911083103201912043500130102812000XXX000000300801032019310801122018350200TL04T0040213NOT DISCLOSED0402050501108080803201809080704201810080704201811083004201812043000130102803000300801042018310801042018TL04T0050213NOT DISCLOSED0402050501108080202201809081302201810081302201811083004201812042000130102803000300801042018310801042018TL04T0060213NOT DISCLOSED0402050501108081801201809080102201810080102201811083004201812041000130102803000300801042018310801042018IQ04I0010108150520190413NOT DISCLOSED0502000606200000IQ04I0020108150520190413NOT DISCLOSED050206060550000IQ04I0030108010520190413NOT DISCLOSED050205060520000IQ04I0040108110420190413NOT DISCLOSED050205060520000IQ04I0050108090420190413NOT DISCLOSED05020506043000IQ04I0060108270320190413NOT DISCLOSED0502100606150000IQ04I0070108030320190413NOT DISCLOSED050205060520000IQ04I0080108290120190413NOT DISCLOSED05020506045000IQ04I0090108180120190413NOT DISCLOSED050212060510000IQ04I0100108031220180413NOT DISCLOSED050205060525000IQ04I0110108281120180413NOT DISCLOSED0502050606200000IQ04I0120108281020180413NOT DISCLOSED050205060550000IQ04I0130108161020180413NOT DISCLOSED0502510603100IQ04I0140108141020180413NOT DISCLOSED0502100603100IQ04I0150108111020180413NOT DISCLOSED0502100606150000IQ04I0160108101020180413NOT DISCLOSED0502050606100000IQ04I0170108101020180413NOT DISCLOSED0502050606100000IQ04I0180108101020180413NOT DISCLOSED05020506043000IQ04I0190108081020180413NOT DISCLOSED05021006041000IQ04I0200108081020180413NOT DISCLOSED050200060540000IQ04I0210108010620180413NOT DISCLOSED0502050606200000IQ04I0220108251220170413NOT DISCLOSED050205060550000IQ04I0230108251220170413NOT DISCLOSED050205060550000ES0700027790102**</BureauResponseRaw><BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7798688426</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002905884375</EnquiryControlNumber><DateProcessed>17052019</DateProcessed><TimeProcessed>095358</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>06</ConsumerName1FieldLength><ConsumerName1>HITESH</ConsumerName1><ConsumerName2FieldLength>05</ConsumerName2FieldLength><ConsumerName2>DILIP</ConsumerName2><ConsumerName3FieldLength>07</ConsumerName3FieldLength><ConsumerName3>CHAVHAN</ConsumerName3><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>15021995</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>BOIPC7021J</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>503202371400</IDNumber></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>9607202907</TelephoneNumber><TelephoneExtensionFieldLength>02</TelephoneExtensionFieldLength><TelephoneExtension>91</TelephoneExtension><TelephoneType>01</TelephoneType><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></TelephoneSegment><TelephoneSegment><Length>03</Length><SegmentTag>T02</SegmentTag><TelephoneNumberFieldLength>12</TelephoneNumberFieldLength><TelephoneNumber>917798688426</TelephoneNumber><TelephoneType>00</TelephoneType></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>27</EmailIDFieldLength><EmailID>HITESHCHAVHAN1995@GMAIL.COM</EmailID></EmailContactSegment><EmploymentSegment><Length>03</Length><SegmentTag>E01</SegmentTag><AccountType>05</AccountType><DateReportedCertified>31032019</DateReportedCertified><OccupationCode>04</OccupationCode></EmploymentSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>17052019</ScoreDate><Score>00718</Score><ReasonCode1FieldLength>02</ReasonCode1FieldLength><ReasonCode1>20</ReasonCode1><ReasonCode2FieldLength>02</ReasonCode2FieldLength><ReasonCode2>11</ReasonCode2><ReasonCode3FieldLength>02</ReasonCode3FieldLength><ReasonCode3>05</ReasonCode3></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>15</AddressLine1FieldLength><AddressLine1>HOUSE NO. 836-G</AddressLine1><AddressLine2FieldLength>12</AddressLine2FieldLength><AddressLine2>SUNDER NAGAR</AddressLine2><AddressLine3FieldLength>06</AddressLine3FieldLength><AddressLine3>RAIPUR</AddressLine3><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492013</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>03032019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>10</AddressLine1FieldLength><AddressLine1>PIN_492013</AddressLine1><AddressLine5FieldLength>06</AddressLine5FieldLength><AddressLine5>RAIPUR</AddressLine5><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492013</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>10102018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A03</SegmentTag><AddressLine1FieldLength>33</AddressLine1FieldLength><AddressLine1>FLAT NO. 325, GODAVARI APARTMENT,</AddressLine1><AddressLine2FieldLength>35</AddressLine2FieldLength><AddressLine2>KARVENAGAR, PUNE MAHARASHTRA 411052</AddressLine2><StateCode>27</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>411052</PinCode><AddressCategory>02</AddressCategory><DateReported>08102018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A04</SegmentTag><AddressLine1FieldLength>39</AddressLine1FieldLength><AddressLine1>HOUSE NO. 646, SUNDER NAGAR, STREET NO.</AddressLine1><AddressLine2FieldLength>09</AddressLine2FieldLength><AddressLine2>5, RAIPUR</AddressLine2><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492001</PinCode><AddressCategory>02</AddressCategory><DateReported>08102018</DateReported></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>10</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>30012019</DateOpenedOrDisbursed><DateOfLastPayment>14022019</DateOfLastPayment><DateReportedAndCertified>28022019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>05</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>10000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>05</CurrentBalanceFieldLength><CurrentBalance>10000</CurrentBalance><PaymentHistory1FieldLength>06</PaymentHistory1FieldLength><PaymentHistory1>000000</PaymentHistory1><PaymentHistoryStartDate>01022019</PaymentHistoryStartDate><PaymentHistoryEndDate>01012019</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T002</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>25122018</DateOpenedOrDisbursed><DateClosed>24012019</DateClosed><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3500</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>12</PaymentHistory1FieldLength><PaymentHistory1>000XXX000000</PaymentHistory1><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01122018</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T003</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>08122018</DateOpenedOrDisbursed><DateClosed>24012019</DateClosed><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3500</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>12</PaymentHistory1FieldLength><PaymentHistory1>000XXX000000</PaymentHistory1><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01122018</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T004</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>08032018</DateOpenedOrDisbursed><DateOfLastPayment>07042018</DateOfLastPayment><DateClosed>07042018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T005</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>02022018</DateOpenedOrDisbursed><DateOfLastPayment>13022018</DateOfLastPayment><DateClosed>13022018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>2000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T006</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>18012018</DateOpenedOrDisbursed><DateOfLastPayment>01022018</DateOfLastPayment><DateClosed>01022018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>1000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>15052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>00</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>15052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>01052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>11042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>09042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>27032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I007</SegmentTag><DateOfEnquiryFields>03032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I008</SegmentTag><DateOfEnquiryFields>29012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>5000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I009</SegmentTag><DateOfEnquiryFields>18012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>12</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>10000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I010</SegmentTag><DateOfEnquiryFields>03122018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>25000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I011</SegmentTag><DateOfEnquiryFields>28112018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I012</SegmentTag><DateOfEnquiryFields>28102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I013</SegmentTag><DateOfEnquiryFields>16102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>51</EnquiryPurpose><EnquiryAmountFieldLength>03</EnquiryAmountFieldLength><EnquiryAmount>100</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I014</SegmentTag><DateOfEnquiryFields>14102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>03</EnquiryAmountFieldLength><EnquiryAmount>100</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I015</SegmentTag><DateOfEnquiryFields>11102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I016</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I017</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I018</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I019</SegmentTag><DateOfEnquiryFields>08102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>1000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I020</SegmentTag><DateOfEnquiryFields>08102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>00</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>40000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I021</SegmentTag><DateOfEnquiryFields>01062018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I022</SegmentTag><DateOfEnquiryFields>25122017</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I023</SegmentTag><DateOfEnquiryFields>25122017</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0002779</TotalLength></End></CreditReport></BureauResponseXml>"+
//"<SecondaryReportXml><Root></Root></SecondaryReportXml><IsSucess>True</IsSucess></CibilBureauResponse></Response></DsCibilBureau></Applicant></Field><Field key=\"Applicants\"><Applicants><Applicant><Accounts><Account><AccountNumber/></Account></Accounts><Addresses><Address><StateCode>22</StateCode><ResidenceType>02</ResidenceType><PinCode>492013</PinCode><City>Raipur</City><AddressType>02</AddressType><AddressLine5>NA</AddressLine5><AddressLine4>NA</AddressLine4><AddressLine3>NA</AddressLine3><AddressLine2>Vasant Nagar, near diksha bhoomi</AddressLine2><AddressLine1>Sunder Nagar, kargil chowk</AddressLine1></Address></Addresses><Telephones><Telephone><TelephoneType>01</TelephoneType><TelephoneNumber>7798688426</TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone><Telephone><TelephoneType></TelephoneType><TelephoneNumber></TelephoneNumber><TelephoneExtension></TelephoneExtension>"+
//"</Telephone></Telephones><Identifiers><Identifier><IdType>01</IdType><IdNumber>BOIPC7021J</IdNumber></Identifier><Identifier><IdType/><IdNumber/></Identifier></Identifiers><CompanyName>Ace HVAC engineers </CompanyName><EmailAddress>hiteshchavhan1995@gmail.com</EmailAddress><Gender>M</Gender><DateOfBirth>15021995</DateOfBirth><ApplicantLastName>Dilip</ApplicantLastName><ApplicantMiddleName>Chavhan</ApplicantMiddleName><ApplicantFirstName>Hitesh</ApplicantFirstName><ApplicantType>Main</ApplicantType><DsCibilBureau><DsCibilBureauData><Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7798688426</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Hitesh</ConsumerName1> <ConsumerName2>Chavhan</ConsumerName2> <ConsumerName3>Dilip</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>15021995</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>BOIPC7021J</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7798688426</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>Sunder Nagar, kargil chowk</AddressLine1><AddressLine2>Vasant Nagar, near diksha bhoomi</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>22</StateCode><PinCode>492013</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request></DsCibilBureauData><DsCibilBureauStatus><Trail></Trail></DsCibilBureauStatus><Response><CibilBureauResponse><BureauResponseRaw>TUEF127798688426                 0000NB67098899_C2C                100290588437517052019095358PN03N010106HITESH0205DILIP0307CHAVHAN07081502199508012ID03I010102010210BOIPC7021JID03I020102060212503202371400PT03T01011096072029070202910302019001YPT03T020112917798688426030200EC03C010127HITESHCHAVHAN1995@GMAIL.COMEM03E01010205020831032019030204SC10CIBILTUSC2010204020210030817052019040500718250220260211270205PA03A010115HOUSE NO. 836-G0212SUNDER NAGAR0306RAIPUR06022207064920130802010902011008030320199001YPA03A020110PIN_4920130506RAIPUR06022207064920130802010902011008101020189001YPA03A030133FLAT NO. 325, GODAVARI APARTMENT,0235KARVENAGAR, PUNE MAHARASHTRA 41105206022707064110520802021008081020189001YPA03A040139HOUSE NO. 646, SUNDER NAGAR, STREET NO.02095, RAIPUR0602220706492001080202100808102018TL04T0010213NOT DISCLOSED040210050110808300120190908140220191108280220191205100001305100002806000000300801022019310801012019TL04T0020213NOT DISCLOSED0402050501108082512201810082401201911083103201912043500130102812000XXX000000300801032019310801122018350200TL04T0030213NOT DISCLOSED0402050501108080812201810082401201911083103201912043500130102812000XXX000000300801032019310801122018350200TL04T0040213NOT DISCLOSED0402050501108080803201809080704201810080704201811083004201812043000130102803000300801042018310801042018TL04T0050213NOT DISCLOSED0402050501108080202201809081302201810081302201811083004201812042000130102803000300801042018310801042018TL04T0060213NOT DISCLOSED0402050501108081801201809080102201810080102201811083004201812041000130102803000300801042018310801042018IQ04I0010108150520190413NOT DISCLOSED0502000606200000IQ04I0020108150520190413NOT DISCLOSED050206060550000IQ04I0030108010520190413NOT DISCLOSED050205060520000IQ04I0040108110420190413NOT DISCLOSED050205060520000IQ04I0050108090420190413NOT DISCLOSED05020506043000IQ04I0060108270320190413NOT DISCLOSED0502100606150000IQ04I0070108030320190413NOT DISCLOSED050205060520000IQ04I0080108290120190413NOT DISCLOSED05020506045000IQ04I0090108180120190413NOT DISCLOSED050212060510000IQ04I0100108031220180413NOT DISCLOSED050205060525000IQ04I0110108281120180413NOT DISCLOSED0502050606200000IQ04I0120108281020180413NOT DISCLOSED050205060550000IQ04I0130108161020180413NOT DISCLOSED0502510603100IQ04I0140108141020180413NOT DISCLOSED0502100603100IQ04I0150108111020180413NOT DISCLOSED0502100606150000IQ04I0160108101020180413NOT DISCLOSED0502050606100000IQ04I0170108101020180413NOT DISCLOSED0502050606100000IQ04I0180108101020180413NOT DISCLOSED05020506043000IQ04I0190108081020180413NOT DISCLOSED05021006041000IQ04I0200108081020180413NOT DISCLOSED050200060540000IQ04I0210108010620180413NOT DISCLOSED0502050606200000IQ04I0220108251220170413NOT DISCLOSED050205060550000IQ04I0230108251220170413NOT DISCLOSED050205060550000ES0700027790102**</BureauResponseRaw><BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7798688426</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002905884375</EnquiryControlNumber><DateProcessed>17052019</DateProcessed><TimeProcessed>095358</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>06</ConsumerName1FieldLength><ConsumerName1>HITESH</ConsumerName1><ConsumerName2FieldLength>05</ConsumerName2FieldLength><ConsumerName2>DILIP</ConsumerName2><ConsumerName3FieldLength>07</ConsumerName3FieldLength><ConsumerName3>CHAVHAN</ConsumerName3><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>15021995</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>BOIPC7021J</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>503202371400</IDNumber></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>9607202907</TelephoneNumber><TelephoneExtensionFieldLength>02</TelephoneExtensionFieldLength><TelephoneExtension>91</TelephoneExtension><TelephoneType>01</TelephoneType><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></TelephoneSegment><TelephoneSegment><Length>03</Length><SegmentTag>T02</SegmentTag><TelephoneNumberFieldLength>12</TelephoneNumberFieldLength><TelephoneNumber>917798688426</TelephoneNumber><TelephoneType>00</TelephoneType></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>27</EmailIDFieldLength><EmailID>HITESHCHAVHAN1995@GMAIL.COM</EmailID></EmailContactSegment><EmploymentSegment><Length>03</Length><SegmentTag>E01</SegmentTag><AccountType>05</AccountType><DateReportedCertified>31032019</DateReportedCertified><OccupationCode>04</OccupationCode></EmploymentSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>17052019</ScoreDate><Score>00718</Score><ReasonCode1FieldLength>02</ReasonCode1FieldLength><ReasonCode1>20</ReasonCode1><ReasonCode2FieldLength>02</ReasonCode2FieldLength><ReasonCode2>11</ReasonCode2><ReasonCode3FieldLength>02</ReasonCode3FieldLength><ReasonCode3>05</ReasonCode3></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>15</AddressLine1FieldLength><AddressLine1>HOUSE NO. 836-G</AddressLine1><AddressLine2FieldLength>12</AddressLine2FieldLength><AddressLine2>SUNDER NAGAR</AddressLine2><AddressLine3FieldLength>06</AddressLine3FieldLength><AddressLine3>RAIPUR</AddressLine3><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492013</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>03032019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>10</AddressLine1FieldLength><AddressLine1>PIN_492013</AddressLine1><AddressLine5FieldLength>06</AddressLine5FieldLength><AddressLine5>RAIPUR</AddressLine5><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492013</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>10102018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A03</SegmentTag><AddressLine1FieldLength>33</AddressLine1FieldLength><AddressLine1>FLAT NO. 325, GODAVARI APARTMENT,</AddressLine1><AddressLine2FieldLength>35</AddressLine2FieldLength><AddressLine2>KARVENAGAR, PUNE MAHARASHTRA 411052</AddressLine2><StateCode>27</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>411052</PinCode><AddressCategory>02</AddressCategory><DateReported>08102018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A04</SegmentTag><AddressLine1FieldLength>39</AddressLine1FieldLength><AddressLine1>HOUSE NO. 646, SUNDER NAGAR, STREET NO.</AddressLine1><AddressLine2FieldLength>09</AddressLine2FieldLength><AddressLine2>5, RAIPUR</AddressLine2><StateCode>22</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>492001</PinCode><AddressCategory>02</AddressCategory><DateReported>08102018</DateReported></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>10</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>30012019</DateOpenedOrDisbursed><DateOfLastPayment>14022019</DateOfLastPayment><DateReportedAndCertified>28022019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>05</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>10000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>05</CurrentBalanceFieldLength><CurrentBalance>10000</CurrentBalance><PaymentHistory1FieldLength>06</PaymentHistory1FieldLength><PaymentHistory1>000000</PaymentHistory1><PaymentHistoryStartDate>01022019</PaymentHistoryStartDate><PaymentHistoryEndDate>01012019</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T002</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>25122018</DateOpenedOrDisbursed><DateClosed>24012019</DateClosed><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3500</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>12</PaymentHistory1FieldLength><PaymentHistory1>000XXX000000</PaymentHistory1><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01122018</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T003</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>08122018</DateOpenedOrDisbursed><DateClosed>24012019</DateClosed><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3500</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>12</PaymentHistory1FieldLength><PaymentHistory1>000XXX000000</PaymentHistory1><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01122018</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T004</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>08032018</DateOpenedOrDisbursed><DateOfLastPayment>07042018</DateOfLastPayment><DateClosed>07042018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>3000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T005</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>02022018</DateOpenedOrDisbursed><DateOfLastPayment>13022018</DateOfLastPayment><DateClosed>13022018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>2000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Account><Length>04</Length><SegmentTag>T006</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>18012018</DateOpenedOrDisbursed><DateOfLastPayment>01022018</DateOfLastPayment><DateClosed>01022018</DateClosed><DateReportedAndCertified>30042018</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>1000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01042018</PaymentHistoryStartDate><PaymentHistoryEndDate>01042018</PaymentHistoryEndDate></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>15052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>00</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>15052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>01052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>11042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>09042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>27032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I007</SegmentTag><DateOfEnquiryFields>03032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I008</SegmentTag><DateOfEnquiryFields>29012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>5000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I009</SegmentTag><DateOfEnquiryFields>18012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>12</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>10000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I010</SegmentTag><DateOfEnquiryFields>03122018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>25000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I011</SegmentTag><DateOfEnquiryFields>28112018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I012</SegmentTag><DateOfEnquiryFields>28102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I013</SegmentTag><DateOfEnquiryFields>16102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>51</EnquiryPurpose><EnquiryAmountFieldLength>03</EnquiryAmountFieldLength><EnquiryAmount>100</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I014</SegmentTag><DateOfEnquiryFields>14102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>03</EnquiryAmountFieldLength><EnquiryAmount>100</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I015</SegmentTag><DateOfEnquiryFields>11102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I016</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I017</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I018</SegmentTag><DateOfEnquiryFields>10102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I019</SegmentTag><DateOfEnquiryFields>08102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>1000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I020</SegmentTag><DateOfEnquiryFields>08102018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>00</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>40000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I021</SegmentTag><DateOfEnquiryFields>01062018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I022</SegmentTag><DateOfEnquiryFields>25122017</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I023</SegmentTag><DateOfEnquiryFields>25122017</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0002779</TotalLength></End></CreditReport></BureauResponseXml><SecondaryReportXml><Root></Root></SecondaryReportXml><IsSucess>True</IsSucess></CibilBureauResponse></Response></DsCibilBureau></Applicant></Applicants></Field><Field key=\"ApplicationData\"><ApplicationData><FormattedReport>TRUE</FormattedReport><MFIBranchReferenceNo/><MFICenterReferenceNo/><MFILoanPurpose/><MFIEnquiryAmount/><BranchReferenceNo/><CenterReferenceNo/><MFIMemberCode/><IDVPDFReport>False</IDVPDFReport><MFIPDFReport>False</MFIPDFReport><CIBILPDFReport>True</CIBILPDFReport><MFIBureauFlag>True</MFIBureauFlag><IDVerificationFlag>True</IDVerificationFlag><DSTuNtcFlag>True</DSTuNtcFlag><CibilBureauFlag>False</CibilBureauFlag><GSTStateCode>22</GSTStateCode><ConsumerConsentForUIDAIAuthentication>Y</ConsumerConsentForUIDAIAuthentication><RepaymentPeriodInMonths>1</RepaymentPeriodInMonths><ScoreType>04</ScoreType><IDVMemberCode/><NTCProductType/><Income>20000</Income><ReferenceNumber>7798688426</ReferenceNumber><Password>au*bnu5Kbx</Password><MemberCode>NB67098899_C2C</MemberCode><Amount>10000</Amount><Purpose>05</Purpose><User>KUDOS_Dev_User</User><BusinessUnitId>410</BusinessUnitId><ApplicationId>438510889</ApplicationId><SolutionSetId>2081</SolutionSetId><EnvironmentTypeId>2</EnvironmentTypeId><EnvironmentType>Production</EnvironmentType><Milestone><Step>Capture Quick Application Data</Step><Step>CIBIL Bureau call</Step><Step>IDVision Score</Step><Step>Domain Verification</Step></Milestone><Start>2019-05-17T04:23:58.7586607Z</Start><InputValReasonCodes></InputValReasonCodes><DTTrail><Step><Name>Capture Quick Application Data</Name><Duration>00:00:00.0156000</Duration></Step><Step><Name>CIBIL Bureau call</Name><Duration>00:00:01.2012021</Duration></Step><Step><Name>IDVision Score</Name><Duration>00:00:00.0156001</Duration></Step><Step><Name>Domain Verification</Name><Duration>00:00:00</Duration></Step></DTTrail></ApplicationData></Field><Field key=\"Decision\">Pass</Field></ContextData></DCResponse>";
		String cibilxml = "<?xml version=\"1.0\"?><DCResponse><Status>Success</Status><Authentication><Status>Success</Status><Token>0a65eca8-14d3-4237-b46a-49fb75851e6b</Token></Authentication><ResponseInfo><ApplicationId>434597406</ApplicationId><SolutionSetInstanceId>627f91cf-237d-4905-a72d-fa87a8612fb6</SolutionSetInstanceId><CurrentQueue></CurrentQueue></ResponseInfo><ContextData><Field key=\"Applicant\"><Applicant><Accounts><Account><AccountNumber/></Account></Accounts><Addresses><Address><StateCode>18</StateCode><ResidenceType>02</ResidenceType><PinCode>783371</PinCode><City>Dhubri</City><AddressType>02</AddressType><AddressLine5>NA</AddressLine5><AddressLine4>NA</AddressLine4><AddressLine3>NA</AddressLine3><AddressLine2>kazipara pt 2</AddressLine2><AddressLine1>main road chapar</AddressLine1></Address></Addresses><Telephones><Telephone>"+
				"<TelephoneType>01</TelephoneType><TelephoneNumber>7896540448</TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone><Telephone><TelephoneType></TelephoneType><TelephoneNumber></TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone></Telephones><Identifiers><Identifier><IdType>01</IdType><IdNumber>FTBPS1435Q</IdNumber></Identifier><Identifier><IdType/><IdNumber/></Identifier></Identifiers><CompanyName>M/S ASHA DRUGS</CompanyName><EmailAddress>sheikhabdulla59@gmail.com</EmailAddress><Gender>M</Gender><DateOfBirth>09061991</DateOfBirth><ApplicantLastName>Sheikh</ApplicantLastName><ApplicantMiddleName>NA</ApplicantMiddleName><ApplicantFirstName>Abdulla</ApplicantFirstName><ApplicantType>Main</ApplicantType><DsCibilBureau><DsCibilBureauData><Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7896540448</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Abdulla</ConsumerName1> <ConsumerName2>NA</ConsumerName2> <ConsumerName3>Sheikh</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>09061991</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>FTBPS1435Q</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7896540448</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>main road chapar</AddressLine1><AddressLine2>kazipara pt 2</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>18</StateCode><PinCode>783371</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request></DsCibilBureauData><DsCibilBureauStatus><Trail></Trail></DsCibilBureauStatus><Response><CibilBureauResponse><BureauResponseRaw>TUEF127896540448                 0000NB67098899_C2C                100289176091906052019045011PN03N010111MR. ABDULLA0306SHEIKH07080901199108012ID03I010102010210FTBPS1435QID03I020102030210DBG17084369001YID03I0301020602124631155004259001YPT03T0101107002713794030201PT03T0201129178965404480302009001YEC03C010120ASHADRUGS7@GMAIL.COMSC10CIBILTUSC2010204020210030806052019040500648250214260218270207PA03A010109DHUBRI AS06021807067833710802021008060520199001YPA03A020112DHUBRI,ASSAM06021807067833710802010902011008300420199001YPA03A030138C/O MOFIZ UDDIN KAZIPARA PT.II, DHUBRI0214ASSAM - 78337106021807067833710802011008300420199001YPA03A040110PIN_7833710506DHUBRI06021807067833710802010902011008160420199001YTL04T0010213NOT DISCLOSED040201050140808071120160908120320191108310320191206270000130611359628540000000000000000000000000000000000200210180520210200002933STDSTDXXXSTDSTD051021018STDSTDSTD30080103201931080111201639024540047946IQ04I0010108060520190413NOT DISCLOSED0502410606150000IQ04I0020108300420190413NOT DISCLOSED0502510606100000IQ04I0030108300420190413NOT DISCLOSED050205060520000IQ04I0040108300420190413NOT DISCLOSED050205060522500IQ04I0050108160420190413NOT DISCLOSED05020506043000IQ04I0060108160420190413NOT DISCLOSED050205060550000IQ04I0070108150420190413NOT DISCLOSED050205060550000IQ04I0080108150420190413NOT DISCLOSED050206060550000IQ04I0090108120420190413NOT DISCLOSED050205060510000IQ04I0100108200320180413NOT DISCLOSED050210060550000IQ04I0110108071120160413NOT DISCLOSED0502010606270000IQ04I0120108171020160413NOT DISCLOSED0502010606270000IQ04I0130108070420160413NOT DISCLOSED0502060606200000ES0700016240102**</BureauResponseRaw><BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7896540448</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002891760919</EnquiryControlNumber><DateProcessed>06052019</DateProcessed><TimeProcessed>045011</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>11</ConsumerName1FieldLength><ConsumerName1>MR. ABDULLA</ConsumerName1><ConsumerName3FieldLength>06</ConsumerName3FieldLength><ConsumerName3>SHEIKH</ConsumerName3><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>09011991</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>FTBPS1435Q</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>03</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>DBG1708436</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><IDSegment><Length>03</Length><SegmentTag>I03</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>463115500425</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>7002713794</TelephoneNumber><TelephoneType>01</TelephoneType></TelephoneSegment><TelephoneSegment><Length>03</Length><SegmentTag>T02</SegmentTag><TelephoneNumberFieldLength>12</TelephoneNumberFieldLength><TelephoneNumber>917896540448</TelephoneNumber><TelephoneType>00</TelephoneType><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>20</EmailIDFieldLength><EmailID>ASHADRUGS7@GMAIL.COM</EmailID></EmailContactSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>06052019</ScoreDate><Score>00648</Score><ReasonCode1FieldLength>02</ReasonCode1FieldLength><ReasonCode1>14</ReasonCode1><ReasonCode2FieldLength>02</ReasonCode2FieldLength><ReasonCode2>18</ReasonCode2><ReasonCode3FieldLength>02</ReasonCode3FieldLength><ReasonCode3>07</ReasonCode3></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>09</AddressLine1FieldLength><AddressLine1>DHUBRI AS</AddressLine1><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>02</AddressCategory><DateReported>06052019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>12</AddressLine1FieldLength><AddressLine1>DHUBRI,ASSAM</AddressLine1><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>30042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A03</SegmentTag><AddressLine1FieldLength>38</AddressLine1FieldLength><AddressLine1>C/O MOFIZ UDDIN KAZIPARA PT.II, DHUBRI</AddressLine1><AddressLine2FieldLength>14</AddressLine2FieldLength><AddressLine2>ASSAM - 783371</AddressLine2><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><DateReported>30042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A04</SegmentTag><AddressLine1FieldLength>10</AddressLine1FieldLength><AddressLine1>PIN_783371</AddressLine1><AddressLine5FieldLength>06</AddressLine5FieldLength><AddressLine5>DHUBRI</AddressLine5><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>16042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>01</AccountType><OwenershipIndicator>4</OwenershipIndicator><DateOpenedOrDisbursed>07112016</DateOpenedOrDisbursed><DateOfLastPayment>12032019</DateOfLastPayment><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>06</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>270000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>06</CurrentBalanceFieldLength><CurrentBalance>113596</CurrentBalance><PaymentHistory1FieldLength>54</PaymentHistory1FieldLength><PaymentHistory1>000000000000000000000000000000000020021018052021020000</PaymentHistory1><PaymentHistory2FieldLength>33</PaymentHistory2FieldLength><PaymentHistory2>STDSTDXXXSTDSTD051021018STDSTDSTD</PaymentHistory2><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01112016</PaymentHistoryEndDate><RepaymentTenureFieldLength>02</RepaymentTenureFieldLength><RepaymentTenure>45</RepaymentTenure><EmiAmountFieldLength>04</EmiAmountFieldLength><EmiAmount>7946</EmiAmount></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>06052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>41</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>51</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>22500</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>16042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>16042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I007</SegmentTag><DateOfEnquiryFields>15042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I008</SegmentTag><DateOfEnquiryFields>15042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I009</SegmentTag><DateOfEnquiryFields>12042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>10000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I010</SegmentTag><DateOfEnquiryFields>20032018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I011</SegmentTag><DateOfEnquiryFields>07112016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>01</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>270000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I012</SegmentTag><DateOfEnquiryFields>17102016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>01</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>270000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I013</SegmentTag><DateOfEnquiryFields>07042016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0001624</TotalLength></End></CreditReport></BureauResponseXml><SecondaryReportXml><Root></Root></SecondaryReportXml><IsSucess>True</IsSucess></CibilBureauResponse></Response></DsCibilBureau></Applicant></Field><Field key=\"Applicants\"><Applicants><Applicant><Accounts><Account><AccountNumber/></Account></Accounts><Addresses><Address><StateCode>18</StateCode><ResidenceType>02</ResidenceType><PinCode>783371</PinCode><City>Dhubri</City><AddressType>02</AddressType><AddressLine5>NA</AddressLine5><AddressLine4>NA</AddressLine4><AddressLine3>NA</AddressLine3><AddressLine2>kazipara pt 2</AddressLine2><AddressLine1>main road chapar</AddressLine1></Address></Addresses><Telephones><Telephone><TelephoneType>01</TelephoneType><TelephoneNumber>7896540448</TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone><Telephone><TelephoneType></TelephoneType><TelephoneNumber></TelephoneNumber><TelephoneExtension></TelephoneExtension></Telephone></Telephones><Identifiers><Identifier><IdType>01</IdType><IdNumber>FTBPS1435Q</IdNumber></Identifier><Identifier><IdType/><IdNumber/></Identifier></Identifiers><CompanyName>M/S ASHA DRUGS</CompanyName><EmailAddress>sheikhabdulla59@gmail.com</EmailAddress><Gender>M</Gender><DateOfBirth>09061991</DateOfBirth><ApplicantLastName>Sheikh</ApplicantLastName><ApplicantMiddleName>NA</ApplicantMiddleName><ApplicantFirstName>Abdulla</ApplicantFirstName><ApplicantType>Main</ApplicantType><DsCibilBureau><DsCibilBureauData><Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7896540448</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Abdulla</ConsumerName1> <ConsumerName2>NA</ConsumerName2> <ConsumerName3>Sheikh</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>09061991</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>FTBPS1435Q</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7896540448</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>main road chapar</AddressLine1><AddressLine2>kazipara pt 2</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>18</StateCode><PinCode>783371</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request></DsCibilBureauData><DsCibilBureauStatus><Trail></Trail></DsCibilBureauStatus><Response><CibilBureauResponse><BureauResponseRaw>TUEF127896540448                 0000NB67098899_C2C                100289176091906052019045011PN03N010111MR. ABDULLA0306SHEIKH07080901199108012ID03I010102010210FTBPS1435QID03I020102030210DBG17084369001YID03I0301020602124631155004259001YPT03T0101107002713794030201PT03T0201129178965404480302009001YEC03C010120ASHADRUGS7@GMAIL.COMSC10CIBILTUSC2010204020210030806052019040500648250214260218270207PA03A010109DHUBRI AS06021807067833710802021008060520199001YPA03A020112DHUBRI,ASSAM06021807067833710802010902011008300420199001YPA03A030138C/O MOFIZ UDDIN KAZIPARA PT.II, DHUBRI0214ASSAM - 78337106021807067833710802011008300420199001YPA03A040110PIN_7833710506DHUBRI06021807067833710802010902011008160420199001YTL04T0010213NOT DISCLOSED040201050140808071120160908120320191108310320191206270000130611359628540000000000000000000000000000000000200210180520210200002933STDSTDXXXSTDSTD051021018STDSTDSTD30080103201931080111201639024540047946IQ04I0010108060520190413NOT DISCLOSED0502410606150000IQ04I0020108300420190413NOT DISCLOSED0502510606100000IQ04I0030108300420190413NOT DISCLOSED050205060520000IQ04I0040108300420190413NOT DISCLOSED050205060522500IQ04I0050108160420190413NOT DISCLOSED05020506043000IQ04I0060108160420190413NOT DISCLOSED050205060550000IQ04I0070108150420190413NOT DISCLOSED050205060550000IQ04I0080108150420190413NOT DISCLOSED050206060550000IQ04I0090108120420190413NOT DISCLOSED050205060510000IQ04I0100108200320180413NOT DISCLOSED050210060550000IQ04I0110108071120160413NOT DISCLOSED0502010606270000IQ04I0120108171020160413NOT DISCLOSED0502010606270000IQ04I0130108070420160413NOT DISCLOSED0502060606200000ES0700016240102**</BureauResponseRaw><BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7896540448</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002891760919</EnquiryControlNumber><DateProcessed>06052019</DateProcessed><TimeProcessed>045011</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>11</ConsumerName1FieldLength><ConsumerName1>MR. ABDULLA</ConsumerName1><ConsumerName3FieldLength>06</ConsumerName3FieldLength><ConsumerName3>SHEIKH</ConsumerName3><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>09011991</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>FTBPS1435Q</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>03</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>DBG1708436</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><IDSegment><Length>03</Length><SegmentTag>I03</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>463115500425</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>7002713794</TelephoneNumber><TelephoneType>01</TelephoneType></TelephoneSegment><TelephoneSegment><Length>03</Length><SegmentTag>T02</SegmentTag><TelephoneNumberFieldLength>12</TelephoneNumberFieldLength><TelephoneNumber>917896540448</TelephoneNumber><TelephoneType>00</TelephoneType><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>20</EmailIDFieldLength><EmailID>ASHADRUGS7@GMAIL.COM</EmailID></EmailContactSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>06052019</ScoreDate><Score>00648</Score><ReasonCode1FieldLength>02</ReasonCode1FieldLength><ReasonCode1>14</ReasonCode1><ReasonCode2FieldLength>02</ReasonCode2FieldLength><ReasonCode2>18</ReasonCode2><ReasonCode3FieldLength>02</ReasonCode3FieldLength><ReasonCode3>07</ReasonCode3></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>09</AddressLine1FieldLength><AddressLine1>DHUBRI AS</AddressLine1><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>02</AddressCategory><DateReported>06052019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>12</AddressLine1FieldLength><AddressLine1>DHUBRI,ASSAM</AddressLine1><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>30042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A03</SegmentTag><AddressLine1FieldLength>38</AddressLine1FieldLength><AddressLine1>C/O MOFIZ UDDIN KAZIPARA PT.II, DHUBRI</AddressLine1><AddressLine2FieldLength>14</AddressLine2FieldLength><AddressLine2>ASSAM - 783371</AddressLine2><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><DateReported>30042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A04</SegmentTag><AddressLine1FieldLength>10</AddressLine1FieldLength><AddressLine1>PIN_783371</AddressLine1><AddressLine5FieldLength>06</AddressLine5FieldLength><AddressLine5>DHUBRI</AddressLine5><StateCode>18</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>783371</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>16042019</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>01</AccountType><OwenershipIndicator>4</OwenershipIndicator><DateOpenedOrDisbursed>07112016</DateOpenedOrDisbursed><DateOfLastPayment>12032019</DateOfLastPayment><DateReportedAndCertified>31032019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>06</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>270000</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>06</CurrentBalanceFieldLength><CurrentBalance>113596</CurrentBalance><PaymentHistory1FieldLength>54</PaymentHistory1FieldLength><PaymentHistory1>000000000000000000000000000000000020021018052021020000</PaymentHistory1><PaymentHistory2FieldLength>33</PaymentHistory2FieldLength><PaymentHistory2>STDSTDXXXSTDSTD051021018STDSTDSTD</PaymentHistory2><PaymentHistoryStartDate>01032019</PaymentHistoryStartDate><PaymentHistoryEndDate>01112016</PaymentHistoryEndDate><RepaymentTenureFieldLength>02</RepaymentTenureFieldLength><RepaymentTenure>45</RepaymentTenure><EmiAmountFieldLength>04</EmiAmountFieldLength><EmiAmount>7946</EmiAmount></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>06052019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>41</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>150000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>51</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>100000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>30042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>22500</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>16042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>16042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I007</SegmentTag><DateOfEnquiryFields>15042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I008</SegmentTag><DateOfEnquiryFields>15042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I009</SegmentTag><DateOfEnquiryFields>12042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>10000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I010</SegmentTag><DateOfEnquiryFields>20032018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>10</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I011</SegmentTag><DateOfEnquiryFields>07112016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>01</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>270000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I012</SegmentTag><DateOfEnquiryFields>17102016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>01</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>270000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I013</SegmentTag><DateOfEnquiryFields>07042016</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>06</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>200000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0001624</TotalLength></End></CreditReport></BureauResponseXml><SecondaryReportXml><Root></Root></SecondaryReportXml><IsSucess>True</IsSucess></CibilBureauResponse></Response></DsCibilBureau></Applicant></Applicants></Field><Field key=\"ApplicationData\"><ApplicationData><FormattedReport>TRUE</FormattedReport><MFIBranchReferenceNo/><MFICenterReferenceNo/><MFILoanPurpose/><MFIEnquiryAmount/><BranchReferenceNo/><CenterReferenceNo/><MFIMemberCode/><IDVPDFReport>False</IDVPDFReport><MFIPDFReport>False</MFIPDFReport><CIBILPDFReport>True</CIBILPDFReport><MFIBureauFlag>True</MFIBureauFlag><IDVerificationFlag>True</IDVerificationFlag><DSTuNtcFlag>True</DSTuNtcFlag><CibilBureauFlag>False</CibilBureauFlag><GSTStateCode>18</GSTStateCode><ConsumerConsentForUIDAIAuthentication>Y</ConsumerConsentForUIDAIAuthentication><RepaymentPeriodInMonths>1</RepaymentPeriodInMonths><ScoreType>04</ScoreType><IDVMemberCode/><NTCProductType/><Income>27500</Income><ReferenceNumber>7896540448</ReferenceNumber><Password>au*bnu5Kbx</Password><MemberCode>NB67098899_C2C</MemberCode><Amount>10000</Amount><Purpose>05</Purpose><User>KUDOS_Dev_User</User><BusinessUnitId>410</BusinessUnitId><ApplicationId>434597406</ApplicationId><SolutionSetId>2081</SolutionSetId><EnvironmentTypeId>2</EnvironmentTypeId><EnvironmentType>Production</EnvironmentType><Milestone><Step>Capture Quick Application Data</Step><Step>CIBIL Bureau call</Step><Step>IDVision Score</Step><Step>Domain Verification</Step></Milestone><Start>2019-05-05T23:20:15.0985150Z</Start><InputValReasonCodes></InputValReasonCodes><DTTrail><Step><Name>Capture Quick Application Data</Name><Duration>00:00:00.0156001</Duration></Step><Step><Name>CIBIL Bureau call</Name><Duration>00:00:03.7596066</Duration></Step><Step><Name>IDVision Score</Name><Duration>00:00:00.0156000</Duration></Step><Step><Name>Domain Verification</Name><Duration>00:00:00.0156000</Duration></Step></DTTrail></ApplicationData></Field><Field key=\"Decision\">Pass</Field></ContextData></DCResponse>";
		/*String cibilxml = "<?xml version=\"1.0\"?><DCResponse><Status>Success</Status><Authentication><Status>Success</Status><Token>4a8a6ed9-666c-43b3-8e09-2c1245586254</Token></Authentication><ResponseInfo><ApplicationId>431648874</ApplicationId><SolutionSetInstanceId>8eead34d-0325-4022-bb86-de0a63538192</SolutionSetInstanceId><CurrentQueue></CurrentQueue></ResponseInfo><ContextData><Field key=\"Applicant\"><Applicant>"+
				"<Accounts>"+
				"<Account>"+
				"<AccountNumber/>"+
				"</Account>"+
				"</Accounts>"+
				"<Addresses>"+
				"<Address>"+
				"<StateCode>16</StateCode>"+
				"<ResidenceType>02</ResidenceType>"+
				"<PinCode>799205</PinCode>"+
				"<City>West Tripura</City>"+
				"<AddressType>02</AddressType>"+
				"<AddressLine5>NA</AddressLine5>"+
				"<AddressLine4>NA</AddressLine4>"+
				"<AddressLine3>NA</AddressLine3>"+
				"<AddressLine2>Subhananda para</AddressLine2>"+
				"<AddressLine1>agartala</AddressLine1>"+
				"</Address>"+
				"</Addresses>"+
				"<Telephones>"+
				"<Telephone>"+
				"<TelephoneType>01</TelephoneType>"+
				"<TelephoneNumber>7005291485</TelephoneNumber>"+
				"<TelephoneExtension>"+
				"</TelephoneExtension>"+
				"</Telephone>"+
				"<Telephone>"+
				"<TelephoneType>"+
				"</TelephoneType>"+
				"<TelephoneNumber>"+
				"</TelephoneNumber>"+
				"<TelephoneExtension>"+
				"</TelephoneExtension>"+
				"</Telephone>"+
				"</Telephones>"+
				"<Identifiers>"+
				"<Identifier>"+
				"<IdType>01</IdType>"+
				"<IdNumber>BJNPD9844A</IdNumber>"+
				"</Identifier>"+
				"<Identifier>"+
				"<IdType/>"+
				"<IdNumber/>"+
				"</Identifier>"+
				"</Identifiers>"+
				"<CompanyName>vestige</CompanyName>"+
				"<EmailAddress>PushraiDebbarma52730@gmail.com</EmailAddress>"+
				"<Gender>M</Gender>"+
				"<DateOfBirth>25041987</DateOfBirth>"+
				"<ApplicantLastName>Debbarma</ApplicantLastName>"+
				"<ApplicantMiddleName>NA</ApplicantMiddleName>"+
				"<ApplicantFirstName>Pushrai</ApplicantFirstName>"+
				"<ApplicantType>Main</ApplicantType>"+
				"<DsCibilBureau>"+
				"<DsCibilBureauData>"+
				"<Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7005291485</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Pushrai</ConsumerName1> <ConsumerName2>NA</ConsumerName2> <ConsumerName3>Debbarma</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>25041987</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>BJNPD9844A</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7005291485</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>agartala</AddressLine1><AddressLine2>Subhananda para</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>16</StateCode><PinCode>799205</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request>"+
				"</DsCibilBureauData>"+
				"<DsCibilBureauStatus>"+
				"<Trail></Trail>"+
				"</DsCibilBureauStatus>"+
				"<Response>"+
				"<CibilBureauResponse>"+
				"<BureauResponseRaw>TUEF127005291485                 0000NB67098899_C2C                100288296642326042019130902PN03N010117PUSHRAI DEB BARMA07082504198708012ID03I010102010210BJNPD9844AID03I0201020602128069800595779001YPT03T0101107005291485030201EC03C010130PUSHRAIDEBBARMA52730@GMAIL.COMSC10CIBILTUSC2010204020210030826042019040500002PA03A010127WEST TRIPURA,TRIPURA,7992050602160706799205080201090201100814112018PA03A020137S/O SUDHAN DEBBARMA. VILL  SUBHANANDA0236PARA . P.O  MUNGTAKAMI, SRIRAMKHARA,0322KHOWAI. TRIPURA 79920506021607067992050802011008240920189001YTL04T0010213NOT DISCLOSED0402050501108081812201809080201201910080201201911083101201912042200130102803000300801012019310801012019350200440203IQ04I0010108200420190413NOT DISCLOSED050205060550000IQ04I0020108020320190413NOT DISCLOSED05020506043000IQ04I0030108020220190413NOT DISCLOSED0502050606101000IQ04I0040108050120190413NOT DISCLOSED050205060560000IQ04I0050108141120180413NOT DISCLOSED05020506043000IQ04I0060108240920180413NOT DISCLOSED050205060520000ES0700010140102**</BureauResponseRaw>"+
				"<BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7005291485</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002882966423</EnquiryControlNumber><DateProcessed>26042019</DateProcessed><TimeProcessed>130902</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>17</ConsumerName1FieldLength><ConsumerName1>PUSHRAI DEB BARMA</ConsumerName1><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>25041987</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>BJNPD9844A</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>806980059577</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>7005291485</TelephoneNumber><TelephoneType>01</TelephoneType></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>30</EmailIDFieldLength><EmailID>PUSHRAIDEBBARMA52730@GMAIL.COM</EmailID></EmailContactSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>26042019</ScoreDate><Score>00002</Score></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>27</AddressLine1FieldLength><AddressLine1>WEST TRIPURA,TRIPURA,799205</AddressLine1><StateCode>16</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>799205</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>14112018</DateReported></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>37</AddressLine1FieldLength><AddressLine1>S/O SUDHAN DEBBARMA. VILL  SUBHANANDA</AddressLine1><AddressLine2FieldLength>36</AddressLine2FieldLength><AddressLine2>PARA . P.O  MUNGTAKAMI, SRIRAMKHARA,</AddressLine2><AddressLine3FieldLength>22</AddressLine3FieldLength><AddressLine3>KHOWAI. TRIPURA 799205</AddressLine3><StateCode>16</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>799205</PinCode><AddressCategory>01</AddressCategory><DateReported>24092018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>18122018</DateOpenedOrDisbursed><DateOfLastPayment>02012019</DateOfLastPayment><DateClosed>02012019</DateClosed><DateReportedAndCertified>31012019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>2200</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01012019</PaymentHistoryStartDate><PaymentHistoryEndDate>01012019</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral><PaymentFrequency>03</PaymentFrequency></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>20042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>02032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>02022019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>101000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>05012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>60000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>14112018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>24092018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0001014</TotalLength></End></CreditReport></BureauResponseXml>"+
				"<SecondaryReportXml><Root></Root></SecondaryReportXml>"+
				"<IsSucess>True</IsSucess>"+
				"</CibilBureauResponse>"+
				"</Response>"+
				"</DsCibilBureau>"+
				"</Applicant>"+
				"</Field><Field key=\"Applicants\"><Applicants>"+
				"<Applicant>"+
				"<Accounts>"+
				"<Account>"+
				"<AccountNumber/>"+
				"</Account>"+
				"</Accounts>"+
				"<Addresses>"+
				"<Address>"+
				"<StateCode>16</StateCode>"+
				"<ResidenceType>02</ResidenceType>"+
				"<PinCode>799205</PinCode>"+
				"<City>West Tripura</City>"+
				"<AddressType>02</AddressType>"+
				"<AddressLine5>NA</AddressLine5>"+
				"<AddressLine4>NA</AddressLine4>"+
				"<AddressLine3>NA</AddressLine3>"+
				"<AddressLine2>Subhananda para</AddressLine2>"+
				"<AddressLine1>agartala</AddressLine1>"+
				"</Address>"+
				"</Addresses>"+
				"<Telephones>"+
				"<Telephone>"+
				"<TelephoneType>01</TelephoneType>"+
				"<TelephoneNumber>7005291485</TelephoneNumber>"+
				"<TelephoneExtension>"+
				"</TelephoneExtension>"+
				"</Telephone>"+
				"<Telephone>"+
				"<TelephoneType>"+
				"</TelephoneType>"+
				"<TelephoneNumber>"+
				"</TelephoneNumber>"+
				"<TelephoneExtension>"+
				"</TelephoneExtension>"+
				"</Telephone>"+
				"</Telephones>"+
				"<Identifiers>"+
				"<Identifier>"+
				"<IdType>01</IdType>"+
				"<IdNumber>BJNPD9844A</IdNumber>"+
				"</Identifier>"+
				"<Identifier>"+
				"<IdType/>"+
				"<IdNumber/>"+
				"</Identifier>"+
				"</Identifiers>"+
				"<CompanyName>vestige</CompanyName>"+
				"<EmailAddress>PushraiDebbarma52730@gmail.com</EmailAddress>"+
				"<Gender>M</Gender>"+
				"<DateOfBirth>25041987</DateOfBirth>"+
				"<ApplicantLastName>Debbarma</ApplicantLastName>"+
				"<ApplicantMiddleName>NA</ApplicantMiddleName>"+
				"<ApplicantFirstName>Pushrai</ApplicantFirstName>"+
				"<ApplicantType>Main</ApplicantType>"+
				"<DsCibilBureau>"+
				"<DsCibilBureauData>"+
				"<Request><Request><ConsumerDetails><CreditReportInquiry><Header><SegmentTag> TUEF </SegmentTag><Version> 12 </Version><ReferenceNumber>7005291485</ReferenceNumber><FutureUse1></FutureUse1><MemberCode>NB67098899_C2C</MemberCode><Password>au*bnu5Kbx</Password><Purpose>05</Purpose><Amount>10000</Amount><FutureUse2></FutureUse2><ScoreType>04</ScoreType><OutputFormat>01</OutputFormat><ResponseSize>1</ResponseSize><MediaType>CC</MediaType><AuthenticationMethod>L</AuthenticationMethod></Header><Names><Name> <ConsumerName1>Pushrai</ConsumerName1> <ConsumerName2>NA</ConsumerName2> <ConsumerName3>Debbarma</ConsumerName3> <ConsumerName4></ConsumerName4> <ConsumerName5></ConsumerName5> <DateOfBirth>25041987</DateOfBirth> <Gender>M</Gender> </Name></Names><Identifications> <Identification><PanNo>BJNPD9844A</PanNo><PassportNumber></PassportNumber><DLNo></DLNo><VoterId></VoterId><UId></UId><RationCardNo></RationCardNo><AdditionalID1></AdditionalID1><AdditionalID2></AdditionalID2></Identification></Identifications><Telephones><Telephone><TelephoneNumber>7005291485</TelephoneNumber><TelephoneExtension></TelephoneExtension><TelephoneType>01</TelephoneType></Telephone></Telephones><Addresses><Address><AddressLine1>agartala</AddressLine1><AddressLine2>Subhananda para</AddressLine2><AddressLine3>NA</AddressLine3><AddressLine4>NA</AddressLine4><AddressLine5>NA</AddressLine5><StateCode>16</StateCode><PinCode>799205</PinCode><AddressCategory>02</AddressCategory><ResidenceCode>02</ResidenceCode></Address></Addresses></CreditReportInquiry></ConsumerDetails></Request></Request>"+
				"</DsCibilBureauData>"+
				"<DsCibilBureauStatus>"+
				"<Trail></Trail>"+
				"</DsCibilBureauStatus>"+
				"<Response>"+
				"<CibilBureauResponse>"+
				"<BureauResponseRaw>TUEF127005291485                 0000NB67098899_C2C                100288296642326042019130902PN03N010117PUSHRAI DEB BARMA07082504198708012ID03I010102010210BJNPD9844AID03I0201020602128069800595779001YPT03T0101107005291485030201EC03C010130PUSHRAIDEBBARMA52730@GMAIL.COMSC10CIBILTUSC2010204020210030826042019040500002PA03A010127WEST TRIPURA,TRIPURA,7992050602160706799205080201090201100814112018PA03A020137S/O SUDHAN DEBBARMA. VILL  SUBHANANDA0236PARA . P.O  MUNGTAKAMI, SRIRAMKHARA,0322KHOWAI. TRIPURA 79920506021607067992050802011008240920189001YTL04T0010213NOT DISCLOSED0402050501108081812201809080201201910080201201911083101201912042200130102803000300801012019310801012019350200440203IQ04I0010108200420190413NOT DISCLOSED050205060550000IQ04I0020108020320190413NOT DISCLOSED05020506043000IQ04I0030108020220190413NOT DISCLOSED0502050606101000IQ04I0040108050120190413NOT DISCLOSED050205060560000IQ04I0050108141120180413NOT DISCLOSED05020506043000IQ04I0060108240920180413NOT DISCLOSED050205060520000ES0700010140102**</BureauResponseRaw>"+
				"<BureauResponseXml><CreditReport><Header><SegmentTag>TUEF</SegmentTag><Version>12</Version><ReferenceNumber>7005291485</ReferenceNumber><MemberCode>NB67098899_C2C                </MemberCode><SubjectReturnCode>1</SubjectReturnCode><EnquiryControlNumber>002882966423</EnquiryControlNumber><DateProcessed>26042019</DateProcessed><TimeProcessed>130902</TimeProcessed></Header><NameSegment><Length>03</Length><SegmentTag>N01</SegmentTag><ConsumerName1FieldLength>17</ConsumerName1FieldLength><ConsumerName1>PUSHRAI DEB BARMA</ConsumerName1><DateOfBirthFieldLength>08</DateOfBirthFieldLength><DateOfBirth>25041987</DateOfBirth><GenderFieldLength>01</GenderFieldLength><Gender>2</Gender></NameSegment><IDSegment><Length>03</Length><SegmentTag>I01</SegmentTag><IDType>01</IDType><IDNumberFieldLength>10</IDNumberFieldLength><IDNumber>BJNPD9844A</IDNumber></IDSegment><IDSegment><Length>03</Length><SegmentTag>I02</SegmentTag><IDType>06</IDType><IDNumberFieldLength>12</IDNumberFieldLength><IDNumber>806980059577</IDNumber><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></IDSegment><TelephoneSegment><Length>03</Length><SegmentTag>T01</SegmentTag><TelephoneNumberFieldLength>10</TelephoneNumberFieldLength><TelephoneNumber>7005291485</TelephoneNumber><TelephoneType>01</TelephoneType></TelephoneSegment><EmailContactSegment><Length>03</Length><SegmentTag>C01</SegmentTag><EmailIDFieldLength>30</EmailIDFieldLength><EmailID>PUSHRAIDEBBARMA52730@GMAIL.COM</EmailID></EmailContactSegment><ScoreSegment><Length>10</Length><ScoreName>CIBILTUSC2</ScoreName><ScoreCardName>04</ScoreCardName><ScoreCardVersion>10</ScoreCardVersion><ScoreDate>26042019</ScoreDate><Score>00002</Score></ScoreSegment><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A01</SegmentTag><AddressLine1FieldLength>27</AddressLine1FieldLength><AddressLine1>WEST TRIPURA,TRIPURA,799205</AddressLine1><StateCode>16</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>799205</PinCode><AddressCategory>01</AddressCategory><ResidenceCode>01</ResidenceCode><DateReported>14112018</DateReported></Address><Address><AddressSegmentTag>PA</AddressSegmentTag><Length>03</Length><SegmentTag>A02</SegmentTag><AddressLine1FieldLength>37</AddressLine1FieldLength><AddressLine1>S/O SUDHAN DEBBARMA. VILL  SUBHANANDA</AddressLine1><AddressLine2FieldLength>36</AddressLine2FieldLength><AddressLine2>PARA . P.O  MUNGTAKAMI, SRIRAMKHARA,</AddressLine2><AddressLine3FieldLength>22</AddressLine3FieldLength><AddressLine3>KHOWAI. TRIPURA 799205</AddressLine3><StateCode>16</StateCode><PinCodeFieldLength>06</PinCodeFieldLength><PinCode>799205</PinCode><AddressCategory>01</AddressCategory><DateReported>24092018</DateReported><EnrichedThroughEnquiry>Y</EnrichedThroughEnquiry></Address><Account><Length>04</Length><SegmentTag>T001</SegmentTag><Account_Summary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength></Account_Summary_Segment_Fields><Account_NonSummary_Segment_Fields><ReportingMemberShortNameFieldLength>13</ReportingMemberShortNameFieldLength><ReportingMemberShortName>NOT DISCLOSED</ReportingMemberShortName><AccountType>05</AccountType><OwenershipIndicator>1</OwenershipIndicator><DateOpenedOrDisbursed>18122018</DateOpenedOrDisbursed><DateOfLastPayment>02012019</DateOfLastPayment><DateClosed>02012019</DateClosed><DateReportedAndCertified>31012019</DateReportedAndCertified><HighCreditOrSanctionedAmountFieldLength>04</HighCreditOrSanctionedAmountFieldLength><HighCreditOrSanctionedAmount>2200</HighCreditOrSanctionedAmount><CurrentBalanceFieldLength>01</CurrentBalanceFieldLength><CurrentBalance>0</CurrentBalance><PaymentHistory1FieldLength>03</PaymentHistory1FieldLength><PaymentHistory1>000</PaymentHistory1><PaymentHistoryStartDate>01012019</PaymentHistoryStartDate><PaymentHistoryEndDate>01012019</PaymentHistoryEndDate><TypeOfCollateralFieldLength>02</TypeOfCollateralFieldLength><TypeOfCollateral>00</TypeOfCollateral><PaymentFrequency>03</PaymentFrequency></Account_NonSummary_Segment_Fields></Account><Enquiry><Length>04</Length><SegmentTag>I001</SegmentTag><DateOfEnquiryFields>20042019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>50000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I002</SegmentTag><DateOfEnquiryFields>02032019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I003</SegmentTag><DateOfEnquiryFields>02022019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>06</EnquiryAmountFieldLength><EnquiryAmount>101000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I004</SegmentTag><DateOfEnquiryFields>05012019</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>60000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I005</SegmentTag><DateOfEnquiryFields>14112018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>04</EnquiryAmountFieldLength><EnquiryAmount>3000</EnquiryAmount></Enquiry><Enquiry><Length>04</Length><SegmentTag>I006</SegmentTag><DateOfEnquiryFields>24092018</DateOfEnquiryFields><EnquiringMemberShortNameFieldLength>13</EnquiringMemberShortNameFieldLength><EnquiringMemberShortName>NOT DISCLOSED</EnquiringMemberShortName><EnquiryPurpose>05</EnquiryPurpose><EnquiryAmountFieldLength>05</EnquiryAmountFieldLength><EnquiryAmount>20000</EnquiryAmount></Enquiry><End><SegmentTag>ES07</SegmentTag><TotalLength>0001014</TotalLength></End></CreditReport></BureauResponseXml>"+
				"<SecondaryReportXml><Root></Root></SecondaryReportXml>"+
				"<IsSucess>True</IsSucess>"+
				"</CibilBureauResponse>"+
				"</Response>"+
				"</DsCibilBureau>"+
				"</Applicant>"+
				"</Applicants>"+
				"</Field><Field key=\"ApplicationData\"><ApplicationData>"+
				"<FormattedReport>TRUE</FormattedReport>"+
				"<MFIBranchReferenceNo/>"+
				"<MFICenterReferenceNo/>"+
				"<MFILoanPurpose/>"+
				"<MFIEnquiryAmount/>"+
				"<BranchReferenceNo/>"+
				"<CenterReferenceNo/>"+
				"<MFIMemberCode/>"+
				"<IDVPDFReport>False</IDVPDFReport>"+
				"<MFIPDFReport>False</MFIPDFReport>"+
				"<CIBILPDFReport>True</CIBILPDFReport>"+
				"<MFIBureauFlag>True</MFIBureauFlag>"+
				"<IDVerificationFlag>True</IDVerificationFlag>"+
				"<DSTuNtcFlag>True</DSTuNtcFlag>"+
				"<CibilBureauFlag>False</CibilBureauFlag>"+
				"<GSTStateCode>16</GSTStateCode>"+
				"<ConsumerConsentForUIDAIAuthentication>Y</ConsumerConsentForUIDAIAuthentication>"+
				"<RepaymentPeriodInMonths>1</RepaymentPeriodInMonths>"+
				"<ScoreType>04</ScoreType>"+
				"<IDVMemberCode/>"+
				"<NTCProductType/>"+
				"<Income>18000</Income>"+
				"<ReferenceNumber>7005291485</ReferenceNumber>"+
				"<Password>au*bnu5Kbx</Password>"+
				"<MemberCode>NB67098899_C2C</MemberCode>"+
				"<Amount>10000</Amount>"+
				"<Purpose>05</Purpose>"+
				"<User>KUDOS_Dev_User</User>"+
				"<BusinessUnitId>410</BusinessUnitId>"+
				"<ApplicationId>431648874</ApplicationId>"+
				"<SolutionSetId>2081</SolutionSetId>"+
				"<EnvironmentTypeId>2</EnvironmentTypeId>"+
				"<EnvironmentType>Production</EnvironmentType>"+
				"<Milestone>"+
				"<Step>Capture Quick Application Data</Step>"+
				"<Step>CIBIL Bureau call</Step>"+
				"<Step>IDVision Score</Step>"+
				"<Step>Domain Verification</Step>"+
				"</Milestone>"+
				"<Start>2019-04-26T07:39:01.7679997Z</Start>"+
				"<InputValReasonCodes>"+
				"</InputValReasonCodes>"+
				"<DTTrail>"+
				"<Step>"+
				"<Name>Capture Quick Application Data</Name>"+
				"<Duration>00:00:00</Duration>"+
				"</Step>"+
				"<Step>"+
				"<Name>CIBIL Bureau call</Name>"+
				"<Duration>00:00:00.7488013</Duration>"+
				"</Step>"+
				"<Step>"+
				"<Name>IDVision Score</Name>"+
				"<Duration>00:00:00</Duration>"+
				"</Step>"+
				"<Step>"+
				"<Name>Domain Verification</Name>"+
				"<Duration>00:00:00</Duration>"+
				"</Step>"+
				"</DTTrail>"+
				"</ApplicationData>"+
				"</Field><Field key=\"Decision\">Pass</Field></ContextData></DCResponse>";*/

			KudosServiceImpl t = new KudosServiceImpl();
		 	Document doc = t.StringTOXml(cibilxml);
	        // getNodeValue
	        String score = t.getNodeValue(doc, "/DCResponse/ContextData/Field/Applicant/DsCibilBureau/Response/CibilBureauResponse/BureauResponseXml/CreditReport/ScoreSegment/Score");
	        String errorcode = t.getNodeValue(doc, "DCResponse/ContextData/Field/Applicants/Applicant/DsCibilBureau/Response/CibilBureauResponse/ErrorCode");
	        String errormessage = t.getNodeValue(doc, "DCResponse/ContextData/Field/Applicants/Applicant/DsCibilBureau/Response/CibilBureauResponse/ErrorMessage");
	        List<CibilInfo> lsList = queryCibilInfoCount(orderNo);
	        if (lsList.size() > 0) {
	            return "此数据已存在";
	        }
				CibilInfo cibilInfo = new CibilInfo();
				cibilInfo.setCreateTime(new Date());
				cibilInfo.setOrderNo(orderNo);
				cibilInfo.setScore(score);
				cibilInfo.setErrorcode(errorcode);
				cibilInfo.setErrormessage(errormessage);
				cibilInfo.setInfo(cibilxml);
//				insertCibilInfo(cibilInfo);
				JSONObject xmlJSONObj1 = XML.toJSONObject(cibilxml);
				com.alibaba.fastjson.JSONObject jsonObject1 = (com.alibaba.fastjson.JSONObject) JSON.parse(xmlJSONObj1.toString());
				DCResponse dr = jsonObject1.toJavaObject(jsonObject1.getJSONObject("DCResponse"), DCResponse.class);
				ContextData contextdata = dr.getContextdata();
				List<Field> fieldList = contextdata.getField();
				Applicants applicants = new Applicants();
				for (int i = 0; i < fieldList.size(); i++) {
					if (fieldList.get(i).getApplicants() != null) {
						applicants = fieldList.get(i).getApplicants();
						break ;
					}
				}
				Dscibilbureau dscibilbureau = applicants.getApplicant().getDscibilbureau();
				if (dscibilbureau==null) {//失败，但暂无相关失败原因
					Applicant applicant = applicants.getApplicant();//存
					insertApplicant(applicant,orderNo);
					Address address = applicant.getAddresses().getAddress();
					insertAddress(address,orderNo);
					List<Telephone> telephoneList = applicant.getTelephones().getTelephone();
					insertTelephone(telephoneList,orderNo);
					List<Identifier> identifierList = applicant.getIdentifiers().getIdentifier();
					insertIdentifier(identifierList,orderNo);
				}else {
					Response response = dscibilbureau.getResponse();
					Cibilbureauresponse cibilbureauresponse = response.getCibilbureauresponse();
					Bureauresponsexml bureauresponsexml = cibilbureauresponse.getBureauresponsexml();
					if (bureauresponsexml.getCreditreport()!=null) {//pass
						Creditreport creditreport = bureauresponsexml.getCreditreport();
						insertCreditreport(creditreport,orderNo);
						Header header = creditreport.getHeader();//存
						insertHeader(header,orderNo);
						Namesegment namesegment = creditreport.getNamesegment();//存
						insertNameSegment(namesegment, orderNo);
						/*List<Idsegment> idSegmentsList = creditreport.getIdsegment();//存
						insertIdsegment(idSegmentsList, orderNo);*/
						Creditreport cr = getCreditreportInfo(orderNo);
						if (cr != null && cr.getIdsegment() != null) {
							this.idSegment(cr.getIdsegment(), orderNo);
						}
						if (cr != null && cr.getEmailcontactsegment() != null) {
							this.emailContactSegment(cr.getEmailcontactsegment(), orderNo);
						}
//						Telephonesegment telephonesegment = creditreport.getTelephonesegment();//存
//						insertTelephonesegment(telephonesegment,orderNum);
						/*EmailContactSegment emailContactSegment = creditreport.getEmailContactSegment();//存
						insertEmailContactSegment(emailContactSegment,orderNo);*/
						Scoresegment scoresegment = creditreport.getScoresegment();//存
						inesrtScoresegment(scoresegment,orderNo);
						List<Address> addressList = creditreport.getAddress();//存
						insertAddressList(addressList,orderNo);
//						Account account = creditreport.getAccount();
//						insertAccount(account,orderNo);
//						AccountNonsummarySegmentFields accountNonsummarySegmentFields = creditreport.getAccount().getAccountNonsummarySegmentFields();//存
//						insertAccountNonsummarySegmentFields(accountNonsummarySegmentFields,orderNo);
						List<Enquiry> enquiryList = creditreport.getEnquiry();//存
						insertEnquiry(enquiryList,orderNo);
						End end = creditreport.getEnd();//存
						insertEnd(end,orderNo);
					}else {//返回error
						insertCibilbureauresponse(cibilbureauresponse,orderNo);
					}
				}
				Applicationdata applicationdata = new Applicationdata();
				for (int i = 0; i < fieldList.size(); i++) {
					if (fieldList.get(i).getApplicationdata() != null) {
						applicationdata = fieldList.get(i).getApplicationdata();
						break ;
					}
				}
				insertApplicationdata(applicationdata,orderNo);
//				List<Step> stepList = applicationdata.getDttrail().getStep();
//				insertStep(stepList,orderNum);
		return "测试数据";
	}
	public void emailContactSegment(String ecs,String orderNo){
		List<EmailContactSegment> isList = new ArrayList<EmailContactSegment>();
		if (ecs.contains("[")) {
			JSONArray data = com.alibaba.fastjson.JSONObject.parseArray(ecs);
			for(Object object : data){
				EmailContactSegment segment = new EmailContactSegment();
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) object;
				if (jsonObject.getString("EmailID")!=null) {
					segment.setEmailid(jsonObject.getString("EmailID"));
				}
				if (jsonObject.getString("Length")!=null) {
					segment.setLength(jsonObject.getString("Length"));
				}
				if (jsonObject.getString("EmailIDFieldLength")!=null) {
					segment.setEmailidfieldlength(jsonObject.getString("EmailIDFieldLength"));
				}
				if (jsonObject.getString("SegmentTag")!=null) {
					segment.setSegmenttag(jsonObject.getString("SegmentTag"));
				}
//				isList.add(segment);
				insertEmailContactSegment(segment, orderNo);
			}
		}else{
			EmailContactSegment segment = new EmailContactSegment();
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(ecs);
			if (jsonObject.get("EmailID")!=null) {
				segment.setEmailid(jsonObject.get("EmailID").toString());
			}
			if (jsonObject.get("Length")!=null) {
				segment.setLength(jsonObject.get("Length").toString());
			}
			if (jsonObject.get("EmailIDFieldLength")!=null) {
				segment.setEmailidfieldlength(jsonObject.get("EmailIDFieldLength").toString());
			}
			if (jsonObject.get("SegmentTag")!=null) {
				segment.setSegmenttag(jsonObject.get("SegmentTag").toString());
			}
			insertEmailContactSegment(segment, orderNo);
		}
	}
	public void idSegment(String ids,String orderNo){
		List<Idsegment> isList = new ArrayList<Idsegment>();
		if (ids.contains("[")) {
			JSONArray data = com.alibaba.fastjson.JSONObject.parseArray(ids);
			for (Object object : data) {
				Idsegment idsegment = new Idsegment();
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) object;
				if (jsonObject.getString("Length")!=null) {
					idsegment.setLength(jsonObject.getString("Length"));
				}
				if (jsonObject.getString("IDNumberFieldLength")!=null) {
					idsegment.setIdnumberfieldlength(jsonObject.getString("IDNumberFieldLength"));
				}
				if (jsonObject.getString("SegmentTag")!=null) {
					idsegment.setSegmenttag(jsonObject.getString("SegmentTag"));
				}
				if (jsonObject.getString("IDType")!=null) {
					idsegment.setIdtype(jsonObject.getString("IDType"));
				}
				if (jsonObject.getString("IDNumber")!=null) {
					idsegment.setIdnumber(jsonObject.getString("IDNumber"));
				}
				if (jsonObject.getString("EnrichedThroughEnquiry")!=null) {
					idsegment.setEnrichedthroughenquiry(jsonObject.getString("EnrichedThroughEnquiry"));
				}
				if (jsonObject.getString("IssueDate")!=null) {
					idsegment.setIssuedate(jsonObject.getString("IssueDate"));
				}
				if (jsonObject.getString("ExpirationDate")!=null) {
					idsegment.setExpirationdate(jsonObject.getString("ExpirationDate"));
				}
				isList.add(idsegment);
			}
		} else {
			Idsegment idsegment = new Idsegment();
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(ids);
			if (jsonObject.get("Length")!=null) {
				idsegment.setLength(jsonObject.get("Length").toString());
			}
			if (jsonObject.get("IDNumberFieldLength")!=null) {
				idsegment.setIdnumberfieldlength(jsonObject.get("IDNumberFieldLength").toString());				
			}
			if (jsonObject.get("SegmentTag")!=null) {
				idsegment.setSegmenttag(jsonObject.get("SegmentTag").toString());
			}
			if (jsonObject.get("IDType")!=null) {
				idsegment.setIdtype(jsonObject.get("IDType").toString());
			}
			if (jsonObject.get("IDNumber")!=null) {
				idsegment.setIdnumber(jsonObject.get("IDNumber").toString());
			}
			if (jsonObject.get("EnrichedThroughEnquiry")!=null) {
				idsegment.setEnrichedthroughenquiry(jsonObject.get("EnrichedThroughEnquiry").toString());
			}
			if (jsonObject.get("IssueDate")!=null) {
				idsegment.setIssuedate(jsonObject.get("IssueDate").toString());
			}
			if (jsonObject.get("ExpirationDate")!=null) {
				idsegment.setExpirationdate(jsonObject.get("ExpirationDate").toString());
			}
			isList.add(idsegment);
		}
		insertIdsegment(isList, orderNo);
	}
	//校验邮编是否为数字
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
	@Override
	public String kudosCibil(LoanCIBILEntity cibilEntity,String kudosType,String orderNo,String urlValue){
		Map<String, String> params = new HashMap<>();
		params.put("BorrowerFName", cibilEntity.getBorrowerFName());//借款人名字
    	params.put("BorrowerMName", cibilEntity.getBorrowerMName());//借款人中间名
    	params.put("BorrowerLName", cibilEntity.getBorrowerLName());//借款人姓氏
    	params.put("BorrowerDOB", cibilEntity.getBorrowerDOB());//借款人出生日期格式应为DDMMYYYY
    	params.put("BorrowerGender", cibilEntity.getBorrowerGender());//借款人性别
    	params.put("BorrowerEmail", cibilEntity.getBorrowerEmail());//借款人电子邮件地址
    	params.put("BorrowerCompanyName", cibilEntity.getBorrowerCompanyName());//借款人公司名称
    	params.put("Idnumber", cibilEntity.getIdnumber());//借款人身份证号码为Idtype    pancard-01/aadhaar-06
    	params.put("Idtype", cibilEntity.getIdtype());//借款人ID类型有关不同类型的ID，请参阅附录1
    	params.put("BorrowerPhone", cibilEntity.getBorrowerPhone());//借款人电话号码
    	params.put("BorrowerPhoneType", cibilEntity.getBorrowerPhoneType());//借款人电话号码类型 - 参见附录2
    	params.put("Borrower_Addr1", cibilEntity.getBorrowerAddr1());//借款人地址第1行
    	params.put("Borrower_Addr2", cibilEntity.getBorrowerAddr2());//借款人地址第2行
    	params.put("Borrower_Addr3", cibilEntity.getBorrowerAddr3());//借款人地址第3行
    	params.put("Borrower_Addr4", cibilEntity.getBorrowerAddr4());//借款人地址第4行
    	params.put("Borrower_Addr5", cibilEntity.getBorrowerAddr5());//借款人地址第5行
    	params.put("Borrower_AddrType", cibilEntity.getBorrowerAddrType());//借款人地址类型参见附录3
    	params.put("Borrower_City", cibilEntity.getBorrowerCity());//借款人城市
    	//如果不是6位的数字，则使用000000代替
    	if (isInteger(cibilEntity.getBorrowerPincode())) {
            if (cibilEntity.getBorrowerPincode().length() == 6) {
            } else {
            	cibilEntity.setBorrowerPincode("000000");
            }
        } else {
        	cibilEntity.setBorrowerPincode("000000");
        }
    	params.put("Borrower_Pincode", cibilEntity.getBorrowerPincode());//借款人邮编
    	params.put("Borrower_ResiType", cibilEntity.getBorrowerResiType());//借款人居住类型
    	params.put("Borrower_StateCode", cibilEntity.getBorrowerStateCode());//借款人州代码 - 参见附录4
    	params.put("Borrower_RequestAmount", cibilEntity.getBorrowerRequestAmount());//借款人申请金额
    	params.put("Borrower_LoanPurpose", cibilEntity.getBorrowerLoanPurpose());//借款人贷款目的{请使用值05}study
    	params.put("Borrower_RepaymentPer_Mnths", cibilEntity.getBorrowerRepaymentPerMnths());//借款人还款期限为几个月
    	params.put("ConsumerConsentForUIDAIAuthentication", cibilEntity.getConsumerConsentForUIDAIAuthentication());//消费者同意UIDAI认证真或假值    
    	params.put("GSTStateCode", cibilEntity.getgSTStateCode());//消费税国家代码 - 参见附录5
    	params.put("Request_ReferenceNum", cibilEntity.getRequestReferenceNum());//请求参考编号（仅使用数字0-9）
    	params.put("Borrower_Income", cibilEntity.getBorrowerIncome());//没有范围的借款人收入，以及逗号分隔符或小数（例如：12500）
    	String str = HttpUtils.https(urlValue, params, kudosType,cibilEntity.getSecretKet());
    	int cibil = saveCibilApi(str, kudosType, orderNo);
    	log.info("------------------------------params="+params);
    	log.info("------------------------------返回cibil="+cibil);
    	return String.valueOf(cibil);
	}
	
	//insert into kudos cibil api   
	/**
	 * cibil返回的数据，从中获取了score
	 * 暂时只存score、xml
	 * */
	public int saveCibilApi(String cibilxml,String kudosType,String orderNo){
		List<CibilInfo> lsList = queryCibilInfoCount(orderNo);
        if (lsList.size() > 0) {
            return lsList.size();
        }
		KudosServiceImpl t = new KudosServiceImpl();
        // String ————》XML
        Document doc = t.StringTOXml(cibilxml);
        // getNodeValue
        String score = t.getNodeValue(doc, "/DCResponse/ContextData/Field/Applicant/DsCibilBureau/Response/CibilBureauResponse/BureauResponseXml/CreditReport/ScoreSegment/Score");
        String errorcode = t.getNodeValue(doc, "DCResponse/ContextData/Field/Applicants/Applicant/DsCibilBureau/Response/CibilBureauResponse/ErrorCode");
        String errormessage = t.getNodeValue(doc, "DCResponse/ContextData/Field/Applicants/Applicant/DsCibilBureau/Response/CibilBureauResponse/ErrorMessage");
//		LoanCibilResponseEntity le = new LoanCibilResponseEntity();
		CibilInfo cibilInfo = new CibilInfo();
		cibilInfo.setOrderNo(orderNo);
		cibilInfo.setErrorcode(errorcode);
		cibilInfo.setErrormessage(errormessage);
		cibilInfo.setCreateTime(new Date());
		cibilInfo.setScore(score);
//		byte[] result = CibilEncryptUtil.encrypt(cibilxml.getBytes(),password);
//		cibilInfo.setInfo(new String(result));
		cibilInfo.setInfo(cibilxml);
		insertCibilInfo(cibilInfo);//存xml

		if (errormessage.equals("Your request has been rejected by the Credit Bureau server. Error Message : Invalid Membercode or password . Please contact your system administrator. Error Details : ")) {
			log.error("获取CibilInfo 错误: {}", cibilInfo);
		}
//		le.setOrderId(Integer.valueOf(cibilxml));
//		if (score.equals("")) {
//			le.setScore("0");
//		}
//		le.setScore(score);
//		le.setInfo(cibilxml);
//		int cibilInfo = insertKudosCibil(le);
//		System.out.println("是否是加密过后的cibil="+cibilxml);
		JSONObject xmlJSONObj1 = XML.toJSONObject(cibilxml);
		com.alibaba.fastjson.JSONObject jsonObject1 = (com.alibaba.fastjson.JSONObject) JSON.parse(xmlJSONObj1.toString());
		DCResponse dr = jsonObject1.toJavaObject(jsonObject1.getJSONObject("DCResponse"), DCResponse.class);
		ContextData contextdata = dr.getContextdata();
		List<Field> fieldList = contextdata.getField();
		Applicants applicants = new Applicants();
		
		for (int i = 0; i < fieldList.size(); i++) {
			if (fieldList.get(i).getApplicants() != null) {
				applicants = fieldList.get(i).getApplicants();
				break ;
			}
		}
		Dscibilbureau dscibilbureau = applicants.getApplicant().getDscibilbureau();
		if (dscibilbureau==null) {//失败，但暂无相关失败原因
			Applicant applicant = applicants.getApplicant();//存
			insertApplicant(applicant,orderNo);
			Address address = applicant.getAddresses().getAddress();
			insertAddress(address,orderNo);
			List<Telephone> telephoneList = applicant.getTelephones().getTelephone();
			insertTelephone(telephoneList,orderNo);
			List<Identifier> identifierList = applicant.getIdentifiers().getIdentifier();
			insertIdentifier(identifierList,orderNo);
		}else {
			Response response = dscibilbureau.getResponse();
			Cibilbureauresponse cibilbureauresponse = response.getCibilbureauresponse();
			Bureauresponsexml bureauresponsexml = cibilbureauresponse.getBureauresponsexml();
			if (bureauresponsexml.getCreditreport()!=null) {//pass
				List<Creditreport> crlist = queryCreditreportCount(orderNo);
				if (crlist.size()>0) {
					return crlist.size();
				}
				Creditreport creditreport = bureauresponsexml.getCreditreport();
				insertCreditreport(creditreport,orderNo);
				Header header = creditreport.getHeader();//存
				insertHeader(header,orderNo);
				if (creditreport.getNamesegment()!=null) {
					Namesegment namesegment = creditreport.getNamesegment();//存
					insertNameSegment(namesegment, orderNo);
				}
				/*if (creditreport.getIdsegment()!=null) {
					List<Idsegment> idSegmentsList = creditreport.getIdsegment();//存
					insertIdsegment(idSegmentsList, orderNo);
				}*/
				Creditreport cr = getCreditreportInfo(orderNo);
				if (cr != null && cr.getIdsegment() != null) {
					this.idSegment(cr.getIdsegment(), orderNo);
				}
				if (cr != null && cr.getEmailcontactsegment() != null) {
					this.emailContactSegment(cr.getIdsegment(), orderNo);
				}
//				List<Telephonesegment> telephonesegmentList = new ArrayList<>();
//				insertTelephonesegmentList(telephonesegmentList, orderNum);
//				Telephonesegment telephonesegment = creditreport.getTelephonesegment();//存
//				insertTelephonesegment(telephonesegment,orderNo);
				if (creditreport.getEmploymentsegment()!=null) {
					Employmentsegment employmentsegment = creditreport.getEmploymentsegment();
					insertEmploymentsegment(employmentsegment,orderNo);
				}
				/*if (creditreport.getEmailContactSegment()!=null) {
					EmailContactSegment emailContactSegment = creditreport.getEmailContactSegment();//存
					insertEmailContactSegment(emailContactSegment,orderNo);
				}*/
				if (creditreport.getScoresegment()!=null) {
					Scoresegment scoresegment = creditreport.getScoresegment();//存
					inesrtScoresegment(scoresegment,orderNo);
				}
				if (creditreport.getAddress()!=null) {
					List<Address> addressList = creditreport.getAddress();//存
					insertAddressList(addressList,orderNo);
				}
//				AccountNonsummarySegmentFields accountNonsummarySegmentFields = creditreport.getAccount().getAccountNonsummarySegmentFields();//存
//				insertAccountNonsummarySegmentFields(accountNonsummarySegmentFields,orderNo);
				if (creditreport.getEnquiry()!=null) {
					List<Enquiry> enquiryList = creditreport.getEnquiry();//存
					insertEnquiry(enquiryList,orderNo);
				}
				End end = creditreport.getEnd();//存
				insertEnd(end,orderNo);
			}else {//返回error
				insertCibilbureauresponse(cibilbureauresponse,orderNo);
			}
		}
		Applicationdata applicationdata = new Applicationdata();
		for (int i = 0; i < fieldList.size(); i++) {
			if (fieldList.get(i).getApplicationdata() != null) {
				applicationdata = fieldList.get(i).getApplicationdata();
				break ;
			}
		}
		insertApplicationdata(applicationdata,orderNo);
//		List<Step> stepList = applicationdata.getDttrail().getStep();
//		insertStep(stepList,orderNum);
		return 1;
	}

//	xml形状的str串
	public Document StringTOXml(String str) {
        StringBuilder sXML = new StringBuilder();
        sXML.append(str);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            InputStream is = new ByteArrayInputStream(sXML.toString().getBytes("utf-8"));
            doc = dbf.newDocumentBuilder().parse(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
	//获取节点
	public String getNodeValue(Document document, String nodePaht) {
        XPathFactory xpfactory = XPathFactory.newInstance();
        XPath path = xpfactory.newXPath();
        String servInitrBrch = "";
        try {
            servInitrBrch = path.evaluate(nodePaht, document);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return servInitrBrch;
    }
	@Override
	public List<CibilInfo> queryCibilInfoCount(String orderNo){
		List<CibilInfo> lsList = kudosDao.queryCibilInfoCount(orderNo);
		return lsList;
	}
	@Override
	public List<Creditreport> queryCreditreportCount(String orderNo){
		return kudosDao.queryCreditreportCount(orderNo);
	}
	@Override
	public Scoresegment getScoresegmentInfo(String orderNo) {
		Scoresegment scoresegment = new Scoresegment();
		scoresegment.setOrderNo(orderNo);
		return cibilScoresegmentDao.selectOne(scoresegment);
	}
	@Override
	public Inquiry getInquiryInfo(String orderNo){
		return kudosDao.getInquiryInfo(orderNo);
	}
	@Override
	public List<UserAddress> getUserAddressInfo(String orderNo){
		return kudosDao.getUserAddressInfo(orderNo);
	}
	@Override
	public Header getHeaderInfo(String orderNo) {
		Header header = new Header();
		header.setOrderNo(orderNo);
		return headerDao.selectOne(header);
	}
	@Override
	public Namesegment getNameSegmentInfo(String orderNo){
		Namesegment namesegment = new Namesegment();
		namesegment.setOrderNo(orderNo);
		return cibilNamesegmentDao.selectOne(namesegment);
	}
	@Override
	public List<Idsegment> getIdsegmentInfo(String orderNo){
		Idsegment idsegment = new Idsegment();
		idsegment.setOrderNo(orderNo);
		return cibilIdsegmentDao.select(idsegment);
	}

	@Override
	public List<Idsegment> getIdsegmentInfo(String orderNo, String idType) {
		Idsegment idsegment = new Idsegment();
		idsegment.setOrderNo(orderNo);
		idsegment.setIdtype(idType);
		return cibilIdsegmentDao.select(idsegment);
	}

	@Override
	public Telephonesegment getTelephoneSegmentInfo(String orderNo){
		Telephonesegment telephonesegment = new Telephonesegment();
		telephonesegment.setOrderNo(orderNo);
		return cibilTelephonesegmentDao.selectOne(telephonesegment);
	}
	@Override
	public List<EmailContactSegment> getEmailContactSegmentInfo(String orderNo){
		EmailContactSegment emailContactSegment = new EmailContactSegment();
		emailContactSegment.setOrderNo(orderNo);
		return cibilEmailContactSegmentDao.select(emailContactSegment);
	}
	@Override
	public List<Address> getAddressInfo(String orderNo){
		Address address = new Address();
		address.setOrderNo(orderNo);
		return cibilAddressDao.select(address);
	}
	@Override
	public List<Employmentsegment> getEmploymentSegmentInfo(String orderNo) {
		Employmentsegment employmentsegment = new Employmentsegment();
		employmentsegment.setOrderNo(orderNo);
		return cibilEmploymentsegmentDao.select(employmentsegment);
	}
	@Override
	public AccountNonsummarySegmentFields getAccountNonsummarySegmentFieldsInfo(String orderNo){
		AccountNonsummarySegmentFields accountNonsummarySegmentFields = new AccountNonsummarySegmentFields();
		accountNonsummarySegmentFields.setOrderNo(orderNo);
		return cibilAccountNonsummarySegmentFieldsDao.selectOne(accountNonsummarySegmentFields);
	}
	@Override
	public List<Enquiry> getEnquiryInfo(String orderNo){
		Enquiry record = new Enquiry();
		record.setOrderNo(orderNo);
		return cibilEnquiryDao.select(record);
	}
	@Override
	public Creditreport getCreditreportInfo(String orderNo) {
		Creditreport creditreport = new Creditreport();
		creditreport.setOrderNo(orderNo);
		return cibilCreditreportDao.selectOne(creditreport);
	}

	@Override
	public List<PartKudosAccountNonSummary> getRiskKudosAccountNonSummary(String orderNo) {
		Creditreport creditreport = getCreditreportInfo(orderNo);
		if (null == creditreport) {
			log.info("未找到orderNo:{}的CreditreportInfo", orderNo);
			return new ArrayList<>();
		}
		String account = creditreport.getAccount();
		log.info("要解析的account: {}", account);
		try {
			KudosModelUtils.getInstance().setRedisUtil(redisUtil);
			KudosModelUtils.getInstance().setFieldKey("Account_NonSummary_Segment_Fields");
			List<PartKudosAccountNonSummary> summaryList = KudosModelUtils.getInstance().getModelList(account, PartKudosAccountNonSummary.class);
			KudosModelUtils.getInstance().saveToRedis(orderNo, summaryList, 60 * 5);
			return summaryList;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

}
