package com.cloud.common.enums;

/**
 * @author walle
 */
public interface EnumKeyValue<E extends Enum> {

    Integer getCode();

    String getMessage();
}
