package com.easysui.redis.service;

/**
 * @author CHAO 2019/5/20 23:50
 */
public interface RedisService {

    <T> void set(String key, T value, long expireSeconds);

    <T> T get(String key, Class<T> clazz);

    void del(String key);

    /**
     * 构建key
     *
     * @param key 业务键值
     * @return 实际key值
     */
    String buildKey(String key);
}
