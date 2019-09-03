package com.cloud.model.appUser;

import lombok.Getter;
import lombok.Setter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class LoanStatusVo {
    // 当前借款状态 0:无借款行为 1-审批中 2-拒绝 3-逾期 4-完成 5-未激活 6-已处置(催收) 7-展期申请中 8-还款中
	private int loanStatus;

	private Date reOpenTime;

    public Boolean getCanBorrow() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        return caculateCanBorrow(now);
    }

	public Boolean caculateCanBorrow(Date currentDate) {
		return loanStatus == 0 || loanStatus == 4 ||
                (reOpenTime != null &&    loanStatus == 5 &&
                        (dayDate(currentDate).after(dayDate(reOpenTime))||dayDate(currentDate).equals(dayDate(reOpenTime)))) ||
				(reOpenTime != null && loanStatus == 2 &&
                        (dayDate(currentDate).after(dayDate(reOpenTime))||dayDate(currentDate).equals(dayDate(reOpenTime))));
	}

    public static Date dayDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayString = sdf.format(date);
        try {
            Date dayDate = sdf.parse(dayString);
            return dayDate;
        } catch (ParseException e) {

        }
        return null;
    }

}
