package com.cloud.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.order.model.KudosParamEntity;
import com.cloud.order.model.KudosResponseEntity;
import com.cloud.order.model.LoanCibilResponseEntity;
import com.cloud.order.model.kudos.ValidationPost;

@Mapper
public interface KudosDao {
	List<KudosParamEntity> queryKudos();

    KudosParamEntity queryKudosParams(@Param("name") String name, @Param("code") String code, @Param("value") String value);

    ValidationPost queryValidationPostParams(@Param("orderNo") String orderNo);
    ValidationPost queryValidationPostCibilScore(@Param("orderNo") String orderNo);
    int insertKudosResponse(KudosResponseEntity kudosResponse);

    int updateKudosResponse(KudosResponseEntity kudosResponse);

    int insertKudosCibil(LoanCibilResponseEntity le);

    KudosResponseEntity selectKudosResponse(@Param("orderNo") String orderNo, @Param("kudosType") String kudosType);

    String queryKudosResponseId(@Param("orderId") String orderId, @Param("kudosType") String kudosType);

    LoanCibilResponseEntity queryCibilScore(@Param("orderId") String orderId);

    List<LoanCibilResponseEntity> queryCibilCount(@Param("orderId") String orderId);

    String queryCibilScoreByOrderNo(@Param("orderNo") String orderNo);

}
