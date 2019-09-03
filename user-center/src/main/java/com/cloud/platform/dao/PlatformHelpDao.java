package com.cloud.platform.dao;

import com.cloud.platform.model.PlatformAnswerModelModel;
import com.cloud.platform.model.PlatformProcessedFeedbackModel;
import com.cloud.platform.model.PlatformUserFeedbackModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 平台帮助中心dao层
 * @author bjy
 * @date 2019/3/8 0008 17:56
 */
@Mapper
public interface PlatformHelpDao {
    /**
     * 查询平台问答信息
     * @param params
     * @return
     */
    List<PlatformAnswerModelModel> getPlatformAnswer(Map<String, Object> params);

    /**
     * 新建平台问答信息
     * @param modelModel
     */
    void createPlatformAnswer(PlatformAnswerModelModel modelModel);

    /**
     * 删除平台问答信息
     * @param id
     * @return
     */
    int deletePlatformAnswer(@Param("id") String id);

    /**
     * 修改平台问答信息
     * @param modelModel
     * @return
     */
    int updatePlatformAnswer(PlatformAnswerModelModel modelModel);

    /**
     * 查询用户反馈
     * @param params
     * @return
     */
    List<PlatformUserFeedbackModel> getPlatformUserFeedback(Map<String, Object> params);

    /**
     * 创建人工处理结果
     * @param model
     * @return
     */
    int createPlatformProcessedFeedback(PlatformProcessedFeedbackModel model);

    /**
     * 查询处理记录
     * @param id
     * @return
     */
    List<PlatformProcessedFeedbackModel> getPlatformProcessedFeedback(@Param("id") String id);

    /**
     * 标记处理操作
     * @param id
     * @return
     */
    int markPlatformUserFeedback(@Param("id") String id, @Param("processDate")Date processDate);

    /**
     * 根据主键查询用户反馈信息
     * @param id
     * @return
     */
    PlatformUserFeedbackModel getPlatformUserFeedbackById(@Param("id") String id);
}
