package com.tellhow.admin.common.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.tellhow.admin.common.mybatis.plugins.AdminPaginationInnerInterceptor;
import com.tellhow.admin.common.mybatis.resolver.SqlFilterArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/8 17:04
 * @descripe mbp 统一配置
 */
@Configuration(proxyBeanMethods = false)
public class MybatisAutoConfiguration  implements WebMvcConfigurer {


    /**
     * 规避sql注入
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SqlFilterArgumentResolver());
    }


    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new AdminPaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * 自动填充
     * @return
     */
    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler(){
        return new MybatisPlusMetaObjectHandler();
    }
}
