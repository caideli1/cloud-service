package com.cloud.kudos.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpUtils {

    public static String getUrlAsString(String url, Map<String, String> params, Map<String, String> requestHeader) {
        return httpRequest(url, params, requestHeader, HttpMethod.GET);
    }

    public static String postUrlAsString(String url, Map<String, String> params, Map<String, String> requestHeader) {
        return httpRequest(url, params, requestHeader, HttpMethod.POST);
    }

    //post kudos api
    public static String httpRequest(String url, Map<String, String> params, Map<String, String> requestHeader, HttpMethod httpMethod) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sbf = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sbf.append(e.getKey());
                sbf.append("=");
                sbf.append(e.getValue());
                sbf.append("&");
            }
            //            sbf.substring(0, sbf.length() - 1);
            sbf.deleteCharAt(sbf.length() - 1);
        }
        log.info("---------------------------http请求地址：" + url);
        log.info("---------------------------http请求参数：" + sbf.toString());
        log.info("---------------------------Partner=" + requestHeader.get("Partner"));
        log.info("---------------------------Authkey=" + requestHeader.get("Authkey"));
        log.info("---------------------------Partnerid=" + requestHeader.get("Partnerid"));
        log.info("---------------------------Query=" + requestHeader.get("Query"));
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(httpMethod.name());
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Partner", requestHeader.get("Partner"));
            con.setRequestProperty("Authkey", requestHeader.get("Authkey"));
            con.setRequestProperty("Partnerid", requestHeader.get("Partnerid"));
            con.setRequestProperty("Query", requestHeader.get("Query"));
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
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            log.info("---------------------------http请求返回原内容：" + buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //kudos api
    @Deprecated
    public static String http(String url, Map<String, String> params, String kudosType, String secretKey) {
        URL u = null;
        HttpURLConnection con = null;
        // 构建请求参数
        StringBuffer sbf = new StringBuffer();
        if (params != null) {
            for (Entry<String, String> e : params.entrySet()) {
                sbf.append(e.getKey());
                sbf.append("=");
                sbf.append(e.getValue());
                sbf.append("&");
            }
            //            sbf.substring(0, sbf.length() - 1);
            sbf.deleteCharAt(sbf.length() - 1);
        }
        log.info("---------------------------http请求地址：" + url);
        log.info("---------------------------http请求参数：" + sbf.toString());
        log.info("---------------------------Partner=" + "MONEED");
        log.info("---------------------------Authkey=" + secretKey);
        log.info("---------------------------Partnerid=" + "KUD-MND-3871");
        log.info("---------------------------Query=" + kudosType);
        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Partner", "MONEED");
            con.setRequestProperty("Authkey", secretKey);
            con.setRequestProperty("Partnerid", "KUD-MND-3871");
            con.setRequestProperty("Query", kudosType);
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
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            //一定要有返回值，否则无法把请求发送给server端。
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
            log.info("---------------------------http请求返回原内容：" + buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
