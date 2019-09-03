
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FaultType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡcode���Ե�ֵ��
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
     * ����code���Ե�ֵ��
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
     * ��ȡreason���Ե�ֵ��
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
     * ����reason���Ե�ֵ��
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
     * ��ȡinvalidField���Ե�ֵ��
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
     * ����invalidField���Ե�ֵ��
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
     * ��ȡsubmittedFieldValue���Ե�ֵ��
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
     * ����submittedFieldValue���Ե�ֵ��
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
