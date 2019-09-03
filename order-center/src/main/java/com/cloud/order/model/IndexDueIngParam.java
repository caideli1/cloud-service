package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class IndexDueIngParam {

    private Date day;

    private String dayStr;

    private Double dueInPre;
}
