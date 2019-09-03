package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IndexDueIngModel {
    private List<IndexDueIngParam>  dueIngParamsDay;
    private List<IndexDueIngParam>  dueIngParamsWeek;
    private List<IndexDueIngParam>  dueIngParamsMonth;
}
