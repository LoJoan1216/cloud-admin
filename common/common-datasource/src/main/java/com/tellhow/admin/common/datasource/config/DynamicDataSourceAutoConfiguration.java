package com.tellhow.admin.common.datasource.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/7 09:53
 * @descripe 动态数据源切换配置
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {
    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor, DataSourceProperties properties){
        return new JdbcDynamicDataSourceProvider(stringEncryptor,properties);

    }

    @Bean
    public DsProcessor dsProcessor(){
        return new LastParamDsProcessor();
    }
}
