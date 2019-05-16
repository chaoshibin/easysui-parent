package com.easysui.distribute.lock.service.impl;

import com.easysui.distribute.lock.service.DistributeLockService;

/**
 * @author CHAO 2019/5/16 17:20
 */
public class RedisDistributeLockServiceImpl implements DistributeLockService {
    @Override
    public boolean lock(String key, String token, long expire) {
        return false;
    }

    @Override
    public void unLock(String key, String token) {

    }
}