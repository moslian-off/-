package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    ShoppingCart getByDishId(Long dishId, Long userId, String flavor);

    ShoppingCart getBySetmealId(Long dishId, Long userId, String flavor);

    @Update("UPDATE shopping_cart set number = #{number} where id = #{id}")
    void updateById(ShoppingCart sc);

    @Insert("INSERT INTO shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) VALUES " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ShoppingCart shoppingCart);

    @Select("SELECT * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> listByUserId(Long userId);

    @Delete("DELETE from shopping_cart where id = #{id}")
    void deleteById(ShoppingCart sc);

    @Delete("DELETE from shopping_cart where user_id = #{userId}")
    void clean(Long userId);
}
