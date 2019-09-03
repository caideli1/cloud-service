package com.cloud.collection.dao;

import com.cloud.collection.model.CollectionDetailAuditModel;
import com.cloud.collection.model.CollectionDetailsAuditModel;
import com.cloud.collection.model.req.CollectionDetailsAuditReq;
import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.collection.CollectionRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 催收审核详情查看mapper
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Mapper
public interface CollectionDetailsAuditDao extends CommonMapper<CollectionDetailsAuditModel>  {
    @Update("update collection_details_audit set audit_status = #{auditStatus}, audit_date = DATE_FORMAT(NOW(),'%Y-%m-%d'), " +
            "auditor_id = #{auditorId} where id in (${detailAuditIds})")
    Integer updateCollDetailsAudit(@Param("detailAuditIds")String detailAuditIds, @Param("auditStatus")Integer auditStatus, @Param("auditorId")Integer auditorId);

    /**
     * 查询申请订单详情查看列表
     * @param collectionDetailsAuditReq
     * @return
     */
    List<CollectionRecordVo> queryCollDetailsAuditList(Map<String,Object> collectionDetailsAuditReq);

    int existCollDetailsAudit(@Param("sysUserId") long sysUserId, @Param("orderNo") String orderNo);

    /**
     * 根据催收记录主键ids查找记录
     * @param collectionIds
     * @return
     */
    List<CollectionDetailsAuditModel> queryCollDetailAuditByCollIds(String collectionIds);

    /**
     * 批量更新
     * @param detailsAuditModelList
     */
    void updateBatch(List<CollectionDetailsAuditModel> detailsAuditModelList);
}
