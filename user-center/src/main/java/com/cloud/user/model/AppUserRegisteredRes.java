package com.cloud.user.model;

import lombok.*;

import java.io.Serializable;

/**
 * @author yoga
 * @Description: AppUserRegisteredRes
 * @date 2019-07-2614:11
 */
@Getter
@Setter
@Builder
public class AppUserRegisteredRes implements Serializable {
    private static final long serialVersionUID = -4014246368922417699L;
    private Boolean isRegistered;
    private Boolean isSetPassword;
    private String tmpToken;
}
