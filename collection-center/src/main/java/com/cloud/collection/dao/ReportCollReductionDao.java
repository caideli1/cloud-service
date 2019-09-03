package com.cloud.collection.dao;

import com.cloud.collection.model.ReportCollReductionModel;
import com.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 罚息减免报表mapper
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Mapper
public interface ReportCollReductionDao extends CommonMapper<ReportCollReductionModel> {
    List<ReportCollReductionModel> queryCollReductionModelList(@Param("collectorId")Integer collectorId, @Param("appointCaseDate") String appointCaseDate);
}
