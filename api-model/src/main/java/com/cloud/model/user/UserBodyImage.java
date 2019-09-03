package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: 用户活体图片
 */
@Data
public class UserBodyImage implements Serializable {
    private static final long serialVersionUID = 2442457969543825193L;
    private Integer id;
    /**
     * 眨眼图url
     */
    private String blinkImg;
    /**
     * 张嘴图片url
     */
    private String mouthImg;
    /**
     * 点头图片url
     */
    private String headImg;
    private Date createTime;
}
