package com.pdd.config;

import com.mysql.jdbc.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author:liyangpeng
 * @date:2018/10/10 11:01
 */
@Component
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if(StringUtils.isNullOrEmpty(source)){
            return null;
        }
        return new Date(Long.parseLong(source));
    }
}
