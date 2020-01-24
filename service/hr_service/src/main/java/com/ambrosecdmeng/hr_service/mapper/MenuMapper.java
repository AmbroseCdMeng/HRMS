package com.ambrosecdmeng.hr_service.mapper;

import com.ambrosecdmeng.hr_service.model.Menu;

import java.util.List;

public interface MenuMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Menu model);

    int insertSelective(Menu model);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Menu model);

    int updateByPrimaryKeySelective(Menu model);

    List<Menu> getAllMenus();

    List<Menu> getMenuByHrId(Integer hrid);

    List<Menu> getAllMenusWithRole();

    List<Integer> getMidsByRid(Integer rid);
}
