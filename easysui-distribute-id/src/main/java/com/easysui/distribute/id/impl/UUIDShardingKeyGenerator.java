package com.easysui.distribute.id.impl;

import com.easysui.distribute.id.ShardingKeyGenerator;

import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author CHAO
 * @since 2019/7/10
 **/
public class UUIDShardingKeyGenerator implements ShardingKeyGenerator<String> {
    @Override
    public String generateKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}