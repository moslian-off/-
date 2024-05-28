package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void update(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void status(Integer status, Long id);

    void batchDelete(List<Long> ids);

    void insert(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);
}
