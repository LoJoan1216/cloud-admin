package com.tellhow.common.security.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author joan
 * @version V1.0.0
 * @date 2022/9/9 15:00
 * @descripe 扩展用户信息
 */
public class AdminUser extends User implements OAuth2AuthenticatedPrincipal {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

//    用户id
    @Getter
    private Long id;
//    部门id
    @Getter
    private Long deptId;
//    手机号
    @Getter
    private String phone;

    public AdminUser(Long id,Long deptId,String username,String password,String phone,boolean enabled,boolean accountNonExpired,boolean credentialsNonExpired,boolean accountNonLocked,Collection<? extends GrantedAuthority> authorities){
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.deptId = deptId;
        this.id = id;
        this.phone = phone;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public String getName() {
        return this.getUsername();
    }
}
