package com.easysui.redis;

import com.easysui.core.spring.SpringBeanFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * @author CHAO 2019/5/17 0:44
 */
public final class JedisManager {
    private static JedisPool pool;

    public static Jedis getJedis() {
        if (Objects.isNull(pool)) {
            synchronized (JedisManager.class) {
                if (Objects.isNull(pool)) {
                    JedisProperties properties = SpringBeanFactory.get(JedisProperties.class);
                    if (Objects.isNull(properties)) {
                        throw new RuntimeException("读取redis连接信息失败");
                    }
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(properties.getMaxTotal());
                    config.setMaxIdle(properties.getMaxIdle());
                    config.setMinIdle(properties.getMinIdle());
                    config.setMaxWaitMillis(properties.getMaxWaitMillis());
                    pool = new JedisPool(config,properties.getIp(),properties.getPort(),3000,properties.getPassword());
                }
            }
        }
        return pool.getResource();
    }

    public static void close(Jedis jedis) {
        if (Objects.nonNull(jedis)) {
            jedis.close();
        }
    }
}
