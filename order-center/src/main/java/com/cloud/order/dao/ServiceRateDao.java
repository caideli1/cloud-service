package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.resp.ServiceRateDetailRes;
import com.cloud.order.model.ServiceRateModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * @author yoga
 * @Description: ServiceRateDao
 * @date 2019-07-0918:03
 */
@Mapper
public interface ServiceRateDao extends CommonMapper<ServiceRateModel> {
    List<ServiceRateModel> getServiceRateModelList(@Param("name") String name);

    ServiceRateDetailRes getServiceRateDetail(Long id);

    void deleteServiceRate(Long id);

    int addServiceRate(ServiceRateModel serviceRateModel);

    void updateServiceRate(ServiceRateModel serviceRateModel);

    // 看服务费关联了几个产品
    @Select("SELECT COUNT(*) FROM product_loan WHERE is_deleted = 0 AND service_rate_id = #{id}")
    int queryServiceRateRelatedCount(Long id);


    @Select("SELECT COUNT(*) FROM product_service_rate WHERE id = #{id} AND is_deleted = 0")
    int isServiceRateExist(Long id);
}
