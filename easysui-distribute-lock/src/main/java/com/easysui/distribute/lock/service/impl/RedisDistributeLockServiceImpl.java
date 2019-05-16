package com.easysui.distribute.lock.service.impl;

import com.easysui.distribute.lock.enums.LockEnum;
import com.easysui.distribute.lock.service.DistributeLockService;
import com.easysui.redis.JedisManager;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Objects;

/**
 * @author CHAO 2019/5/16 17:20
 */
public class RedisDistributeLockServiceImpl implements DistributeLockService {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;

    @Override
    public boolean lock(String lockKey, String requestId, long expire) {
        Jedis jedis = JedisManager.getJedis();
        // Jedis 3.0没有此方法
        String result;
        try {
            result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire);
        } finally {
            JedisManager.close(jedis);
        }
        return Objects.equals(LOCK_SUCCESS, result);
    }

    @Override
    public boolean unLock(String lockKey, String requestId) {
        Jedis jedis = JedisManager.getJedis();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result;
        try {
            result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        } finally {
            JedisManager.close(jedis);
        }
        return Objects.equals(RELEASE_SUCCESS, result);
    }

    @Override
    public LockEnum type() {
        return LockEnum.REDIS;
    }
}