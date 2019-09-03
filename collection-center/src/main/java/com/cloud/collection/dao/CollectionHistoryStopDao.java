package com.cloud.collection.dao;

import com.cloud.collection.model.CollectionStopHistory;
import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.collection.CollectionStopHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 16:34
 * 描述：
 */
@Mapper
public interface CollectionHistoryStopDao extends CommonMapper<CollectionStopHistory> {

    @Select("select * from collection_stop_history where collection_id = #{collectionId} order by id desc limit 1 ")
    CollectionStopHistory getNewOneCollectionStopHistory(@Param("collectionId") Long collectionId);

}
