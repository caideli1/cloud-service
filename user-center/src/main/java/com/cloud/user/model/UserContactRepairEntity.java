package com.cloud.user.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: UserContactRepairEntity
 * @date 2019-06-0616:30
 */
@Data
@Builder
@Table(name = "user_contact_repaired")
public class UserContactRepairEntity implements Serializable {
    private static final long serialVersionUID = -394661323116718301L;
    @Id
    private Long id;
    private Long userId;
    private String contactName;
    private String contactMobile;
    private Integer contactType;
    private Date repairDate;
}
