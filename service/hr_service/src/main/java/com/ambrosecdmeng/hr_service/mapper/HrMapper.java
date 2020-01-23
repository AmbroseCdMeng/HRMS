package com.ambrosecdmeng.hr_service.mapper;

import com.ambrosecdmeng.hr_service.model.Hr;
import com.ambrosecdmeng.hr_service.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HrMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Hr model);

    int insertSelective(Hr model);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Hr model);

    int updateByPrimaryKeySelective(Hr model);

    Hr loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);

    List<Hr> getAllHrs(@Param("hrid") Integer id, @Param("keywords") String keywords);

    List<Hr> getAllHrsExceptCurrentHr(Integer id);
}
