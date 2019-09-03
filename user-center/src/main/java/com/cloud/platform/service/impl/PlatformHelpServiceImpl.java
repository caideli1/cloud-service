package com.cloud.platform.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.NotificationTemplateTypeEnum;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.model.notification.NotificationDto;
import com.cloud.platform.dao.PlatformHelpDao;
import com.cloud.platform.model.PlatformAnswerModelModel;
import com.cloud.platform.model.PlatformProcessedFeedbackModel;
import com.cloud.platform.model.PlatformUserFeedbackModel;
import com.cloud.platform.service.PlatformHelpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台帮助中心服务实现类
 *
 * @author bjy
 * @date 2019/3/8 0008 17:55
 */
@Slf4j
@Service
public class PlatformHelpServiceImpl implements PlatformHelpService {

    @Autowired
    private PlatformHelpDao helpDao;

    @Autowired
    private NotificationSender notificationSender;

    /**
     * 处理类型：1：邮件回复 2：人工回复
     */
    private static final int EMAIL_BACK = 1;
    private static final int PERSON_BACK = 2;

    /**
     * 查询平台问答信息
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult getPlatformAnswer(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformAnswerModelModel> list = helpDao.getPlatformAnswer(params);
        PageInfo<PlatformAnswerModelModel> page = new PageInfo<PlatformAnswerModelModel>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 新建文答信息
     *
     * @param modelModel
     * @return
     */
    @Override
    public JsonResult createPlatformAnswer(PlatformAnswerModelModel modelModel) {
        try {
            helpDao.createPlatformAnswer(modelModel);
            return JsonResult.ok();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    /**
     * 删除平台问答信息
     *
     * @param id
     * @return
     */
    @Override
    public JsonResult deletePlatformAnswer(String id) {

        int i = helpDao.deletePlatformAnswer(id);
        if (i > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:删除平台问答信息失败！");
    }

    /**
     * 修改平台问答信息
     *
     * @param modelModel
     * @return
     */
    @Override
    public JsonResult updatePlatformAnswer(PlatformAnswerModelModel modelModel) {
        int i = helpDao.updatePlatformAnswer(modelModel);
        if (i > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:修改平台问答信息失败！");
    }

    /**
     * 查询用户反馈
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult getPlatformUserFeedback(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getIntValue(params, "page"), MapUtils.getIntValue(params, "limit"));
        List<PlatformUserFeedbackModel> modelList = helpDao.getPlatformUserFeedback(params);
        PageInfo<PlatformUserFeedbackModel> pageInfo = new PageInfo<PlatformUserFeedbackModel>(modelList);
        return JsonResult.ok(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    /**
     * 创建回复邮件
     *
     * @param params
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult createMail(Map<String, Object> params) {

        String id = MapUtils.getString(params, "id");
        PlatformUserFeedbackModel userFeedback = helpDao.getPlatformUserFeedbackById(id);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("content", MapUtils.getString(params, "content"));
        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(NotificationTemplateTypeEnum.FEED_BACK.getCode())
                // .userId((int) userFeedback.getUserId())
                .email(userFeedback.getUserEmail())
                .templateOtherParams(paramMap)
                .build();
        notificationSender.send(notificationDto);

        PlatformProcessedFeedbackModel processedFeedbackModel = new PlatformProcessedFeedbackModel();
        processedFeedbackModel.setProcessUser(MapUtils.getString(params, "processUser"));
        processedFeedbackModel.setFeedbackId(MapUtils.getInteger(params, "id"));
        processedFeedbackModel.setProcessContext(MapUtils.getString(params, "content"));
        processedFeedbackModel.setProcessType(EMAIL_BACK);
        helpDao.createPlatformProcessedFeedback(processedFeedbackModel);
        return JsonResult.ok();

    }

    /**
     * 创建人工处理结果
     *
     * @param model
     * @return
     */
    @Override
    public JsonResult createPlatformProcessedFeedback(PlatformProcessedFeedbackModel model) {
        model.setProcessType(PERSON_BACK);
        if (helpDao.createPlatformProcessedFeedback(model) > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:创建人工处理结果失败!");
    }

    /**
     * 查询处理记录
     *
     * @param id
     * @return
     */
    @Override
    public List<PlatformProcessedFeedbackModel> getPlatformProcessedFeedback(String id) {
        return helpDao.getPlatformProcessedFeedback(id);
    }

    /**
     * 标记处理操作
     *
     * @param id
     * @return
     */
    @Override
    public JsonResult markPlatformUserFeedback(String id) {
        int i = helpDao.markPlatformUserFeedback(id,new Date());
        if (i > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:标记处理失败");
    }
}
