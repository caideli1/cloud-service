package com.cloud.common.utils;


public class PhoneUtil {

	
	private static final Integer PHONE_LENGTH = 10;

	/**
	 * 校验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (phone == null || phone.length() != PHONE_LENGTH) {
			return Boolean.FALSE;
		}

		if ( !isNumeric(phone)){
			return false;
		}

		return true;
	}

	/**
	 * 是否为纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
}
