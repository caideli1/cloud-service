package com.cloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yoga
 * @Description: 借款产品
 */

@Data
public class LoanProductModel implements Serializable {
    private static final long serialVersionUID = -2708884434708731585L;
    private Integer id;
    private String name;

    /**
     * 期限
     */
    private Integer term;

    private String description;

    /**
     * 产品状态
     */
    private Integer status;

    /**
     * 允许最大罚息天数
     */
    private Integer maxPenaltyDay;

    /**
     * 是否允许多笔借款
     */
    private Integer isAllowMultiple;

    /**
     * 还款方式
     */
    private Integer repaymentType;

    /**
     * 合同编号
     */
    private String contractNumber;

    /**
     * 借款金额类型
     */
    private Integer loanAmountType;

    /**
     * 最大借款金额 现在只有固定额度（2019.7） 所以max=min
     */
    private BigDecimal maxAmount;

    /**
     * 最小借款金额 现在只有固定额度（2019.7） 所以max=min
     */
    private BigDecimal minAmount;

    /**
     * 利率表
     */
    private Integer interestId;

    /**
     * 是否收取罚息
     */
    private Integer isCollectPenalty;

    /**
     * 是否收允许提前结清
     */
    private Integer isAllowPrepay;

    /**
     * 罚息计算金额组成
     */
    private Integer penaltyGroup;

    /**
     * 罚息表
     */
    private Integer penaltyInterestId;

    private Integer isDeleted;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

    /**
     * 其他费用
     */
    private List<RateType> rateTypeList;

    private List<Long> rateTypeIdList;

    private InterestModel interest;

    private InterestPenaltyModel interestPenalty;

    private ServiceRateModel serviceRate;

    private Long serviceRateId;

    private Boolean isRecommendation;

    private Integer recommendationSort;
    
}
