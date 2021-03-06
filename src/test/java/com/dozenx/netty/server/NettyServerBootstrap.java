package com.dozenx.netty.server;

import com.dozenx.netty.model.ChatTextMsg;
import com.dozenx.netty.ui.MainFrame;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import com.dozenx.netty.model.AskMsg;


import java.util.concurrent.TimeUnit;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class NettyServerBootstrap {

    private int port;

    private SocketChannel socketChannel;

    public NettyServerBootstrap(int port) throws InterruptedException {

        this.port = port;

        bind();

    }



    private void bind() throws InterruptedException {

        EventLoopGroup boss=new NioEventLoopGroup();

        EventLoopGroup worker=new NioEventLoopGroup();

        ServerBootstrap bootstrap=new ServerBootstrap();

        bootstrap.group(boss,worker);

        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.option(ChannelOption.SO_BACKLOG, 128);

        //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去

        bootstrap.option(ChannelOption.TCP_NODELAY, true);

        //保持长连接状态

        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override

            protected void initChannel(SocketChannel socketChannel) throws Exception {

                ChannelPipeline p = socketChannel.pipeline();

                p.addLast(new ObjectEncoder());

                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));

                p.addLast(new NettyServerHandler());

            }

        });

        ChannelFuture f= bootstrap.bind(port).sync();

        if(f.isSuccess()){

            System.out.println("server start---------------");

        }

    }

    public static void main(String []args) throws InterruptedException {

        NettyServerBootstrap bootstrap=new NettyServerBootstrap(9999);
        //启动服务后没隔5s 发送请求给客户端


        Thread thread = new Thread(){
            public void run(){
                MainFrame main =new MainFrame();
                main.main(new String []{});
            }
        };
        thread.start();


        String msg =null;
        while ((msg=MainFrame.message.take())!=null){
//            if(MainFrame.message.peek()!=null) {
            try {


                SocketChannel channel = (SocketChannel) NettyChannelMap.get("001");
                ChatTextMsg chatTextMsg = new ChatTextMsg("001");
                chatTextMsg.setText(msg);
                channel.writeAndFlush(chatTextMsg);
                System.out.println(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
//            }


        }
        System.out.println("likaile ");

    }
}
