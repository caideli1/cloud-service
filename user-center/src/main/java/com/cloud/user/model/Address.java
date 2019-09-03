package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_address")
public class Address extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1978815863053077679L;
    private String addresssegmenttag;
    private String length;
    private String segmenttag;
    private String addressline1fieldlength;
    private String addressline1;
    private String addressline2fieldlength;
    private String addressline2;
    private String addressline3fieldlength;
    private String addressline3;
    private String addressline4fieldlength;
    private String addressline4;
    private String addressline5fieldlength;
    private String addressline5;
    private String statecode;
    private String pincodefieldlength;
    private String pincode;
    private String addresscategory;
    private String residencecode;
    private String datereported;
    private String enrichedthroughenquiry;
}