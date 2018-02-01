package com.dozenx.netty.model;

import java.io.Serializable;

/**
 * Created by dozen.zhang on 2017/11/14.
 */

//请求类型参数

//必须实现序列化接口

public class AskParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String auth;



    public String getAuth() {

        return auth;

    }



    public void setAuth(String auth) {

        this.auth = auth;

    }

}