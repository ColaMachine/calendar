package com.dozenx.netty.client;

import com.dozenx.netty.model.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;


/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {

    //利用写空闲发送心跳检测消息

    @Override

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {

            IdleStateEvent e = (IdleStateEvent) evt;

            switch (e.state()) {

                case WRITER_IDLE:

                    PingMsg pingMsg=new PingMsg();

                    ctx.writeAndFlush(pingMsg);

                    System.out.println("send ping to server----------");

                    break;

                default:

                    break;

            }

        }

    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        MsgType msgType=baseMsg.getType();

        switch (msgType){

            case LOGIN:{

                //向服务器发起登录

                LoginMsg loginMsg=new LoginMsg();

                loginMsg.setPassword("yao");

                loginMsg.setUserName("robin");

                channelHandlerContext.writeAndFlush(loginMsg);

            }break;

            case PING:{

                System.out.println("receive ping from server----------");

            }break;

            case ASK:{

                ReplyClientBody replyClientBody=new ReplyClientBody("client info **** !!!");

                ReplyMsg replyMsg=new ReplyMsg();

                replyMsg.setBody(replyClientBody);

                channelHandlerContext.writeAndFlush(replyMsg);

            }break;

            case REPLY:{

                ReplyMsg replyMsg=(ReplyMsg)baseMsg;

                ReplyServerBody replyServerBody=(ReplyServerBody)replyMsg.getBody();

                System.out.println("receive server msg: "+replyServerBody.getServerInfo());

            }break;

            case CAHTTEXT:{

                ChatTextMsg replyMsg=(ChatTextMsg)baseMsg;
                String text = replyMsg.getText();

                if("screenshot".equals(text)){
                    //ADBUtil.screentShot();
                }
                if(text.startsWith("adb")){
                    //ADBUtil.exec(text);
                }

                System.out.println("receive chat text  msg: "+text);

            }break;
            case CAHTIMG:{

                ChatImgMsg replyMsg=(ChatImgMsg)baseMsg;
                byte[] img = replyMsg.getImage();
                System.out.println("receive chat text  msg: "+img.length);

            }break;

            default:break;

        }

        ReferenceCountUtil.release(msgType);

    }
}
