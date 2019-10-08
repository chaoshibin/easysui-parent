package com.easysui.distribute.lock.service.impl;

import com.easysui.distribute.lock.enums.LockEnum;
import com.easysui.distribute.lock.service.DistributeLockService;
import com.easysui.redis.redis.RedisUtil;

/**
 * @author Chao Shibin 2019/5/16 17:20
 */
public class RedisDistributeLockServiceImpl implements DistributeLockService {
    @Override
    public boolean lock(String lockKey, String requestId, long expireSeconds) {
        return RedisUtil.lock(lockKey, requestId, expireSeconds);
    }

    @Override
    public boolean unLock(String lockKey, String requestId) {
        return RedisUtil.unlock(lockKey, requestId);
    }

    @Override
    public LockEnum type() {
        return LockEnum.REDIS;
    }
}