package com.easysui.redis.redis;

import com.easysui.core.spring.SpringBeanFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.Objects;

/**
 * @author CHAO 2019/5/20 1:48
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RedisUtil {
    private static final StringRedisTemplate REDIS_TEMPLATE = SpringBeanFactory.get(StringRedisTemplate.class);
    private static final String RELEASE_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final String LOCK_SCRIPT =
            "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then " +
                    "return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final Long RELEASE_SUCCESS = 1L;


    public static Boolean lockJedis(String lockKey, String requestId, long expireSeconds) {
        return REDIS_TEMPLATE.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireSeconds);
            return Objects.equals(LOCK_SUCCESS, result);
        });
    }


    public static Boolean unlockJedis(String lockKey, String requestId) {
        return REDIS_TEMPLATE.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return Objects.equals(RELEASE_SUCCESS, result);
        });
    }

    public static boolean lock(String lockKey, String value, long expireTime) {
        RedisScript<String> redisScript = new DefaultRedisScript<>(LOCK_SCRIPT, String.class);
        Object result = REDIS_TEMPLATE.execute(redisScript, Collections.singletonList(lockKey), value, expireTime);
        return Objects.equals(LOCK_SUCCESS, result);
    }

    public static boolean unlock(String lockKey, String value) {
        RedisScript<String> redisScript = new DefaultRedisScript<>(RELEASE_SCRIPT, String.class);
        Object result = REDIS_TEMPLATE.execute(redisScript, Collections.singletonList(lockKey), value);
        return Objects.equals(RELEASE_SUCCESS, result);
    }
}
