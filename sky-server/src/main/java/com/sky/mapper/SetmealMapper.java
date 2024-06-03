package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    void batchDelete(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    List<Integer> getStatusByIds(List<Long> ids);

    @AutoFill(value = OperationType.INSERT)
    @Insert("INSERT INTO setmeal " +
            "(category_id, name, price,status,description, image, create_time, update_time, create_user, update_user) " +
            "VALUES " +
            "(#{categoryId},#{name},#{price},#{status},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Setmeal setmeal);

    List<SetmealVO> get(Setmeal setmeal);

    List<Setmeal> list(Setmeal setmeal);

    @Select("SELECT sd.name,sd.copies,d.image,d.description " +
            "from setmeal_dish sd LEFT OUTER JOIN dish d " +
            "on sd.dish_id = d.id")
    List<DishItemVO> getDishItemById(Long id);
}
