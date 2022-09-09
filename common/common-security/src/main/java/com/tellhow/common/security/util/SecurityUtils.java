package com.tellhow.common.security.util;

import cn.hutool.core.util.StrUtil;
import com.tellhow.admin.common.core.constant.SecurityConstants;
import com.tellhow.common.security.entity.AdminUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     *
     * @return
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     *
     * @param authentication
     * @return
     */
    public AdminUser getUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AdminUser) {
            return (AdminUser) principal;
        }
        return null;
    }

    /**
     * 获取用户角色集合
     *
     * @return
     */
    public List<Long> getRoles() {
        Authentication authentication = getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = new ArrayList<>();
        authorities.stream().filter(grantedAuthority -> StrUtil.startWith(grantedAuthority.getAuthority(), SecurityConstants.ROLE))
                .forEach(grantedAuthority -> {
                    String id = StrUtil.removePrefix(grantedAuthority.getAuthority(), SecurityConstants.ROLE);
                    roleIds.add(Long.parseLong(id));
                });
        return roleIds;
    }

}
