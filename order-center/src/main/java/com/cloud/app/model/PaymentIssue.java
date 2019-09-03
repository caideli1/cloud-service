package com.cloud.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author yoga
 * @Description: AddPaymentIssueReq
 * @date 2019-07-3016:50
 */
@Getter
@Setter
@Table(name = "finance_payment_issue")
public class PaymentIssue implements Serializable {
    private static final long serialVersionUID = 7760700239853900656L;
    @Id
    private Long id;
    private String phone;
    private String issue = "";
    private String contactPhone;
    private String detailMsg = "";
    private String loanNumber;
}
