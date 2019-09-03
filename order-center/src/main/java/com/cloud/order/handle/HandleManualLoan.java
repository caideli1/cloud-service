package com.cloud.order.handle;

import com.cloud.common.config.CommonConfig;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.user.UserBankCard;
import com.cloud.order.BeanFactory.OrderBeanFactory;
import com.cloud.order.constant.LoanType;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.service.feign.user.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class HandleManualLoan {

	@Autowired
	private FinanceLoanDao financeLoanDao;

	@Autowired
	private UserClient userClient;

	@Autowired
	private OrderBeanFactory orderBeanFactory;

	public FinanceLoanModel existFailRecord(Map<String, Object> map, String id) throws Exception {
		log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询放款失败记录【id:{}】是否存在", id);
		// 根据id查询出一条失败的记录
		FinanceLoanModel loan = financeLoanDao.getFinanceLoanById(id);
		if (loan == null) {
			log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放款失败记录【id:{}】不存在,无法进行放款重提", id);
			map.put("manualLoan", "failure:没有此放款失败记录!");
			throw new Exception("failure:没有此放款失败记录!");
		}
		else if(loan.getPayStatus() != PayStatus.FAILURE.num){
			log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>该放款记录已经不是失败状态,无法进行放款重提", id);
			map.put("manualLoan", "failure:该放款记录已经不是失败状态!");
			throw new Exception("failure:该放款记录已经不是失败状态!");
		}
		//增加更新 银行卡判定   author by zhujingtao  开始
		else if(
				//银行卡失败 且comment为状态未更新
				//comment为空  表示银行卡卡状态未更新或者未更新成功
				(!StringUtils.isBlank(loan.getFailureReason())&&loan.getFailureReason().contains(CommonConfig.BANKCARD_ERROR_FLAG))
						//判定备注是否为空字符串
						&&(StringUtils.isBlank(loan.getComment()))) {

			log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放款失败记录银行卡未更新,无法进行放款重提", id);
			map.put("manualLoan", "failure:银行卡信息未更新!");
			throw new Exception("failure:银行卡信息未更新!");
		}
		//增加更新 银行卡判定   author by zhujingtao  结束

		log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放款失败记录【id:{}】存在,可以进行放款重提", id);
		return loan;
	}

	public void getLatestBankNo(Map<String, Object> map, FinanceLoanModel financeLoan) throws Exception {
		log.info("step2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取放款失败记录【id:{}】对应的最新银行卡", financeLoan.getId());
		try {
			// 获取最新银行卡
			UserBankCard userBankCard = userClient.getBankCardByUserId(financeLoan.getCustomerNo());
			if (userBankCard != null&& !StringUtils.isBlank(userBankCard.getBankAccount())) {
				map.put("userBankCard",userBankCard);
				financeLoan.setBankNo(userBankCard.getBankAccount());
				financeLoan.setIfscCode(userBankCard.getIfscCode());
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			log.info("step2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>无法获取放款失败记录【id:{}】对应的最新银行卡", financeLoan.getId());
			e.printStackTrace();
			map.put("manualLoan", "failure:无法获取银行卡!");
			throw new Exception("failure:无法获取银行卡!");
		}
	}

	//放款重提交易记录
	public FinancePayLogModel save(FinanceLoanModel financeLoan, Map<String, Object> map) throws Exception {
		log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>插入放款交易记录开始");
		try {
			FinancePayLogModel payLogModel = orderBeanFactory.createFinancePayLogModel(financeLoan, LoanType.RELOAN.num);
			financeLoanDao.savePayLog(payLogModel);
			log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>插入放款交易记录完成");
			return payLogModel;
		} catch (Exception e) {
			log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>插入放款交易记录失败");
			e.printStackTrace();
			map.put("manualLoan", "failure:插入放款交易记录失败!");
			throw new Exception("failure:插入放款交易记录失败!");
		}
	}
}
