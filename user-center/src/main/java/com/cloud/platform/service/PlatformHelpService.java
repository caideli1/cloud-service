package com.cloud.platform.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformAnswerModelModel;
import com.cloud.platform.model.PlatformProcessedFeedbackModel;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心服务接口有
 * @author bjy
 * @date 2019/3/8 0008 17:55
 */
public interface PlatformHelpService {
    /**
     * 查询平台问答
     * @param params
     * @return
     */
    JsonResult getPlatformAnswer(Map<String, Object> params);

    /**
     * 新建平台问答信息
     * @param modelModel
     * @return
     */
    JsonResult createPlatformAnswer(PlatformAnswerModelModel modelModel);

    /**
     * 删除平台问答信息
     * @param id
     * @return
     */
    JsonResult deletePlatformAnswer(String id);

    /**
     * 修改平台问答信息
     * @param modelModel
     * @return
     */
    JsonResult updatePlatformAnswer(PlatformAnswerModelModel modelModel);

    /**
     * 查询用户反馈
     * @param params
     * @return
     */
    JsonResult getPlatformUserFeedback(Map<String, Object> params);


    /**
     * 创建邮件
     * @param params
     * @return
     */
    JsonResult createMail(Map<String,Object> params);

    /**
     * 创建人工处理结果
     * @param model
     * @return
     */
    JsonResult createPlatformProcessedFeedback(PlatformProcessedFeedbackModel model);

    /**
     * 查询处理记录
     * @param id
     * @return
     */
    List<PlatformProcessedFeedbackModel> getPlatformProcessedFeedback(String id);

    /**
     * 标记处理操作
     * @param id
     * @return
     */
    JsonResult markPlatformUserFeedback(String id);
}
