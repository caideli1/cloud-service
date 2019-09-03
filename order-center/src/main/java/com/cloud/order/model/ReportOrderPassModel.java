package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="report_order_pass")
@Builder
public class ReportOrderPassModel {

    @Id
    private   Integer id;


    /**
     * 报表日期
     */
    private Date  reportDate;

    /**
     * 机审通过数量
     */
    private  Integer  mechinePassNum;


    /**
     * 申请数量
     */
    private  Integer applyNum;

    /**
     * 人工通过数量
     */
    private   Integer  humanPassNum;

    /**
     * 人工申请数量
     */
    private  Integer  humanApplyNum;

    /**
     * 平台通过 数量
     */
    private  Integer platformPassNum;

    /**
     * 平台申请数量
     */
    private  Integer platformApplyNum;

}
