package com.cloud.user.service;


public interface VerificationCodeService {

	void sendVerificationCode(String phone, Integer msgType);
}
