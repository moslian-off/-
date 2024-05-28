package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    void batchDelete(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(SetmealDTO setmealDTO);

    List<Integer> getStatusByIds(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    @Update("UPDATE setmeal set status = #{status} where id = #{id}")
    void status(Integer status, Long id);

    @AutoFill(OperationType.INSERT)
    @Insert("INSERT INTO setmeal " +
            "(category_id, name, price, status, description, image) " +
            "VALUES " +
            "(#{categoryId},#{name},#{price},#{status},#{description},#{image})")
    @Options(useGeneratedKeys = true)
    void insert(Setmeal setmeal);

    List<SetmealVO> get(Setmeal setmeal);

}
