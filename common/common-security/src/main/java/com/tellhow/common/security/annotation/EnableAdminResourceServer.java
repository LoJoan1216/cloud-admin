package com.tellhow.common.security.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/9 15:45
 * @descripe  资源服务注解
 */

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ AdminResourceServerAutoConfiguration.class, AdminResourceServerConfiguration.class,
        AdminFeignClientConfiguration.class })
public @interface EnableAdminResourceServer {


}
