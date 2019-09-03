
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="InstRefNo" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar20_ST"/&gt;
 *         &lt;element name="InstStatusCd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar10_ST" minOccurs="0"/&gt;
 *         &lt;element name="InstStatusRem" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar2000_ST" minOccurs="0"/&gt;
 *         &lt;element name="ErrorList" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}ErrorListType" minOccurs="0"/&gt;
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
    "instStatusCd",
    "instStatusRem",
    "errorList"
})
public class InstrumentType {

    @XmlElement(name = "InstRefNo", required = true)
    protected String instRefNo;
    @XmlElement(name = "InstStatusCd")
    protected String instStatusCd;
    @XmlElement(name = "InstStatusRem")
    protected String instStatusRem;
    @XmlElement(name = "ErrorList")
    protected ErrorListType errorList;

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
     * ��ȡinstStatusCd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstStatusCd() {
        return instStatusCd;
    }

    /**
     * ����instStatusCd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstStatusCd(String value) {
        this.instStatusCd = value;
    }

    /**
     * ��ȡinstStatusRem���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstStatusRem() {
        return instStatusRem;
    }

    /**
     * ����instStatusRem���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstStatusRem(String value) {
        this.instStatusRem = value;
    }

    /**
     * ��ȡerrorList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ErrorListType }
     *     
     */
    public ErrorListType getErrorList() {
        return errorList;
    }

    /**
     * ����errorList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorListType }
     *     
     */
    public void setErrorList(ErrorListType value) {
        this.errorList = value;
    }

}
