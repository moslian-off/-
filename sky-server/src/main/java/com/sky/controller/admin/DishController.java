package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品");
        dishService.save(dishDTO);
        return Result.success("保存成功");
    }

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询");
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result<String> batchDelete(@RequestParam List<Long> ids) {
        log.info("批量删除菜品");
        dishService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品");
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @GetMapping("/list")
    public Result<List<Dish>> getByCategoryId(@RequestParam Long categoryId) {
        List<Dish> dishes = dishService.getByCategoryId(categoryId);
        return Result.success(dishes);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success("更新成功");
    }

    @PostMapping("/status/{status}")
    public Result<String> status(@PathVariable Integer status, @RequestParam Long id) {
        dishService.status(id, status);
        return Result.success();
    }
}
