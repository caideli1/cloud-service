package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppAccreditVo {
    private boolean aadhaarStatus;
    private boolean panCardStatus;
    private boolean voterIdCardStatus;
    private boolean needCertify;
}
