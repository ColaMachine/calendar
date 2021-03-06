package com.dozenx.test.rpc.server.impl;

import com.dozenx.test.rpc.anotation.RpcService;
import com.dozenx.test.rpc.server.HelloService;

/**
 * Created by dozen.zhang on 2017/1/13.
 * copy from http://blog.csdn.net/tantexian/article/details/46852685
 */
@RpcService(clazz=HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
         return "Hello! " + msg;
    }
}
