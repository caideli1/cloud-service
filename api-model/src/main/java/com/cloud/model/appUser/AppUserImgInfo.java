package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;
@Data
public class AppUserImgInfo {
    //用户ID
    private Long userId;
    //眨眼图片url
    /*private String blinkImg;
    //张嘴图片url
    private String mouthImg;
    //点头图片url
    private String nodImg;
    //摇头图片url
    private String headImg;*/
    //用户图片url
    private String faceImg;
    //用户图片PDFurl
    private String facePdf;
    private Date createTime;
}
