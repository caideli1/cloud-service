package com.cloud.collection.service;

import java.util.List;

import com.cloud.collection.model.SmsManageModel;

public interface SmsManageService {
	List<SmsManageModel> querySmsManageList(Integer userId,String sendStartTime,String sendEndTime);
	
	int sendSmsInfo(String phone,String message,Integer userId,String orderNo);
}
