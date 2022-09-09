package com.tellhow.admin.common.datasource.config;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/7 10:40
 * @descripe 数据源参数解析类
 */
public class LastParamDsProcessor extends DsProcessor {


    private static final String LAST_PREFIX = "#last";

    /**
     * 抽象匹配条件  匹配才会走当前执行器否则走下一级执行器
     * @param key  DS 注解中的内容
     * @return
     */
    @Override
    public boolean matches(String key) {
        if (key.startsWith(LAST_PREFIX)){
            DynamicDataSourceContextHolder.clear();
            return true;
        }
        return false;
    }

    /**
     * 抽象最终决定数据源
     * @param methodInvocation
     * @param s
     * @return
     */
    @Override
    public String doDetermineDatasource(MethodInvocation methodInvocation, String s) {
        Object[] arguments = methodInvocation.getArguments();
        return String.valueOf(arguments[arguments.length-1]);
    }
}
