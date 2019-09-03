package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class DateParam {
    /**
     * 開始時間
     */
    private Date startDate;
    /**
     * 結束時間
     */
    private Date endDate;

}
