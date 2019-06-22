package com.easysui.redis.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author CHAO 2019/5/17 0:40
 */
@Getter
@Setter
@ToString
@Deprecated
public class JedisProperties {
    private String enabled;
    private String host;
    private int port;
    private String password;
    //最大连接数
    private int maxTotal;
    //最大空闲时间
    private int maxIdle;
    //最小空闲时间
    private int minIdle;
    //连接池没有连接后客户端的最大等待时间 ( 单位为毫秒 )
    private int maxWaitMillis;
}
