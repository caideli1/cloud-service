package com.cloud.platform.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.StringUtils;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.platform.dao.ContractManagerDao;
import com.cloud.platform.model.PlatformContractModel;
import com.cloud.platform.model.PlatformContractTagModel;
import com.cloud.platform.model.PlatformTagsModel;
import com.cloud.platform.service.ContractManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同服务实现
 *
 * @author bjy
 * @date 2019/3/13 0013 11:42
 */
@Service
public class ContractManagerServiceImpl implements ContractManagerService {
    /**
     * 公司合同
     */
    public static final int COMPANY_CONTRACT = 2;

    @Autowired
    private ContractManagerDao contractManagerDao;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;

    /**
     * 获取合同模板列表
     * @param params
     * @return
     */
    @Override
    public JsonResult getContract(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformContractModel> list = contractManagerDao.getContract(params);
        Map<String, String> map = new HashMap<String, String>();
        String[] tagId = null;
        String[] tagName = null;
        for (PlatformContractModel ls : list) {
            if (StringUtils.isNotBlank(ls.getTagIds()) && StringUtils.isNotBlank(ls.getTagNames())) {
                String[] lsTagIds = ls.getTagIds().split(",");
                String[] lsTagNames = ls.getTagNames().split(",");
                for (String tag : lsTagIds) {
                    tagId = tag.split(" ");
                }
                for (String tag : lsTagNames) {
                    tagName = tag.split(" ");
                }
                for (int i = 0; i < tagId.length; i++) {
                    map.put(tagId[i], tagName[i]);
                }
            }
            ls.setMaps(map);
        }

        list.forEach(a -> a.setContractSrc(aliOssUtil.getUrl(a.getContractSrc())));
        PageInfo<PlatformContractModel> page = new PageInfo<PlatformContractModel>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 增加合同模板
     *
     * @param model
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult addContract(PlatformContractModel model) {
        String contractSrc = model.getContractSrc();
        contractSrc = contractSrc.substring(contractSrc.lastIndexOf("/") + 1);
        model.setContractSrc(contractSrc);
        int id = contractManagerDao.addContract(model);
        if (id > 0) {
            if (model.getBusinessStatus() == COMPANY_CONTRACT) {
                List<PlatformContractTagModel> list = new ArrayList<>();
                String[] tagIds = model.getTagIds().split(",");
                if (tagIds.length > 0) {
                    for (int j = 0; j < tagIds.length; j++) {
                        PlatformContractTagModel tagModel = new PlatformContractTagModel();
                        tagModel.setContractId(model.getId());
                        tagModel.setTagId(Integer.parseInt(tagIds[j]));
                        list.add(tagModel);
                    }
                    contractManagerDao.insertContractTags(list);
                    return JsonResult.ok();
                }
            }
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:增加合同模板失败！");
    }

    /**
     * 修改合同模板
     *
     * @param model
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult modifyContract(PlatformContractModel model) {
        contractManagerDao.saveContractSnapshot(model);
        contractManagerDao.updateContract(model);
        contractManagerDao.deleteContractTagIds(model.getId() + "");
        if (model.getBusinessStatus() == COMPANY_CONTRACT) {
            List<PlatformContractTagModel> list = new ArrayList<>();
            String[] tagIds = model.getTagIds().split(",");
            if (tagIds.length > 0) {
                for (int j = 0; j < tagIds.length; j++) {
                    PlatformContractTagModel tagModel = new PlatformContractTagModel();
                    tagModel.setContractId(model.getId());
                    tagModel.setTagId(Integer.parseInt(tagIds[j]));
                    list.add(tagModel);
                }
                contractManagerDao.insertContractTags(list);
                return JsonResult.ok();
            }
        }
        return JsonResult.ok();
    }

    /**
     * 删除合同模板
     *
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult deleteContract(String ids) {
        contractManagerDao.saveContractSnapshotIds(ids);
        contractManagerDao.deleteContract(ids);
        return JsonResult.ok();
    }

    /**
     * 获取合约标签
     *
     * @return
     */
    @Override
    public JsonResult getContractTags() {
        List<PlatformTagsModel> list = contractManagerDao.getContractTags();
        return JsonResult.ok(list);
    }
}
