package com.easysui.zookeeper.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author CHAO 2019/5/18 23:10
 */
@Getter
@Setter
@ToString
public class ZookeeperProperties {
    private String connectionString = "localhost:2181";
    private int sessionTimeoutMs = 60000;
    private int connectionTimeoutMs = 15000;
    private int maxCloseWaitMs = 60000;
    private String namespace = null;
}
