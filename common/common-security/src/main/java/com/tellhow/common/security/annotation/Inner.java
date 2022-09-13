package com.tellhow.common.security.annotation;

import java.lang.annotation.*;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/13 11:06
 * @descripe 服务调用不鉴权注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {


    /**
     * 是否AOP统一处理
     * @return  默认为true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段
     * @return
     */
    String [] field() default {};
}
