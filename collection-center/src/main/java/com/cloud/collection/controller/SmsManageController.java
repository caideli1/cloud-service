package com.cloud.collection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.collection.model.SmsManageModel;
import com.cloud.collection.service.SmsManageService;
import com.cloud.common.dto.JsonResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: shipingshang
 * @CreateDate: 2019年8月12日 14:48:31
 * 催收批量短信发送
 */
@Slf4j
@RestController
public class SmsManageController {
	
	@Autowired
	private SmsManageService smsManageService;
	
	/**
     * 催收主管查询列表
     * @param params 前端传入参数
     * @return 催收列表信息
     * @Author: shipingshang
     */
    @GetMapping("/collection/querySmsManageList")
    public JsonResult querySmsManageList(@RequestParam Integer userId,@RequestParam String sendTime) {
    	String ssTime = "";
		String seTime = "";
    	if (!sendTime.equals("")) {
    		String[] sendStartTime = sendTime.split("~");
    		ssTime = sendStartTime[0]+"00:00:00";
    		seTime = sendStartTime[1].trim()+" 23:59:59";
		}
        List<SmsManageModel> smsManageList = smsManageService.querySmsManageList(userId, ssTime,seTime);
        return JsonResult.ok(smsManageList, smsManageList.size());
    }
    
    /**
     * 催收短信编辑发送
     * @param params 前端传入参数
     * @return SMS sent successfully. 
     * @Author: shipingshang
     */
    @GetMapping("/collection/sendSmsInfo")
    public JsonResult sendSmsInfo(@RequestParam String phone,@RequestParam String message,@RequestParam Integer userId,@RequestParam String orderNo){
    	int insertSms = smsManageService.sendSmsInfo(phone, message, userId, orderNo);
    	if (insertSms == 1) {
    		return JsonResult.ok("SMS sent successfully.");
		}
    	return JsonResult.ok();
    }
}
