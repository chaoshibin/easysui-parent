package com.easysui.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author CHAO 2019/5/19 1:40
 */
@Setter
@Getter
@ToString
public class RestTemplateProperties {
    private int socketTimeoutMs = 10000;
    private int connectTimeoutMs = 10000;
    /**
     * 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，等待时间过长将是灾难性的
     */
    private int connectionRequestTimeoutMs = 50;
    /**
     * 最大并发
     */
    private int maxTotal = 2000;
    /**
     * 同路由的并发数
     */
    private int defaultMaxPerRoute = new Double(maxTotal * 0.6).intValue();
}
