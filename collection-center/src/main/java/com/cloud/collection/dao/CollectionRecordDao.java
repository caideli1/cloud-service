package com.cloud.collection.dao;

import com.cloud.collection.model.CollectionRecord;
import com.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/14 14:33
 * 描述：
 */
@Mapper
public interface CollectionRecordDao extends CommonMapper<CollectionRecord> {
    /**
     * 批量标量
     * @param ids
     * @param isMark
     * @return
     */
    int batchMarkCollectionRecords(@Param("ids") String ids, @Param("isMark") Boolean isMark);

    /**
     * 批量停催
     * @param ids
     * @return
     */
    @Update("update collection_record set date_withdrawal = now(),collection_station = 4,close_collection_count = close_collection_count + 1 where id in (${ids}) ")
    int batchStopCollection(@Param("ids") String ids);

    @Update("update collection_record set due_end=#{dueEnd},is_extension=#{isExtension},pay_status=#{payStatus} where due_id=#{dueId} ")
    int  updateCollectionToDueEnd(CollectionRecord collectionRecord);
    @Update("update collection_record set due_end=#{dueEnd},is_extension=#{isExtension},pay_status=#{payStatus} where loan_num=#{orderNo} and  is_extension=0 ")
    int  updateCollectionToExtension(CollectionRecord collectionRecord);
    @Update("update collection_record set pay_status=#{payStatus} where loan_num=#{loanNum} and  is_extension=0   ")
    int  updateCollectionPayStatus(CollectionRecord collectionRecord);

}
