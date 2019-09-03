package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IndexDueTypeModel {

    private List<IndexDueTypeParam> indexDueTypeParamsDay;

    private List<IndexDueTypeParam> indexDueTypeParamsWeek;

    private List<IndexDueTypeParam> indexDueTypeParamsMonth;


}
