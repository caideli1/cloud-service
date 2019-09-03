package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class IndexCustomerDayParam {
    private String  dayStr;

    private Date  day;
    /**
     * 0 日  1：月 3：周
     */
   private  Integer  type;

    private Integer newUserAdded;

    private Integer orderApplyNum;

    private Integer orderPassNum;

}
