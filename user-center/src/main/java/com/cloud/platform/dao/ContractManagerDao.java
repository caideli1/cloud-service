package com.cloud.platform.dao;

import com.cloud.platform.model.PlatformContractModel;
import com.cloud.platform.model.PlatformContractTagModel;
import com.cloud.platform.model.PlatformTagsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同管理dao层
 * @author bjy
 * @date 2019/3/13 0013 11:43
 */
@Mapper
public interface ContractManagerDao {
    /**
     * 获取合同模板
     * @param params
     * @return
     */
    List<PlatformContractModel> getContract(Map<String, Object> params);

    /**
     * 增加合同模板
     * @param model
     * @return
     */
    int addContract(PlatformContractModel model);

    /**
     * 商务合同添加合同标签
     * @param list
     * @return
     */
    int insertContractTags(List<PlatformContractTagModel> list);

    /**
     * 保存合同模板快照
     * @param model
     */
    void saveContractSnapshot(PlatformContractModel model);

    /**
     * 更新合同模板
     * @param model
     * @return
     */
    int updateContract(PlatformContractModel model);

    /**
     * 删除合同标签库
     * @param id
     * @return
     */
    int deleteContractTagIds(@Param("id") String id);

    /**
     * 删除合同模板
     * @param ids
     */
    void deleteContract(@Param("ids") String ids);

    /**
     * 获取平台合同模板标签信息
     * @return
     */
    List<PlatformTagsModel> getContractTags();

    /**
     * 批量保存快照
     * @param ids
     */
    void saveContractSnapshotIds(@Param("ids") String ids);
}
