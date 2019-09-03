package com.cloud.order.dao;


import com.cloud.model.appProduct.PlatformStartPageModel;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.InterestPenaltyReq;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.order.model.AppVersionModel;
import com.cloud.order.model.resp.AppProductRateDescriptionRes;
import com.cloud.order.model.resp.AppProductRes;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mapper
public interface ProductDao {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into product_interest_penalty(name, type, rate, is_deleted, create_user, modify_user, is_penalty) "
            + "values(#{name}, #{type}, #{rate}, 0, #{createUser}, #{createUser}, #{isPenalty})")
    int save(InterestPenaltyReq req);

    @Deprecated
    @Select("select * from product_interest_penalty t where t.name = #{name} and is_deleted = 0")
    InterestPenaltyModel findByName(String name);

    @Select("select t.* from product_interest_penalty t where t.id = #{id} and t.is_deleted = 0 LIMIT 1")
    InterestPenaltyModel findById(Integer id);
    //根据id查询利息表
    @Select("select * from product_interest_penalty where id = #{id} and is_deleted = 0")
    InterestPenaltyModel findInterestById(Integer id);
    //查询所有
    List<InterestPenaltyModel> findAllInterestPenalty(Map<String, Object> params);

    List<InterestPenaltyModel> getInterestPenaltyModelList(Map<String, Object> params);

    int count(Map<String, Object> params);

    //根据ID逻辑删除
    void delInterestPenaltyById(Integer id);

    int updateInterestPenaltyById(InterestPenaltyReq req);

    @Select("select count(*) from product_loan t where t.name = #{name} and t.is_deleted = 0")
    int findProductByName(String name);

    /**
     * 查询对应ID在产品工厂表中是否存在
     * @param id
     * @return
     */
    @Select("select id from product_loan t where t.penalty_interest_id = #{id} and t.is_deleted = 0")
    Integer[] findProductById(Integer id);

    // LoanProduct 相关
    List<LoanProductModel> loanProductList(@Param("offset") int offset, @Param("limit") int limit, @Param("name") String name);

    void insertLoanProduct(LoanProductModel loanProductModel);

    void insertLoanProductRateType(@Param("loanId") Integer loanId, @Param("list") List<Long> rateTypeIds);

    void updateLoanProduct(LoanProductModel loanProductModel);

    LoanProductModel getLoanProductById(Integer id);

    void deleteLoanProductById(@Param("id") Integer id, @Param("userName") String userName);

    int countLoanProduct(@Param("name") String name);

    List<RateType> rateTypeList(@Param("mode") Integer mode, @Param("type") Integer type);

    List<RateType> rateTypeListDistinctName(@Param("type") Integer type);

    void deleteInsertLoanRateTypeByLoanId(Integer id);

    HashMap<String, BigDecimal> getLoanProductMaxAndMinAmountById(Integer id);

    Integer getLoanProductStatusById(Integer id);

    int updateLoanProductStatus(@Param("id") Integer id, @Param("status") Integer status);

    List<LoanProductModel> getLoanProductList();

    PlatformStartPageModel getStartPage();

    InterestPenaltyModel getInterestPenaltyByProductId(Integer productId);
    /**
     * 查询对应ID在产品工厂表中是否存在
     * @param id
     * @return
     */
    @Select("select id from product_loan t where t.interest_id = #{id} and t.is_deleted = 0")
    Integer[] findProductByInterestId(Integer id);
    @Select("select rate from product_interest_penalty t where t.is_deleted = 0 and t.name = #{interestName}")
    BigDecimal getInterestByType(String interestName);
    
    /**
     * 获取版本号
     * @param
     * @return
     * */
    AppVersionModel queryAppVersion();
    /**
     * 更新版本号
     * @param model
     * @return
     * */
    int updateAppVersion(AppVersionModel model);

    void setProductLoanRecommend(Long productId, boolean isRec, int recSort);

    @Select("SELECT count(1) FROM product_loan WHERE recommendation_sort = #{recSort} AND is_deleted = 0 AND is_recommendation = 1 AND recommendation_sort != 0")
    int queryProductCountByRecommendSort(Integer recSort);


    List<AppProductRes> appQueryProductList(@Param("term") Integer term, @Param("isRec") Boolean isRec);

    AppProductRateDescriptionRes appQueryProductServiceRateRateDescription(@Param("term")
                                                                                   Integer term);

    @Select("SELECT psr.gst FROM product_loan pl JOIN product_service_rate psr ON psr.id=pl.service_rate_id WHERE pl.id=#{id}")
    BigDecimal getGstByProductId(Integer id);
}
