package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_header")
public class Header extends CibilOrderEntity{
	private static final long serialVersionUID = 2999316187557360438L;
    private String segmenttag;
    private String version;
    private String referencenumber;
    private String membercode;
    private String subjectreturncode;
    private String enquirycontrolnumber;
    private String dateprocessed;
    private String timeprocessed;
    

}