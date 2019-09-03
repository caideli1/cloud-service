
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
     * 获取instStatusCd属性的值。
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
     * 设置instStatusCd属性的值。
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
     * 获取instStatusRem属性的值。
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
     * 设置instStatusRem属性的值。
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
     * 获取errorList属性的值。
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
     * 设置errorList属性的值。
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
