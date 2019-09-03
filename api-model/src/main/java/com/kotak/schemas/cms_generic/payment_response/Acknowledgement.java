
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Acknowledgement complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="Acknowledgement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageId" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar20_ST"/&gt;
 *         &lt;element name="StatusCd" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar10_ST"/&gt;
 *         &lt;element name="StatusRem" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MinChar1_MaxChar2000_ST"/&gt;
 *         &lt;element name="ResRF1" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ResRF2" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *         &lt;element name="ResRF3" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}MaxChar200_ST" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Acknowledgement", propOrder = {
    "messageId",
    "statusCd",
    "statusRem",
    "resRF1",
    "resRF2",
    "resRF3"
})
public class Acknowledgement {

    @XmlElement(name = "MessageId", required = true)
    protected String messageId;
    @XmlElement(name = "StatusCd", required = true)
    protected String statusCd;
    @XmlElement(name = "StatusRem", required = true)
    protected String statusRem;
    @XmlElement(name = "ResRF1")
    protected String resRF1;
    @XmlElement(name = "ResRF2")
    protected String resRF2;
    @XmlElement(name = "ResRF3")
    protected String resRF3;

    /**
     * ��ȡmessageId���Ե�ֵ��
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
     * ����messageId���Ե�ֵ��
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
     * ��ȡstatusCd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCd() {
        return statusCd;
    }

    /**
     * ����statusCd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCd(String value) {
        this.statusCd = value;
    }

    /**
     * ��ȡstatusRem���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusRem() {
        return statusRem;
    }

    /**
     * ����statusRem���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusRem(String value) {
        this.statusRem = value;
    }

    /**
     * ��ȡresRF1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResRF1() {
        return resRF1;
    }

    /**
     * ����resRF1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResRF1(String value) {
        this.resRF1 = value;
    }

    /**
     * ��ȡresRF2���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResRF2() {
        return resRF2;
    }

    /**
     * ����resRF2���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResRF2(String value) {
        this.resRF2 = value;
    }

    /**
     * ��ȡresRF3���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResRF3() {
        return resRF3;
    }

    /**
     * ����resRF3���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResRF3(String value) {
        this.resRF3 = value;
    }

}
