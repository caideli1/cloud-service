package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 更新文档类
 */
@Data
public class UploadDocumentApiEntity extends KudosBaseApiEntity {
    private String borrowerPanDoc;

    private String borrowerAdhaarDoc;

    private String borrowerPhotoDoc;

    private String borrowerEmployerId;

    private String borrowerCibilDoc;

    private String borrowerBnkIfsc;

    private String borrowerBnkStmtDoc;

    private String loanDataReqFlg;

    private String loanSanctionLetterDoc;

    private String loanAgreementDoc;
}
