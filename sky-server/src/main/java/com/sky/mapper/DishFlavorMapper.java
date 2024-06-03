package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insert(List<DishFlavor> flavors);

    void batchDelete(List<Long> ids);

    @Select("SELECT * from dish_flavor where dish_id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    List<DishFlavor> getById(Long id);

    void batchInsert(List<DishFlavor> flavors);
}
