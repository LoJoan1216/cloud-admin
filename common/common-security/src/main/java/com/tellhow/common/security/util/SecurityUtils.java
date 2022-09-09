package com.tellhow.common.security.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/9 14:45
 * @descripe 安全工具类
 */
@UtilityClass
public class SecurityUtils {


    /**
     * 获取Authentication
     * @return
     */
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

//    public AdminUser

}
