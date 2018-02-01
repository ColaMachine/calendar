package com.dozenx.netty.model;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class Constants {

    public static  byte[] IMAGE = null;
    private static String clientId;

    public static String getClientId() {

        return clientId;

    }

    public static void setClientId(String clientId) {

        Constants.clientId = clientId;

    }
}
