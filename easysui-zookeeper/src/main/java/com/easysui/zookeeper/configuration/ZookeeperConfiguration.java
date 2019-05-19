package com.easysui.zookeeper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author CHAO 2019/5/18 23:18
 */
@Slf4j
@ConditionalOnProperty("easysui.zookeeper.connection-string")
public class ZookeeperConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({CuratorFramework.class, ZooKeeper.class})
    public CuratorFramework curatorFramework(ZookeeperProperties zkProperties) {
        log.info("easysui使用Curator连接zookeeper,配置参数->{}", zkProperties);
        RetryPolicy retryPolicy = new RetryNTimes(Integer.MAX_VALUE, 5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkProperties.getConnectionString())
                .sessionTimeoutMs(zkProperties.getSessionTimeoutMs())
                .connectionTimeoutMs(zkProperties.getConnectionTimeoutMs())
                .maxCloseWaitMs(zkProperties.getMaxCloseWaitMs())
                .namespace(zkProperties.getNamespace())
                //.aclProvider(aclProvider)
                //.authorization(scheme, auth)
                //.defaultData(defaultData)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }
}
