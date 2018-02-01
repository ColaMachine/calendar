package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */


public class ReplyServerBody extends ReplyBody {

    private String serverInfo;

    public ReplyServerBody(String serverInfo) {

        this.serverInfo = serverInfo;

    }

    public String getServerInfo() {

        return serverInfo;

    }

    public void setServerInfo(String serverInfo) {

        this.serverInfo = serverInfo;

    }

}