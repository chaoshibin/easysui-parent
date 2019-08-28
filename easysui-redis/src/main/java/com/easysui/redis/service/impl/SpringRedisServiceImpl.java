package com.easysui.redis.service.impl;

import com.easysui.core.configuration.AppConfiguration;
import com.easysui.core.util.StringFormatUtils;
import com.easysui.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author CHAO 2019/5/20 23:52
 */
public class SpringRedisServiceImpl implements RedisService {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valueOperations;
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private AppConfiguration appConfiguration;

    @Override
    public <T> void set(String key, T value, long expireSeconds) {
        valueOperations.set(this.buildKey(key), value, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) valueOperations.get(this.buildKey(key));
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(this.buildKey(key));
    }

    @Override
    public String buildKey(String key) {
        if (StringUtils.isBlank(appConfiguration.getAppName())) {
            throw new IllegalArgumentException("未读取到app-name属性");
        }
        return StringFormatUtils.format(appConfiguration.getAppName(), key);
    }
}
