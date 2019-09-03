package com.cloud.user.controller;

import com.cloud.model.cibil.CibilIdSegmentDto;
import com.cloud.model.risk.RiskExecuteRequestDto;
import com.cloud.model.risk.dto.AppBaseDto;
import com.cloud.mq.sender.RiskExecuteSender;
import com.cloud.user.model.Creditreport;
import com.cloud.user.model.PartKudosAccountNonSummary;
import com.cloud.user.model.Enquiry;
import com.cloud.user.model.Scoresegment;
import com.cloud.user.service.KudosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * kudos cibil 提供给其他服务调用接口
 *
 * @author walle
 */
@Slf4j
@RestController
@RequestMapping("/users-anon/cibil")
public class KudosCibilController {

    @Autowired
    private KudosService kudosService;

    @GetMapping("/score")
    String getUserCibleScoreByOrderNo(@RequestParam("orderNo") String orderNo) {
        Scoresegment scoresegment = Optional.ofNullable(kudosService.getScoresegmentInfo(orderNo))
                .orElseGet(() -> new Scoresegment());
        return scoresegment.getScore();
    }

    @GetMapping("/idSegmentList")
    List<CibilIdSegmentDto> getUserIdSegmentListByOrderNoAndIdType(@RequestParam("orderNo") String orderNo, @RequestParam("idType") String idType) {
        return kudosService.getIdsegmentInfo(orderNo, idType)
                .stream()
                .map(idSegment -> CibilIdSegmentDto.builder()
                        .id(idSegment.getId())
                        .idtype(idSegment.getIdtype())
                        .idnumber(idSegment.getIdnumber())
                        .build())
                .collect(Collectors.toList());
    }


    @GetMapping("/kudosAccountNonSummaryList")
    List<PartKudosAccountNonSummary> getRiskKudosAccountNonSummaryList(@RequestParam("orderNo") String orderNo) {
        return kudosService.getRiskKudosAccountNonSummary(orderNo);
    }

    @Autowired
    private RiskExecuteSender riskExecuteSender;


    @GetMapping("/queryEnquiryListByOrderNo")
    List<Enquiry> queryEnquiryListByOrderNo(@RequestParam("orderNo") String  orderNo){
      return   kudosService.getEnquiryInfo(orderNo);
    }

    @GetMapping("/getCreditreportInfo")
    Creditreport getCreditreportInfo(@RequestParam("orderNo") String orderNo){
        return kudosService.getCreditreportInfo(orderNo);
    }
}
