package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Table(name="kudos_application_data")
public class Applicationdata extends CibilOrderEntity {
	private static final long serialVersionUID = -5323631446254602154L;
	@JsonProperty("FormattedReport")
    private String formattedreport;
    @JsonProperty("MFIBranchReferenceNo")
    private String mfibranchreferenceno;
    @JsonProperty("MFICenterReferenceNo")
    private String mficenterreferenceno;
    @JsonProperty("MFILoanPurpose")
    private String mfiloanpurpose;
    @JsonProperty("MFIEnquiryAmount")
    private String mfienquiryamount;
    @JsonProperty("BranchReferenceNo")
    private String branchreferenceno;
    @JsonProperty("CenterReferenceNo")
    private String centerreferenceno;
    @JsonProperty("MFIMemberCode")
    private String mfimembercode;
    @JsonProperty("IDVPDFReport")
    private String idvpdfreport;
    @JsonProperty("MFIPDFReport")
    private String mfipdfreport;
    @JsonProperty("CIBILPDFReport")
    private String cibilpdfreport;
    @JsonProperty("MFIBureauFlag")
    private String mfibureauflag;
    @JsonProperty("IDVerificationFlag")
    private String idverificationflag;
    @JsonProperty("DSTuNtcFlag")
    private String dstuntcflag;
    @JsonProperty("CibilBureauFlag")
    private String cibilbureauflag;
    @JsonProperty("GSTStateCode")
    private String gststatecode;
    @JsonProperty("ConsumerConsentForUIDAIAuthentication")
    private String consumerconsentforuidaiauthentication;
    @JsonProperty("RepaymentPeriodInMonths")
    private String repaymentperiodinmonths;
    @JsonProperty("ScoreType")
    private String scoretype;
    @JsonProperty("IDVMemberCode")
    private String idvmembercode;
    @JsonProperty("NTCProductType")
    private String ntcproducttype;
    @JsonProperty("Income")
    private String income;
    @JsonProperty("ReferenceNumber")
    private String referencenumber;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("MemberCode")
    private String membercode;
    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("Purpose")
    private String purpose;
    @JsonProperty("User")
    private String user;
    @JsonProperty("BusinessUnitId")
    private String businessunitid;
    @JsonProperty("ApplicationId")
    private String applicationid;
    @JsonProperty("SolutionSetId")
    private String solutionsetid;
    @JsonProperty("EnvironmentTypeId")
    private String environmenttypeid;
    @JsonProperty("EnvironmentType")
    private String environmenttype;
    @JsonProperty("Milestone")
    private String milestone;//Milestone
    @JsonProperty("Start")
    private String start;
    @JsonProperty("InputValReasonCodes")
    private String inputvalreasoncodes;
//    private InputValReasonCodes inputvalreasoncodes;//实体类
    @JsonProperty("DTTrail")
    private Dttrail dttrail;//Dttrail
    
    @JsonProperty("SkipPreQDEFlag")
    private String skippreqdeflag;
    @JsonProperty("SkipEkycFlag")
    private String skipekycflag;
    @JsonProperty("SkipVelocityCheckFlag")
    private String skipvelocitycheckflag;
    @JsonProperty("SkipCibilBureauFlag")
    private String skipcibilbureauflag;
    @JsonProperty("SkipMultiBureauFlag")
    private String skipmultibureauflag;
    @JsonProperty("SkipDsCrifIndFlag")
    private String skipdscrifindflag;
    @JsonProperty("SkipDsEverifyFlag")
    private String skipdseverifyflag;
    @JsonProperty("SkipTuVerificationFlag")
    private String skiptuverificationflag;
    @JsonProperty("SkipCreditRiskPoliciesFlag")
    private String skipcreditriskpoliciesflag;
    @JsonProperty("SkipIndiaInputQueueQdeFlag")
    private String skipindiainputqueueqdeflag;
    @JsonProperty("SkipDSTuNtcFlag")
    private String skipdstuntcflag;
    @JsonProperty("SkipCPVPoliciesQDEFlag")
    private String skipcpvpoliciesqdeflag;
    @JsonProperty("SkipIDVerificationFlag")
    private String skipidverificationflag;
    @JsonProperty("SkipDSTuIDVisionFlag")
    private String skipdstuidvisionflag;
    @JsonProperty("SkipMFICIRPuller")
    private String skipmficirpuller;
    @JsonProperty("SkipDVFlag")
    private String skipdvflag;
    @JsonProperty("SkipQDEValidationQ")
    private String skipqdevalidationq;
    @JsonProperty("SkipQDEResultsQ")
    private String skipqderesultsq;
    @JsonProperty("SkipDocVerificationFlag")
    private String skipdocverificationflag;
    @JsonProperty("SkipIndiaInputQueueDdeFlag")
    private String skipindiainputqueueddeflag;
    @JsonProperty("SkipReferQueue")
    private String skipreferqueue;
    
