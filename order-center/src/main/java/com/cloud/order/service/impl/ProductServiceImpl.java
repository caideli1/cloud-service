package com.cloud.order.service.impl;

import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.common.Page;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.InterestPenaltyReq;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.model.user.SysUser;
import com.cloud.order.constant.ProductConstants;
import com.cloud.order.dao.ProductDao;
import com.cloud.order.dao.RateTypeDao;
import com.cloud.order.dao.ServiceRateDao;
import com.cloud.order.model.ServiceRateModel;
import com.cloud.order.model.req.AddRateTypeReq;
import com.cloud.order.model.req.AddServiceRateReq;
import com.cloud.order.model.req.UpdateRateTypeReq;
import com.cloud.order.model.resp.AppProductRateDescriptionRes;
import com.cloud.order.model.resp.AppProductRes;
import com.cloud.order.model.resp.ServiceRateDetailRes;
import com.cloud.order.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

	@Autowired
    private RateTypeDao rateTypeDao;

	@Autowired
    private ServiceRateDao serviceRateDao;

    /**
     * 分页查询所有罚息表数据,也可根据罚息名称分页查询
     *
     * @param params
     * @return
     */
    @Override
    public PageInfo<InterestPenaltyModel> findInterestPenalty(Map<String, Object> params) {
//        Integer isPenalty = MapUtils.getInteger(params, "isPenalty");
        Integer page = MapUtils.getInteger(params, "page");
        Integer limit = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, limit);
        List<InterestPenaltyModel> list = productDao.findAllInterestPenalty(params);
        for (InterestPenaltyModel obj : list) {
            //如果在产品表中无关联就设值0
                /*if (null != obj && (obj.getIsRelevance() == null || obj.getProIsDel().equals(ProductConstants.PRO_IS_DELETED))) {
                    obj.setIsRelevance(ProductConstants.NOT_RELEVSNCE);
                }*/
            obj.setRate(obj.getRate().multiply(new BigDecimal(100)));
            obj.setCreateTimeStr(DateUtil.getStringDate(obj.getCreateTime(), DateUtil.DateFormat1));
        }
        PageInfo<InterestPenaltyModel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * @Description: 利息+罚息列表查询
     * @author YogaXiong
     */
    @Override
    public List<InterestPenaltyModel> getInterestPenaltyModelList(Map<String, Object> params) {
        List<InterestPenaltyModel> modelList = productDao.getInterestPenaltyModelList(params);
        for (InterestPenaltyModel model : modelList) {
            model.setRate(model.getRate().multiply(new BigDecimal(100)));
        }
        return modelList;
    }

    /**
     * 根据罚息表ID查询
     *
     * @param id
     * @return
     */
    @Override
    public InterestPenaltyModel findInterestPenaltyDetail(Integer id) {
        InterestPenaltyModel interestPenaltyModel = productDao.findById(id);
        if (null != interestPenaltyModel) {
            String date = DateUtil.getStringDate(interestPenaltyModel.getCreateTime(), DateUtil.DateFormat1);
            interestPenaltyModel.setCreateTimeStr(date);
            if (interestPenaltyModel.getIsRelevance() == null) {
                interestPenaltyModel.setIsRelevance(ProductConstants.NOT_RELEVSNCE);
            }
        }
        return interestPenaltyModel;
    }

    /**
     * 根据罚息表ID更新
     *
     * @param req
     */
    @Override
    public JsonResult updateInterestPenaltyById(InterestPenaltyReq req) {
        req.setRate(req.getRate().divide(new BigDecimal(100), 3, RoundingMode.HALF_UP));
        int i = productDao.updateInterestPenaltyById(req);

        if (i < 0) {
            return JsonResult.errorMsg("修改失败");
        }

        return JsonResult.ok();
    }

    @Override
    public void delInterestPenalty(InterestPenaltyReq req) {
        InterestPenaltyModel model = productDao.findById(req.getId());
        if (model == null) {
            throw new IllegalArgumentException("无此id");
        }
        Integer[] ids = null;
        Integer[] interestIds = null;
        ids = productDao.findProductById(req.getId());
        interestIds = productDao.findProductByInterestId(req.getId());
        //如果数组有值,则对应ID在产品表中有数据,不能删除
        if (ArrayUtils.isNotEmpty(ids) || ArrayUtils.isNotEmpty(interestIds)) {
            throw new IllegalArgumentException("有关联产品,无法删除");
        }
        productDao.delInterestPenaltyById(req.getId());
    }

    @Override
    public Page<LoanProductModel> loanProductList(Map<String, Object> params) {
        int page = (int) params.getOrDefault("page", 1);
        int pageSize = (int) params.getOrDefault("pageSize", 10);
        String loanProductName = (String) params.get("loanProductName");
        List<LoanProductModel> loanProductList = productDao.loanProductList((page - 1) * pageSize, pageSize, loanProductName);
        Page<LoanProductModel> loanProductPage = new Page<LoanProductModel>();
        loanProductPage.setData(loanProductList);
        int count = productDao.countLoanProduct(loanProductName);
        loanProductPage.setTotal(count);
        return loanProductPage;
    }

    @Transactional()
    @Override
    public void insertProduct(LoanProductModel loanProductModel) {
        productDao.insertLoanProduct(loanProductModel);

        Long serviceRateId = loanProductModel.getServiceRateId();
        if (null == serviceRateId) {
            return;
        }

        List<Long> ids = rateTypeDao.queryByServiceRateId(serviceRateId).stream()
                .map(RateType::getId)
                .collect(Collectors.toList());

        if (null == ids) {
            return;
        }
        if (ids.size() > 0) {
            productDao.insertLoanProductRateType(loanProductModel.getId(), ids);
        }
    }

    @Override
    public LoanProductModel getLoanProductById(Integer id) {
        return productDao.getLoanProductById(id);
    }

    @Transactional()
    @Override
    public void updateLoanProduct(LoanProductModel loanProductModel) throws Exception {
        Integer status = productDao.getLoanProductStatusById(loanProductModel.getId());
        if (status.intValue() == ProductConstants.STATUS_ACTIVE) {
            throw new RuntimeException("不能修改激活的产品");
        }
        SysUser user = AppUserUtil.getLoginSysUser();
        loanProductModel.setModifyUser(user.getUsername());
        productDao.updateLoanProduct(loanProductModel);
        List<Long> ids = loanProductModel.getRateTypeIdList();
        if (null == ids) {
            return;
        }
        // 更新中间表: 先删除，再插入
        productDao.deleteInsertLoanRateTypeByLoanId(loanProductModel.getId());
        if (ids.size() > 0) {
            productDao.insertLoanProductRateType(loanProductModel.getId(), ids);
        }
    }

    @Override
    public void deleteLoanProductById(Integer id) {
        SysUser user = AppUserUtil.getLoginSysUser();
        productDao.deleteLoanProductById(id, user.getUsername());
    }

    @Override
    public void verifyLoanProductModelAdded(LoanProductModel model) {
        log.info("start verifyLoanProductModel");
        Integer status = model.getStatus();
        Integer repaymentType = model.getRepaymentType();
        Integer loanAmountType = model.getLoanAmountType();
        BigDecimal maxAmount = model.getMaxAmount();
        BigDecimal minAmount = model.getMinAmount();
        Integer penaltyGroup = model.getPenaltyGroup();

        // 新建产品时检验名称
        if (model.getId() == null && productDao.findProductByName(model.getName()) > 0 ) {
            throw new IllegalArgumentException("产品名称重复");
        }

        if (null != status && (0 > status || 4 < status)) {
            throw new IllegalArgumentException("产品状态error");
        }

        // repaymentType 共有 1-6 七种类型
        if (null != repaymentType && (0 > repaymentType && 7 < repaymentType)) {
            throw new IllegalArgumentException("还款方式error");
        }


        /*
         * repaymentType 共有1-2 两种类型 --> 现在只有固定额度2 （2019.7）
         *
        if (null != loanAmountType && (loanAmountType > 2 || loanAmountType < 1)
        ) { //区间
            result = "借款金额类型error";
            return result;
        }

         */

        if (null != loanAmountType && (loanAmountType != 2)) { //区间
            throw new IllegalArgumentException("借款金额类型error");
        }

        /*
         *废弃 maxAmount minAmount 现在只有固定额度（2019.7） 所以max=min
         *
        if (null != maxAmount && null != minAmount && maxAmount.compareTo(minAmount) != 1) { // min max同时设置
            result = "最大最小借款金额error";
            return result;
        } else if (null == minAmount && null != maxAmount) { // 单独更新 max
            BigDecimal dataBaseMinAmount = productDao.getLoanProductMaxAndMinAmountById(model.getId())
                    .getOrDefault("minAmount", BigDecimal.valueOf(0.0));
            result = maxAmount.compareTo(dataBaseMinAmount) == 1 ? "ok" : "最大借款金额error";
            return result;
        } else if (null == maxAmount && null != minAmount) { // 单独更新 min
            BigDecimal dataBaseMaxAmount = productDao.getLoanProductMaxAndMinAmountById(model.getId())
                    .getOrDefault("maxAmount", BigDecimal.valueOf(0.0));
            result = dataBaseMaxAmount.compareTo(minAmount) == 1 ? "ok" : "最小借款金额error";
            return result;
        }
        */
        if (!(null != maxAmount && null != minAmount && maxAmount.compareTo(minAmount) == 0)) {
            throw new IllegalArgumentException("最大最小借款金额error");
        }


        // penaltyGroup 共有1-4 种类型
        if (null != penaltyGroup && (penaltyGroup < 1 || penaltyGroup > 4)) {
            throw new IllegalArgumentException("罚息计算金额组成error");
        }
    }

    @Override
    public JsonResult addInterestPenalty(InterestPenaltyReq req) {
        req.setRate(req.getRate().divide(new BigDecimal(100), 3, RoundingMode.HALF_UP));
        int result = productDao.save(req);

        if (result < 1) {
            return JsonResult.errorMsg("新建罚息数据失败");
        }
        return JsonResult.ok();
    }

    @Override
    public List<RateType> getRateTypeList(boolean ignoreMode, Integer mode, Integer type) {
        if (ignoreMode) {
            return productDao.rateTypeListDistinctName(type);
        } else {
            return productDao.rateTypeList(mode, type);
        }
    }

    @Override
    public InterestPenaltyModel findInterestPenaltyByName(String name) {
        InterestPenaltyModel penaltyModel = productDao.findByName(name);
        if (null != penaltyModel) {
            String date = DateUtil.getStringDate(penaltyModel.getCreateTime(), DateUtil.DateFormat1);
            penaltyModel.setCreateTimeStr(date);
            if (penaltyModel.getIsRelevance() == null) {
                penaltyModel.setIsRelevance(ProductConstants.NOT_RELEVSNCE);
            }
        }
        return penaltyModel;
    }

    @Transactional()
    @Override
    public void updateLoanProductStatus(Integer id, Integer status) throws Exception {
        if (status.intValue() != ProductConstants.STATUS_ABANDON && status.intValue() != ProductConstants.STATUS_ACTIVE) {
            throw new Exception("状态参数错误！");
        }
        int result = productDao.updateLoanProductStatus(id, status);
        if (result == 0) {
            throw new Exception("请检查产品id！");
        }
    }

    @Transactional
    @Override
    public List<Long> batchInsertRateType(List<AddRateTypeReq> rateTypeReqList) {
        List<RateType> rateTypeList = rateTypeReqList.stream()
                .map(i -> RateType.builder()
                        .name(i.getName())
                        .mode(i.getMode())
                        .type(i.getType())
                        .rate(i.getRate())
                        .createUser(AppUserUtil.getLoginSysUser().getUsername())
                        .createTime(DateUtil.getDate())
                        .build()
                ).collect(Collectors.toList());

        rateTypeDao.batchInsertRateType(rateTypeList);
        return rateTypeList.stream().map(RateType::getId).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void batchUpdateRateTypes(List<UpdateRateTypeReq> rateTypeList) {
        //检查关联产品是否被禁用
        boolean res = rateTypeList.stream()
                .anyMatch(i -> null != rateTypeDao.queryRelatedProductStatus(i.getId()) &&
                        !Objects.equals(rateTypeDao.queryRelatedProductStatus(i.getId()), 3));
        if (res) {
            throw new RuntimeException("关联的产品未禁用！");
        }

        if(!CollectionUtils.isEmpty(rateTypeList)) {
            rateTypeDao.batchUpdateRateTypes(rateTypeList);
        }
    }

    @Transactional
    @Override
    public void deleteRateTypes(List<Long> ids) {
        boolean res = ids.stream()
                .anyMatch(i -> null != rateTypeDao.queryRelatedProductStatus(i) &&
                        !Objects.equals(rateTypeDao.queryRelatedProductStatus(i), 3));
        if (res) {
            throw new RuntimeException("关联的产品未禁用！");
        }
        if (CollectionUtils.isEmpty(ids)) { return; }
        rateTypeDao.deleteRateType(ids);
    }

    @Override
    public List<ServiceRateModel> getServiceRateModelList(String name) {
        return serviceRateDao.getServiceRateModelList(name);
    }

    @Override
    public ServiceRateDetailRes getServiceRateDetail(Long id) {
        ServiceRateDetailRes detailRes = serviceRateDao.getServiceRateDetail(id);
        List<RateType> rateTypeList = rateTypeDao.queryByServiceRateId(id);
        detailRes.setRateTypeList(rateTypeList);
        return detailRes;
    }

    @Transactional
    @Override
    public void addServiceRate(AddServiceRateReq addServiceRateReq) {

        // 校验totalRate是否正确
        List<Long> rateTypeIdList = addServiceRateReq.getRateTypeIdList();

        BigDecimal caculateTotalRate = rateTypeDao.sumRateOfRateTypes(rateTypeIdList);

        if(caculateTotalRate.compareTo(addServiceRateReq.getTotalRate()) != 0) {
            throw new IllegalArgumentException("总费率不正确！");
        }

        String userName = AppUserUtil.getLoginSysUser().getUsername();
        ServiceRateModel model = ServiceRateModel.builder()
                .name(addServiceRateReq.getName())
                .totalRate(addServiceRateReq.getTotalRate())
                .gst(addServiceRateReq.getGst())
                .createTime(DateUtil.getDate())
                .createUser(userName)
                .updateTime(DateUtil.getDate())
                .updateUser(userName)
                .build();
        //insert service_rate
        serviceRateDao.addServiceRate(model);
        //更改关联表
        rateTypeDao.updateRateTypeServiceRateId(model.getId(), rateTypeIdList);
    }

    @Transactional
    @Override
    public void deleteServiceRate(Long id) {
        if (!isServiceRateNotUsed(id)) {
            throw new RuntimeException("无法删除! 服务费正在使用");
        }

        serviceRateDao.deleteServiceRate(id);
        //删除关联product_rate_type ？
        List<Long> rateTypeIdList = rateTypeDao.queryByServiceRateId(id).stream()
                .map(RateType::getId)
                .collect(Collectors.toList());
        deleteRateTypes(rateTypeIdList);
    }

    @Transactional
    @Override
    public void updateServiceRate(AddServiceRateReq updateServiceRate) {
        if (!isServiceRateNotUsed(updateServiceRate.getId())) {
            throw new RuntimeException("无法更新! 服务费正在使用");
        }

        String userName = AppUserUtil.getLoginSysUser().getUsername();
        ServiceRateModel serviceRateModel = ServiceRateModel.builder()
                .id(updateServiceRate.getId())
                .name(updateServiceRate.getName())
                .totalRate(updateServiceRate.getTotalRate())
                .gst(updateServiceRate.getGst())
                .updateTime(DateUtil.getDate())
                .updateUser(userName)
                .build();

        //update service_rate
        serviceRateDao.updateServiceRate(serviceRateModel);

        List<Long> oldRateTypeIdList = rateTypeDao.queryByServiceRateId(serviceRateModel.getId()).stream()
                .map(RateType::getId)
                .collect(Collectors.toList());

        List<Long> newRateTypeIdList = updateServiceRate.getRateTypeIdList();

        //要删除的rateTypeId - 删除关联+逻辑删除rate_type
        Collection<Long> deleteRateTypeIds = org.apache.commons.collections4.CollectionUtils
                .subtract(oldRateTypeIdList, newRateTypeIdList);
        log.info("{} 要删除的rateTypeId：{}",serviceRateModel, deleteRateTypeIds);
        if (!CollectionUtils.isEmpty(deleteRateTypeIds)) {
            deleteRateTypes(deleteRateTypeIds.stream().collect(Collectors.toList()));
//            rateTypeDao.deleteRateType(deleteRateTypeIds.stream().collect(Collectors.toList()));
        }

        //要新关联的rateTypeId
        Collection<Long> addRateTypeIds = org.apache.commons.collections4.CollectionUtils.subtract(newRateTypeIdList,
                oldRateTypeIdList);
        log.info("{} 要增加的rateTypeId：{}",serviceRateModel, addRateTypeIds);
        if (!CollectionUtils.isEmpty(addRateTypeIds)) {
            rateTypeDao.updateRateTypeServiceRateId(serviceRateModel.getId(), addRateTypeIds.stream().collect(Collectors.toList()));
        }
    }

    /**
     *  检查服务费是否未关联产品
     *  true - 没有关联 -> 可以操作
     *  false - 有关联或不存在 -> 不可操作
     */
    @Override
    public boolean isServiceRateNotUsed(Long serviceRateId) {
        boolean isServiceRateExist = serviceRateDao.isServiceRateExist(serviceRateId) > 0;
        int serviceRateRelatedCount = serviceRateDao.queryServiceRateRelatedCount(serviceRateId);
        return isServiceRateExist && serviceRateRelatedCount == 0;
    }

    @Override
    public void setProductLoanRecommend(Long productId, boolean isRec, Integer recSort) {
        // 检查排序是否有重复
        if (isRec) {
            int cnt = productDao.queryProductCountByRecommendSort(recSort);
            if (cnt > 0) {
                throw new RuntimeException("存在相同的推荐排序！");
            }
        }
        productDao.setProductLoanRecommend(productId, isRec, isRec ? recSort : 0);
    }

    @Override
    public List<AppProductRes> appGetProductList(Integer term, Boolean isRec) {
        return productDao.appQueryProductList(term, isRec);
    }

    @Override
    public AppProductRateDescriptionRes appProductServiceRateRateDesc(Integer term) {
        AppProductRateDescriptionRes res = productDao.appQueryProductServiceRateRateDescription(term);
        // TODO: - 暂时写死
        RateType interestRateType = RateType.builder().name("Loan interest rate").rate(new BigDecimal("0.001")).build();
        res.getRateTypesList().add(0, interestRateType);
        return res;
    }

    @Override
    public BigDecimal getProductGstByProductId(Integer id) {
        return productDao.getGstByProductId(id);
    }
}
