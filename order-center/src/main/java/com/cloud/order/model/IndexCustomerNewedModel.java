package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IndexCustomerNewedModel {

    private   IndexCustomerMoth  indexCustomerMoth;

    private List<IndexCustomerDayParam> indexCustomerDayParamList;

    private List<IndexCustomerDayParam> indexCustomerWeekParamList;

    private List<IndexCustomerDayParam> indexCustomerMonthParamList;
 


}
