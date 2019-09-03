package com.cloud.platform.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformContractModel;

import java.util.Map;

/**
 * 合同管理服务接口
 * @author bjy
 * @date 2019/3/13 0013 11:42
 */
public interface ContractManagerService {
    /**
     * 获取合同模板列表
     * @param params
     * @return
     */
    JsonResult getContract(Map<String, Object> params);

    /**
     * 增加合同模板
     * @param model
     * @return
     */
    JsonResult addContract(PlatformContractModel model);

    /**
     * 修改合同模板
     * @param model
     * @return
     */
    JsonResult modifyContract(PlatformContractModel model);

    /**
     * 删除合同模板
     * @param ids
     * @return
     */
    JsonResult deleteContract(String ids);

    /**
     * 获取合同标签
     * @return
     */
    JsonResult getContractTags();
}
