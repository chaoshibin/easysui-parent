package com.easysui.distribute.id.impl;

import com.easysui.distribute.id.ShardingKeyGenerator;

/**
 * <p>
 * 使用redis自增
 * </p>
 *
 * @author Chao Shibin
 * @since 2019/7/10
 **/
public class RedisShardingKeyGenerator implements ShardingKeyGenerator<Long> {
    @Override
    public Long generateKey() {
        return 0L;
    }
}