    public String getSkippreqdeflag() {
		return skippreqdeflag;
	}
	public void setSkippreqdeflag(String skippreqdeflag) {
		this.skippreqdeflag = skippreqdeflag;
	}
	public String getSkipekycflag() {
		return skipekycflag;
	}
	public void setSkipekycflag(String skipekycflag) {
		this.skipekycflag = skipekycflag;
	}
	public String getSkipvelocitycheckflag() {
		return skipvelocitycheckflag;
	}
	public void setSkipvelocitycheckflag(String skipvelocitycheckflag) {
		this.skipvelocitycheckflag = skipvelocitycheckflag;
	}
	public String getSkipcibilbureauflag() {
		return skipcibilbureauflag;
	}
	public void setSkipcibilbureauflag(String skipcibilbureauflag) {
		this.skipcibilbureauflag = skipcibilbureauflag;
	}
	public String getSkipmultibureauflag() {
		return skipmultibureauflag;
	}
	public void setSkipmultibureauflag(String skipmultibureauflag) {
		this.skipmultibureauflag = skipmultibureauflag;
	}
	public String getSkipdscrifindflag() {
		return skipdscrifindflag;
	}
	public void setSkipdscrifindflag(String skipdscrifindflag) {
		this.skipdscrifindflag = skipdscrifindflag;
	}
	public String getSkipdseverifyflag() {
		return skipdseverifyflag;
	}
	public void setSkipdseverifyflag(String skipdseverifyflag) {
		this.skipdseverifyflag = skipdseverifyflag;
	}
	public String getSkiptuverificationflag() {
		return skiptuverificationflag;
	}
	public void setSkiptuverificationflag(String skiptuverificationflag) {
		this.skiptuverificationflag = skiptuverificationflag;
	}
	public String getSkipcreditriskpoliciesflag() {
		return skipcreditriskpoliciesflag;
	}
	public void setSkipcreditriskpoliciesflag(String skipcreditriskpoliciesflag) {
		this.skipcreditriskpoliciesflag = skipcreditriskpoliciesflag;
	}
	public String getSkipindiainputqueueqdeflag() {
		return skipindiainputqueueqdeflag;
	}
	public void setSkipindiainputqueueqdeflag(String skipindiainputqueueqdeflag) {
		this.skipindiainputqueueqdeflag = skipindiainputqueueqdeflag;
	}
	public String getSkipdstuntcflag() {
		return skipdstuntcflag;
	}
	public void setSkipdstuntcflag(String skipdstuntcflag) {
		this.skipdstuntcflag = skipdstuntcflag;
	}
	public String getSkipcpvpoliciesqdeflag() {
		return skipcpvpoliciesqdeflag;
	}
	public void setSkipcpvpoliciesqdeflag(String skipcpvpoliciesqdeflag) {
		this.skipcpvpoliciesqdeflag = skipcpvpoliciesqdeflag;
	}
	public String getSkipidverificationflag() {
		return skipidverificationflag;
	}
	public void setSkipidverificationflag(String skipidverificationflag) {
		this.skipidverificationflag = skipidverificationflag;
	}
	public String getSkipdstuidvisionflag() {
		return skipdstuidvisionflag;
	}
	public void setSkipdstuidvisionflag(String skipdstuidvisionflag) {
		this.skipdstuidvisionflag = skipdstuidvisionflag;
	}
	public String getSkipmficirpuller() {
		return skipmficirpuller;
	}
	public void setSkipmficirpuller(String skipmficirpuller) {
		this.skipmficirpuller = skipmficirpuller;
	}
	public String getSkipdvflag() {
		return skipdvflag;
	}
	public void setSkipdvflag(String skipdvflag) {
		this.skipdvflag = skipdvflag;
	}
	public String getSkipqdevalidationq() {
		return skipqdevalidationq;
	}
	public void setSkipqdevalidationq(String skipqdevalidationq) {
		this.skipqdevalidationq = skipqdevalidationq;
	}
	public String getSkipqderesultsq() {
		return skipqderesultsq;
	}
	public void setSkipqderesultsq(String skipqderesultsq) {
		this.skipqderesultsq = skipqderesultsq;
	}
	public String getSkipdocverificationflag() {
		return skipdocverificationflag;
	}
	public void setSkipdocverificationflag(String skipdocverificationflag) {
		this.skipdocverificationflag = skipdocverificationflag;
	}
	public String getSkipindiainputqueueddeflag() {
		return skipindiainputqueueddeflag;
	}
	public void setSkipindiainputqueueddeflag(String skipindiainputqueueddeflag) {
		this.skipindiainputqueueddeflag = skipindiainputqueueddeflag;
	}
	public String getSkipreferqueue() {
		return skipreferqueue;
	}
	public void setSkipreferqueue(String skipreferqueue) {
		this.skipreferqueue = skipreferqueue;
	}
	public void setFormattedreport(String formattedreport) {
         this.formattedreport = formattedreport;
     }
     public String getFormattedreport() {
         return formattedreport;
     }

