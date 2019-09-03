package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.DateParam;
import com.cloud.order.model.ReportDueModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportDueDao  extends CommonMapper<ReportDueModel> {


  @Select("select  *  from   report_due  where  report_date>=#{startDate} and  report_date<=#{endDate}")
    List<ReportDueModel> selectAllByDateParam(DateParam dteParam);
}
