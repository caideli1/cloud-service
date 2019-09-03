package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.common.Page;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.InterestPenaltyReq;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.model.user.SysUser;
import com.cloud.order.model.ServiceRateModel;
import com.cloud.order.model.req.AddRateTypeReq;
import com.cloud.order.model.req.AddServiceRateReq;
import com.cloud.order.model.req.UpdateRateTypeReq;
import com.cloud.order.model.resp.AppProductRateDescriptionRes;
import com.cloud.order.model.resp.AppProductRes;
import com.cloud.order.model.resp.ServiceRateDetailRes;
import com.cloud.order.service.FinanceLoanService;
import com.cloud.order.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 产品工厂
 * @Author: wza
 * @CreateDate: 2019/1/8 15:49
 * @Version: 1.0
 */

@Slf4j
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private FinanceLoanService financeLoanService;


    /**
     * @Description: 利息+罚息列表查询
     * @author YogaXiong
     */
//    @PreAuthorize("hasAuthority('product:penalty') or hasAuthority('permission:all')")
    @GetMapping("/interestPenalty/list")
    public JsonResult getInterestPenaltyList(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int limit,
                                             @RequestParam(required = false) Integer type,
                                             @RequestParam(required = false) Integer isPenalty) {

        log.info("start api - get /interestPenalty/list");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("pageSize", limit);
        map.put("type", type);
        map.put("isPenalty", isPenalty);

//            目前不用分页
        List<InterestPenaltyModel> interestPenaltyModelList = productService.getInterestPenaltyModelList(map);
        PageInfo pageInfo = new PageInfo<>(interestPenaltyModelList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    /**
     * 罚息列表查询
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('product:penalty') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/interestPenalty")
    public JsonResult findInterestPenalty(@RequestParam Map<String, Object> params) {
        PageInfo<InterestPenaltyModel> interestPenalty = productService.findInterestPenalty(params);
        return JsonResult.ok(interestPenalty.getList(), (int) interestPenalty.getTotal());
    }


    /**
     * 根据罚息ID更新
     *
     * @param req
     */

    @PreAuthorize(
            "(hasAuthority('product:penalty:update') and #req.isPenalty == 1) " +
                    "or (hasAuthority('product:interest:update') and #req.isPenalty == 0) " +
                    "or hasAuthority('permission:all')"
    )
    @PutMapping("/orders-anon/interestPenalty")
    public JsonResult updateInterestPenaltyById(@RequestBody InterestPenaltyReq req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        req.setModifyUser(name);

        return productService.updateInterestPenaltyById(req);
    }

    @PreAuthorize(
            "(hasAuthority('product:penalty:save') and #req.isPenalty == 1)" +
                    " or (hasAuthority('product:interest:save') and #req.isPenalty == 0) " +
                    "or hasAuthority('permission:all')"
    )
    @PostMapping("/orders-anon/interestPenalty/save")
    public JsonResult save(@RequestBody InterestPenaltyReq req) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        req.setCreateUser(name);
        if (null != req && null != req.getRate()) {

            if (req.getRate().compareTo(new BigDecimal("50.0")) != -1) {
                return JsonResult.errorMsg("创建失败,利率值超出范围");
            }

            if (req.getRate().compareTo(new BigDecimal("0.00")) == -1) {
                return JsonResult.errorMsg("创建失败,利率值不能小于0");
            }
        }
        JsonResult jsonResult = null;
        if (null != req && null != req.getName()) {
            InterestPenaltyModel penalty = productService.findInterestPenaltyByName(req.getName());
            if (null != penalty) {
                return JsonResult.errorMsg("新建失败,存在相同名称数据");
            }
            jsonResult = productService.addInterestPenalty(req);
            jsonResult.setData(req);
        }
        return jsonResult;

    }

    @PreAuthorize(
            "(hasAuthority('product:interest:delete') and #req.isPenalty == 0) " +
                    "or (hasAuthority('product:penalty:delete') and #req.isPenalty == 1) " +
                    "or hasAuthority('permission:all')"
    )
    @DeleteMapping("/interestPenalty")
    public JsonResult delInterestPenaltyById(@RequestBody InterestPenaltyReq req) {
        productService.delInterestPenalty(req);
        return JsonResult.ok("删除成功");
    }

    /**
     * @param page     页数
     * @param limit    每一页大小
     * @param loanName 产品名
     * @Description: 借款产品列表
     * @author YogaXiong
     */
    @PreAuthorize("hasAuthority('product:info') or hasAuthority('permission:all')")
    @GetMapping("/loanProductList")
    public JsonResult loanProductList(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int limit,
                                      @RequestParam(required = false) String loanName) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("page", page);
        map.put("pageSize", limit);
        map.put("loanProductName", loanName);

        Page<LoanProductModel> loanPage = productService.loanProductList(map);
        return JsonResult.ok(loanPage.getData(), loanPage.getTotal());
    }


    /**
     * @Description: 新建借款产品
     * @author YogaXiong
     */
    @PreAuthorize("hasAuthority('product:info:save') or hasAuthority('permission:all')")
    @PostMapping("/loanProduct/create")
    public JsonResult addLoanProduct(@RequestBody LoanProductModel loanProduct) {
        productService.verifyLoanProductModelAdded(loanProduct);

        SysUser user = AppUserUtil.getLoginSysUser();
        loanProduct.setCreateUser(user.getUsername());
        loanProduct.setModifyUser(user.getUsername());

        productService.insertProduct(loanProduct);
        return JsonResult.ok();
    }

    /**
     * @Description: 更新借款产品
     * @author YogaXiong
     */
    @PreAuthorize("hasAuthority('product:info:update') or hasAuthority('permission:all')")
    @PostMapping("/loanProduct/update")
    public JsonResult updateLoanProduct(@RequestBody LoanProductModel loanProduct) throws Exception {
        productService.verifyLoanProductModelAdded(loanProduct);
        productService.updateLoanProduct(loanProduct);
        return JsonResult.ok();
    }

    /**
     * @Description: 修改借款产品状态
     * @author YogaXiong
     */
    @PreAuthorize("hasAuthority('product:info:disable') or hasAuthority('permission:all')")
    @PostMapping("/loanProduct/status/update")
    public JsonResult updateLoanProductStatus(@RequestBody LoanProductModel loanProduct) throws Exception {
        productService.updateLoanProductStatus(loanProduct.getId(), loanProduct.getStatus());
        return JsonResult.ok();
    }

    /**
     * @Description: 删除借款产品
     * @author YogaXiong
     */
    @PreAuthorize("hasAuthority('product:info:delete') or hasAuthority('permission:all')")
    @DeleteMapping("/loanProduct")
    public JsonResult delteLoanProductById(@RequestParam Integer id) {
        productService.deleteLoanProductById(id);
        return JsonResult.ok();
    }

    /**
     * @Description: 借款产品详情
     * @author YogaXiong
     */
    @GetMapping("/loanProduct")
    public JsonResult getLoanProductById(@RequestParam Integer id) {
        LoanProductModel loanProduct = productService.getLoanProductById(id);
        return JsonResult.ok(loanProduct, null);
    }

    /**
     * @Description: 收费类型列表
     * @author YogaXiong
     */
    @GetMapping("/rateTypeList")
    public JsonResult getProductRateTypeList(@RequestParam(required = false) boolean ignoreMode, @RequestParam(required = false) Integer mode, @RequestParam(required = false) Integer type) {
        List<RateType> rateTypeList = productService.getRateTypeList(ignoreMode, mode, type);
        return JsonResult.ok(rateTypeList, rateTypeList.size());
    }

    /**
     * @Description: 新增收费类型
     * @author YogaXiong
     */
