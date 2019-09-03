package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConformFile {
    /**
     * 银行卡链接
     */
    private  String bankStatementUrl;

    /**
     * 工作证明链接
     */
    private String workCard;
}
