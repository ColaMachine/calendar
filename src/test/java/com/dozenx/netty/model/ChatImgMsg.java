package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class ChatImgMsg extends FromToMsg {

    byte[] image ;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ChatImgMsg(String toId) {

        super(toId);

        setType(MsgType.CAHTIMG);

    }

    private AskParams params;



    public AskParams getParams() {

        return params;

    }



    public void setParams(AskParams params) {

        this.params = params;

    }

}
