package com.cloud.model.kudosCibil;
import java.util.List;


import lombok.Data;
@Data
public class CreditreportEntity {

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

