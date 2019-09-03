package com.cloud.user.model;

/**
 * @author yoga
 * @Description: UserAadhaarQrCode
 * @date 2019-07-2319:48
 */


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <PrintLetterBarcodeData
 *     uid="807217934459" name="Mukesh Bhoi" gender="M"
 *     yob="1994" house="H. No. 588/A" street="Opp Agro Chemical Gate" loc="Zari"
 *     vtc="Zuarinagar" po="Zuarinagar" dist="South Goa" subdist="Mormugao"
 *     state="Goa" pc="403726" dob="17/02/1994"
 * />
 */

@XmlRootElement(name = "PrintLetterBarcodeData")
public class UserAadhaarQrCode {
    private String uid;
    private String name;
    private String gender;
    private String yob;
    private String house;
    private String street;
    private String loc;
    private String vtc;
    private String po;
    private String dist;
    private String subdist;
    private String state;
    private String pc;
    private String dob;

    @XmlAttribute
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlAttribute
    public String getYob() {
        return yob;
    }

    public void setYob(String yob) {
        this.yob = yob;
    }

    @XmlAttribute
    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    @XmlAttribute
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @XmlAttribute
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @XmlAttribute
    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    @XmlAttribute
    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    @XmlAttribute
    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    @XmlAttribute
    public String getSubdist() {
        return subdist;
    }

    public void setSubdist(String subdist) {
        this.subdist = subdist;
    }

    @XmlAttribute
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlAttribute
    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    @XmlAttribute
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
