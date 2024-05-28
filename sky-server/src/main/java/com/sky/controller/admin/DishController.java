package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        dishService.save(dishDTO);
        return Result.success("保存成功");
    }

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result<String> batchDelete(@RequestParam List<Long> ids) {
        dishService.batchDelete(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO){
        dishService.update(dishDTO);
        return Result.success("更新成功");
    }
}
