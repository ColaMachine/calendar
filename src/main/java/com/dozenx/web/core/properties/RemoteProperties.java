package com.dozenx.web.core.properties;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.MapUtils;
import com.dozenx.util.PropertiesUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.util.db.MysqlUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.util.ConfigUtil;
import org.apache.zookeeper.ZooKeeper;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Created by dozen.zhang on 2017/1/10.
 */
public class RemoteProperties implements InitializingBean, FactoryBean<Properties> {
    //插销锁
    private CountDownLatch latch = new CountDownLatch(1);
    //zookeeper对象

    Logger logger = LoggerFactory.getLogger(RemoteProperties.class);

    private String url = null;

    private String user = null;

    private String password = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    private String driver = null;

    private Properties properties ;

    private ZooKeeper zk=null;

    @Override
    public Properties getObject() throws Exception {
        return properties;
    }

    @Override
    public Class<?> getObjectType() {
        return properties.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadProperty();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void loadProperty() {//其实此种方式并不合适从properties 中读取配置,应为spring 已经支持了http远程加载配置文件的方式,那么此种方式在通过http请求就显得多余了,此种自定义Properties加载方式目前唯一的好处是可以从数据库中读取配置信息 或者可以通过加密的方式获取配置信息

//        logger.info("" + properties.size());
      //  if (StringUtils.isEmpty(url)) return;
        logger.debug("loading remote properties:" + url);
        //String content =  HttpRequestUtil.sendGet(url,null);


        List<HashMap<String, String>> propertiesList = new ArrayList<HashMap<String, String>>();
       // Properties  propertiesFromFile = PropertiesUtil.load("main.properties");
        properties=PropertiesUtil.load("main.properties");
       // this.properties = propertiesFromFile;
        String readFrom = (String)properties.get("readfrom");
        if(StringUtil.isNotBlank(readFrom)){
            String[] readFromAry = readFrom.split(",");//一般有两个值config 或者db config 表示从properties 文件获取 db表示从数据库获取

            for(String s : readFromAry){
                if(s.equals("config")){
                    String basePropertiesPath =  (String)properties.get("basePropertiesPath");
                    String addPropertiesPath =  (String)properties.get("addPropertiesPath");
                    try {
                        List<File> files = com.dozenx.util.FileUtil.listFile(PathManager.getInstance().getClassPath().resolve(basePropertiesPath).toFile());
                        for(File file : files){
                            properties.load(new FileInputStream(file));
                            //need to test if override add the add File content
                            // properties.putAll(properties);
                        }
                        if(StringUtil.isNotBlank(addPropertiesPath)){
                            //先从主文件获取配置文件
                            // String basePropertiesPathReal = PathManager.getInstance().getClassPath().resolve(basePropertiesPath).toString();
                            files = com.dozenx.util.FileUtil.listFile(PathManager.getInstance().getClassPath().resolve(addPropertiesPath).toFile());
                            for(File file : files){
                                properties.load(new FileInputStream(file));
                                //need to test if override add the add File content
                                // properties.putAll(properties);
                            }
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else if(s.equals("db")){
                    try {
                        MysqlUtil mysqlUtil = new MysqlUtil();
                        // Connection con = mysqlUtil.getConnection(this.getDriver(),this.getUser(), this.getPassword(), this.getUrl());
                        String driver= (String)properties.get("db.jdbc.driver");
                        String  user = (String)properties.get("db.jdbc.user");
                        String  pwd= (String)properties.get("db.jdbc.password");
                        String url = (String)properties.get("db.jdbc.url");
                        Connection con = mysqlUtil.getConnection(driver,user,pwd,  url);
                        propertiesList = mysqlUtil.executeQuery(con, "select `key`,`value` from sys_config");
                        for (HashMap record : propertiesList) {
                            properties.put(MapUtils.getString(record, "key").trim(), MapUtils.getStringValue(record, "value").trim());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                //说明是zookeeper配置文件读取方式
                }

                //else if(s.equals("zookeeper")){
                //=========读取zookeeper 配置文件 ip 端口 节点===============
                String ip = (String)properties.get("zookeeper.ip");
                //  String port = (String)properties.get("port");
                String path = (String)properties.get("zookeeper.path");



             //   zk = connectServer(ip,path);
                    //从zk从获取所有配置参数



                // }
            }
        }
        //首先从指定的配置文件读取排位置信息
        //然后区分是数据库读取还是配置文件读取
        //如果是数据库读取则去数据库读取
        //如果是配置文件读取知道对应的配置文件目录,读取线面所有配置信息

        if (properties.size()==0) {
            File file = PathManager.getInstance().getClassPath().resolve("properties/jdbc.properties").toFile();
            String content = null;
            try {
                content = FileUtil.readAsString(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.debug("remote properties conent:" + content);
            String[] lines = content.replaceAll("\r", "").split("\n");
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("#"))
                    continue;
                if (!StringUtils.isEmpty(line)) {
                    String[] arr = line.split("=");
                    properties.put(arr[0].trim(), arr[1].trim());
                }
            }
        }

        ConfigUtil.properties =properties;
    }


   // Watcher watcher =null;
    private ZooKeeper connectServer(String registryAddress,String path) {
       // ZooKeeper zk = null;
        PropertiesWatcher watcher =  new PropertiesWatcher(latch,path,properties);

        try {
            zk = new ZooKeeper(registryAddress, Constants.ZK_SESSION_TIMEOUT,watcher);
            watcher.setZk( zk);
            logger.info("latch.await()!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            latch.await();
        } catch (IOException | InterruptedException e) {
            logger.error("", e);
        }
        return zk;
    }

}
