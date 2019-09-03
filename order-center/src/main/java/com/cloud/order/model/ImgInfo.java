package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImgInfo {
    private String faceImg;
    private String signatureUrl;
}
