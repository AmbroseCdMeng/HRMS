package com.ambrosecdmeng.hr_service.mapper;

import com.ambrosecdmeng.hr_service.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getAllMenu();
}
