package com.cloud.user.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author yoga
 * @Description: 修复联系人请求实体
 * @date 2019-06-0614:56
 */
@Getter
@Setter
public class UserContactRepairReq implements Serializable {
    private static final long serialVersionUID = 8428755347402711674L;
    @NotNull
    @Min(1)
    private long userId;
    @NotBlank
    private String contactName;
    @NotBlank
    private String contactMobile;
    @NotNull
    @Max(2)
    @Min(1)
    private int contactType;

}
