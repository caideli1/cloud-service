package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppUserImgInfoReq {
    //用户ID
    private Long userId;
    /*//眨眼图片url
    private String blinkImg;
    //张嘴图片url
    private String mouthImg;
    //点头图片url
    private String nodImg;
    //摇头图片url
    private String headImg;*/
    //用户图片url
    private String faceImg;

    private String facePdf;
}
