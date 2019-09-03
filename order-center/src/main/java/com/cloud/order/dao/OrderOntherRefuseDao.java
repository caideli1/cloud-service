package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.OrderOntherRefuse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OrderOntherRefuseDao  extends CommonMapper<OrderOntherRefuse> {

    @Select("select  *  from   order_onther_refuse  where  order_no=#{orderNo}")
    List<OrderOntherRefuse> slectAllByOrderNo(String orderNo);

}
