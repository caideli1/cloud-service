package com.cloud.common.enums;

/**
 * kudos cibil id segment type enum
 *
 * @author walle
 */
public enum KudosCibilIdType {
    PANCARD("01", "Pan Card"),
    PASSPORT("02", "Passport"),
    VOTER_ID("03", "Voter Id"),
    DRIVING_LICENSE("04", "Driving license"),
    RATION_CARD("05", "Ration card"),
    UID("06", "UID");

    private String code;

    private String message;

    KudosCibilIdType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }


    public String getMessage() {
        return this.message;
    }
}
