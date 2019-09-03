package com.cloud.order.model;

import lombok.Data;

import java.util.Date;

@Data
public class FinanceExtensionReportParam {
    private Date reportDate;
    private Integer exporeCount;
}
