package com.dozenx.test.zookeeper;

import com.dozenx.test.rpc.client.RpcProxy;
import com.dozenx.web.core.Constants;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dozen.zhang on 2017/1/16.
 */

public class ZookeeperTest {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperTest.class);
    @Autowired
    private RpcProxy rpcProxy;
    CountDownLatch latch = new CountDownLatch(1);
    @Test
    public void testConnect() {
        ZooKeeper zk = null;
        try {//该方法是异步的 所以要加 latch
            zk = new ZooKeeper("127.0.0.1:2181", Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            System.out.println(new String(zk.getData(
                    "/tmp_root_path/childPath2", true, null)));
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error("", e);
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        logger.info("complete");
    }


}
