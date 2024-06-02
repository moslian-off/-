package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getByDishId(List<Long> ids);

    void batchDeleteBySetmealId(List<Long> ids);

    void batchInsert(List<SetmealDish> setmealDishes);

    @Select("SELECT d.status from setmeal_dish s left join dish d " +
            "on s.dish_id = d.id where s.setmeal_id = #{id}")
    List<Integer> getStatusBySetmealId(Long id);

    @Select("SELECT * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getBySetmealId(Long id);
}
