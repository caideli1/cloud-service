package com.cloud.common.exception;


import com.cloud.common.enums.EnumKeyValue;

/**
 * @author walle
 */
public class BusinessException extends BaseException{

    public BusinessException(EnumKeyValue<?> status) {
        super(status);
    }

    public BusinessException(String message, EnumKeyValue<?> status) {
        super(message, status);
    }

    public BusinessException(String message, Throwable cause, EnumKeyValue<?> status) {
        super(message, cause, status);
    }

    public BusinessException(EnumKeyValue<?> status, Throwable cause) {
        super(status, cause);
    }
}