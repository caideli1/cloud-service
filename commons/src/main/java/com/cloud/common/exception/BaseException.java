package com.cloud.common.exception;


import com.cloud.common.enums.EnumKeyValue;

/**
 * @author walle
 */
public class BaseException extends RuntimeException {

    private EnumKeyValue<?> status;

    public BaseException(EnumKeyValue<?> status) {
        super();
        this.status = status;
    }

    public BaseException(EnumKeyValue<?> status, Throwable cause) {
        super(status.getMessage(), cause);
        this.status = status;
    }

    public BaseException(String message, EnumKeyValue<?> status) {
        super(message);
        this.status = status;
    }

    public BaseException(String message, Throwable cause, EnumKeyValue<?> status) {
        super(message, cause);
        this.status = status;
    }

    public EnumKeyValue<?> getStatus() {
        return status;
    }

    public void setStatus(EnumKeyValue<?> status) {
        this.status = status;
    }
}