package com.cloud.common.component.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * 获取MD5加密
	 * 
	 * @param pwd
	 *            需要加密的字符串
	 * @return String字符串 加密后的字符串
	 */
	public static String getPwd(String pwd) {
		try {
			// 创建加密对象
			MessageDigest digest = MessageDigest.getInstance("md5");

			// 调用加密对象的方法，加密的动作已经完成
			byte[] bs = digest.digest(pwd.getBytes());
			
			// 第一步，将数据全部转换成正数：
			String hexString = "";
			for (byte b : bs) {
				
				int temp = b & 255;
				// 第二步，将所有的数据转换成16进制的形式
				// 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
				// 因此，需要对temp进行判断
				if (temp < 16 && temp >= 0) {
					// 手动补上一个“0”
					hexString = hexString + "0" + Integer.toHexString(temp);
				} else {
					hexString = hexString + Integer.toHexString(temp);
				}
			}
			return hexString;
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		String a = MD5Utils.getPwd("123");
		System.out.println(a);
	}

}
