
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FaultType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FaultType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Code" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="Reason" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar2000_ST"/&gt;
 *         &lt;element name="InvalidField" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar50_ST" minOccurs="0"/&gt;
 *         &lt;element name="SubmittedFieldValue" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar4000_ST" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultType", propOrder = {
    "code",
    "reason",
    "invalidField",
    "submittedFieldValue"
})
public class FaultType {

    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "Reason", required = true)
    protected String reason;
    @XmlElement(name = "InvalidField")
    protected String invalidField;
    @XmlElement(name = "SubmittedFieldValue")
    protected String submittedFieldValue;

    /**
     * 获取code属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * 获取reason属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置reason属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * 获取invalidField属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidField() {
        return invalidField;
    }

    /**
     * 设置invalidField属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidField(String value) {
        this.invalidField = value;
    }

    /**
     * 获取submittedFieldValue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmittedFieldValue() {
        return submittedFieldValue;
    }

    /**
     * 设置submittedFieldValue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmittedFieldValue(String value) {
        this.submittedFieldValue = value;
    }

}
