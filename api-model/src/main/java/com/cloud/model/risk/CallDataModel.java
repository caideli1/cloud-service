package com.cloud.model.risk;

import lombok.Data;

/**
 * @author bjy
 * @date 2019/3/28 0028 17:50
 */
@Data
public class CallDataModel {

    private long callTimes;
    private long missedTimes;
    private String missedRate;
    private long inTimes;
    private long outTimes;
    private long dayCallTimes;
    private long nightCallTimes;
    private long deepNightCallTimes;
    private long effectiveTimes;
    private String effectiveRate;
    private double avgEffectiveDuration;
    private String outRate;
    private String inRate;
    private double avgInDuration;
    private double avgOutDuration;
    private String dayCallRate;
    private String nightCallRate;
    private String deepNightCallRate;

    public void setMissedRate(String missedRate) {
        try {
            this.missedRate = missedRate;
        }catch (Exception e){
            this.missedRate="0%";
        }
    }

    public void setEffectiveRate(String effectiveRate) {
        try {
            this.effectiveRate = effectiveRate;
        }catch (Exception e){
            this.effectiveRate="0%";
        }
    }

    public void setOutRate(String outRate) {
        try {
            this.outRate = outRate;
        }catch (Exception e){
            this.outRate="0%";
        }
    }

    public void setInRate(String inRate) {
        try {
            this.inRate = inRate;
        }catch (Exception e){
            this.inRate="0%";
        }
    }

    public void setDayCallRate(String dayCallRate) {
        try {
            this.dayCallRate = dayCallRate;
        }catch (Exception e){
            this.dayCallRate ="0%";
        }
    }

    public void setNightCallRate(String nightCallRate) {
        try {
            this.nightCallRate = nightCallRate;
        }catch (Exception e){
            this.nightCallRate="0%";
        }
    }

    public void setDeepNightCallRate(String deepNightCallRate) {
        try {
            this.deepNightCallRate = deepNightCallRate;
        }catch (Exception e){
            this.deepNightCallRate="0%";
        }
    }

    public void setCallTimes(long callTimes) {
        try {
            this.callTimes = callTimes;
        }catch (Exception e){
            this.callTimes=0;
        }
    }

    public void setMissedTimes(long missedTimes) {
        try {
            this.missedTimes = missedTimes;
        }catch (Exception e){
            this.missedTimes=0;
        }
    }

    public void setInTimes(long inTimes) {
        try {
            this.inTimes = inTimes;
        }catch (Exception e){
            this.inTimes=0;
        }
    }

    public void setOutTimes(long outTimes) {
        try {
            this.outTimes = outTimes;
        }catch (Exception e){
            this.outTimes=0;
        }
    }

    public void setDayCallTimes(long dayCallTimes) {
        try {
            this.dayCallTimes = dayCallTimes;
        }catch (Exception e){
            this.dayCallTimes=0;
        }
    }

    public void setNightCallTimes(long nightCallTimes) {
        try {
            this.nightCallTimes = nightCallTimes;
        }catch (Exception e){
            this.nightCallTimes=0;
        }
    }

    public void setDeepNightCallTimes(long deepNightCallTimes) {
        try {
            this.deepNightCallTimes = deepNightCallTimes;
        }catch (Exception e){
            this.deepNightCallTimes=0;
        }
    }

    public void setEffectiveTimes(long effectiveTimes) {
        try {
            this.effectiveTimes = effectiveTimes;
        }catch (Exception e){
            this.effectiveTimes=0;
        }
    }

    public void setAvgEffectiveDuration(double avgEffectiveDuration) {
        try {
            this.avgEffectiveDuration = avgEffectiveDuration;
        }catch (Exception e){
            this.avgEffectiveDuration=0;
        }
    }

    public void setAvgInDuration(double avgInDuration) {
        try {
            this.avgInDuration = avgInDuration;
        }catch (Exception e){
            this.avgInDuration=0;
        }
    }

    public void setAvgOutDuration(double avgOutDuration) {
        try {
            this.avgOutDuration = avgOutDuration;
        }catch (Exception e){
            this.avgOutDuration=0;
        }
    }
}
