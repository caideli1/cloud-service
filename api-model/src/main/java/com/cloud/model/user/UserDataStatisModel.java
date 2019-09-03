package com.cloud.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataStatisModel {
    private Long id;
    private Long userId;
    private String startImi;
    private String channel;
    private String loginImi;
    private Date createTime;
}
