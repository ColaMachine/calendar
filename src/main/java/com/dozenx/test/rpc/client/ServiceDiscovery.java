package com.dozenx.test.rpc.client;

import com.dozenx.web.core.Constants;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dozen.zhang on 2017/1/16.
 */
public class ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<>();
    private volatile List<String> servers = new ArrayList<>();

    private String registryAddress;

    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;
        //ZkClient zkClient = initServerList();
        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    public String discover() {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);
                logger.debug("using only data: {}", data);
            } else {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                logger.debug("using random data: {}", data);
            }
        }
        return data;
    }

    private  ZkClient initServerList() {
        //启动时从ZooKeeper读取可用服务器Z
       // String path = "/test";
       final ZkClient zkClient = new ZkClient(registryAddress, 60000, 1000);
        List<String> childs = zkClient.getChildren(Constants.ZK_REGISTRY_PATH);
        servers.clear();
        for (String p : childs) {
            servers.add((String)zkClient.readData(Constants.ZK_REGISTRY_PATH + "/" + p));
        }
        //订阅节点变化事件
        zkClient.subscribeChildChanges(Constants.ZK_REGISTRY_PATH , new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds)
                    throws Exception {
                System.out.println(String.format("[ZookeeperRegistry] service list change: path=%s, currentChilds=%s", parentPath, currentChilds.toString()));
                servers.clear();
                for (String p : currentChilds) {
                    servers.add((String)zkClient.readData(Constants.ZK_REGISTRY_PATH  + "/" + p));
                }
                System.out.println("Servers: " + servers.toString());
            }
        });
        return zkClient;
    }
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constants.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            logger.info("latch.await()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error("", e);
        }
        return zk;
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constants.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        logger.info("changed");
                        watchNode(zk);
                    }
                    if (event.getType() == Event.EventType.NodeCreated) {
                        logger.info("created");

                    }
                    if (event.getType() == Event.EventType.NodeDeleted) {
                        logger.info("deleted");

                    }
                }
            });
            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constants.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }//需要重新连接服务器
            logger.debug("node data: {}", dataList);
            this.dataList = dataList;
        } catch (KeeperException | InterruptedException e) {
            logger.error("", e);
        }
    }
}
