package com.dozenx.test.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by dozen.zhang on 2017/1/16.
 */
public class RpcBootstrap {
    public static void main(String[] args) {
        //  new ClassPathXmlApplicationContext("classpath:/config/xml/applicationContext.xml");
      new ClassPathXmlApplicationContext("classpath:/config/xml/spring-rpc-server.xml");
    }

}
