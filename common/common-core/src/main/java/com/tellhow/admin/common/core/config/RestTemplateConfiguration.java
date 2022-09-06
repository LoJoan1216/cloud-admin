package com.tellhow.admin.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 15:25
 * @descripe 远程调用配置
 */
@AutoConfiguration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
