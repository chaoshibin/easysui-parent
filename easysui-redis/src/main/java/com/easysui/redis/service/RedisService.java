package com.easysui.redis.service;

import java.io.Serializable;

/**
 * @author CHAO 2019/5/20 23:50
 */
public interface RedisService {

    <T extends Serializable> void set(String key, T value, long expireSeconds);

    <T> T get(String key, T clazz);

    void del(String key);
}
