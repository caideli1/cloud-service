package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_enquiry")
public class Enquiry extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2433550771776632424L;
    private String length;
    private String segmenttag;
    private String dateofenquiryfields;
    private String enquiringmembershortnamefieldlength;
    private String enquiringmembershortname;
    private String enquirypurpose;
    private String enquiryamountfieldlength;
    private String enquiryamount;

}