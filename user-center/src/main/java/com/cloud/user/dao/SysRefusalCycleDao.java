package com.cloud.user.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.SysRefusalCycle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysRefusalCycleDao extends CommonMapper<SysRefusalCycle> {

    @Update("update  sys_refusal_cycle  set  cycle_day_count=#{cycleDayCount}  where id=#{id}")
    int  updateCycleDayCountById(SysRefusalCycle sysRefusalCycle);

}
