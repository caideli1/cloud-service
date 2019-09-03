package com.cloud.collection.controller;

import com.cloud.collection.constant.CollectionApplyStatus;
import com.cloud.collection.model.CollectionInterestReductionModel;
import com.cloud.collection.service.CollectionService;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.collection.CollectionInterestReductionModelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/20 15:29
 * 描述：罚息减免类
 */
@Slf4j
@RestController
public class CollectionReductionController {

    @Autowired(required = false)
    private CollectionService collectionService;

    /**
     * 获取最近一条已经通过的罚息减免
     * @param orderNo
     * @return
     */
    @GetMapping("/collection/getOneEnableCollectionInterestReduction")
    public CollectionInterestReductionModelVo getOneEnableCollectionInterestReduction(@RequestParam String orderNo) {
        //获取审核通过的，最近的一条罚息减免记录
        Map<String,Object> params = new HashMap<>(8);
        params.put("applyStatus", CollectionApplyStatus.PASS.num);//审核通过的
        params.put("orderNo", orderNo);
        params.put("enable", true);
        CollectionInterestReductionModel collectionInterestReductionModel = collectionService.getOneCollectionInterestReductionByParams(params);
        CollectionInterestReductionModelVo collectionInterestReductionModelVo = null;
        if (collectionInterestReductionModel!=null){
            collectionInterestReductionModelVo = new CollectionInterestReductionModelVo();
            BeanUtils.copyProperties(collectionInterestReductionModel, collectionInterestReductionModelVo);
        }
        return collectionInterestReductionModelVo;
    }

}
