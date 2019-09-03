package com.cloud.common.config;

import java.math.BigDecimal;

/**
 * Created by hasee on 2019/4/12.
 */
public class CommonConfig {
    //增值税
    public static final BigDecimal ADDEDVALUE = new BigDecimal("0.18");
    public static final BigDecimal CASH_LOAN_EXTENSION_INTEREST = new BigDecimal("0.01500");

    //利率表记录名称   由于利率的获取是根据记录的名称获取  为了防止名称的修改导致的影响
    //这里将所有的利率名称列出如下
    public static final String EXTENSION_INTEREST = "Cash Loan Extension Interest";
    public static final String INTEREST_14 = "Cash Loan Interest-14Days";
    public static final String INTEREST_7 = "Cash Loan Interest-7Days";
    public static final String INTEREST_DUE_14 = "Penalty Interest-14days";
    public static final String INTEREST_DUE_7 = "Penalty Interest-7days";

    //被拒绝后重新申请的周期
    public static final int REAPPLYPERIOD = 7;

    //银行卡最多修改（其实是新增）次数
    public static final int MAX_MODIFY_BANKCARD_COUNT = 3;

    //银行卡信息需要在多少天内更新成功
    public static final int MAX_MODIFY_BANKCARD_PERIOD = 3;

    public static final boolean VALIDATE_BANKCARD_OPEN = false;

    public static final String BANKCARD_ERROR_FLAG = "BANKCARD_ERROR_FLAG";

    public static final String ABLE_TO_RELOAN = "ABLE_TO_RELOAN";


    //kotak环境配置
    //测试
    /*public static final String CLIENT_CODE = "TEMPTEST1";

    public static final String PRODUCT_CODE = "WPAY";

    public static final String MSG_SOURCE = "MUTUALIND";

    public static final String ACCOUNT_NO = "09582650000173";*/
    //生产
    public static final String CLIENT_CODE = "ESCROW";

    public static final String PRODUCT_CODE = "VENPAY";

    public static final String MSG_SOURCE = "MONEED";

    public static final String ACCOUNT_NO = "4312845472";
}
