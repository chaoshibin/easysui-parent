package com.easysui.zookeeper.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author CHAO 2019/5/19 1:10
 */
public class ZkUtil {
    @Autowired
    private CuratorFramework curatorFramework;

    public boolean sharedLock(String path) {
        try {
            String path1 = curatorFramework.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
