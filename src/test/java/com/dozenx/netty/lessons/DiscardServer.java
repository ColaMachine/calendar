package com.dozenx.netty.lessons;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)  boss
        EventLoopGroup workerGroup = new NioEventLoopGroup();  /// worker
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)  help to start a server
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)  实例化一个新的channel去接收连接
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)ChannelInitializer一个channel 配置类 往channel 里添加事件
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)Option用于设置bossGroup相关参数 .
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6) childOption用于设置workerGroup

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}