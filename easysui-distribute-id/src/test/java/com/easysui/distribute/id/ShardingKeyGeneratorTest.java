package com.easysui.distribute.id;

import com.easysui.distribute.id.impl.SnowflakeShardingKeyGenerator;

import java.util.HashSet;

/**
 * <p>
 *
 * </p>
 *
 * @author CHAO
 * @since 2019/7/11
 **/
public class ShardingKeyGeneratorTest {

    public static void main(String[] args) {
        HashSet<Long> set = new HashSet<>();
        ShardingKeyGenerator<Long> generator = new SnowflakeShardingKeyGenerator();
        for (int i = 0; i < 10000; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        Long key = generator.generateKey();
                        System.out.println(key);
                        set.add(key);
                    }
                }
            }).start();

        }
        System.out.println(set.size());
    }
}