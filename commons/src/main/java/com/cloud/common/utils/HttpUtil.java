package com.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具类.
 *
 * @author David.Huang
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 默认编码方式 -UTF8
     */
    private static final String DEFAULT_ENCODE = "utf-8";

    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 构造方法
     */
    private HttpUtil() {
        // empty constructor for some tools that need an instance object of the
        // class
    }

    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url 请求地址
     * @return 内容字符串
     */
    public static String getUrlAsString(String url) throws Exception {
        return getUrlAsString(url, null, DEFAULT_ENCODE);
    }

    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 内容字符串
     */
    public static String getUrlAsString(String url, Map<String, String> params) throws Exception {
        return getUrlAsString(url, params, DEFAULT_ENCODE);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url 请求地址
     * @return 内容字符串
     */
    public static String postUrlAsString(String url) throws Exception {
        return postUrlAsString(url, null, null, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 内容字符串
     */
    public static String postUrlAsString(String url, Map<String, String> params) throws Exception {
        return postUrlAsString(url, params, null, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param reqHeader 请求头内容
     * @return 内容字符串
     * @throws Exception
     */
    public static String postUrlAsString(String url, Map<String, String> params, Map<String, String> reqHeader)
            throws Exception {
        return postUrlAsString(url, params, reqHeader, null);
    }

    /**
     * POST请求, 结果以字符串形式返回.
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param reqHeader 请求头内容
     * @param encode    编码方式
     * @return 内容字符串
     * @throws Exception
     */
    public static String postUrlAsString(String url, Map<String, String> params, Map<String, String> reqHeader,
                                         String encode) throws Exception {
        // 开始时间
        long t1 = System.currentTimeMillis();
        // 获得HttpPost对象
        HttpPost httpPost = getHttpPost(url, params, encode);
        // 发送请求
        String result = executeHttpRequest(httpPost, reqHeader);
        // 结束时间
        long t2 = System.currentTimeMillis();
        // 调试信息
        log.debug("url:" + url);
        if (params != null) {
            log.debug("params:" + params.toString());
        }
        log.debug("reqHeader:" + reqHeader);
        log.debug("encode:" + encode);
        log.debug("result:" + result);
        log.debug("consume time:" + ((t2 - t1)));
        httpPost.releaseConnection();
        // 返回结果
        return result;
    }

    /**
     * post xml请求
     *
     * @param url
     * @param paramStr
     * @param reqHeader
     * @param encode
     * @return
     * @throws Exception
     */
    public static String postXmlUrl(String url, String paramStr, Map<String, String> reqHeader, String encode) throws Exception {
        // 开始时间
        long t1 = System.currentTimeMillis();
        // 获得HttpPost对象
        HttpPost httpPost = getStringEntityHttpPost(url, paramStr, encode);
        // 发送请求
        String result = executeHttpRequest(httpPost, reqHeader);
        // 结束时间
        long t2 = System.currentTimeMillis();

        log.debug("result:{}, consume time:{}ms", result, t2 - t1);
        return result;
    }

    /**
     * 下载文件保存到本地
     *
     * @param path 文件保存位置
     * @param url  文件地址
     * @throws IOException
     */
    public static void downloadFile(String path, String url) throws IOException {
        log.debug("path:" + path);
        log.debug("url:" + url);
        // 发送请求获得返回结果
        CloseableHttpResponse response = null;
        try {
            // 获得HttpGet对象
            log.info("=============downloadFile path=================" + path + "=======url=========" + url);
            HttpGet httpGet = getHttpGet(url, null, null);
            // 发送请求获得返回结果
            response = PoolingHttpClient.getInstance().execute(httpGet);
            // 如果成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("=============downloadFile httpStatus OK=================");
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try {
                    // 创建文件对象
                    File f = new File(path);
                    // 创建文件路径
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bw = new BufferedOutputStream(new FileOutputStream(path));
                    bw.write(result);
                } catch (Exception e) {
                    log.error("保存文件错误,path=" + path + ",url=" + url, e);
                    throw e;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                        response.close();
                        httpGet.releaseConnection();
                    } catch (Exception e) {
                        log.error("finally BufferedOutputStream shutdown close", e);
                    }
                }
            }
            // 如果失败
            else {
                log.info("=============downloadFile httpStatus fail=================");
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                log.error("HttpResonse Error:" + errorMsg);

            }
        } catch (ClientProtocolException e) {
            log.error("下载文件保存到本地,http连接异常,path=" + path + ",url=" + url, e);
            throw e;
        } catch (IOException e) {
            log.error("下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url, e);
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }
        }
    }

    /**
     * 下载文件保存到本地
     *
     * @param path 文件保存位置
     * @param url  文件地址
     * @throws IOException
     */
    public static void downloadFile(String path, String url, String reqStr) throws IOException {
        log.debug("path:" + path);
        log.debug("url:" + url);
        // 发送请求获得返回结果
        CloseableHttpResponse response = null;
        try {
            // 获得HttpGet对象
            log.info("=============downloadFile path=================" + path + "=======url=========" + url);
            HttpGet httpGet = getHttpGet(url, null, "UTF-8");
            httpGet.addHeader("reqStr", reqStr);
            // 发送请求获得返回结果
            response = PoolingHttpClient.getInstance().execute(httpGet);
            // 如果成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("=============downloadFile httpStatus OK=================");
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try {
                    // 创建文件对象
                    File f = new File(path);
                    // 创建文件路径
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }
                    // 写入文件
                    bw = new BufferedOutputStream(new FileOutputStream(path));
                    bw.write(result);
                } catch (Exception e) {
                    log.error("保存文件错误,path=" + path + ",url=" + url, e);
                    throw e;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                        response.close();
                        httpGet.releaseConnection();
                    } catch (Exception e) {
                        log.error("finally BufferedOutputStream shutdown close", e);
                    }
                }
            }
            // 如果失败
            else {
                log.info("=============downloadFile httpStatus fail=================");
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                log.error("HttpResonse Error:" + errorMsg);

            }
        } catch (ClientProtocolException e) {
            log.error("下载文件保存到本地,http连接异常,path=" + path + ",url=" + url, e);
            throw e;
        } catch (IOException e) {
            log.error("下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url, e);
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }
        }
    }

    public static String doPost(String url, String json, String charset) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            // 设置参数
            StringEntity s = new StringEntity(json, charset);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }
            httpPost.releaseConnection();
        }

        return result;
    }

    @Deprecated
    public static String doPostWithTimeout(String url, String json, String charset, Integer connectionTimeout, Integer socketTimeout) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            // 设置参数
            StringEntity s = new StringEntity(json, charset);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }
            httpPost.releaseConnection();
        }
        return result;
    }

    public static String doPost(String url, String json, Header... headers) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;

        try {
            httpPost = new HttpPost(url);
            httpPost.setHeaders(headers);
            // 设置参数
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }
            httpPost.releaseConnection();
        }
        return result;
    }

    public static String doPost(String url, JSONObject json, String charset) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            // 设置参数
            StringEntity s = new StringEntity(json.toString(), charset);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }
            httpPost.releaseConnection();
        }
        return result;
    }

    public static String doPostGzip(String url, JSONObject json, String charset) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {

            httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(10000).build();
            httpPost.setConfig(requestConfig);
            // 设置参数
            StringEntity s = new StringEntity(json.toString(), charset);
            s.setContentEncoding("gzip");
            s.setContentType("application/json; charset=UTF-8");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, charset);
                    }
                } else {
                    log.error("响应失败:" + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
                }
            }
        } catch (Exception ex) {
            log.error("https 请求API异常", ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }
            httpPost.releaseConnection();
        }
        return result;
    }

    public static String doPost(String url, String json, Map<String, String> map) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            for (String key : map.keySet()) {
                httpPost.addHeader(key, map.get(key));
            }

            // 设置参数
            StringEntity s = new StringEntity(json, "UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }
            httpPost.releaseConnection();
        }

        return result;
    }


    public static String doPostMul(String urlStr, Map<String, String> textMap,
                                   Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Charset", "UTF-8");
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            if (textMap != null) {
                StringBuilder strBuf = new StringBuilder();
                for (Object o : textMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(boundary)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"").append(inputName).append("\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            if (fileMap != null) {
                for (Object o : fileMap.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }

                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] b = decoder.decodeBuffer(inputValue);
                    for (int i = 0; i < b.length; ++i) {
                        // 调整异常数据
                        if (b[i] < 0) {
                            b[i] += 256;
                        }
                    }
                    ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
                    fileOut.write(b);
                    String strBuf = "\r\n" + "--" + boundary +
                            "\r\n" +
                            "Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + ""
                            + "\"\r\n" +
                            "Content-Type:application/octet-stream\r\n\r\n";
                    out.write(strBuf.getBytes());
                    DataInputStream in = new DataInputStream(
                            new ByteArrayInputStream(fileOut.toByteArray()));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                    fileOut.close();
                }
            }
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // 读取返回数据
                StringBuilder strBuf = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line).append("\n");
                }
                res = strBuf.toString();
                reader.close();
