package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.common.Page;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.InterestPenaltyReq;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.order.model.ServiceRateModel;
import com.cloud.order.model.req.AddRateTypeReq;
import com.cloud.order.model.req.AddServiceRateReq;
import com.cloud.order.model.req.UpdateRateTypeReq;
import com.cloud.order.model.resp.AppProductRateDescriptionRes;
import com.cloud.order.model.resp.AppProductRes;
import com.cloud.order.model.resp.ServiceRateDetailRes;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {
    PageInfo<InterestPenaltyModel> findInterestPenalty(Map<String, Object> params);

    InterestPenaltyModel findInterestPenaltyDetail(Integer id);

    Page<LoanProductModel> loanProductList(Map<String, Object> params);

    List<InterestPenaltyModel> getInterestPenaltyModelList(Map<String, Object> params);

    void insertProduct(LoanProductModel loanProductModel);

    void updateLoanProduct(LoanProductModel loanProductModel) throws Exception;

    void deleteLoanProductById(Integer id);

    LoanProductModel getLoanProductById(Integer id);

    JsonResult updateInterestPenaltyById(InterestPenaltyReq req);

    void delInterestPenalty(InterestPenaltyReq req);

    void verifyLoanProductModelAdded(LoanProductModel model);

    JsonResult addInterestPenalty(InterestPenaltyReq req);

    List<RateType> getRateTypeList(boolean ignoreMode, Integer mode, Integer type);

    InterestPenaltyModel findInterestPenaltyByName(String name);

    void updateLoanProductStatus(Integer id, Integer status) throws Exception;

    List<Long> batchInsertRateType(List<AddRateTypeReq> reqList);

    void batchUpdateRateTypes(List<UpdateRateTypeReq> rateTypeList);

    void deleteRateTypes(List<Long> ids);

    List<ServiceRateModel> getServiceRateModelList(String name);

    ServiceRateDetailRes getServiceRateDetail(Long id);

    void addServiceRate(AddServiceRateReq addServiceRateReq);

    void deleteServiceRate(Long id);

    void updateServiceRate(AddServiceRateReq updateServiceRateReq);

    boolean isServiceRateNotUsed(Long serviceRateId);

    void setProductLoanRecommend(Long productId, boolean isRec, Integer recSort);

    List<AppProductRes> appGetProductList(Integer term, Boolean isRec);

    AppProductRateDescriptionRes appProductServiceRateRateDesc(Integer term);

    BigDecimal getProductGstByProductId(Integer id);
}
