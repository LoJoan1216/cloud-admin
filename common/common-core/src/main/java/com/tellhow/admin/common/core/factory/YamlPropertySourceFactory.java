package com.tellhow.admin.common.core.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/6 15:09
 * @descripe 读取自定义yaml文件工厂类
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Properties properties = loadYamlIntoProperties(resource);
        String sourceName = name !=null ? name: resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName,properties);
    }

    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
            factoryBean.setResources(resource.getResource());
            factoryBean.afterPropertiesSet();
            return factoryBean.getObject();
        }catch (IllegalStateException e){
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException){
                throw (FileNotFoundException) e.getCause();
            }
            throw e;
        }
    }
}
