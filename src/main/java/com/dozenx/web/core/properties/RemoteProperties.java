package com.dozenx.web.core.properties;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.StringUtil;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dozen.zhang on 2017/1/10.
 */
public class RemoteProperties implements InitializingBean, FactoryBean<Properties> {

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

    private Properties properties = new Properties();


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
        if (StringUtils.isEmpty(url)) return;
        logger.debug("loading remote properties:" + url);
        //String content =  HttpRequestUtil.sendGet(url,null);

        if(StringUtil.isUrl(url)){

        }
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

}
