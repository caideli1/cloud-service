package com.cloud.order.dao;

import com.cloud.order.model.QueryParamModel;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 查询字段dao
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Mapper
public interface ReportQueryParamsDao {
    //根据类型来获取查询字段
    @Select("SELECT eng_name, cn_name, param_type, param_value FROM report_query_params WHERE data_type = #{type}")
    List<QueryParamModel> selectQueryParamsByType(@Param("type")int type);
}
