/*
 * Copyright (c) 2001-2019 GuaHao.com Corporation Limited. All rights reserved.
 * This software is the confidential and proprietary information of GuaHao Company.
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with GuaHao.com.
 */
package com.cloud.kudos.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * 图片工具类
 *
 * @author danquanmiao
 * @version V1.0
 * @since 2019-05-23 17:50
 */
@Slf4j
public class ImageUtils {
    public static String fileToString(String filePath) {
        String result = null;
        InputStream is = null;

        try {
            URL url = new URL(filePath);
            URLConnection connection = url.openConnection();
            is = connection.getInputStream();

            byte[] bytes = new byte[connection.getContentLength()];
            is.read(bytes);

            result = new String(Base64.encodeBase64(bytes), "UTF-8");

            is.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            try {
                is.close();
            } catch (Exception exception) {
                log.error(e.getMessage(), exception);
            }
        }

        return result;
    }

    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.encodeBase64(bytes).toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

    public static void main(String[] args) {
        String path = "https://moneed-dev.oss-cn-hangzhou.aliyuncs.com/img/aadhaar/2019/05/08/1241016475857197.jpg";
        System.out.println(fileToString(path));

    }
}
