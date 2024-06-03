package com.sky.service.impl;

import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.service.DIshFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl implements DIshFlavorService {

    private static final String CACHE_NAME_DISH_FLAVOR = "dish-flavor";

    @Autowired
    DishFlavorMapper dishFlavorMapper;


    @Override
    @Cacheable(cacheNames = CACHE_NAME_DISH_FLAVOR, key = "#id")
    public List<DishFlavor> getDishFlavors(Long id) {
        return dishFlavorMapper.getById(id);
    }

}