    public void setMfibranchreferenceno(String mfibranchreferenceno) {
         this.mfibranchreferenceno = mfibranchreferenceno;
     }
     public String getMfibranchreferenceno() {
         return mfibranchreferenceno;
     }

    public void setMficenterreferenceno(String mficenterreferenceno) {
         this.mficenterreferenceno = mficenterreferenceno;
     }
     public String getMficenterreferenceno() {
         return mficenterreferenceno;
     }

    public void setMfiloanpurpose(String mfiloanpurpose) {
         this.mfiloanpurpose = mfiloanpurpose;
     }
     public String getMfiloanpurpose() {
         return mfiloanpurpose;
     }

    public void setMfienquiryamount(String mfienquiryamount) {
         this.mfienquiryamount = mfienquiryamount;
     }
     public String getMfienquiryamount() {
         return mfienquiryamount;
     }

    public void setBranchreferenceno(String branchreferenceno) {
         this.branchreferenceno = branchreferenceno;
     }
     public String getBranchreferenceno() {
         return branchreferenceno;
     }

    public void setCenterreferenceno(String centerreferenceno) {
         this.centerreferenceno = centerreferenceno;
     }
     public String getCenterreferenceno() {
         return centerreferenceno;
     }

    public void setMfimembercode(String mfimembercode) {
         this.mfimembercode = mfimembercode;
     }
     public String getMfimembercode() {
         return mfimembercode;
     }

    public void setIdvpdfreport(String idvpdfreport) {
         this.idvpdfreport = idvpdfreport;
     }
     public String getIdvpdfreport() {
         return idvpdfreport;
     }

    public void setMfipdfreport(String mfipdfreport) {
         this.mfipdfreport = mfipdfreport;
     }
     public String getMfipdfreport() {
         return mfipdfreport;
     }

    public void setCibilpdfreport(String cibilpdfreport) {
         this.cibilpdfreport = cibilpdfreport;
     }
     public String getCibilpdfreport() {
         return cibilpdfreport;
     }

    public void setMfibureauflag(String mfibureauflag) {
         this.mfibureauflag = mfibureauflag;
     }
     public String getMfibureauflag() {
         return mfibureauflag;
     }

