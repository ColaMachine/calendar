package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */

public class ReplyClientBody extends ReplyBody {

    private String clientInfo;



    public ReplyClientBody(String clientInfo) {

        this.clientInfo = clientInfo;

    }



    public String getClientInfo() {

        return clientInfo;

    }



    public void setClientInfo(String clientInfo) {

        this.clientInfo = clientInfo;

    }

}
