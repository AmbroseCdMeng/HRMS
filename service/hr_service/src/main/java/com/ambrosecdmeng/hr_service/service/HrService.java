package com.ambrosecdmeng.hr_service.service;

import com.ambrosecdmeng.hr_service.mapper.HrMapper;
import com.ambrosecdmeng.hr_service.mapper.HrRoleMapper;
import com.ambrosecdmeng.hr_service.model.Hr;
import com.ambrosecdmeng.hr_service.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 自定义 HRService 实现 UserDetailService 接口，并实现接口中 loadUserByUsername 方法
 * loadUserByUsername 方法根据用户名查询用户的所有信息，包括用户角色。
 * 如果没有查到相关信息，抛出 UsernameNotFoundException 异常。
 * 如果查到了相关信息，直接返回。
 * 由 Spring Security 框架完成密码的对比操作
 */
@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
        if (hr == null)
            throw new UsernameNotFoundException("用户名不存在");
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(), keywords);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }
}
