package com.cloud.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yoga
 * @Description: AppCodeVerifyRes
 * @date 2019-07-2614:35
 */
@Getter
@Setter
@Builder
public class AppCodeVerifyRes implements Serializable {
    private static final long serialVersionUID = 6492534739161657998L;
    private Boolean verifyResult;
    private String tmpToken;
}
