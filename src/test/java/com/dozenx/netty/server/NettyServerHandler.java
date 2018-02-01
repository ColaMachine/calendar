package com.dozenx.netty.server;

import com.dozenx.core.Path.PathManager;
import com.dozenx.netty.model.*;
import com.dozenx.netty.ui.MainFrame;
import com.dozenx.util.DateUtil;
import com.dozenx.util.FileUtil;
import com.dozenx.util.ImageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

    @Override

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        //channel失效，从Map中移除

        NettyChannelMap.remove((SocketChannel)ctx.channel());

    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {


        if(MsgType.LOGIN.equals(baseMsg.getType())){

            LoginMsg loginMsg=(LoginMsg)baseMsg;

            if("robin".equals(loginMsg.getUserName())&&"yao".equals(loginMsg.getPassword())){

                //登录成功,把channel存到服务端的map中

                NettyChannelMap.add(loginMsg.getClientId(),(SocketChannel)channelHandlerContext.channel());

                System.out.println("client"+loginMsg.getClientId()+" 登录成功");

            }

        }else{

            if(NettyChannelMap.get(baseMsg.getClientId())==null){

                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录

                LoginMsg loginMsg=new LoginMsg();

                channelHandlerContext.channel().writeAndFlush(loginMsg);

            }

        }

        switch (baseMsg.getType()){

            case PING:{

                PingMsg pingMsg=(PingMsg)baseMsg;

                PingMsg replyPing=new PingMsg();

                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);

            }break;

            case ASK:{

                //收到客户端的请求

                AskMsg askMsg=(AskMsg)baseMsg;

                if("authToken".equals(askMsg.getParams().getAuth())){

                    ReplyServerBody replyBody=new ReplyServerBody("server info $$$$ !!!");

                    ReplyMsg replyMsg=new ReplyMsg();

                    replyMsg.setBody(replyBody);

                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);

                }

            }break;

            case REPLY:{

                //收到客户端回复

                ReplyMsg replyMsg=(ReplyMsg)baseMsg;

                ReplyClientBody clientBody=(ReplyClientBody)replyMsg.getBody();

                System.out.println("receive client msg: "+clientBody.getClientInfo());

            }break;
            case CAHTTEXT:{

                ChatTextMsg replyMsg=(ChatTextMsg)baseMsg;
                String text = replyMsg.getText();



                System.out.println("receive chat text  msg: "+text);

            }break;
            case CAHTIMG:{

                ChatImgMsg replyMsg=(ChatImgMsg)baseMsg;
                byte[] img = replyMsg.getImage();

                //File file = PathManager.getInstance().getHomePath().resolve("saves").resolve(DateUtil.getNowStringDate()+".png").toFile();
//                String filePath = PathManager.getInstance().getHomePath().resolve("saves").resolve(DateUtil.getNowStringDate()+".png").toString();
//                FileUtil.getFile(img, filePath);
                BufferedImage imageBuffer = ImageUtil.bytesToImage(img);
                MainFrame.images.offer(imageBuffer);
                System.out.println("receive chat img  msg: "+img.length);
                Constants.IMAGE = img;
            }break;

            default:break;

        }

        ReferenceCountUtil.release(baseMsg);
    }
}
