package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_name_segment")
public class Namesegment extends CibilOrderEntity{
	private static final long serialVersionUID = 1718422716092550327L;
    private String length;
    private String segmenttag;
    private String consumername1fieldlength;
    private String consumername1;
    private String dateofbirthfieldlength;
    private String dateofbirth;
    private String genderfieldlength;
    private String gender;
    
    private String consumername3;
    private String consumername3fieldlength;
    
    private String consumername2;
    private String consumername2fieldlength;
    
}