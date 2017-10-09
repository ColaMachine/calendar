package com.dozenx.test.rpc.client.test;

import com.dozenx.test.rpc.client.RpcProxy;
import com.dozenx.test.rpc.server.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by dozen.zhang on 2017/1/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/xml/spring-rpc-client.xml")
public class HelloServiceTest {
    private static Logger logger = LoggerFactory.getLogger(HelloServiceTest.class);
    @Autowired
    private RpcProxy rpcProxy;

    @Test
    public void helloTest() {

        HelloService helloService = rpcProxy.create(HelloService.class);


        for(int i=0;i<1000;i++){
            try {
                String result = helloService.hello("World");
                logger.info("helloService:"+i+"  "+result);
                Assert.assertEquals("Hello! World", result);
            }catch(Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000*120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
