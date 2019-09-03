package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.DateParam;
import com.cloud.order.model.ReportOrderPassModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportOrderPassDao   extends CommonMapper<ReportOrderPassModel> {

    @Select("select  *  from report_order_pass where  report_date>=#{startDate} and report_date<=#{endDate}")
    List<ReportOrderPassModel>  selectAllByDate(DateParam dateParam);
}
