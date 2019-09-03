package com.cloud.order.model;

import com.cloud.model.user.UserBankCard;
import com.cloud.model.user.UserContact;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDetailParam {

    /**
     * aadhaar认证
     */
    private AadhaarAuth aadhaarAuth;

    /**
     * 学生信息
     */
    private StudentInfo studentInfo;

    /**
     * 订单处理
     */
    private OrderProcessModel orderProcessModel;

    /**
     * 图片信息
     */
    private ImgInfo imgInfo;

    /**
     * 工作地址信息
     */
    private WorkAddressInfo workAddressInfo;



    /**
     * 认证信息
     *
     */
    private ConformFile conformFile;


    /**
     * 用户银行卡
     */
    private UserBankCard userBankCard;

    /**
     * UserInfo信息
     */
    private UserInfoParam userInfoParam;

    /**
     *pancard  认证信息
     */
    private PanCardAuth panCardAuth;

    /**
     *   Voter 认证信息
     */
    private VoterAuthInfo voterAuthInfo;

    /**
     * 用户联系人表
     */
    private List<UserContact> userContacts;
}
