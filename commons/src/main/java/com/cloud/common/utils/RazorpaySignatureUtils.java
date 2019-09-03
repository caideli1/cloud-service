package com.cloud.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class RazorpaySignatureUtils {
    public static boolean verifyPaymentSignature(JSONObject attributes, String apiSecret) throws JSONException {
        String expectedSignature = attributes.getString("razorpay_signature");
        String orderId = attributes.getString("razorpay_order_id");
        String paymentId = attributes.getString("razorpay_payment_id");
        String payload = orderId + '|' + paymentId;
        return verifySignature(payload, expectedSignature, apiSecret);
    }

    public static boolean verifyWebhookSignature(String payload, String expectedSignature,
                                                 String webhookSecret) {
        return verifySignature(payload, expectedSignature, webhookSecret);
    }

    public static boolean verifySignature(String payload, String expectedSignature, String secret) {
        String actualSignature;
        try {
            actualSignature = getHash(payload, secret);
            return isEqual(actualSignature.getBytes(), expectedSignature.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getHash(String payload, String secret) throws Exception {
        Mac sha256_HMAC;

        sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
        return new String(Hex.encodeHex(hash));

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

    /**
     * Authentication
     *
     * @param username
     * @param password
     * @return
     */
    public static String buildAuth(String username, String password) {
        String authString = username + ":" + password;
        byte[] authEncBytes = null;
        try {
            authEncBytes = Base64.encodeBase64(authString.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        String authStringEnc = new String(authEncBytes);
        return authStringEnc;
    }

}
