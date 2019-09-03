package com.cloud.model.appUser;
/**
* @Description:    user_pan_card dto
* @Author:         wza
* @CreateDate:     2019/3/15 11:05
* @Version:        1.0
*/
import lombok.Data;

@Data
public class UserPanCardReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * panCard正面照
     */
    private String cardFrontUrl;
    /**
     * panCard背面照
     */
    private String cardBackUrl;
    /**
     * panCard正面照pdf
     */
    private String cardFrontPdf;
    /**
     * panCard背面照pdf
     */
    private String cardBackPdf;
    /**
     * 持有人的全名
     */
    private String holdsFullName;
    /**
     * 父亲名称
     */
    private String fathersName;

    /**
     * 永久账户
     */
    private String permanentNo;

    private Boolean isIdentified;

    private String  birthday;

    private Boolean match;

    private String matchScore;

}
