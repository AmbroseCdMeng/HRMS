package com.ambrosecdmeng.hr_service.service;

import com.ambrosecdmeng.hr_service.mapper.HrMapper;
import com.ambrosecdmeng.hr_service.model.Hr;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * 自定义 HRService 实现 UserDetailService 接口，并实现接口中 loadUserByUsername 方法
 * loadUserByUsername 方法根据用户名查询用户的所有信息，包括用户角色。
 * 如果没有查到相关信息，抛出 UsernameNotFoundException 异常。
 * 如果查到了相关信息，直接返回。
 * 由 Spring Security 框架完成密码的对比操作
 */
public class HrService implements UserDetailsService {

    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
        if (hr == null)
            throw new UsernameNotFoundException("Username not Found");
        return hr;
    }
}