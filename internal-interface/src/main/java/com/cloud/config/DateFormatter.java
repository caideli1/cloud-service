package com.cloud.config;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化
 *
 * @author danquan.miao
 * @date 2019/8/22 0022
 * @since 1.0.0
 */
@Component
public class DateFormatter implements Formatter<Date> {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return FORMATTER.parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return FORMATTER.format(date);
    }
}
