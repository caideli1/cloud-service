package com.cloud.model.kudosCibil;

import lombok.Data;

@Data
public class EnquiryEntity extends CibilOrderEntity{


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