package com.easysui.distribute.id.impl;

import com.easysui.distribute.id.ShardingKeyGenerator;

/**
 * <p>
 * 雪花算法，使用zookeeper临时顺序节点获取workerId
 * </p>
 *
 * @author CHAO
 * @since 2019/7/10
 **/
public class SnowflakeShardingKeyGenerator implements ShardingKeyGenerator<Long> {

    @Override
    public Long generateKey() {
        return 0L;
    }
}