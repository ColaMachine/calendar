package com.dozenx.netty.client;

import com.dozenx.netty.model.Constants;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class AndroidServer {

    public void start() throws InterruptedException {
        Constants.setClientId("001");

        NettyClientBootstrap bootstrap=new NettyClientBootstrap(9999,"localhost");
    }
}
