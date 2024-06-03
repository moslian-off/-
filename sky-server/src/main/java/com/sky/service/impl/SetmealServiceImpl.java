package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.StatusSetException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sky.constant.StatusConstant.DISABLE;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Long id = setmealDTO.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(
                setmealDish -> setmealDish.setSetmealId(id)
        );
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        setmealDishMapper.batchDeleteBySetmealId(ids);
        setmealDishMapper.batchInsert(setmealDishes);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealPageQueryDTO, setmeal);
        Page<SetmealVO> p = (Page<SetmealVO>) setmealMapper.get(setmeal);
        return new PageResult(p.getTotal(), p.getResult());
    }

    @Override
    public void status(Integer status, Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        Integer preStatus = setmealMapper.getStatusByIds(ids).get(0);
        if (Objects.equals(preStatus, status)) {
            throw new StatusSetException("状态设置异常");
        }
        List<Integer> dishStatuses = setmealDishMapper.getStatusBySetmealId(id);
        for (Integer dishStatus : dishStatuses) {
            if (dishStatus.equals(DISABLE)) {
                throw new StatusSetException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }
        Setmeal setmeal = Setmeal.builder().id(id).status(status).build();
        setmealMapper.update(setmeal);
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
        setmealMapper.batchDelete(ids);
        setmealDishMapper.batchDeleteBySetmealId(ids);
    }

    @Override
    @Transactional
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(DISABLE);
        setmealMapper.insert(setmeal);
        Long id = setmeal.getId();
        setmealDishes.forEach(
                setmealDish -> setmealDish.setSetmealId(id)
        );
        setmealDishMapper.batchInsert(setmealDishes);
    }

    @Override
    public SetmealVO getById(Long id) {
        SetmealVO setmealVO = setmealMapper.get(Setmeal.builder().id(id).build()).get(0);
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemById(id);
    }
}
