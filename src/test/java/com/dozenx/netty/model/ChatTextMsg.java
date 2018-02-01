package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class ChatTextMsg extends FromToMsg {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatTextMsg(String toId) {

        super(toId);

        setType(MsgType.CAHTTEXT);

    }

    private AskParams params;



    public AskParams getParams() {

        return params;

    }



    public void setParams(AskParams params) {

        this.params = params;

    }

}
