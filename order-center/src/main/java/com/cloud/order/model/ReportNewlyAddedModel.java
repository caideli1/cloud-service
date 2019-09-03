package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "report_newly_added")
@Builder
public class ReportNewlyAddedModel {


    /**
     * id
     */
    @Id
    private  Integer  id;
    /**
     * 报表时间
     */
    private Date reportDate;
    /**
     * 新客数量
     */
    private  Integer  customerNum;
    /**
     * 新客申请数量
     */
    private   Integer   newlyCustomerApplyNum;
    /**
     * 老客数量
     */
    private Integer  oldCustomerApplyNum;

}
