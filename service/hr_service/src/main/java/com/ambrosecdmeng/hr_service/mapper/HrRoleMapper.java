package com.ambrosecdmeng.hr_service.mapper;

import com.ambrosecdmeng.hr_service.model.HrRole;
import org.apache.ibatis.annotations.Param;

public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole model);

    int insertSelective(HrRole model);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole model);

    int updateByPrimaryKey(HrRole model);

    void deleteByHrid(Integer hrid);

    Integer addRole(@Param("hrid") Integer id, @Param("rids") Integer[] rids);
}
