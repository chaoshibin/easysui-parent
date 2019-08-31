package com.easysui.zookeeper.util;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author CHAO 2019/5/19 1:10
 */
public class ZkUtil {
    @Autowired
    private CuratorFramework curatorFramework;

    @SneakyThrows
    public boolean sharedLock(String path) {
        curatorFramework.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path);
        return false;
    }
}
