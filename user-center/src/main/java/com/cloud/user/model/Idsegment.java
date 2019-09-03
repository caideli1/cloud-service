package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_id_segment")
public class Idsegment extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 908776180733087431L;
	private Integer id;
    private String length;
    private String segmenttag;
    private String idtype;
    private String idnumberfieldlength;
    private String idnumber;
    private String enrichedthroughenquiry;
    private String issuedate;
    private String expirationdate;

}