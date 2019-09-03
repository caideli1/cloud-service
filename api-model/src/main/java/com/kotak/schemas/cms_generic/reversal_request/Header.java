
package com.kotak.schemas.cms_generic.reversal_request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.kotak.com/schemas/CMS_Generic/Reversal_Request.xsd}Req_Id"/&gt;
 *         &lt;element ref="{http://www.kotak.com/schemas/CMS_Generic/Reversal_Request.xsd}Msg_Src"/&gt;
 *         &lt;element ref="{http://www.kotak.com/schemas/CMS_Generic/Reversal_Request.xsd}Client_Code"/&gt;
 *         &lt;element ref="{http://www.kotak.com/schemas/CMS_Generic/Reversal_Request.xsd}Date_Post"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reqId",
    "msgSrc",
    "clientCode",
    "datePost"
})
@XmlRootElement(name = "Header")
public class Header {

    @XmlElement(name = "Req_Id", required = true)
    protected String reqId;
    @XmlElement(name = "Msg_Src", required = true)
    protected String msgSrc;
    @XmlElement(name = "Client_Code", required = true)
    protected String clientCode;
    @XmlElement(name = "Date_Post", required = true)
    protected String datePost;

    /**
     * 获取reqId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqId() {
        return reqId;
    }

    /**
     * 设置reqId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqId(String value) {
        this.reqId = value;
    }

    /**
     * 获取msgSrc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgSrc() {
        return msgSrc;
    }

    /**
     * 设置msgSrc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgSrc(String value) {
        this.msgSrc = value;
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
     * 获取datePost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatePost() {
        return datePost;
    }

    /**
     * 设置datePost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePost(String value) {
        this.datePost = value;
    }

}
