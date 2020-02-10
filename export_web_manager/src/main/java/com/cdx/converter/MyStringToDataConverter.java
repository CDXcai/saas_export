package com.cdx.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 定义一个自定义的字符串转化日期类,继承与Converter接口
public class MyStringToDataConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        // 定义一个用于格式化时间的对象
        SimpleDateFormat simpleDateFormat = null;
        // 准备返回的Date对象
        Date date = null;

        // 解析yyyy/MM/dd格式的日期
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 把字符串解析为对应的日期
            date = simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 返回一个日期对象
        return date;
    }
}

