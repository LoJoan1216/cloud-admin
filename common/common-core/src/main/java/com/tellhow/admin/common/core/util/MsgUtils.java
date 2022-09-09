package com.tellhow.admin.common.core.util;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 14:17
 * @descripe  i18n 工具类
 */
public class MsgUtils {

    /**
     * 通过code 获取中文错误信息
     * @param code
     * @return
     */
    public String getMessage(String code){
        MessageSource messageSource = SpringUtil.getBean("messageSource");
        return messageSource.getMessage(code,null, Locale.CHINA);
    }

    /**
     *  通过code 和参数获取中文错误信息
     * @param code
     * @param objects
     * @return
     */
    public String getMessage(String code,Object... objects){
        MessageSource messageSource = SpringUtil.getBean("messageSource");
        return messageSource.getMessage(code,objects, Locale.CHINA);
    }
}
