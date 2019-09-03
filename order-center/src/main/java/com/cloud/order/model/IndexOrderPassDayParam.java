package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class IndexOrderPassDayParam {
    /**
     * 日期处理后
     */
    private String dayStr;
    /**
     * 日期
     */
    private Date day;
    /**
     * 机审通过率
     */
    private Double  mechinePassPre;
    /**
     * 人工通过率
     */
    private Double  humanPassPre;
    /**
     * 整体通过率
     */
    private Double  totalPassPre;
    /**
     * 平台通过率
     */
    private Double platformPassPre;

}
