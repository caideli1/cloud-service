package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class IndexDueTypeParam {

    private Date day;
    private String dayStr;
    private Double d1DuePre;
    private Double d3DuePre;
    private Double d7DuePre;
    private Double d15DuePre;
    private Double d30DuePre;

}
