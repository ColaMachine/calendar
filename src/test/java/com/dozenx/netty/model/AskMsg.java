package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class AskMsg extends BaseMsg {

    public AskMsg() {

        super();

        setType(MsgType.ASK);

    }

    private AskParams params;



    public AskParams getParams() {

        return params;

    }



    public void setParams(AskParams params) {

        this.params = params;

    }

}
