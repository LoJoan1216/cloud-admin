package com.tellhow.common.security.component;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.tellhow.common.security.annotation.Inner;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/13 11:09
 * @descripe  资源服务器对外直接暴露URL,如果设置context-path 需要特殊处理
 */
@Slf4j
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class PermitAllUrlProperties implements InitializingBean {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String[] DEFAULT_IGNORE_URLS = new String[]{"/actuator/**","/error","/v3/api-docs"};

    @Getter
    @Setter
    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        urls.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
        RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(info ->{
            HandlerMethod handlerMethod = map.get(info);
//           获取方法上面的注解 替代path variable 为 *
            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
            Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition()).getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url,PATTERN,"*"))));
//          获取类上边的注解, 替代path variable 为 *
            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
            Optional.ofNullable(controller).ifPresent(inner -> Objects.requireNonNull(info.getPathPatternsCondition())
                    .getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
        });

    }
}
