package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Table(name="kudos_applicant")
public class Applicant extends CibilOrderEntity{

	private static final long serialVersionUID = 4397589920301965367L;
	@JsonProperty("Accounts")
    private Accounts accounts;
    @JsonProperty("Addresses")
    private Addresses addresses;
    @JsonProperty("Telephones")
    private Telephones telephones;
    @JsonProperty("Identifiers")
    private Identifiers identifiers;
    @JsonProperty("CompanyName")
    private String companyname;
    @JsonProperty("EmailAddress")
    private String emailaddress;
    @JsonProperty("Gender")
    private String gender;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("ApplicantLastName")
    private String applicantlastname;
    @JsonProperty("ApplicantMiddleName")
    private String applicantmiddlename;
    @JsonProperty("ApplicantFirstName")
    private String applicantfirstname;
    @JsonProperty("ApplicantType")
    private String applicanttype;
    @JsonProperty("DsCibilBureau")
    private Dscibilbureau dscibilbureau;
    
    @JsonProperty("AddressLine1")
    private String addressline1;
    @JsonProperty("AddressLine2")
    private String addressline2;
    @JsonProperty("AddressLine3")
    private String addressline3;
    @JsonProperty("City")
    private String city;
    @JsonProperty("State")
    private String state;
    @JsonProperty("PinCode")
    private String pincode;
    @JsonProperty("MobilePhone")
    private String mobilephone;
    @JsonProperty("PanNumber")
    private String pannumber;
    
    public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	public String getAddressline3() {
		return addressline3;
	}
	public void setAddressline3(String addressline3) {
		this.addressline3 = addressline3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getPannumber() {
		return pannumber;
	}
	public void setPannumber(String pannumber) {
		this.pannumber = pannumber;
	}
	public void setAccounts(Accounts accounts) {
         this.accounts = accounts;
     }
     public Accounts getAccounts() {
         return accounts;
     }

    public void setAddresses(Addresses addresses) {
         this.addresses = addresses;
     }
     public Addresses getAddresses() {
         return addresses;
     }

    public void setTelephones(Telephones telephones) {
         this.telephones = telephones;
     }
     public Telephones getTelephones() {
         return telephones;
     }

    public void setIdentifiers(Identifiers identifiers) {
         this.identifiers = identifiers;
     }
     public Identifiers getIdentifiers() {
         return identifiers;
     }

    public void setCompanyname(String companyname) {
         this.companyname = companyname;
     }
     public String getCompanyname() {
         return companyname;
     }

    public void setEmailaddress(String emailaddress) {
         this.emailaddress = emailaddress;
     }
     public String getEmailaddress() {
         return emailaddress;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

    public void setDateofbirth(String dateofbirth) {
         this.dateofbirth = dateofbirth;
     }
     public String getDateofbirth() {
         return dateofbirth;
     }

    public void setApplicantlastname(String applicantlastname) {
         this.applicantlastname = applicantlastname;
     }
     public String getApplicantlastname() {
         return applicantlastname;
     }

    public void setApplicantmiddlename(String applicantmiddlename) {
         this.applicantmiddlename = applicantmiddlename;
     }
     public String getApplicantmiddlename() {
         return applicantmiddlename;
     }

    public void setApplicantfirstname(String applicantfirstname) {
         this.applicantfirstname = applicantfirstname;
     }
     public String getApplicantfirstname() {
         return applicantfirstname;
     }

    public void setApplicanttype(String applicanttype) {
         this.applicanttype = applicanttype;
     }
     public String getApplicanttype() {
         return applicanttype;
     }

    public void setDscibilbureau(Dscibilbureau dscibilbureau) {
         this.dscibilbureau = dscibilbureau;
     }
     public Dscibilbureau getDscibilbureau() {
         return dscibilbureau;
     }

}