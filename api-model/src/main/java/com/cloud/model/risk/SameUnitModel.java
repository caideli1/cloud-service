package com.cloud.model.risk;

import lombok.Data;

/**
 * 匹配信息下面相同单位下的订单信息
 * @author bjy
 * @date 2019/3/28 0028 15:18
 */
@Data
public class SameUnitModel {
    private String orderNum;
    private String name;
    private String cellPhone;
    private String companyName;
    private String checkStatus;
    private String companyPhone;
    private String imiId;
    private String reason;
}
