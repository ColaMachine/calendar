package com.dozenx.web.third.dbcenter.bean;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
public class Token {
    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    int expiresIn;
    Long loseTimestamp;

    public Long getLoseTimestamp() {
        return loseTimestamp;
    }

    public void setLoseTimestamp(Long loseTimestamp) {
        this.loseTimestamp = loseTimestamp;
    }

    public Long getOauthTimestamp() {
        return oauthTimestamp;
    }

    public void setOauthTimestamp(Long oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    Long oauthTimestamp;
    String oauthToken;
}
