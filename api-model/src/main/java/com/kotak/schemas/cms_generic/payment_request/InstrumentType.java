
package com.kotak.schemas.cms_generic.payment_request;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>InstrumentType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡinstRefNo���Ե�ֵ��
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
     * ����instRefNo���Ե�ֵ��
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
     * ��ȡcompanyId���Ե�ֵ��
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
     * ����companyId���Ե�ֵ��
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
     * ��ȡcompBatchId���Ե�ֵ��
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
     * ����compBatchId���Ե�ֵ��
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
     * ��ȡconfidentialInd���Ե�ֵ��
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
     * ����confidentialInd���Ե�ֵ��
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
     * ��ȡmyProdCode���Ե�ֵ��
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
     * ����myProdCode���Ե�ֵ��
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
     * ��ȡcompTransNo���Ե�ֵ��
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
     * ����compTransNo���Ե�ֵ��
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
     * ��ȡpayMode���Ե�ֵ��
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
     * ����payMode���Ե�ֵ��
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
     * ��ȡtxnAmnt���Ե�ֵ��
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
     * ����txnAmnt���Ե�ֵ��
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
     * ��ȡaccountNo���Ե�ֵ��
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
     * ����accountNo���Ե�ֵ��
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
     * ��ȡdrRefNmbr���Ե�ֵ��
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
     * ����drRefNmbr���Ե�ֵ��
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
     * ��ȡdrDesc���Ե�ֵ��
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
     * ����drDesc���Ե�ֵ��
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
     * ��ȡpaymentDt���Ե�ֵ��
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
     * ����paymentDt���Ե�ֵ��
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
     * ��ȡbankCdInd���Ե�ֵ��
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
     * ����bankCdInd���Ե�ֵ��
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
     * ��ȡbeneBnkCd���Ե�ֵ��
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
     * ����beneBnkCd���Ե�ֵ��
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
     * ��ȡrecBrCd���Ե�ֵ��
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
     * ����recBrCd���Ե�ֵ��
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
     * ��ȡbeneAcctNo���Ե�ֵ��
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
     * ����beneAcctNo���Ե�ֵ��
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
     * ��ȡbeneName���Ե�ֵ��
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
     * ����beneName���Ե�ֵ��
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
     * ��ȡbeneCode���Ե�ֵ��
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
     * ����beneCode���Ե�ֵ��
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
     * ��ȡbeneEmail���Ե�ֵ��
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
     * ����beneEmail���Ե�ֵ��
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
     * ��ȡbeneFax���Ե�ֵ��
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
     * ����beneFax���Ե�ֵ��
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
     * ��ȡbeneMb���Ե�ֵ��
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
     * ����beneMb���Ե�ֵ��
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
     * ��ȡbeneAddr1���Ե�ֵ��
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
     * ����beneAddr1���Ե�ֵ��
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
     * ��ȡbeneAddr2���Ե�ֵ��
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
     * ����beneAddr2���Ե�ֵ��
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
     * ��ȡbeneAddr3���Ե�ֵ��
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
     * ����beneAddr3���Ե�ֵ��
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
     * ��ȡbeneAddr4���Ե�ֵ��
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
     * ����beneAddr4���Ե�ֵ��
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
     * ��ȡbeneAddr5���Ե�ֵ��
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
     * ����beneAddr5���Ե�ֵ��
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
     * ��ȡcity���Ե�ֵ��
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
     * ����city���Ե�ֵ��
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
     * ��ȡzip���Ե�ֵ��
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
     * ����zip���Ե�ֵ��
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
     * ��ȡcountry���Ե�ֵ��
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
     * ����country���Ե�ֵ��
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
     * ��ȡstate���Ե�ֵ��
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
     * ����state���Ե�ֵ��
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
     * ��ȡtelephoneNo���Ե�ֵ��
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
     * ����telephoneNo���Ե�ֵ��
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
     * ��ȡbeneId���Ե�ֵ��
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
     * ����beneId���Ե�ֵ��
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
     * ��ȡbeneTaxId���Ե�ֵ��
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
     * ����beneTaxId���Ե�ֵ��
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
     * ��ȡauthPerson���Ե�ֵ��
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
     * ����authPerson���Ե�ֵ��
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
     * ��ȡauthPersonId���Ե�ֵ��
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
     * ����authPersonId���Ե�ֵ��
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
     * ��ȡdeliveryMode���Ե�ֵ��
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
     * ����deliveryMode���Ե�ֵ��
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
     * ��ȡpayoutLoc���Ե�ֵ��
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
     * ����payoutLoc���Ե�ֵ��
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
     * ��ȡpickupBr���Ե�ֵ��
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
     * ����pickupBr���Ե�ֵ��
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
     * ��ȡpaymentRef���Ե�ֵ��
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
     * ����paymentRef���Ե�ֵ��
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
     * ��ȡchgBorneBy���Ե�ֵ��
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
     * ����chgBorneBy���Ե�ֵ��
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
     * ��ȡinstDt���Ե�ֵ��
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
     * ����instDt���Ե�ֵ��
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
     * ��ȡmicrNo���Ե�ֵ��
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
     * ����micrNo���Ե�ֵ��
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
     * ��ȡcreditRefNo���Ե�ֵ��
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
     * ����creditRefNo���Ե�ֵ��
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
     * ��ȡpaymentDtl���Ե�ֵ��
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
     * ����paymentDtl���Ե�ֵ��
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
     * ��ȡpaymentDtl1���Ե�ֵ��
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
     * ����paymentDtl1���Ե�ֵ��
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
     * ��ȡpaymentDtl2���Ե�ֵ��
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
     * ����paymentDtl2���Ե�ֵ��
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
     * ��ȡpaymentDtl3���Ե�ֵ��
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
     * ����paymentDtl3���Ե�ֵ��
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
     * ��ȡmailToAddr1���Ե�ֵ��
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
     * ����mailToAddr1���Ե�ֵ��
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
     * ��ȡmailToAddr2���Ե�ֵ��
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
     * ����mailToAddr2���Ե�ֵ��
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
     * ��ȡmailToAddr3���Ե�ֵ��
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
     * ����mailToAddr3���Ե�ֵ��
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
     * ��ȡmailToAddr4���Ե�ֵ��
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
     * ����mailToAddr4���Ե�ֵ��
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
     * ��ȡmailTo���Ե�ֵ��
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
     * ����mailTo���Ե�ֵ��
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
     * ��ȡexchDoc���Ե�ֵ��
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
     * ����exchDoc���Ե�ֵ��
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
     * ��ȡinstChecksum���Ե�ֵ��
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
     * ����instChecksum���Ե�ֵ��
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
     * ��ȡinstRF1���Ե�ֵ��
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
     * ����instRF1���Ե�ֵ��
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
     * ��ȡinstRF2���Ե�ֵ��
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
     * ����instRF2���Ե�ֵ��
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
     * ��ȡinstRF3���Ե�ֵ��
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
     * ����instRF3���Ե�ֵ��
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
     * ��ȡinstRF4���Ե�ֵ��
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
     * ����instRF4���Ե�ֵ��
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
     * ��ȡinstRF5���Ե�ֵ��
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
     * ����instRF5���Ե�ֵ��
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
     * ��ȡinstRF6���Ե�ֵ��
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
     * ����instRF6���Ե�ֵ��
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
     * ��ȡinstRF7���Ե�ֵ��
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
     * ����instRF7���Ե�ֵ��
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
     * ��ȡinstRF8���Ե�ֵ��
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
     * ����instRF8���Ե�ֵ��
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
     * ��ȡinstRF9���Ե�ֵ��
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
     * ����instRF9���Ե�ֵ��
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
     * ��ȡinstRF10���Ե�ֵ��
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
     * ����instRF10���Ե�ֵ��
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
     * ��ȡinstRF11���Ե�ֵ��
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
     * ����instRF11���Ե�ֵ��
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
     * ��ȡinstRF12���Ե�ֵ��
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
     * ����instRF12���Ե�ֵ��
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
     * ��ȡinstRF13���Ե�ֵ��
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
     * ����instRF13���Ե�ֵ��
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
     * ��ȡinstRF14���Ե�ֵ��
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
     * ����instRF14���Ե�ֵ��
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
     * ��ȡinstRF15���Ե�ֵ��
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
     * ����instRF15���Ե�ֵ��
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
     * ��ȡenrichmentSet���Ե�ֵ��
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
     * ����enrichmentSet���Ե�ֵ��
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
