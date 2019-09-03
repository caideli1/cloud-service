package com.cloud.common.component.utils;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
 
import javax.crypto.SecretKey;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HttpEncryptUtil {
 	// TODO: - app端rsa加密后的内容无法解析（原因还没找到），暂时先用aes加密。

	//APP加密请求内容 -- (本地调试使用)
	// 得到的结果如果进行 url直接传参 要urldecode 否则会丢失符号
	public static String appEncrypt(String publicKeyStr, String content) throws Exception{

		//将Base64编码后的Server公钥转换成PublicKey对象
//		PublicKey serverPublicKey = RSAUtil.string2PublicKey(publicKeyStr);
		//每次都随机生成AES秘钥
		String aesKeyStr = AESUtil.genKeyAES();
		SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
		//用Server公钥加密AES秘钥
//		byte[] encryptAesKey = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), serverPublicKey);

		byte[] encryptAesKey = AESUtil.encryptAES(aesKeyStr.getBytes(), aesKey);
		//用AES秘钥加密请求内容
		byte[] encryptRequest = AESUtil.encryptAES(content.getBytes(), aesKey);

		JSONObject result = new JSONObject();
//		result.put("ak", RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
//		result.put("ct", RSAUtil.byte2Base64(encryptRequest).replaceAll("\r\n", ""));

		result.put("ak", AESUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
		result.put("ct", AESUtil.byte2Base64(encryptRequest).replaceAll("\r\n", ""));
		return result.toString();
//		return URLEncoder.encode(result.toString(), "utf-8");
	}
	
	//服务器解密APP的请求内容
	public static String serverDecrypt(String content) throws Exception{
		log.info("要解密的内容 -- {}", content);
//		String dcontent = URLDecoder.decode(content, "utf-8");
		JSONObject fromResult = JSONObject.parseObject(content);
		String encryptAesKeyStr = (String) fromResult.get("ak");
		String encryptContent = (String) fromResult.get("ct");

		//将Base64编码后的Server私钥转换成PrivateKey对象
//		PrivateKey serverPrivateKey = RSAUtil.string2PrivateKey(RSAKeyUtil.SERVER_PRIVATE_KEY);

		//用Server私钥解密AES秘钥
//		byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), serverPrivateKey);

		String aesKeyStr = AESUtil.genKeyAES();
		SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);

		byte[] aesKeyBytes = AESUtil.decryptAES(AESUtil.base642Byte(encryptAesKeyStr), aesKey);
//		log.info("aes key str {}", new String(aesKeyBytes));
//		SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
		//用AES秘钥解密请求内容
//		byte[] request = AESUtil.decryptAES(RSAUtil.base642Byte(encryptContent), aesKey);
		byte[] request = AESUtil.decryptAES(AESUtil.base642Byte(encryptContent), aesKey);

		JSONObject result = new JSONObject();
		result.put("ak", new String(aesKeyBytes));
		result.put("ct", new String(request));

//		log.info("解密后的内容 -- {}", result);
		return result.toString();
	}


//	public static void main(String args[]) throws Exception {
//		String appPublicKeyStr = RSAKeyUtil.SERVER_PUBLIC_KEY;
//		String content = "666666";
//		String encContent = HttpEncryptUtil.appEncrypt(appPublicKeyStr, content);
//
//		log.info("url encode -- {}", URLEncoder.encode(encContent));
//
//		String prikey = RSAKeyUtil.SERVER_PRIVATE_KEY;
//		String deContent = HttpEncryptUtil.serverDecrypt(encContent);
//	}
}