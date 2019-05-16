package com.easysui.distribute.lock.service;

/**
 * @author CHAO 2019/5/16 16:05
 */
public interface DistributeLockService {


    /**
     * 获取锁
     *
     * @param key    锁标识
     * @param token  用于实现可重入锁
     * @param expire 过期时间
     * @return 结果
     */
    boolean lock(String key, String token, long expire);

    /**
     * 释放锁
     *
     * @param key   锁标识
     * @param token 避免在释放锁时错误释放其它线程的锁
     */
    void unLock(String key, String token);
}
