
package com.kotak.schemas.cms_generic.payment_request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>InitiateRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="InitiateRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RequestHeader" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}RequestHeaderType"/&gt;
 *         &lt;element name="InstrumentList" type="{http://www.kotak.com/schemas/CMS_Generic/Payment_Request.xsd}InstrumentListType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitiateRequest", propOrder = {
    "requestHeader",
    "instrumentList"
})
public class InitiateRequest {

    @XmlElement(name = "RequestHeader", required = true)
    protected RequestHeaderType requestHeader;
    @XmlElement(name = "InstrumentList", required = true)
    protected InstrumentListType instrumentList;

    /**
     * ��ȡrequestHeader���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RequestHeaderType }
     *     
     */
    public RequestHeaderType getRequestHeader() {
        return requestHeader;
    }

    /**
     * ����requestHeader���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RequestHeaderType }
     *     
     */
    public void setRequestHeader(RequestHeaderType value) {
        this.requestHeader = value;
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

}
