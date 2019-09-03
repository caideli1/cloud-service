package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.product.RateType;
import com.cloud.order.model.req.UpdateRateTypeReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yoga
 * @Description: RateTypeDao
 * @date 2019-07-0314:57
 */
@Mapper
public interface RateTypeDao extends CommonMapper<RateType> {
    void deleteRateType(List<Long> ids);

    int batchUpdateRateTypes(List<UpdateRateTypeReq> rateTypeList);

    int updateRateTypeServiceRateId(Long serviceRateId, List<Long> rateTypeIdList);

    BigDecimal sumRateOfRateTypes(List<Long> rateTypeIdList);

    int batchInsertRateType(List<RateType> rateTypeList);

    List<RateType> queryByServiceRateId(Long serviceRateId);

    @Select("SELECT product_loan.`status` FROM product_loan_rate_type JOIN product_loan ON product_loan.id=product_loan_rate_type.product_id WHERE rate_type_id=#{id}")
    Integer queryRelatedProductStatus(Long id);
}
