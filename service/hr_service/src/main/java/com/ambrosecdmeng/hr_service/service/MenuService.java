package com.ambrosecdmeng.hr_service.service;

import com.ambrosecdmeng.hr_service.mapper.MenuMapper;
import com.ambrosecdmeng.hr_service.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    /**
     * getAllMenus 方法在每次请求时都需要查询数据库，效率低，因此将之缓存下来
     * <p>
     * 这里使用方法名作为缓存 key，另外需要在项目启动类添加 @EnableCaching 注解开启缓存
     *
     * @return
     */
    @Cacheable(key = "#root.methodName")
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenus();
    }
}
