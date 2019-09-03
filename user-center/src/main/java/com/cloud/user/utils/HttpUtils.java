package com.cloud.user.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.cloud.model.cibil.LoanCIBILEntity;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HttpUtils{
//	替换格式
	private static final String param1 = "&amp;lt;";
	private static final String param2 = "&amp;gt;&#xD;";
	private static final String param3 = "&lt;";
	private static final String param4 = "&gt;&lt;";
	private static final String param5 = "&amp;gt;";
	private static final String param6 = "&gt;";
	private static final String param7 = "<";
	private static final String param8 = ">";
	private static final String param11 = "&amp;amp;lt;";
	private static final String param12 = "&amp;amp;gt;";
	
	private static final String param9 = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><ExecuteXMLStringResponse xmlns=\"http://tempuri.org/\"><ExecuteXMLStringResult>";
	private static final String param10 = "</ExecuteXMLStringResult></ExecuteXMLStringResponse></s:Body></s:Envelope>";
	static {
        System.setProperty("jsse.enableSNIExtension", "false");
    }
	
	//kudos cibil api
    public static String https(String url, Map<String, String> params,String kudosType,String secretKey) {
        URL u = null;
        HttpURLConnection con = null;
        StringBuffer sbf = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sbf.append(e.getKey());
                sbf.append("=");
                sbf.append(e.getValue());
                sbf.append("&");
            }
            sbf.deleteCharAt(sbf.length() - 1);
        }
        log.info("---------------------------http请求地址："+url);
        log.info("---------------------------http请求参数："+sbf.toString());
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("PARTNERXAPIKEY", secretKey);
            con.setRequestProperty("PARTNERID", "KUD-MND-3871");
            con.setRequestProperty("QUERY", kudosType);
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(sbf.toString());
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        String s3 = "";
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
          //替换文本
	        s3=buffer.toString();
	        s3=s3.replaceAll(param1, param7);
	        s3=s3.replaceAll(param2, param8);
	        s3=s3.replaceAll(param3, param7);
	        s3=s3.replaceAll(param4, param8);
	        s3=s3.replaceAll(param5, param8);
	        s3=s3.replaceAll(param6, param8);
	        s3=s3.replaceAll(param9, "");
	        s3=s3.replaceAll(param10, "");
	        s3=s3.replaceAll(param11, param7);
	        s3=s3.replaceAll(param12, param8);
            log.info("---------------------------http请求返回处理后内容："+s3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s3;
    }
}

