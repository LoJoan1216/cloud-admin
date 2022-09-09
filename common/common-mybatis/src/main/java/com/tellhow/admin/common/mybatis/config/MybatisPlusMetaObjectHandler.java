package com.tellhow.admin.common.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/8 16:22
 * @descripe MybatisPlus 自动填充配置
 */
@Slf4j
public class MybatisPlusMetaObjectHandler  implements MetaObjectHandler{


    @Override
    public void insertFill( MetaObject metaObject) {
      log.debug("mybatis-plus start insert fill----------");
        LocalDateTime now = LocalDateTime.now();
        fillValIfNullByName("createTime",now,metaObject,false);
        fillValIfNullByName("updateTIme",now,metaObject,false);
        fillValIfNullByName("createBy",getUserName(),metaObject,false);
        fillValIfNullByName("updateBy",getUserName(),metaObject,false);
    }

    @Override
    public void updateFill( MetaObject metaObject) {
        log.debug("mybatis-plus start update fill----------");
        fillValIfNullByName("updateTime",LocalDateTime.now(),metaObject,true);
        fillValIfNullByName("updateBy",getUserName(),metaObject,true);
    }

    /**
     * 获取 springsecurity 当前的用户名
     * @return
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()){
            return authentication.getName();
        }
        return null;
    }


    /**
     * 填充值  先判断是否有手动设置，优先手动设置的值
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject
     * @param isCover  是否覆盖原有值
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
//        没有setter方法直接返回
        if(!metaObject.hasSetter(fieldName)){
            return;
        }
//        判断用户有手动设置的值
        Object value = metaObject.getValue(fieldName);
        String str = StrUtil.str(value, Charset.defaultCharset());
        if (StrUtil.isNotBlank(str) && isCover){
            return;
        }
//        field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType,fieldVal)){
            metaObject.setValue(fieldName,fieldVal);
        }

    }

}
