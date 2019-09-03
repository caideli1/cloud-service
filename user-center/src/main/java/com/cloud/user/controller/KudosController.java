package com.cloud.user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cloud.common.dto.JsonResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.user.dao.KdCibilAccountTypeDao;
import com.cloud.user.model.AccountNonsummarySegmentFields;
import com.cloud.user.model.Address;
import com.cloud.user.model.Creditreport;
import com.cloud.user.model.EmailContactSegment;
import com.cloud.user.model.Employmentsegment;
import com.cloud.user.model.Enquiry;
import com.cloud.user.model.Header;
import com.cloud.user.model.Idsegment;
import com.cloud.user.model.Inquiry;
import com.cloud.user.model.KdCibilAccountType;
import com.cloud.user.model.Namesegment;
import com.cloud.user.model.Scoresegment;
import com.cloud.user.model.Telephonesegment;
import com.cloud.user.model.UserAddress;
import com.cloud.user.service.AppUserInfoService;
import com.cloud.user.service.KudosService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KudosController {
    @Autowired
    private KudosService kudosService;
    @Autowired
    private AppUserInfoService appUserInfoService;
    @Autowired
    private KdCibilAccountTypeDao kdCibilAccountTypeDao;//不同状态数字对应的英文展示
	//其它模块调用
	@GetMapping("/users-anon/cibilInfo")
	public void kudosCibil(String orderNo){
		try{
            appUserInfoService.strTokudos7(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	//手动处理cibil接口
	@GetMapping("/users-anon/getcibilInfo")
	public String getcibilInfo(String orderNo){
         return kudosService.testinsert(orderNo);
	}
	//解析测试使用
	@GetMapping("/users-anon/testinsert")
	public String testinsert(String orderNo){
         return kudosService.testinsert(orderNo);
	}

	/**
	 * 页面显示
	 * cibil 模块
	 * 报告时间，基本信息和地址放在一块，工作信息，信用，账户总览，贷款历史，查询总览，查询明细
	 * */
	//系统中的基本信息
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")
	@GetMapping("/users-anon/getInquiryInfo")
	public JsonResult getInquiryInfo(@RequestParam String orderNo){
		Inquiry inquiry = kudosService.getInquiryInfo(orderNo);
		return JsonResult.ok(inquiry);
	}
	//系统中的基本信息-地址
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getUserAddressInfo")
	public JsonResult getUserAddressInfo(@RequestParam String orderNo){
		List<UserAddress> userAddress = kudosService.getUserAddressInfo(orderNo);
		return JsonResult.ok(userAddress,userAddress.size());
	}
	//信用
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getScoresegmentInfo")
	public JsonResult getScoresegmentInfo(@RequestParam String orderNo){
		Scoresegment scoresegment = kudosService.getScoresegmentInfo(orderNo);
		return JsonResult.ok(scoresegment);
	}
	//报告时间
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getHeaderInfo")
	public JsonResult getHeaderInfo(@RequestParam String orderNo){
		Header header = kudosService.getHeaderInfo(orderNo);
		return JsonResult.ok(header);
	}
	//基本信息-NameSegment
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getNameSegmentInfo")
	public JsonResult getNameSegmentInfo(@RequestParam String orderNo){
		Namesegment namesegment = kudosService.getNameSegmentInfo(orderNo);
		return JsonResult.ok(namesegment);
	}
	//基本数据-IDSegment
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getIdsegmentInfo")
	public JsonResult getIdsegmentInfo(@RequestParam String orderNo){
		List<Idsegment> idsegment = kudosService.getIdsegmentInfo(orderNo);
		return JsonResult.ok(idsegment,idsegment.size());
	}
	//基本数据-TelephoneSegment   已放置Creditreport中，需解析
//	@GetMapping("/users-anon/getTelephoneSegmentInfo")
//	public JsonResult getTelephoneSegmentInfo(@RequestParam String orderNo){
//		Telephonesegment telephonesegment = kudosService.getTelephoneSegmentInfo(orderNo);
//		return JsonResult.ok(telephonesegment);
//	}
	//基本数据-TelephoneSegment手机号  需解析
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getTelephoneSegmentInfo")
	public JsonResult getTelephoneSegmentInfo(@RequestParam String orderNo){
		Creditreport creditreport = kudosService.getCreditreportInfo(orderNo);
		List<Telephonesegment> telephonesegmentList = new ArrayList<>();
		if (creditreport!=null && creditreport.getTelephonesegment()!=null) {
			if (creditreport.getTelephonesegment().contains("[")) {
				com.alibaba.fastjson.JSONArray data = com.alibaba.fastjson.JSONObject.parseArray(creditreport.getTelephonesegment());
				for (Object object : data) {
					Telephonesegment telephonesegment = new Telephonesegment();
					JSONObject jsonObject = (JSONObject) object;
					if (jsonObject.getString("TelephoneNumberFieldLength")!=null) {
						telephonesegment.setTelephonenumberfieldlength(jsonObject.getString("TelephoneNumberFieldLength"));
					}
					if (jsonObject.getString("Length")!=null) {
						telephonesegment.setLength(jsonObject.getString("Length"));
					}
					if (jsonObject.getString("SegmentTag")!=null) {
						telephonesegment.setSegmenttag(jsonObject.getString("SegmentTag"));
					}
					if (jsonObject.getString("TelephoneType")!=null) {
						telephonesegment.setTelephonetype(jsonObject.getString("TelephoneType"));
					}
					if (jsonObject.getString("TelephoneNumber")!=null) {
						telephonesegment.setTelephonenumber(jsonObject.getString("TelephoneNumber"));
					}
					if (jsonObject.getString("EnrichedThroughEnquiry")!=null) {
						telephonesegment.setEnrichedthroughenquiry(jsonObject.getString("EnrichedThroughEnquiry"));
					}
					if (jsonObject.getString("TelephoneExtension")!=null) {
						telephonesegment.setTelephoneextension(jsonObject.getString("TelephoneExtension"));
					}
					if (jsonObject.getString("TelephoneExtensionFieldLength")!=null) {
						telephonesegment.setTelephoneextensionfieldlength(jsonObject.getString("TelephoneExtensionFieldLength"));
					}
					telephonesegmentList.add(telephonesegment);
				}
			}else {
				Telephonesegment telephonesegment = new Telephonesegment();
				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(creditreport.getTelephonesegment());
				if (jsonObject.get("EnrichedThroughEnquiry")!=null) {
					telephonesegment.setEnrichedthroughenquiry(jsonObject.get("EnrichedThroughEnquiry").toString());
				}
				if (jsonObject.get("Length")!=null) {
					telephonesegment.setLength(jsonObject.get("Length").toString());				
				}
				if (jsonObject.get("SegmentTag")!=null) {
					telephonesegment.setSegmenttag(jsonObject.get("SegmentTag").toString());
				}
				if (jsonObject.get("TelephoneExtension")!=null) {
					telephonesegment.setTelephoneextension(jsonObject.get("TelephoneExtension").toString());
				}
				if (jsonObject.get("TelephoneExtensionFieldLength")!=null) {
					telephonesegment.setTelephoneextensionfieldlength(jsonObject.get("TelephoneExtensionFieldLength").toString());
				}
				if (jsonObject.get("TelephoneNumber")!=null) {
					telephonesegment.setTelephonenumber(jsonObject.get("TelephoneNumber").toString());
				}
				if (jsonObject.get("TelephoneNumberFieldLength")!=null) {
					telephonesegment.setTelephonenumberfieldlength(jsonObject.get("TelephoneNumberFieldLength").toString());
				}
				if (jsonObject.get("TelephoneType")!=null) {
					telephonesegment.setTelephonetype(jsonObject.get("TelephoneType").toString());
				}
				telephonesegmentList.add(telephonesegment);
			}
		}
		return JsonResult.ok(telephonesegmentList,telephonesegmentList.size());
	}
	//基本数据-EmailContactSegment
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getEmailContactSegmentInfo")
	public JsonResult getEmailContactSegmentInfo(@RequestParam String orderNo){
		List<EmailContactSegment> emailContactSegment = kudosService.getEmailContactSegmentInfo(orderNo);
		return JsonResult.ok(emailContactSegment,emailContactSegment.size());
	}
	//地址
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getAddressInfo")
	public JsonResult getAddressInfo(@RequestParam String orderNo){
		List<Address> addressesList = kudosService.getAddressInfo(orderNo);
		return JsonResult.ok(addressesList,addressesList.size());
	}
	//工作信息
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getEmploymentSegmentInfo")
	public JsonResult getEmploymentSegmentInfo(@RequestParam String orderNo){
		List<Employmentsegment> employmentsegment = kudosService.getEmploymentSegmentInfo(orderNo);
		return JsonResult.ok(employmentsegment,employmentsegment.size());
	}
	private static TreeMap<String, String> strToMap(String paymenthistory,String paymentHistoryStartDate){
		TreeMap<String, String> map = new TreeMap<>();
		String month = paymentHistoryStartDate.substring(2,4);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH,Integer.valueOf(month));
		String year = paymentHistoryStartDate.substring(4);
		int z = 0;
		for(int i=0;i<paymenthistory.length()/3;i++){
			String dt = "";
			String a = paymenthistory.substring(z,z+3);
			z = z+3;
			calendar.add(Calendar.MONTH, -1);
			dt = String.valueOf(calendar.getTime().getMonth());
			if (dt.equals("0")) {
				dt = "01";
			}else if (dt.equals("1")) {
				dt = "02";
			}else if (dt.equals("2")) {
				dt = "03";
			}else if (dt.equals("3")) {
				dt = "04";
			}else if (dt.equals("4")) {
				dt = "05";
			}else if (dt.equals("5")) {
				dt = "06";
			}else if (dt.equals("6")) {
				dt = "07";
			}else if (dt.equals("7")) {
				dt = "08";
			}else if (dt.equals("8")) {
				dt = "09";
			}else if (dt.equals("9")) {
				dt = "10";
			}else if (dt.equals("10")) {
				dt = "11";
			}else if (dt.equals("11")) {
				dt = "12";
				year = Integer.valueOf(year)-1+"";//十二月后，年减一
			}
			map.put(year+dt, a);
		}
		return map;
	}
	//贷款历史
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")
	@GetMapping("/users-anon/getAccountNonsummarySegmentFieldsInfo")
	public JsonResult getAccountNonsummarySegmentFieldsInfo(@RequestParam String orderNo){
//		AccountNonsummarySegmentFields nonsummarySegmentFields = kudosService.getAccountNonsummarySegmentFieldsInfo(orderNo);//此数据已放置creditreport表account字段中
		Creditreport creditreport = kudosService.getCreditreportInfo(orderNo);
		List<AccountNonsummarySegmentFields> nonsummarySegmentFieldsList = new ArrayList<>();
		if (creditreport!=null && creditreport.getAccount()!=null) {
			int currentBalance = 0;//当前总金额
			int totalactiveAccounts = 0;//活跃账户数
			int zeroBalanceAccounts = 0;//余额为0账户数
			int overDueAccounts = 0;//账户逾期数
			int totalAccountsSanctioned = 0;//放款总金额
			int overdueamt = 0;//逾期总金额
			String dateOpenedRecent = "";//最近一次放款时间
			List<String> recentList = new ArrayList<>();
			Map<String, String> maps = new HashMap<>();
			if (creditreport.getAccount().contains("[")) {
				com.alibaba.fastjson.JSONArray data = com.alibaba.fastjson.JSONObject.parseArray(creditreport.getAccount());
				for(Object object : data){
					JSONObject jsonObject = (JSONObject) object;
					AccountNonsummarySegmentFields ansf = new AccountNonsummarySegmentFields();
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateOpenedOrDisbursed")!=null) {
						ansf.setDateopenedordisbursed(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateOpenedOrDisbursed").toString());
						DateFormat format = new SimpleDateFormat("yyyyMMdd");
						DateFormat format1 = new SimpleDateFormat("ddMMyyyy");
						try {
							Date date = format1.parse(ansf.getDateopenedordisbursed());
							dateOpenedRecent = format.format(date);
							recentList.add(dateOpenedRecent);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AccountType")!=null) {
						KdCibilAccountType cibilAccountType = new KdCibilAccountType();
						cibilAccountType.setName("AccountType");
						cibilAccountType.setCode(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AccountType").toString());
						KdCibilAccountType accountType = kdCibilAccountTypeDao.selectOne(cibilAccountType);
						ansf.setAccounttype(accountType.getValue());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ActualPaymentAmount")!=null) {
						ansf.setActualpaymentamount(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ActualPaymentAmount").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ActualPaymentAmountFieldLength")!=null) {
						ansf.setActualpaymentamountfieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ActualPaymentAmountFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("CurrentBalance")!=null) {
						ansf.setCurrentbalance(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("CurrentBalance").toString());
						currentBalance += Integer.valueOf(ansf.getCurrentbalance());//统计总金额
						if (Integer.valueOf(ansf.getCurrentbalance())>0) {//活跃账户总数
							totalactiveAccounts++;
						}
						if (Integer.valueOf(ansf.getCurrentbalance())<=0) {
							zeroBalanceAccounts++;
						}
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("CurrentBalanceFieldLength")!=null) {
						ansf.setCurrentbalancefieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("CurrentBalanceFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateClosed")!=null) {
						ansf.setDateclosed(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateClosed").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateOfLastPayment")!=null) {
						ansf.setDateoflastpayment(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateOfLastPayment").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateReportedAndCertified")!=null) {
						ansf.setDatereportedandcertified(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("DateReportedAndCertified").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("EmiAmount")!=null) {
						ansf.setEmiamount(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("EmiAmount").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("EmiAmountFieldLength")!=null) {
						ansf.setEmiamountfieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("EmiAmountFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("HighCreditOrSanctionedAmount")!=null) {
						ansf.setHighcreditorsanctionedamount(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("HighCreditOrSanctionedAmount").toString());
						totalAccountsSanctioned += Integer.valueOf(ansf.getHighcreditorsanctionedamount());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("HighCreditOrSanctionedAmountFieldLength")!=null) {
						ansf.setHighcreditorsanctionedamountfieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("HighCreditOrSanctionedAmountFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("OwenershipIndicator")!=null) {
						KdCibilAccountType cibilAccountType = new KdCibilAccountType();
						cibilAccountType.setName("OwnershipIndicator");
						cibilAccountType.setCode(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("OwenershipIndicator").toString());
						KdCibilAccountType accountType = kdCibilAccountTypeDao.selectOne(cibilAccountType);
						ansf.setOwenershipindicator(accountType.getValue());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentFrequency")!=null) {
						ansf.setPaymentfrequency(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentFrequency").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory1")!=null) {
						ansf.setPaymenthistory1(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory1").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory1FieldLength")!=null) {
						ansf.setPaymenthistory1fieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory1FieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory2")!=null) {
						ansf.setPaymenthistory2(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory2").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory2FieldLength")!=null) {
						ansf.setPaymenthistory2fieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistory2FieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistoryEndDate")!=null) {
						ansf.setPaymenthistoryenddate(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistoryEndDate").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistoryStartDate")!=null) {
						ansf.setPaymenthistorystartdate(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("PaymentHistoryStartDate").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RepaymentTenure")!=null) {
						ansf.setRepaymenttenure(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RepaymentTenure").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RepaymentTenureFieldLength")!=null) {
						ansf.setRepaymenttenurefieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RepaymentTenureFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ReportingMemberShortName")!=null) {
						ansf.setReportingmembershortname(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ReportingMemberShortName").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ReportingMemberShortNameFieldLength")!=null) {
						ansf.setReportingmembershortnamefieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("ReportingMemberShortNameFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("TypeOfCollateral")!=null) {
						ansf.setTypeofcollateral(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("TypeOfCollateral").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("TypeOfCollateralFieldLength")!=null) {
						ansf.setTypeofcollateralfieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("TypeOfCollateralFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AmountOverdueFieldLength")!=null) {
						ansf.setAmountoverduefieldlength(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AmountOverdueFieldLength").toString());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AmountOverdue")!=null) {
						ansf.setAmountoverdue(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("AmountOverdue").toString());
						if (Integer.valueOf(ansf.getAmountoverdue()) > 0) {//逾期账户个数				
							overDueAccounts ++;
						}
						overdueamt += Integer.valueOf(ansf.getAmountoverdue());
					}
					if (jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RateOfInterest")!=null) {
						ansf.setRateofinterest(jsonObject.getJSONObject("Account_NonSummary_Segment_Fields").get("RateOfInterest").toString());
					}
					nonsummarySegmentFieldsList.add(ansf);
					maps.put("totalAccounts", String.valueOf(nonsummarySegmentFieldsList.size()));
					maps.put("currentBalance", String.valueOf(currentBalance));
					maps.put("totalactiveAccounts", String.valueOf(totalactiveAccounts));
					maps.put("zeroBalanceAccounts", String.valueOf(zeroBalanceAccounts));
					maps.put("overDueAccounts", String.valueOf(overDueAccounts));
					maps.put("totalAccountsSanctioned", String.valueOf(totalAccountsSanctioned));
					maps.put("overdueamt", String.valueOf(overdueamt));
					Collections.reverse(recentList);
					maps.put("dateOpenedRecent", recentList.get(0));
					Collections.sort(recentList);
					maps.put("dateOpenedOldest", recentList.get(0));
					ansf.setMaps(maps);//贷款历史总览
					String paymenthistory2 = "";
					if (ansf.getPaymenthistory2()!=null) {
						paymenthistory2 = ansf.getPaymenthistory2();
					}
					String paymenthistory1 = "";
					if (ansf.getPaymenthistory1()!=null) {
						paymenthistory1 = ansf.getPaymenthistory1();
					}
					String paymenthistory = paymenthistory2+paymenthistory1;
					ansf.setMap(strToMap(paymenthistory, ansf.getPaymenthistorystartdate()));//解析贷款历史PaymentHistory1
				}
			}else{
				AccountNonsummarySegmentFields ansf = new AccountNonsummarySegmentFields();
				com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(creditreport.getAccount());
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateOpenedOrDisbursed")!=null) {
					ansf.setDateopenedordisbursed(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateOpenedOrDisbursed"));
					DateFormat format = new SimpleDateFormat("yyyyMMdd");
					DateFormat format1 = new SimpleDateFormat("ddMMyyyy");
					try {
						Date date = format1.parse(ansf.getDateopenedordisbursed());
						dateOpenedRecent = format.format(date);
						recentList.add(dateOpenedRecent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AccountType")!=null) {
					KdCibilAccountType cibilAccountType = new KdCibilAccountType();
					cibilAccountType.setName("AccountType");
					cibilAccountType.setCode(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AccountType"));
					KdCibilAccountType accountType = kdCibilAccountTypeDao.selectOne(cibilAccountType);
					ansf.setAccounttype(accountType.getValue());
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ActualPaymentAmount")!=null) {
					ansf.setActualpaymentamount(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ActualPaymentAmount"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ActualPaymentAmountFieldLength")!=null) {
					ansf.setActualpaymentamountfieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ActualPaymentAmountFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("CurrentBalance")!=null) {
					ansf.setCurrentbalance(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("CurrentBalance"));
					currentBalance += Integer.valueOf(ansf.getCurrentbalance());//统计总金额
					if (Integer.valueOf(ansf.getCurrentbalance())>0) {//活跃账户总数
						totalactiveAccounts++;
					}
					if (Integer.valueOf(ansf.getCurrentbalance())<=0) {
						zeroBalanceAccounts++;
					}
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("CurrentBalanceFieldLength")!=null) {
					ansf.setCurrentbalancefieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("CurrentBalanceFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateClosed")!=null) {
					ansf.setDateclosed(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateClosed"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateOfLastPayment")!=null) {
					ansf.setDateoflastpayment(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateOfLastPayment"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateReportedAndCertified")!=null) {
					ansf.setDatereportedandcertified(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("DateReportedAndCertified"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("EmiAmount")!=null) {
					ansf.setEmiamount(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("EmiAmount"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("EmiAmountFieldLength")!=null) {
					ansf.setEmiamountfieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("EmiAmountFieldLength").toString());
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("HighCreditOrSanctionedAmount")!=null) {
					ansf.setHighcreditorsanctionedamount(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("HighCreditOrSanctionedAmount"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("HighCreditOrSanctionedAmountFieldLength")!=null) {
					ansf.setHighcreditorsanctionedamountfieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("HighCreditOrSanctionedAmountFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("OwenershipIndicator")!=null) {
					KdCibilAccountType cibilAccountType = new KdCibilAccountType();
					cibilAccountType.setName("OwnershipIndicator");
					cibilAccountType.setCode(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("OwenershipIndicator"));
					KdCibilAccountType accountType = kdCibilAccountTypeDao.selectOne(cibilAccountType);
					ansf.setOwenershipindicator(accountType.getValue());
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentFrequency")!=null) {
					ansf.setPaymentfrequency(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentFrequency"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory1")!=null) {
					ansf.setPaymenthistory1(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory1"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory1FieldLength")!=null) {
					ansf.setPaymenthistory1fieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory1FieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory2")!=null) {
					ansf.setPaymenthistory2(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory2"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory2FieldLength")!=null) {
					ansf.setPaymenthistory2fieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistory2FieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistoryEndDate")!=null) {
					ansf.setPaymenthistoryenddate(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistoryEndDate"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistoryStartDate")!=null) {
					ansf.setPaymenthistorystartdate(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("PaymentHistoryStartDate"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RepaymentTenure")!=null) {
					ansf.setRepaymenttenure(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RepaymentTenure"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RepaymentTenureFieldLength")!=null) {
					ansf.setRepaymenttenurefieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RepaymentTenureFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ReportingMemberShortName")!=null) {
					ansf.setReportingmembershortname(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ReportingMemberShortName"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ReportingMemberShortNameFieldLength")!=null) {
					ansf.setReportingmembershortnamefieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("ReportingMemberShortNameFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("TypeOfCollateral")!=null) {
					ansf.setTypeofcollateral(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("TypeOfCollateral"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("TypeOfCollateralFieldLength")!=null) {
					ansf.setTypeofcollateralfieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("TypeOfCollateralFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AmountOverdueFieldLength")!=null) {
					ansf.setAmountoverduefieldlength(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AmountOverdueFieldLength"));
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AmountOverdue")!=null) {
					ansf.setAmountoverdue(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("AmountOverdue"));
					if (Integer.valueOf(ansf.getAmountoverdue()) > 0) {//逾期账户个数				
						overDueAccounts ++;
					}
					overdueamt += Integer.valueOf(ansf.getAmountoverdue());
				}
				if (data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RateOfInterest")!=null) {
					ansf.setRateofinterest(data.getJSONObject("Account_NonSummary_Segment_Fields").getString("RateOfInterest"));
				}
				nonsummarySegmentFieldsList.add(ansf);
				maps.put("totalAccounts", String.valueOf(nonsummarySegmentFieldsList.size()));
				maps.put("currentBalance", String.valueOf(currentBalance));
				maps.put("totalactiveAccounts", String.valueOf(totalactiveAccounts));
				maps.put("zeroBalanceAccounts", String.valueOf(zeroBalanceAccounts));
				maps.put("overDueAccounts", String.valueOf(overDueAccounts));
				maps.put("totalAccountsSanctioned", String.valueOf(totalAccountsSanctioned));
				maps.put("overdueamt", String.valueOf(overdueamt));
				Collections.sort(recentList);
				maps.put("dateOpenedRecent", recentList.get(0));
				Collections.reverse(recentList);
				maps.put("dateOpenedOldest", recentList.get(0));
				ansf.setMaps(maps);
				String paymenthistory2 = "";
				if (ansf.getPaymenthistory2()!=null) {
					paymenthistory2 = ansf.getPaymenthistory2();
				}
				String paymenthistory1 = "";
				if (ansf.getPaymenthistory1()!=null) {
					paymenthistory1 = ansf.getPaymenthistory1();
				}
				String paymenthistory = paymenthistory2+paymenthistory1;
				ansf.setMap(strToMap(paymenthistory, ansf.getPaymenthistorystartdate()));//解析贷款历史PaymentHistory1
			}
		}
		return JsonResult.ok(nonsummarySegmentFieldsList,nonsummarySegmentFieldsList.size());
	}
	
	//查询历史
	@PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
			"or (hasAuthority('audit:order:detail:cibil') and hasPermission(#orderNo, 'r'))")	@GetMapping("/users-anon/getEnquiryInfo")
	public JsonResult getEnquiryInfo(@RequestParam String orderNo){
		List<Enquiry> enquiryList = kudosService.getEnquiryInfo(orderNo);
		return JsonResult.ok(enquiryList,enquiryList.size());
	}
	
	@GetMapping("/users-anon/kudosCibilInfo")
	public void kudosCibilInfo(@RequestParam String orderNo){
            appUserInfoService.strTokudos7(orderNo);
	}
	//Total Inquiry Information   近XXX月的查询次数
	@GetMapping("/users-anon/getEnquiryInformation")
	public JsonResult getEnquiryInformation(@RequestParam String orderNo){
		List<Enquiry> enquiryList = kudosService.getEnquiryInfo(orderNo);
		Double amount = 0.0;
		TreeMap<String, String> map = new TreeMap<>();
		map.put("1", searchEnquiryCountAndAmount(enquiryList, amount, 1)+"");
		map.put("3", searchEnquiryCountAndAmount(enquiryList, amount, 3)+"");
		map.put("6", searchEnquiryCountAndAmount(enquiryList, amount, 6)+"");
		map.put("12", searchEnquiryCountAndAmount(enquiryList, amount, 12)+"");
		map.put("18", searchEnquiryCountAndAmount(enquiryList, amount, 18)+"");
		map.put("24", searchEnquiryCountAndAmount(enquiryList, amount, 24)+"");
		return JsonResult.ok(map);
	}
	public Integer searchEnquiryCountAndAmount(List<Enquiry> enquiries, Double amount, int mothCount) {
	    if (CollectionUtils.isEmpty(enquiries)) {
	          return 0;
	     }
	     Double sumAmount = 0.0;
	     int count = 0;
	     Date now = new Date();
	     try {
	         for (int i = 0; i < mothCount; i++) {
	             for (Enquiry enquiry : enquiries) {
	                 String da = enquiry.getDateofenquiryfields();
	                 if (da.substring(2).equals(getLastMonth(now, "MMyyyy",i))) {
	                     //统计含金额时小于 一千时 沪铝
	                     if (amount > 0.0 && new Double(enquiry.getEnquiryamount()) >= 1000.0) {
	                         sumAmount = sumAmount + new Double(enquiry.getEnquiryamount());
	                         count++;
	                     } else {
	                         count++;
	                     }
	                 }
	             }
	         }
	         if (amount > 0.0 && count > 0) {
	             Double avgAmount = sumAmount / count;
	             if (avgAmount > amount) {
	                 return 1;
	             } else {
	                 return 0;
	             }
	         }
	         return count;
	     } catch (Exception e) {
	         return 0;
	     }
	}
    public static String getLastMonth(Date data, String formart,int i) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -i);
        date = calendar.getTime();
        return DateUtil.getStringDate(date, formart);
    }
}
