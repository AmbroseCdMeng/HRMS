package com.ambrosecdmeng.hr_service.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;


/**
 * UrlAccessDecisionManager
 * decide 方法中判断当前用户是否具备请求需要的觉得，若该方法在执行过程中未抛出异常，则说明请求可以通过；若抛出异常，则说明请求权限不足
 * 如果所需要的角色是 ROLE_LOGIN ， 那么只需要判断 auth 不是匿名用户的实例。即表示当前用户已登录
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            String needRole = configAttribute.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("Not Logined");
                } else
                    return;
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole))
                    return;
            }
        }
        throw new AccessDeniedException("Not enough permissions");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
