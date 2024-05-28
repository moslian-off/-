package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @PutMapping
    public Result<String> update(SetmealDTO setmealDTO) {
        log.info("修改套餐数据");
        setmealService.update(setmealDTO);
        return Result.success("修改成功");
    }

    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    public Result<String> status(@PathVariable Integer status, @RequestParam Long id) {
        setmealService.status(status, id);
        return Result.success();
    }

    @DeleteMapping
    public Result<String> batchDelete(@RequestParam List<Long> ids) {
        setmealService.batchDelete(ids);
        return Result.success();
    }

    @PostMapping
    public Result<String> insert(@RequestBody SetmealDTO setmealDTO) {
        setmealService.insert(setmealDTO);
        return Result.success();
    }

    @GetMapping
    public Result<SetmealVO> getById(@RequestParam Long id) {
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }


}
