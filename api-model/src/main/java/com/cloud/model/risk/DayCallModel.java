package com.cloud.model.risk;

import lombok.Data;

import java.util.List;

/**
 * @author bjy
 * @date 2019/3/29 0029 11:18
 */
@Data
public class DayCallModel {
    private String name;
    private List<Double> data;
}
