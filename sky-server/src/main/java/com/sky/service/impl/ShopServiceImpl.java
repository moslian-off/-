package com.sky.service.impl;

import com.sky.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final String KEY = "SHOP_STATUS";
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Integer getStatus() {
        ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
        return Integer.valueOf((String) Objects.requireNonNull(valueOps.get(KEY)));
    }

    @Override
    public void setStatus(Integer status) {
        log.info("修改店铺状态为:{}", status == 1 ? "营业" : "打烊");
        ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(KEY, status.toString());
    }
}