    public void setIdverificationflag(String idverificationflag) {
         this.idverificationflag = idverificationflag;
     }
     public String getIdverificationflag() {
         return idverificationflag;
     }

    public void setDstuntcflag(String dstuntcflag) {
         this.dstuntcflag = dstuntcflag;
     }
     public String getDstuntcflag() {
         return dstuntcflag;
     }

    public void setCibilbureauflag(String cibilbureauflag) {
         this.cibilbureauflag = cibilbureauflag;
     }
     public String getCibilbureauflag() {
         return cibilbureauflag;
     }

    public void setGststatecode(String gststatecode) {
         this.gststatecode = gststatecode;
     }
     public String getGststatecode() {
         return gststatecode;
     }

    public void setConsumerconsentforuidaiauthentication(String consumerconsentforuidaiauthentication) {
         this.consumerconsentforuidaiauthentication = consumerconsentforuidaiauthentication;
     }
     public String getConsumerconsentforuidaiauthentication() {
         return consumerconsentforuidaiauthentication;
     }

    public void setRepaymentperiodinmonths(String repaymentperiodinmonths) {
         this.repaymentperiodinmonths = repaymentperiodinmonths;
     }
     public String getRepaymentperiodinmonths() {
         return repaymentperiodinmonths;
     }

    public void setScoretype(String scoretype) {
         this.scoretype = scoretype;
     }
     public String getScoretype() {
         return scoretype;
     }

    public void setIdvmembercode(String idvmembercode) {
         this.idvmembercode = idvmembercode;
     }
     public String getIdvmembercode() {
         return idvmembercode;
     }

    public void setNtcproducttype(String ntcproducttype) {
         this.ntcproducttype = ntcproducttype;
     }
     public String getNtcproducttype() {
         return ntcproducttype;
     }

    public void setIncome(String income) {
         this.income = income;
     }
     public String getIncome() {
         return income;
     }

    public void setReferencenumber(String referencenumber) {
         this.referencenumber = referencenumber;
     }
     public String getReferencenumber() {
         return referencenumber;
     }

    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     }

    public void setMembercode(String membercode) {
         this.membercode = membercode;
     }
     public String getMembercode() {
         return membercode;
     }

    public void setAmount(String amount) {
         this.amount = amount;
     }
     public String getAmount() {
         return amount;
     }

    public void setPurpose(String purpose) {
         this.purpose = purpose;
     }
     public String getPurpose() {
         return purpose;
     }

    public void setUser(String user) {
         this.user = user;
     }
     public String getUser() {
         return user;
     }

    public void setBusinessunitid(String businessunitid) {
         this.businessunitid = businessunitid;
     }
     public String getBusinessunitid() {
         return businessunitid;
     }

    public void setApplicationid(String applicationid) {
         this.applicationid = applicationid;
     }
     public String getApplicationid() {
         return applicationid;
     }

    public void setSolutionsetid(String solutionsetid) {
         this.solutionsetid = solutionsetid;
     }
     public String getSolutionsetid() {
         return solutionsetid;
     }

    public void setEnvironmenttypeid(String environmenttypeid) {
         this.environmenttypeid = environmenttypeid;
     }
     public String getEnvironmenttypeid() {
         return environmenttypeid;
     }

    public void setEnvironmenttype(String environmenttype) {
         this.environmenttype = environmenttype;
     }
     public String getEnvironmenttype() {
         return environmenttype;
     }
     
    public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public void setStart(String start) {
         this.start = start;
     }
     public String getStart() {
         return start;
     }

    public void setInputvalreasoncodes(String inputvalreasoncodes) {
         this.inputvalreasoncodes = inputvalreasoncodes;
     }
     public String getInputvalreasoncodes() {
         return inputvalreasoncodes;
     }

    public void setDttrail(Dttrail dttrail) {
         this.dttrail = dttrail;
     }
     public Dttrail getDttrail() {
         return dttrail;
     }

}