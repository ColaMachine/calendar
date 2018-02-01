package com.dozenx.netty.model;



/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class ReplyMsg extends BaseMsg {

    public ReplyMsg() {

        super();

        setType(MsgType.REPLY);

    }

    private ReplyBody body;



    public ReplyBody getBody() {

        return body;

    }



    public void setBody(ReplyBody body) {

        this.body = body;

    }

}



