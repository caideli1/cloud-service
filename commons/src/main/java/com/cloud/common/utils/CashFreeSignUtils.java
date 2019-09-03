package com.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CashFreeSignUtils {
    public static boolean verifyPaymentSignature(JSONObject attributes, String apiSecret) {
        Map<String, String> postData = new LinkedHashMap<>(7);

        postData.put("orderId", attributes.getString("orderId"));
        postData.put("orderAmount", attributes.getString("orderAmount"));
        postData.put("referenceId", attributes.getString("referenceId"));
        postData.put("txStatus", attributes.getString("txStatus"));
        postData.put("paymentMode", attributes.getString("paymentMode"));
        postData.put("txMsg", attributes.getString("txMsg"));
        postData.put("txTime", attributes.getString("txTime"));

        StringBuilder sb = new StringBuilder();
        Set<String> keys = postData.keySet();
        keys.stream().forEach(key -> sb.append(postData.get(key)));

        String expectedSignature = attributes.getString("signature");
        return verifySignature(sb.toString(), expectedSignature, apiSecret);
    }

    public static boolean verifySignature(String payload, String expectedSignature, String secret) {
        try {
            String actualSignature = getHash(payload, secret);
            log.info("cashFree verify, expectSign:{}, actualSign:{}", expectedSignature, actualSignature);
            return isEqual(actualSignature.getBytes(), expectedSignature.getBytes());
        } catch (Exception e) {
            log.error("cashFree verify exception", e);
        }

        return false;
    }

    public static String getHash(String payload, String secret) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(payload.getBytes()));
    }

    /**
     * We are not using String.equals() method because of security issue mentioned in
     * <a href="http://security.stackexchange.com/a/83670">StackOverflow</a>
     *
     * @param a
     * @param b
     * @return boolean
     */
    private static boolean isEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
