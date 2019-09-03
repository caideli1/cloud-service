package com.cloud.user.model;

import com.esotericsoftware.minlog.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import java.io.InputStream;

/**
 * @author yoga
 * @Description: AddUserAadhaarInfoReq
 * @date 2019-07-2318:38
 */
@Slf4j
public class AddUserAadhaarInfoReq {
    private String aadhaarUrlFront;
    private String aadhaarUrlBack;
    private String qrCode;
    private UserAadhaarQrCode userAadhaarQrCode;

    public UserAadhaarQrCode getUserAadhaarQrCode() {
        if (null == userAadhaarQrCode) {
            try {
                log.info("qrCode 原始字段为 -- {}", qrCode);
                JAXBContext context = JAXBContext
                        .newInstance(UserAadhaarQrCode.class);
                InputStream buf = IOUtils.toInputStream(qrCode, "utf-8");
                UserAadhaarQrCode tmp = (UserAadhaarQrCode) context.createUnmarshaller().unmarshal(buf);
                userAadhaarQrCode = tmp;
                return tmp;
            } catch (Exception e) {
                Log.warn("create UserAadhaarQrCode.class error: {}", e);
                return new UserAadhaarQrCode();
            }
        }
        return userAadhaarQrCode;
    }

    public String getAadhaarUrlFront() {
        return aadhaarUrlFront;
    }

    public void setAadhaarUrlFront(String aadhaarUrlFront) {
        this.aadhaarUrlFront = aadhaarUrlFront;
    }

    public String getAadhaarUrlBack() {
        return aadhaarUrlBack;
    }

    public void setAadhaarUrlBack(String aadhaarUrlBack) {
        this.aadhaarUrlBack = aadhaarUrlBack;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

}
