
package com.kotak.schemas.cms_generic.payment_request;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>InstrumentType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="InstrumentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InstRefNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MinChar1_MaxChar100_ST"/&gt;
 *         &lt;element name="CompanyId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="CompBatchId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="ConfidentialInd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar1_ST" minOccurs="0"/&gt;
 *         &lt;element name="MyProdCode" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MinChar1_MaxChar20_ST"/&gt;
 *         &lt;element name="CompTransNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="PayMode" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="TxnAmnt" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}Amount_ST"/&gt;
 *         &lt;element name="AccountNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="DrRefNmbr" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar14_ST" minOccurs="0"/&gt;
 *         &lt;element name="DrDesc" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar140_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDt" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}Date_ST" minOccurs="0"/&gt;
 *         &lt;element name="BankCdInd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar1_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneBnkCd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="RecBrCd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar30_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAcctNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneName" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneCode" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar50_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneEmail" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar500_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneFax" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar40_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneMb" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar32_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAddr1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAddr2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAddr3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAddr4" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneAddr5" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="city" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar60_ST" minOccurs="0"/&gt;
 *         &lt;element name="zip" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}ZipCode_ST" minOccurs="0"/&gt;
 *         &lt;element name="Country" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar60_ST" minOccurs="0"/&gt;
 *         &lt;element name="State" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar60_ST" minOccurs="0"/&gt;
 *         &lt;element name="TelephoneNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="BeneTaxId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar20_ST" minOccurs="0"/&gt;
 *         &lt;element name="AuthPerson" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar40_ST" minOccurs="0"/&gt;
 *         &lt;element name="AuthPersonId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar15_ST" minOccurs="0"/&gt;
 *         &lt;element name="DeliveryMode" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar2_ST" minOccurs="0"/&gt;
 *         &lt;element name="PayoutLoc" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="PickupBr" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentRef" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar45_ST" minOccurs="0"/&gt;
 *         &lt;element name="ChgBorneBy" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar1_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstDt" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}Date_ST" minOccurs="0"/&gt;
 *         &lt;element name="MICRNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar6_ST" minOccurs="0"/&gt;
 *         &lt;element name="CreditRefNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar140_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDtl" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar140_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDtl1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDtl2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="PaymentDtl3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="MailToAddr1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="MailToAddr2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="MailToAddr3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="MailToAddr4" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="MailTo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ExchDoc" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar30_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstChecksum" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar400_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF4" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF5" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF6" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF7" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF8" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF9" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF10" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF11" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF12" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF13" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF14" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstRF15" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="EnrichmentSet" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}EnrichmentSetType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstrumentType", propOrder = {
    "instRefNo",
    "companyId",
    "compBatchId",
    "confidentialInd",
    "myProdCode",
    "compTransNo",
    "payMode",
    "txnAmnt",
    "accountNo",
    "drRefNmbr",
    "drDesc",
    "paymentDt",
    "bankCdInd",
    "beneBnkCd",
    "recBrCd",
    "beneAcctNo",
    "beneName",
    "beneCode",
    "beneEmail",
    "beneFax",
    "beneMb",
    "beneAddr1",
    "beneAddr2",
    "beneAddr3",
    "beneAddr4",
    "beneAddr5",
    "city",
    "zip",
    "country",
    "state",
    "telephoneNo",
    "beneId",
    "beneTaxId",
    "authPerson",
    "authPersonId",
    "deliveryMode",
    "payoutLoc",
    "pickupBr",
    "paymentRef",
    "chgBorneBy",
    "instDt",
    "micrNo",
    "creditRefNo",
    "paymentDtl",
    "paymentDtl1",
    "paymentDtl2",
    "paymentDtl3",
    "mailToAddr1",
    "mailToAddr2",
    "mailToAddr3",
    "mailToAddr4",
    "mailTo",
    "exchDoc",
    "instChecksum",
    "instRF1",
    "instRF2",
    "instRF3",
    "instRF4",
    "instRF5",
    "instRF6",
    "instRF7",
    "instRF8",
    "instRF9",
    "instRF10",
    "instRF11",
    "instRF12",
    "instRF13",
    "instRF14",
    "instRF15",
    "enrichmentSet"
})
public class InstrumentType {

