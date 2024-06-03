package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        Long userId = BaseContext.getCurrentId();
        String flavor = shoppingCartDTO.getDishFlavor();

        ShoppingCart sc = parserShoppingCartDTO(shoppingCartDTO);

        if (sc == null || sc.getNumber() == 0) {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            shoppingCart.setDishFlavor(flavor);
            if (dishId != null) {
                DishVO d = dishService.getById(dishId);
                shoppingCart.setImage(d.getImage());
                shoppingCart.setName(d.getName());
                shoppingCart.setAmount(d.getPrice());
            }
            if (setmealId != null) {
                SetmealVO s = setmealService.getById(setmealId);
                shoppingCart.setImage(s.getImage());
                shoppingCart.setName(s.getName());
                shoppingCart.setAmount(s.getPrice());
            }
            shoppingCartMapper.insert(shoppingCart);
        } else {
            sc.setNumber(sc.getNumber() + 1);
            shoppingCartMapper.updateById(sc);
        }
    }

    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        return shoppingCartMapper.listByUserId(userId);
    }

    @Override
    @Transactional
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart sc = parserShoppingCartDTO(shoppingCartDTO);
        if (sc == null || sc.getNumber() == 0) {
            throw new DeletionNotAllowedException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        if (sc.getNumber() == 1) {
            shoppingCartMapper.deleteById(sc);
        } else {
            sc.setNumber(sc.getNumber() - 1);
            shoppingCartMapper.updateById(sc);
        }
    }

    @Override
    @Transactional
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }

    private ShoppingCart parserShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        Long userId = BaseContext.getCurrentId();
        String flavor = shoppingCartDTO.getDishFlavor();

        ShoppingCart sc = null;
        if (dishId != null) {
            sc = shoppingCartMapper.getByDishId(dishId, userId, flavor);
        }
        if (setmealId != null) {
            sc = shoppingCartMapper.getBySetmealId(setmealId, userId, flavor);
        }
        return sc;
    }
}
