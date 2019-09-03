package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppFAQInfo {
    private Integer id;
    private String answerTitle;
    private String answerContext;
    /**
     * 問題類型1：常見問題 2:账户问题3：贷前4：贷中5：贷后
     */
    private int answerType;
    private String[] contentArray;
}