    @XmlElement(name = "InstRefNo", required = true)
    protected String instRefNo;
    @XmlElement(name = "CompanyId")
    protected String companyId;
    @XmlElement(name = "CompBatchId")
    protected String compBatchId;
    @XmlElement(name = "ConfidentialInd")
    protected String confidentialInd;
    @XmlElement(name = "MyProdCode", required = true)
    protected String myProdCode;
    @XmlElement(name = "CompTransNo")
    protected String compTransNo;
    @XmlElement(name = "PayMode")
    protected String payMode;
    @XmlElement(name = "TxnAmnt", required = true)
    protected BigDecimal txnAmnt;
    @XmlElement(name = "AccountNo")
    protected String accountNo;
    @XmlElement(name = "DrRefNmbr")
    protected String drRefNmbr;
    @XmlElement(name = "DrDesc")
    protected String drDesc;
    @XmlElement(name = "PaymentDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar paymentDt;
    @XmlElement(name = "BankCdInd")
    protected String bankCdInd;
    @XmlElement(name = "BeneBnkCd")
    protected String beneBnkCd;
    @XmlElement(name = "RecBrCd")
    protected String recBrCd;
    @XmlElement(name = "BeneAcctNo")
    protected String beneAcctNo;
    @XmlElement(name = "BeneName")
    protected String beneName;
    @XmlElement(name = "BeneCode")
    protected String beneCode;
    @XmlElement(name = "BeneEmail")
    protected String beneEmail;
    @XmlElement(name = "BeneFax")
    protected String beneFax;
    @XmlElement(name = "BeneMb")
    protected String beneMb;
    @XmlElement(name = "BeneAddr1")
    protected String beneAddr1;
    @XmlElement(name = "BeneAddr2")
    protected String beneAddr2;
    @XmlElement(name = "BeneAddr3")
    protected String beneAddr3;
    @XmlElement(name = "BeneAddr4")
    protected String beneAddr4;
    @XmlElement(name = "BeneAddr5")
    protected String beneAddr5;
    protected String city;
    protected String zip;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "TelephoneNo")
    protected String telephoneNo;
    @XmlElement(name = "BeneId")
    protected String beneId;
    @XmlElement(name = "BeneTaxId")
    protected String beneTaxId;
    @XmlElement(name = "AuthPerson")
    protected String authPerson;
    @XmlElement(name = "AuthPersonId")
    protected String authPersonId;
    @XmlElement(name = "DeliveryMode")
    protected String deliveryMode;
    @XmlElement(name = "PayoutLoc")
    protected String payoutLoc;
    @XmlElement(name = "PickupBr")
    protected String pickupBr;
    @XmlElement(name = "PaymentRef")
    protected String paymentRef;
    @XmlElement(name = "ChgBorneBy")
    protected String chgBorneBy;
    @XmlElement(name = "InstDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar instDt;
    @XmlElement(name = "MICRNo")
    protected String micrNo;
    @XmlElement(name = "CreditRefNo")
    protected String creditRefNo;
    @XmlElement(name = "PaymentDtl")
    protected String paymentDtl;
    @XmlElement(name = "PaymentDtl1")
    protected String paymentDtl1;
    @XmlElement(name = "PaymentDtl2")
    protected String paymentDtl2;
    @XmlElement(name = "PaymentDtl3")
    protected String paymentDtl3;
    @XmlElement(name = "MailToAddr1")
    protected String mailToAddr1;
    @XmlElement(name = "MailToAddr2")
    protected String mailToAddr2;
    @XmlElement(name = "MailToAddr3")
    protected String mailToAddr3;
    @XmlElement(name = "MailToAddr4")
    protected String mailToAddr4;
    @XmlElement(name = "MailTo")
    protected String mailTo;
    @XmlElement(name = "ExchDoc")
    protected String exchDoc;
    @XmlElement(name = "InstChecksum")
    protected String instChecksum;
    @XmlElement(name = "InstRF1")
    protected String instRF1;
    @XmlElement(name = "InstRF2")
    protected String instRF2;
    @XmlElement(name = "InstRF3")
    protected String instRF3;
    @XmlElement(name = "InstRF4")
    protected String instRF4;
    @XmlElement(name = "InstRF5")
    protected String instRF5;
    @XmlElement(name = "InstRF6")
    protected String instRF6;
    @XmlElement(name = "InstRF7")
    protected String instRF7;
    @XmlElement(name = "InstRF8")
    protected String instRF8;
    @XmlElement(name = "InstRF9")
    protected String instRF9;
    @XmlElement(name = "InstRF10")
    protected String instRF10;
    @XmlElement(name = "InstRF11")
    protected String instRF11;
    @XmlElement(name = "InstRF12")
    protected String instRF12;
    @XmlElement(name = "InstRF13")
    protected String instRF13;
    @XmlElement(name = "InstRF14")
    protected String instRF14;
    @XmlElement(name = "InstRF15")
    protected String instRF15;
    @XmlElement(name = "EnrichmentSet")
    protected EnrichmentSetType enrichmentSet;

    /**
     * 获取instRefNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRefNo() {
        return instRefNo;
    }

    /**
     * 设置instRefNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRefNo(String value) {
        this.instRefNo = value;
    }

    /**
     * 获取companyId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置companyId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyId(String value) {
        this.companyId = value;
    }

    /**
     * 获取compBatchId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompBatchId() {
        return compBatchId;
    }

    /**
     * 设置compBatchId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompBatchId(String value) {
        this.compBatchId = value;
    }

    /**
     * 获取confidentialInd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfidentialInd() {
        return confidentialInd;
    }

    /**
     * 设置confidentialInd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfidentialInd(String value) {
        this.confidentialInd = value;
    }

    /**
     * 获取myProdCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMyProdCode() {
        return myProdCode;
    }

    /**
     * 设置myProdCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMyProdCode(String value) {
        this.myProdCode = value;
    }

    /**
     * 获取compTransNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompTransNo() {
        return compTransNo;
    }

    /**
     * 设置compTransNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompTransNo(String value) {
        this.compTransNo = value;
    }

    /**
     * 获取payMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayMode() {
        return payMode;
    }

    /**
     * 设置payMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayMode(String value) {
        this.payMode = value;
    }

    /**
     * 获取txnAmnt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTxnAmnt() {
        return txnAmnt;
    }

    /**
     * 设置txnAmnt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTxnAmnt(BigDecimal value) {
        this.txnAmnt = value;
    }

    /**
     * 获取accountNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置accountNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * 获取drRefNmbr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrRefNmbr() {
        return drRefNmbr;
    }

    /**
     * 设置drRefNmbr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrRefNmbr(String value) {
        this.drRefNmbr = value;
    }

    /**
     * 获取drDesc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrDesc() {
        return drDesc;
    }

    /**
     * 设置drDesc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrDesc(String value) {
        this.drDesc = value;
    }

    /**
     * 获取paymentDt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaymentDt() {
        return paymentDt;
    }

    /**
     * 设置paymentDt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaymentDt(XMLGregorianCalendar value) {
        this.paymentDt = value;
    }

    /**
     * 获取bankCdInd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCdInd() {
        return bankCdInd;
    }

    /**
     * 设置bankCdInd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCdInd(String value) {
        this.bankCdInd = value;
    }

    /**
     * 获取beneBnkCd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneBnkCd() {
        return beneBnkCd;
    }

    /**
     * 设置beneBnkCd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneBnkCd(String value) {
        this.beneBnkCd = value;
    }

    /**
     * 获取recBrCd属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecBrCd() {
        return recBrCd;
    }

    /**
     * 设置recBrCd属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecBrCd(String value) {
        this.recBrCd = value;
    }

    /**
     * 获取beneAcctNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAcctNo() {
        return beneAcctNo;
    }

    /**
     * 设置beneAcctNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAcctNo(String value) {
        this.beneAcctNo = value;
    }

    /**
     * 获取beneName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneName() {
        return beneName;
    }

    /**
     * 设置beneName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneName(String value) {
        this.beneName = value;
    }

    /**
     * 获取beneCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneCode() {
        return beneCode;
    }

    /**
     * 设置beneCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneCode(String value) {
        this.beneCode = value;
    }

    /**
     * 获取beneEmail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneEmail() {
        return beneEmail;
    }

    /**
     * 设置beneEmail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneEmail(String value) {
        this.beneEmail = value;
    }

    /**
     * 获取beneFax属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneFax() {
        return beneFax;
    }

    /**
     * 设置beneFax属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneFax(String value) {
        this.beneFax = value;
    }

    /**
     * 获取beneMb属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneMb() {
        return beneMb;
    }

    /**
     * 设置beneMb属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneMb(String value) {
        this.beneMb = value;
    }

    /**
     * 获取beneAddr1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAddr1() {
        return beneAddr1;
    }

    /**
     * 设置beneAddr1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAddr1(String value) {
        this.beneAddr1 = value;
    }

    /**
     * 获取beneAddr2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAddr2() {
        return beneAddr2;
    }

    /**
     * 设置beneAddr2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAddr2(String value) {
        this.beneAddr2 = value;
    }

    /**
     * 获取beneAddr3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAddr3() {
        return beneAddr3;
    }

    /**
     * 设置beneAddr3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAddr3(String value) {
        this.beneAddr3 = value;
    }

    /**
     * 获取beneAddr4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAddr4() {
        return beneAddr4;
    }

    /**
     * 设置beneAddr4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAddr4(String value) {
        this.beneAddr4 = value;
    }

    /**
     * 获取beneAddr5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneAddr5() {
        return beneAddr5;
    }

    /**
     * 设置beneAddr5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneAddr5(String value) {
        this.beneAddr5 = value;
    }

    /**
     * 获取city属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置city属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * 获取zip属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * 设置zip属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * 获取country属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置country属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * 获取state属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * 设置state属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * 获取telephoneNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephoneNo() {
        return telephoneNo;
    }

    /**
     * 设置telephoneNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephoneNo(String value) {
        this.telephoneNo = value;
    }

    /**
     * 获取beneId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneId() {
        return beneId;
    }

    /**
     * 设置beneId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneId(String value) {
        this.beneId = value;
    }

    /**
     * 获取beneTaxId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeneTaxId() {
        return beneTaxId;
    }

    /**
     * 设置beneTaxId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeneTaxId(String value) {
        this.beneTaxId = value;
    }

    /**
     * 获取authPerson属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthPerson() {
        return authPerson;
    }

    /**
     * 设置authPerson属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthPerson(String value) {
        this.authPerson = value;
    }

    /**
     * 获取authPersonId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthPersonId() {
        return authPersonId;
    }

    /**
     * 设置authPersonId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthPersonId(String value) {
        this.authPersonId = value;
    }

    /**
     * 获取deliveryMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryMode() {
        return deliveryMode;
    }

    /**
     * 设置deliveryMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryMode(String value) {
        this.deliveryMode = value;
    }

    /**
     * 获取payoutLoc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayoutLoc() {
        return payoutLoc;
    }

    /**
     * 设置payoutLoc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayoutLoc(String value) {
        this.payoutLoc = value;
    }

    /**
     * 获取pickupBr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickupBr() {
        return pickupBr;
    }

    /**
     * 设置pickupBr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickupBr(String value) {
        this.pickupBr = value;
    }

    /**
     * 获取paymentRef属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentRef() {
        return paymentRef;
    }

    /**
     * 设置paymentRef属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentRef(String value) {
        this.paymentRef = value;
    }

    /**
     * 获取chgBorneBy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChgBorneBy() {
        return chgBorneBy;
    }

    /**
     * 设置chgBorneBy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChgBorneBy(String value) {
        this.chgBorneBy = value;
    }

    /**
     * 获取instDt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstDt() {
        return instDt;
    }

    /**
     * 设置instDt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstDt(XMLGregorianCalendar value) {
        this.instDt = value;
    }

    /**
     * 获取micrNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMICRNo() {
        return micrNo;
    }

    /**
     * 设置micrNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMICRNo(String value) {
        this.micrNo = value;
    }

    /**
     * 获取creditRefNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditRefNo() {
        return creditRefNo;
    }

    /**
     * 设置creditRefNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditRefNo(String value) {
        this.creditRefNo = value;
    }

    /**
     * 获取paymentDtl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDtl() {
        return paymentDtl;
    }

    /**
     * 设置paymentDtl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDtl(String value) {
        this.paymentDtl = value;
    }

    /**
     * 获取paymentDtl1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDtl1() {
        return paymentDtl1;
    }

    /**
     * 设置paymentDtl1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDtl1(String value) {
        this.paymentDtl1 = value;
    }

    /**
     * 获取paymentDtl2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDtl2() {
        return paymentDtl2;
    }

    /**
     * 设置paymentDtl2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDtl2(String value) {
        this.paymentDtl2 = value;
    }

    /**
     * 获取paymentDtl3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDtl3() {
        return paymentDtl3;
    }

    /**
     * 设置paymentDtl3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDtl3(String value) {
        this.paymentDtl3 = value;
    }

    /**
     * 获取mailToAddr1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailToAddr1() {
        return mailToAddr1;
    }

    /**
     * 设置mailToAddr1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailToAddr1(String value) {
        this.mailToAddr1 = value;
    }

    /**
     * 获取mailToAddr2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailToAddr2() {
        return mailToAddr2;
    }

    /**
     * 设置mailToAddr2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailToAddr2(String value) {
        this.mailToAddr2 = value;
    }

    /**
     * 获取mailToAddr3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailToAddr3() {
        return mailToAddr3;
    }

    /**
     * 设置mailToAddr3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailToAddr3(String value) {
        this.mailToAddr3 = value;
    }

    /**
     * 获取mailToAddr4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailToAddr4() {
        return mailToAddr4;
    }

    /**
     * 设置mailToAddr4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailToAddr4(String value) {
        this.mailToAddr4 = value;
    }

    /**
     * 获取mailTo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailTo() {
        return mailTo;
    }

    /**
     * 设置mailTo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailTo(String value) {
        this.mailTo = value;
    }

    /**
     * 获取exchDoc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchDoc() {
        return exchDoc;
    }

    /**
     * 设置exchDoc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchDoc(String value) {
        this.exchDoc = value;
    }

    /**
     * 获取instChecksum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstChecksum() {
        return instChecksum;
    }

    /**
     * 设置instChecksum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstChecksum(String value) {
        this.instChecksum = value;
    }

    /**
     * 获取instRF1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF1() {
        return instRF1;
    }

    /**
     * 设置instRF1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF1(String value) {
        this.instRF1 = value;
    }

    /**
     * 获取instRF2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF2() {
        return instRF2;
    }

    /**
     * 设置instRF2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF2(String value) {
        this.instRF2 = value;
    }

    /**
     * 获取instRF3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF3() {
        return instRF3;
    }

    /**
     * 设置instRF3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF3(String value) {
        this.instRF3 = value;
    }

    /**
     * 获取instRF4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF4() {
        return instRF4;
    }

    /**
     * 设置instRF4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF4(String value) {
        this.instRF4 = value;
    }

    /**
     * 获取instRF5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF5() {
        return instRF5;
    }

    /**
     * 设置instRF5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF5(String value) {
        this.instRF5 = value;
    }

    /**
     * 获取instRF6属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF6() {
        return instRF6;
    }

    /**
     * 设置instRF6属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF6(String value) {
        this.instRF6 = value;
    }

    /**
     * 获取instRF7属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF7() {
        return instRF7;
    }

    /**
     * 设置instRF7属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF7(String value) {
        this.instRF7 = value;
    }

    /**
     * 获取instRF8属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF8() {
        return instRF8;
    }

    /**
     * 设置instRF8属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF8(String value) {
        this.instRF8 = value;
    }

    /**
     * 获取instRF9属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF9() {
        return instRF9;
    }

    /**
     * 设置instRF9属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF9(String value) {
        this.instRF9 = value;
    }

    /**
     * 获取instRF10属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF10() {
        return instRF10;
    }

    /**
     * 设置instRF10属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF10(String value) {
        this.instRF10 = value;
    }

    /**
     * 获取instRF11属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF11() {
        return instRF11;
    }

    /**
     * 设置instRF11属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF11(String value) {
        this.instRF11 = value;
    }

    /**
     * 获取instRF12属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF12() {
        return instRF12;
    }

    /**
     * 设置instRF12属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF12(String value) {
        this.instRF12 = value;
    }

    /**
     * 获取instRF13属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF13() {
        return instRF13;
    }

    /**
     * 设置instRF13属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF13(String value) {
        this.instRF13 = value;
    }

    /**
     * 获取instRF14属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF14() {
        return instRF14;
    }

    /**
     * 设置instRF14属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF14(String value) {
        this.instRF14 = value;
    }

    /**
     * 获取instRF15属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstRF15() {
        return instRF15;
    }

    /**
     * 设置instRF15属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstRF15(String value) {
        this.instRF15 = value;
    }

    /**
     * 获取enrichmentSet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link EnrichmentSetType }
     *     
     */
    public EnrichmentSetType getEnrichmentSet() {
        return enrichmentSet;
    }

    /**
     * 设置enrichmentSet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link EnrichmentSetType }
     *     
     */
    public void setEnrichmentSet(EnrichmentSetType value) {
        this.enrichmentSet = value;
    }

}
