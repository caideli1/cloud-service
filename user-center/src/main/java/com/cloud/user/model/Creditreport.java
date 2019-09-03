package com.cloud.user.model;
import java.util.List;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kudos_creditreport")
public class Creditreport extends CibilOrderEntity{

    private Header header;
    private Namesegment namesegment;
    private String idsegment;
    private String telephonesegment;
    private Employmentsegment employmentsegment;
    private Scoresegment scoresegment;
    private List<Address> address;
    private String account;
    private List<Enquiry> enquiry;
    private End end;
    private String emailcontactsegment;

}