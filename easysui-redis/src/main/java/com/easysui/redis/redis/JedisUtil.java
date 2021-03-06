package com.easysui.redis.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

/**
 * @author Chao Shibin 2019/5/17 0:44
 */
@Deprecated
public final class JedisUtil {
    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }
}
