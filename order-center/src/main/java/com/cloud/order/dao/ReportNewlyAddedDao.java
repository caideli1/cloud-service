package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.DateParam;
import com.cloud.order.model.ReportNewlyAddedModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportNewlyAddedDao  extends CommonMapper<ReportNewlyAddedModel> {

    @Select("select  *  from report_newly_added where  report_date>=#{startDate} and report_date<=#{endDate}")
    List<ReportNewlyAddedModel> selectAllByDate(DateParam dateParam);
}
