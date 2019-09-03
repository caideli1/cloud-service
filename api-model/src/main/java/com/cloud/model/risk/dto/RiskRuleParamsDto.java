package com.cloud.model.risk.dto;

import com.cloud.model.risk.RiskExecuteRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 具体参数按照具体规则定义，继承此类
 *
 * @author walle
 */

@Setter
@Getter
@Builder
public class RiskRuleParamsDto extends RiskExecuteRequestDto {

    /**
     * 查询规则约定结果
     */
    private boolean queryResult;

    /**
     * 客户年龄 - 看情况抽取成一个对象
     */
    private int age;

    /**
     * 数量对象  取数量为一个对象
     */
    private int countNum;

    /**
     * 第二个数量对象  取数量为一个对象
     */
    private int secCountNum;


    /**
     * 客户职业
     */
    private String profession;

    /**
     * 客户家庭地址所属 邦
     */
    private String homeState;

    /**
     * 客户公司地址所属 邦
     */
    private String companyState;

    /**
     * 客户月收入
     */
    private BigDecimal salary;

    /**
     * 申请时间
     */
    private String applyTime;

    /**
     * 账户数量
     */
    private int accountNumber;

    /**
     * TrustCheckr Phone里的coffience显示 high/avg
     */
    private String phoneCoffience;

    /**
     * TrustCheck pancard status-code
     */
    private String trustPanCardStatusCode;

    /**
     * emil里status显示非valid；或disposable显示非false；或toxic显示非false；或isPlusUser显示非true；
     */
    private String emailStatus;

    private String emailDisposable;

    private String emailToxic;

    private String emailIsPlusUser;

    /**
     * 电话模式
     */
    private int phoneModel;

    /**
     * 电池电量
     */
    private  String batter;
}
