package com.tellhow.admin.common.mybatis.resolver;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/8 16:57
 * @descripe 解决sql注入问题
 */
public class SqlFilterArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String[] KEYWORDS = { "master", "truncate", "insert", "select", "delete", "update", "declare",
            "alter", "drop", "sleep", "extractvalue", "concat" };

    /**
     * 判断Controller 是否包含page参数
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Page.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String[] ascs = request.getParameterValues("ascs");
        String[] descs = request.getParameterValues("descs");
        String current = request.getParameter("current");
        String size = request.getParameter("size");
        Page<Object> page = new Page<>();
        if (StrUtil.isNotBlank(current)){
            page.setCurrent(Long.parseLong(current));
        }
        if (StrUtil.isNotBlank(size)){
            page.setCurrent(Long.parseLong(size));
        }
        List<OrderItem> orderItems = new ArrayList<>();
        Optional.ofNullable(ascs).ifPresent(strings -> orderItems.addAll(
                Arrays.stream(strings).filter(sqlInjectPredicate()).map(OrderItem::asc).collect(Collectors.toList())
        ));
        Optional.ofNullable(descs).ifPresent(strings -> orderItems.addAll(
                Arrays.stream(strings).filter(sqlInjectPredicate()).map(OrderItem::desc).collect(Collectors.toList())
        ));

        return page;
    }

    /**
     * 判断用户输入里面有没有关键字
     * @return Predicate
     */
    private Predicate<String> sqlInjectPredicate() {
        return sql -> Arrays.stream(KEYWORDS).noneMatch(keyword -> StrUtil.containsIgnoreCase(sql, keyword));
    }
}
