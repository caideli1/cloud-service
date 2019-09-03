package com.cloud.user.model;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="kudos_accounts")
public class Accounts extends CibilOrderEntity {

    private String account;
}