//    @PreAuthorize("hasAnyAuthority('', 'permission:all')")
    @PostMapping("/rateType")
    public JsonResult addRateTypes(@Validated @RequestBody List<AddRateTypeReq> rateTypeReqList) {
        List<Long> res = productService.batchInsertRateType(rateTypeReqList);
        return JsonResult.ok(res);
    }

    /**
     * @Description: 更新收费类型
     * @author YogaXiong
     */
    @PutMapping("/rateType")
    public JsonResult batchUpdateRateTypes(@Validated @RequestBody List<UpdateRateTypeReq> rateTypeList) {
        productService.batchUpdateRateTypes(rateTypeList);
        return JsonResult.ok();
    }

    /**
     * @Description: 删除收费类型
     * @author YogaXiong
     */
    //    @PreAuthorize("hasAnyAuthority('', 'permission:all')")
    @DeleteMapping("/rateType")
    public JsonResult deleteRateType(@RequestBody List<Long> ids) {
        productService.deleteRateTypes(ids);
        return JsonResult.ok();
    }


    /**
     * 利率表详情
     *
     * @param id
     * @return
     */
    @GetMapping("/orders-anon/internal/interestPenaltyModel")
    public InterestPenaltyModel getInterestPenaltyById(@RequestParam Integer id) {
        InterestPenaltyModel interestPenaltyDetail = productService.findInterestPenaltyDetail(id);
        return interestPenaltyDetail;
    }

    @GetMapping("/orders-anon/internal/loanProduct")
    public LoanProductModel getLoanProduct(@RequestParam Integer id) {
        LoanProductModel loanProduct = productService.getLoanProductById(id);
        return loanProduct;
    }

    @GetMapping("/orders-anon/internal/findFailFianceLoanByUserIdAndReason")
    public List<Map<String, Object>> findFailFianceLoanByUserIdAndReason(@RequestParam Long userId) {
        String reason = "BANKCARD_ERROR_FLAG";
        String reason2 = "transfer not completed";
        return financeLoanService.findFailFianceLoanByUserIdAndReason(userId, reason, reason2);
    }

    @GetMapping("/orders-anon/internal/renewBankInfoTail")
    public JsonResult renewBankInfoTail(@RequestParam Long userId, @RequestParam String bankCode) {
        String reason = "BANKCARD_ERROR_FLAG";
        String reason2 = "transfer not completed";
        return financeLoanService.renewBankInfoTail(userId, bankCode, reason, reason2);
    }

    /**
     *  服务费列表
     */
    @GetMapping("/serviceRate/list")
    public JsonResult getServiceRateList(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int limit,
                                         @RequestParam(required = false) String name) {
        PageHelper.startPage(page, limit);
        List serviceRateModelList = productService.getServiceRateModelList(name);
        PageInfo<ServiceRateModel> pageInfo = new PageInfo<>(serviceRateModelList);
        return JsonResult.ok(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    /**
     *  服务费详情
     */
    @GetMapping("/serviceRate/{id}")
    public JsonResult getServiceRateList(@PathVariable Long id) {
        ServiceRateDetailRes res = productService.getServiceRateDetail(id);
        return JsonResult.ok(res);
    }

    /**
     *  删除服务费
     */
    @DeleteMapping("/serviceRate/{id}")
    public JsonResult deleteServiceRate(@PathVariable Long id) {
        productService.deleteServiceRate(id);
        return JsonResult.ok();
    }

    /**
     *  增加服务费
     */
    @PostMapping("/serviceRate")
    public JsonResult addServiceRate(@RequestBody AddServiceRateReq addServiceRateReq) {
        productService.addServiceRate(addServiceRateReq);
        return JsonResult.ok();
    }

    /**
     *  更新服务费
     */
    @PutMapping("/serviceRate")
    public JsonResult updateServiceRate(@RequestBody AddServiceRateReq updateServiceRateReq) {
        productService.updateServiceRate(updateServiceRateReq);
        return JsonResult.ok();
    }

    /**
     *  检查服务费是否未关联产品
     *  true - 没有关联 -> 可以操作
     *  false - 有关联或不存在 -> 不可操作
     */
    @GetMapping("/serviceRate/{id}/isUsed")
    public JsonResult checkServiceRateIsNotUsed(@PathVariable Long id) {
        boolean res = productService.isServiceRateNotUsed(id);
        return JsonResult.build(200, res ? "Not Used" : "Used or Not Exist" ,res);
    }


    @PutMapping("/loanProduct/{id}/recommend")
    public JsonResult updateProductLoanRecommend(@PathVariable Long id, @RequestParam boolean isRec, @RequestParam(required = false) Integer recSort) {
        productService.setProductLoanRecommend(id, isRec, recSort);
        return JsonResult.ok();
    }


    /**
     *  app - 产品列表+推荐产品
     */
    @GetMapping("/orders-anon/app/productList")
    public JsonResult appGetRecommendProductList(@RequestParam(required = false) Integer term, @RequestParam(required = false) Boolean isRec) {
        List<AppProductRes> appProductResList = productService.appGetProductList(term, isRec);
        return JsonResult.ok(appProductResList, appProductResList.size());
    }

    /**
     *  app - 产品费率说明
     */
    @GetMapping("/orders-anon/app/product/serviceRate/description")
    public JsonResult appProductServiceRateRateDesc(@RequestParam Integer term) {
        AppProductRateDescriptionRes appProductRateDescriptionResList = productService.appProductServiceRateRateDesc(term);
        return JsonResult.ok(appProductRateDescriptionResList);
    }


    @GetMapping("/orders-anon/product/gst")
    public BigDecimal getProductGst(@RequestParam Integer productId) {
        return productService.getProductGstByProductId(productId);
    }

}
