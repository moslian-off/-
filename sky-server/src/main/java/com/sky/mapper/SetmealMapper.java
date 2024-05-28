package com.sky.mapper;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    void batchDelete(List<Long> ids);

    void update(SetmealDTO setmealDTO);

    Object get(Setmeal setmeal);

    List<Integer> getStatusByIds(List<Long> ids);

    void status(Integer status, Long id);
}
