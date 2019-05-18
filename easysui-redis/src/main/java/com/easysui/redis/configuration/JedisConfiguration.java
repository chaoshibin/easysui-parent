package com.easysui.redis.configuration;

import com.easysui.redis.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author CHAO 2019/5/19 0:55
 */

@Slf4j
@ConditionalOnProperty("easysui.jedis.host")
@EnableConfigurationProperties(JedisProperties.class)
public class JedisConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(JedisPool.class)
    public JedisPool jedisPool(JedisProperties jedisProperties) {
        log.info("easysui使用jedis连接redis,配置参数->{}", jedisProperties);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(jedisProperties.getMaxTotal());
        config.setMaxIdle(jedisProperties.getMaxIdle());
        config.setMinIdle(jedisProperties.getMinIdle());
        config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        return new JedisPool(config, jedisProperties.getHost(),
                jedisProperties.getPort(), 3000, jedisProperties.getPassword());
    }

    @Bean
    public RedisUtil redisUtil(){
        return new RedisUtil();
    }
}