package com.dozenx.web.third.dbcenter.bean;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
public class TokenResult {
    String   state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Token getData() {
        return data;
    }

    public void setData(Token data) {
        this.data = data;
    }

    Token data;
}
