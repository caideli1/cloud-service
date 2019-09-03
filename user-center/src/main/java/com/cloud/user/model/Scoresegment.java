package com.cloud.user.model;
import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_score_segment")
public class Scoresegment extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2951084160381340826L;
    private String length;
    private String scorename;
    private String scorecardname;
    private String scorecardversion;
    private String scoredate;
    private String score;
    private String reasoncode1fieldlength;
    private String reasoncode1;
    private String reasoncode2fieldlength;
    private String reasoncode2;
    private String reasoncode3fieldlength;
    private String reasoncode3;
    private String reasoncode4fieldlength;
    private String reasoncode4;
    
    private String exclusioncode4;
    private String exclusioncode4fieldlength;
    private String exclusioncode1;
    private String exclusioncode1fieldlength;
    private String exclusioncode5fieldlength;
    private String exclusioncode5;
    private String exclusioncode10fieldlength;
    private String exclusioncode10;
}