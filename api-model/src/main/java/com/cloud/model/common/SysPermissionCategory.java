package com.cloud.model.common;

import java.util.Arrays;

/**
 * @author yoga
 * @Description: TODO
 * @date 2019-05-0810:44
 */
public enum SysPermissionCategory {

    // 管理员权限
    ADMIN(1, "管理员权限"),
    // 产品工厂
    PRODUCT_FACTORY(2, "产品工厂"),
    //客户管理
    CUSTOMER_MANAGEMENT(3, "客户管理"),
    //业务管理
    BUSINESS_MANAGEMENT(4, "业务管理"),
    //财务清算
    FINANCIAL_SETTLEMENT(5, "财务清算"),
    //催收管理
    URGENCY_MANAGEMENT(6, "催收管理"),
    //平台运营
    PLATFORM_OPERATION(7, "平台运营"),
    //报表中心
    REPORT_CENTER(8, "报表中心"),
    // 风控中心
    RISK_CENTER(9, "风控中心"),
    ;

    public int num;

    public String desc;

    SysPermissionCategory(int num, String desc) {
        this.num = num;
        this.desc = desc;
    }

    public int getNum() {
        return num;
    }

    public String getDesc() {
        return desc;
    }

    public static SysPermissionCategory fromCategoryNum(int num) {
        return Arrays.stream(SysPermissionCategory.values())
                .filter(i -> i.getNum() == num)
                .findFirst().orElseGet(null);
    }
}
