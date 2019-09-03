
package com.kotak.schemas.cms_generic.payment_request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RequestHeaderType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RequestHeaderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MinChar1_MaxChar20_ST"/&gt;
 *         &lt;element name="MsgSource" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MinChar1_MaxChar20_ST"/&gt;
 *         &lt;element name="ClientCode" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MinChar1_MaxChar10_ST"/&gt;
 *         &lt;element name="BatchRefNmbr" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar100_ST" minOccurs="0"/&gt;
 *         &lt;element name="HeaderChecksum" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar400_ST" minOccurs="0"/&gt;
 *         &lt;element name="ReqRF1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ReqRF2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ReqRF3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ReqRF4" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ReqRF5" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestHeaderType", propOrder = {
    "messageId",
    "msgSource",
    "clientCode",
    "batchRefNmbr",
    "headerChecksum",
    "reqRF1",
    "reqRF2",
    "reqRF3",
    "reqRF4",
    "reqRF5"
})
public class RequestHeaderType {

    @XmlElement(name = "MessageId", required = true)
    protected String messageId;
    @XmlElement(name = "MsgSource", required = true)
    protected String msgSource;
    @XmlElement(name = "ClientCode", required = true)
    protected String clientCode;
    @XmlElement(name = "BatchRefNmbr")
    protected String batchRefNmbr;
    @XmlElement(name = "HeaderChecksum")
    protected String headerChecksum;
    @XmlElement(name = "ReqRF1")
    protected String reqRF1;
    @XmlElement(name = "ReqRF2")
    protected String reqRF2;
    @XmlElement(name = "ReqRF3")
    protected String reqRF3;
    @XmlElement(name = "ReqRF4")
    protected String reqRF4;
    @XmlElement(name = "ReqRF5")
    protected String reqRF5;

    /**
     * 获取messageId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置messageId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * 获取msgSource属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgSource() {
        return msgSource;
    }

    /**
     * 设置msgSource属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgSource(String value) {
        this.msgSource = value;
    }

    /**
     * 获取clientCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * 设置clientCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientCode(String value) {
        this.clientCode = value;
    }

    /**
     * 获取batchRefNmbr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchRefNmbr() {
        return batchRefNmbr;
    }

    /**
     * 设置batchRefNmbr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchRefNmbr(String value) {
        this.batchRefNmbr = value;
    }

    /**
     * 获取headerChecksum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderChecksum() {
        return headerChecksum;
    }

    /**
     * 设置headerChecksum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderChecksum(String value) {
        this.headerChecksum = value;
    }

    /**
     * 获取reqRF1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRF1() {
        return reqRF1;
    }

    /**
     * 设置reqRF1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRF1(String value) {
        this.reqRF1 = value;
    }

    /**
     * 获取reqRF2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRF2() {
        return reqRF2;
    }

    /**
     * 设置reqRF2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRF2(String value) {
        this.reqRF2 = value;
    }

    /**
     * 获取reqRF3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRF3() {
        return reqRF3;
    }

    /**
     * 设置reqRF3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRF3(String value) {
        this.reqRF3 = value;
    }

    /**
     * 获取reqRF4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRF4() {
        return reqRF4;
    }

    /**
     * 设置reqRF4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRF4(String value) {
        this.reqRF4 = value;
    }

    /**
     * 获取reqRF5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRF5() {
        return reqRF5;
    }

    /**
     * 设置reqRF5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRF5(String value) {
        this.reqRF5 = value;
    }

}
