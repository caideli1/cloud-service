package com.cloud.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cloud.model.user.UserBankCard;
import com.cloud.model.user.UserInfo;
import com.cloud.platform.dao.ContractManagerDao;
import com.cloud.platform.model.PlatformContractModel;
import com.cloud.user.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RpcCallController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ContractManagerDao contractManagerDao;

	@GetMapping("/users-anon/payout/getUserInfo")
    public UserInfo getUserInfo(@RequestParam("userId") Long userId){
		log.info("获取用户userId{}基本信息",userId);
		UserInfo userInfo = userDao.getUserInfoById(userId);
        return userInfo;
    }
	
	@GetMapping("/users-anon/payout/getBankCard")
    public UserBankCard getBankCardByUserId(@RequestParam("userId") Long userId){
		log.info("获取用户userId{}银行卡信息",userId);
		return userDao.getBankCardByUserId(userId);
    }
	
	@GetMapping("/users-anon/contract/getContractTemplate")
	public String getContractTemplate(@RequestParam("contractName") String contractName){
		log.info("获取{}模板",contractName);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("contractName", contractName);
		List<PlatformContractModel> platformContractModelList = contractManagerDao.getContract(params);
		PlatformContractModel platformContractModel = null;
		if(platformContractModelList.size() > 0){
			platformContractModel = platformContractModelList.get(0);
		}
		if(platformContractModel == null){
			return "";
		}else{
			return JSON.toJSONString(platformContractModel);
		}
	}
}
