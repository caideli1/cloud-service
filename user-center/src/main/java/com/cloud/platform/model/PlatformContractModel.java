package com.cloud.platform.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 合同表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformContractModel {
    private long id;
    private String contractName;
    private int contractStatus;
    private String contractSrc;
    private Date contractStartDate;
    private Date contractDueDate;
    private String processUser;
    private Timestamp createTime;
    private int businessStatus;
    private String companyName = "";
    private String tagIds="";
    private String tagNames;
    private Map<String, String> maps;

}
