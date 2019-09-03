package com.cloud.model.cibil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * kudos cibil idsegment dto
 *
 * @author walle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CibilIdSegmentDto {
    private Integer id;

    private String orderNo;

    private String length;

    private String segmenttag;

    private String idtype;

    private String idnumberfieldlength;

    private String idnumber;

    private String enrichedthroughenquiry;
}
