package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_employmentsegment")
public class Employmentsegment extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5906839231991466734L;
    private String length;
    private String segmenttag;
    private String accounttype;
    private String datereportedcertified;
    private String occupationcode;
    private String incomefieldlength;
    private String income;
    private String netgrossindicator;
    private String monthlyannualindicator;
}