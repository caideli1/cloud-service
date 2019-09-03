package com.cloud.model.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 消息传递参数对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    /**
     * 发送消息模板类型
     *
     * @link com.cloud.common.enums.NotificationTemplateTypeEnum
     */
    private Integer templateType;

    /**
     * 接收方用户id
     */
    private Long userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 收件人邮箱
     */
    private String email;

    /**
     * 其他消息模板中需要的参数
     */
    private Map<String, Object> templateOtherParams;

}
