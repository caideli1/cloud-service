package com.cloud.order.dao;

import com.cloud.order.model.ExportParamModel;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 导出字段dao
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Mapper
public interface ReportExportParamsDao {
    //根据类型来查询导出字段
    @Select("SELECT eng_name, cn_name, is_extra FROM report_export_params WHERE data_type = #{type}")
    List<ExportParamModel> selectExportParamsByType(@Param("type")int type);
}
