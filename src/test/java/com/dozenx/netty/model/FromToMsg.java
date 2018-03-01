package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class FromToMsg extends BaseMsg {
    private String toId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public FromToMsg(String toId) {

        super();

        setType(MsgType.ASK);
        this.toId = toId;
    }

    private AskParams params;



    public AskParams getParams() {

        return params;

    }



    public void setParams(AskParams params) {

        this.params = params;

    }

}
