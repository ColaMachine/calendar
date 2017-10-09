package com.dozenx.test.rpc;

import com.dozenx.web.core.Constants;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dozen.zhang on 2017/1/13.
 */
public class ServiceRegistry {
    /** 日志类**/
    private static final Logger LOGGER = LoggerFactory.getLogger(com.dozenx.test.rpc.ServiceRegistry.class);
    /***门闩**/
    private CountDownLatch latch = new CountDownLatch(1);
    /***仓库地址**/
    private String registryAddress;
    /**构造函数**/
    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    /**
     * 注册
     * @param data
     */
    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();//转到连接服务器
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {//该方法是异步的 所以要加 latch
            zk = new ZooKeeper(registryAddress, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();

            String path = zk.create(Constants.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }
}
