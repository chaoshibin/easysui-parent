package com.easysui.redis.service.impl;

import com.easysui.redis.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author CHAO 2019/5/20 23:52
 */
public class SpringRedisServiceImpl implements RedisService {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Serializable> valueOperations;
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public <T extends Serializable> void set(String key, T value, long expireSeconds) {
        valueOperations.set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) valueOperations.get(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }
}
