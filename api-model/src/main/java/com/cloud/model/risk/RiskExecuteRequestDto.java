package com.cloud.model.risk;

import com.cloud.model.risk.dto.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author walle
 */
@Getter
@Setter
public class RiskExecuteRequestDto {

    /**
     * 基本信息
     */
    private AppBaseDto appBaseDto;

    /**
     * 设备信息
     */
    private AppDeviceDto appDeviceDto;

    /**
     * 短信
     */
    private AppMessagesDto appMessagesDto;

    /**
     * 通讯录
     */
    private AppAddressBookDto appAddressBookDto;

    /**
     * 通话记录
     */
    private AppCallRecordDto appCallRecordDto;
}
