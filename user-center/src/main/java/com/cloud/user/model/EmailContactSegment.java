
package com.cloud.user.model;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_email_contact_segment")
public class EmailContactSegment extends CibilOrderEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841833567947922294L;
    private String length;
    private String segmenttag;
    private String emailidfieldlength;
    private String emailid;
}