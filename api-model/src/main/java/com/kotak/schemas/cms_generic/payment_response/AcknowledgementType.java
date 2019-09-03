
package com.kotak.schemas.cms_generic.payment_response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AcknowledgementType complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AcknowledgementType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AckHeader" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}Acknowledgement"/&gt;
 *         &lt;element name="InstrumentList" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}InstrumentListType" minOccurs="0"/&gt;
 *         &lt;element name="FaultList" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Response.xsd}FaultListType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcknowledgementType", propOrder = {
    "ackHeader",
    "instrumentList",
    "faultList"
})
public class AcknowledgementType {

    @XmlElement(name = "AckHeader", required = true)
    protected Acknowledgement ackHeader;
    @XmlElement(name = "InstrumentList")
    protected InstrumentListType instrumentList;
    @XmlElement(name = "FaultList")
    protected FaultListType faultList;

    /**
     * ��ȡackHeader���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Acknowledgement }
     *     
     */
    public Acknowledgement getAckHeader() {
        return ackHeader;
    }

    /**
     * ����ackHeader���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Acknowledgement }
     *     
     */
    public void setAckHeader(Acknowledgement value) {
        this.ackHeader = value;
    }

    /**
     * ��ȡinstrumentList���Ե�ֵ��
     *
     * @return
     *     possible object is
     *     {@link InstrumentListType }
     *     
     */
    public InstrumentListType getInstrumentList() {
        return instrumentList;
    }

    /**
     * ����instrumentList���Ե�ֵ��
     *
     * @param value
     *     allowed object is
     *     {@link InstrumentListType }
     *     
     */
    public void setInstrumentList(InstrumentListType value) {
        this.instrumentList = value;
    }

    /**
     * ��ȡfaultList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FaultListType }
     *     
     */
    public FaultListType getFaultList() {
        return faultList;
    }

    /**
     * ����faultList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FaultListType }
     *     
     */
    public void setFaultList(FaultListType value) {
        this.faultList = value;
    }

}
