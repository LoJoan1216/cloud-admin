package com.tellhow.admin.common.datasource.annotation;

import com.tellhow.admin.common.datasource.config.DynamicDataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/7 17:41
 * @descripe 开启动态数据源
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DynamicDataSourceAutoConfiguration.class)
public @interface EnableDynamicDataSource {

}
