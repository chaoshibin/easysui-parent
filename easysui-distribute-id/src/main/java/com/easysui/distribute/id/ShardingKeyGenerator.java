package com.easysui.distribute.id;

/**
 * <p>
 *
 * </p>
 *
 * @author CHAO
 * @since 2019/7/10
 **/
public interface ShardingKeyGenerator<T> {
    /**
     * 生成分布式key
     *
     * @return key
     */
    T generateKey();
}
