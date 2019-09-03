package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: UserContact
 */
@Data
public class UserContact implements Serializable {
    private static final long serialVersionUID = 8472584377058074948L;
    private Integer id;
    private String name;
    private String relation;
    private String phone;
    private Date createTime;
}
