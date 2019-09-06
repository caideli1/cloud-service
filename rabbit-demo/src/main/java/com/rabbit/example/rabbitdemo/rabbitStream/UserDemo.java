package com.rabbit.example.rabbitdemo.rabbitStream;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/6 10:47
 * 描述：
 */
@Data
public class UserDemo implements Serializable {
    private String userName;
    private Integer age;
}
