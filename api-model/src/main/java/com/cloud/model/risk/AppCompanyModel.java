package com.cloud.model.risk;

import lombok.Data;

/**
 * app公司类型
 *
 * @Author 朱景涛
 */
@Data
public class AppCompanyModel {


    /**
     * 主键ID
     */
    private Long id;
    /**
     * app名称
     */
    private String appName;

    /**
     * 开始时间
     */
    private String companyStartTime;

    /**
     * 公司官网
     */
    private String companyUrl;


}
