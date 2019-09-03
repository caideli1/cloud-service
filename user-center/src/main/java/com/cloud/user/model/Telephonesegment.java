package com.cloud.user.model;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@Table(name="kudos_tele_phone_segment")
public class Telephonesegment extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6695782806640551995L;
    private String length;
    private String segmenttag;
    private String telephonenumberfieldlength;
    private String telephonenumber;
    private String telephonetype;
    private String telephoneextensionfieldlength;
    private String telephoneextension;
    private String enrichedthroughenquiry;
    
}