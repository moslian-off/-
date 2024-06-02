package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = shopService.getStatus();
        return Result.success(status);
    }

    @PutMapping("/{status}")
    public Result<String> setStatus(@PathVariable Integer status) {
        shopService.setStatus(status);
        return Result.success();
    }


}
