package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    @Insert("INSERT INTO dish (name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) " +
            "VALUES (#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Dish dish);

    List<DishVO> get(Dish dish);

    void batchDelete(List<Long> ids);

    @Select("SELECT * from dish where id = #{id}")
    Dish getById(Long id);

    List<Integer> getStatusByIds(List<Long> ids);

    void update(DishDTO dishDTO);

    @Select("SELECT * from dish where category_id = #{categoryId}")
    List<Dish> getByCategory(Long categoryId);

    @Update("update dish set status = #{status} where id = #{id}")
    void status(Long id, Integer status);
}
