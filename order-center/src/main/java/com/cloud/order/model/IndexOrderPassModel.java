package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class IndexOrderPassModel {

    /**
     * 月度
     */
    private IndexOrderPassMoth indexOrderPassMoth;

    /**
     * 天統計報表
     */
    private List<IndexOrderPassDayParam> IndexOrderPassDayParams;
    /**
     * 周 統計報表
     */
    private List<IndexOrderPassDayParam> indexOrderPassWeekParams;
    /**
     * 月度統計
     */
    private List<IndexOrderPassDayParam> indexOrderPassMonthParams;
}
