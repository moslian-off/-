package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.StatusSetException;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public void update(SetmealDTO setmealDTO) {
        setmealMapper.update(setmealDTO);
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealPageQueryDTO, setmeal);
        return (PageResult) setmealMapper.get(setmeal);
    }

    @Override
    public void status(Integer status, Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        Integer preStatus = setmealMapper.getStatusByIds(ids).get(0);
        if (Objects.equals(preStatus, status)) {
            throw new StatusSetException("状态设置异常");
        }
        setmealMapper.status(status, id);
    }

    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        List<Integer> statuses = setmealMapper.getStatusByIds(ids);
        for (Integer status : statuses) {
            if (status == 1) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

    }

    @Override
    public void insert(SetmealDTO setmealDTO) {

    }

    @Override
    public SetmealVO getById(Long id) {
        return null;
    }
}
