package com.cloud.collection.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.collection.dao.SmsManageDao;
import com.cloud.collection.model.SmsManageModel;
import com.cloud.collection.service.SmsManageService;
import com.cloud.model.user.SysUser;
import com.cloud.service.feign.notification.SmsClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsManageServiceImpl implements SmsManageService {
	
	@Autowired
	private SmsManageDao smsManageDao;
	@Autowired
	private SmsClient smsClient;
	@Override
	public List<SmsManageModel> querySmsManageList(Integer userId, String sendStartTime,String sendEndTime) {
		List<SmsManageModel> smsManageList = smsManageDao.querySmsManageInfo(userId, sendStartTime, sendEndTime);
		return smsManageList;
	}
	
	@Override
	public int sendSmsInfo(String phone, String message, Integer userId,String orderNo) {
		phone = phone.replaceAll("，",",");//中英文
		phone = phone.replaceAll(" ",",");//空格
		String[] to = phone.trim().split(",");
		int ii = 0;
		String regex = "\\d{10}";
		for (int i = 0; i < to.length; i++) {
//			log.info("批量发送短信--------》"+to[i]);
			if (Pattern.matches(regex, to[i])) {//是否为10位数字
				smsClient.smsManageInfo(message, to[i], 0, userId);
				ii++;//发送次数
			}
		}
		SysUser sysUser = smsManageDao.querySysUserInfo(userId);
		SmsManageModel smsManageModel = new SmsManageModel();
		smsManageModel.setNoticeContext(message);
		smsManageModel.setPhone(phone);
		smsManageModel.setUserId(userId);
		smsManageModel.setUserName(sysUser.getUsername());
		smsManageModel.setOrderNo(orderNo);
		smsManageModel.setSendSum(to.length+"");//需要发送的总数，以逗号分隔
		smsManageModel.setReceiveSum(ii+"");
		smsManageModel.setStatusCode("200");
		smsManageModel.setCreateTime(LocalDateTime.now());
		smsManageModel.setUpdateTime(LocalDateTime.now());
		return smsManageDao.insert(smsManageModel);
	}
}