//				reader = null;
            } else {
                StringBuilder error = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        conn.getErrorStream()));
                String line1 = null;
                while ((line1 = bufferedReader.readLine()) != null) {
                    error.append(line1).append("\n");
                }
                res = error.toString();
                bufferedReader.close();
//				bufferedReader=null;
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + e);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    public static String doPost(String url, List<NameValuePair> paramEntity, Header... heders) {
        HttpPost post = null;
        CloseableHttpResponse response = null;
        try {
            post = new HttpPost(url);
            post.setHeaders(heders);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramEntity, "UTF-8");
            post.setEntity(formEntity);
            //服务端发送完数据，即关闭连接
            post.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(post);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            log.error("http post Exception ,url ： " + url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }

            post.releaseConnection();
        }
        return null;
    }


    /**
     * 微贷post请求
     *
     * @param url
     * @param map
     * @param charset
     * @return
     */
    public static String doPostNew(String url, Map<String, String> map, String charset) {
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            //服务端发送完数据，即关闭连接
            httpPost.setHeader("Connection", "close");
            response = PoolingHttpClient.getInstance().execute(httpPost);
            if (response != null) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, charset);
                    }
                } else {
                    log.error("响应失败:" + response.getStatusLine().getStatusCode());
                }
            }
        } catch (Exception ex) {
            log.error("发送请求异常:", ex);
            ex.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally response close error", e);
            }

            httpPost.releaseConnection();
        }

        return result;
    }

    public static String doGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static JSONObject doGet(String url, Header... headers) {
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        try {
            httpGet = new HttpGet(url);
            httpGet.setHeaders(headers);
            // 设置参数
            response = PoolingHttpClient.getInstance().execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    return JSONObject.parseObject(result);
                }
            }
        } catch (Exception ex) {
            log.error("http请求:{}, API异常:{}", url, ex);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }
            httpGet.releaseConnection();
        }

        return null;
    }

    /**
     * 读取request流
     *
     * @return
     * @author guoyx
     */
    public static String readReqStr(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request
                    .getInputStream(), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("readReqStr error", e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("reader close exception.", e);
            }
        }
        return sb.toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknow".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();

            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡获取本机配置的IP地址
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }

        //对于通过多个代理的情况，第一个IP为客户端真实的IP地址，多个IP按照','分割
        if (null != ipAddress && ipAddress.length() > 15) {
            //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }

        log.info("请求ip地址： " + ipAddress);
        return ipAddress;
    }

    /**
     * GET请求, 结果以字符串形式返回.
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param encode 编码方式
     * @return 内容字符串
     */
    private static String getUrlAsString(String url, Map<String, String> params, String encode) throws Exception {
        // 开始时间
        long t1 = System.currentTimeMillis();
        // 获得HttpGet对象
        HttpGet httpGet = getHttpGet(url, params, encode);
        // 调试信息
        log.debug("url:" + url);
        log.debug("encode:" + encode);
        // 发送请求
        String result = executeHttpRequest(httpGet, null);
        // 结束时间
        long t2 = System.currentTimeMillis();
        // 调试信息
        log.debug("result:" + result);
        log.debug("consume time:" + ((t2 - t1)));
        httpGet.releaseConnection();
        // 返回结果
        return result;
    }

    /**
     * 获得HttpGet对象
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param encode 编码方式
     * @return HttpGet对象
     */
    private static HttpGet getHttpGet(String url, Map<String, String> params, String encode) {
        StringBuffer buf = new StringBuffer(url);
        if (params != null) {
            // 地址增加?或者&
            String flag = (url.indexOf('?') == -1) ? "?" : "&";
            // 添加参数
            for (String name : params.keySet()) {
                buf.append(flag);
                buf.append(name);
                buf.append("=");
                try {
                    String param = params.get(name);
                    if (param == null) {
                        param = "";
                    }
                    buf.append(URLEncoder.encode(param, encode));
                } catch (UnsupportedEncodingException e) {
                    log.error("URLEncoder Error,encode=" + encode + ",param=" + params.get(name), e);
                }
                flag = "&";
            }
        }
        log.info("==============getHttpGet url===========================" + buf.toString());
        HttpGet httpGet = new HttpGet(buf.toString());
        return httpGet;
    }

    /**
     * 获得HttpPost对象
     *
     * @param url    请求地址
     * @param params 请求参数
     * @param encode 编码方式
     * @return HttpPost对象
     */
    private static HttpPost getHttpPost(String url, Map<String, String> params, String encode) {
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            List<NameValuePair> form = new ArrayList<NameValuePair>();
            for (String name : params.keySet()) {
                form.add(new BasicNameValuePair(name, params.get(name)));
            }
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, encode);
                httpPost.setEntity(entity);
                //服务端发送完数据，即关闭连接
                httpPost.setHeader("Connection", "close");

            } catch (UnsupportedEncodingException e) {
                log.error("UrlEncodedFormEntity Error,encode=" + encode + ",form=" + form, e);
            }
        }
        return httpPost;
    }

    private static HttpPost getStringEntityHttpPost(String url, String paramStr, String encode) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity data = new StringEntity(paramStr, encode);
        httpPost.setEntity(data);
        //服务端发送完数据，即关闭连接
        httpPost.setHeader("Connection", "close");

        return httpPost;
    }

    /**
     * 执行HTTP请求
     *
     * @param request   请求对象
     * @param reqHeader 请求头信息
     * @return 内容字符串
     */
    private static String executeHttpRequest(HttpUriRequest request, Map<String, String> reqHeader) throws Exception {
        String result = null;
        CloseableHttpResponse response = null;
        try {
            // 设置请求头信息
            if (reqHeader != null) {
                for (String name : reqHeader.keySet()) {
                    request.addHeader(name, reqHeader.get(name));
                }
            }
            //服务端发送完数据，即关闭连接
            request.addHeader(HttpHeaders.CONNECTION, "close");
            // 获得返回结果
            response = PoolingHttpClient.getInstance().execute(request);
            result = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                StringBuffer errorMsg = new StringBuffer();
                errorMsg.append("httpStatus:");
                errorMsg.append(response.getStatusLine().getStatusCode());
                errorMsg.append(response.getStatusLine().getReasonPhrase());
                errorMsg.append(", Header: ");
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    errorMsg.append(header.getName());
                    errorMsg.append(":");
                    errorMsg.append(header.getValue());
                }
                log.error("HttpResonse Error:" + errorMsg);
            }
        } catch (Exception e) {
            log.error("http连接异常", e);
            throw new Exception("http连接异常");
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.error("finally HttpClient shutdown error", e);
            }
        }
        return result;
    }
}
