package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IndexOrderPassMoth {

    /**
     * 当月 机审通过率
     */
    private  Double nowMachinePassPre;
    /**
     * 机审通过环比
     */
    private  Double chainMachinePassPre;

    /**
     * 当月人工通过率
     */
    private Double nowHumanPassPre;

    /**
     * 通过率环比
     */
    private Double chainHumanPassPre;

    /**
     * 当月整体通过率
     */
    private Double nowTotalPassPre;


    /**
     *  整体通过率环比
     */
    private Double chainTotalPassPre;


}
