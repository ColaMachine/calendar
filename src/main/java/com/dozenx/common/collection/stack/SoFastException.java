package com.dozenx.common.collection.stack;

/**
 * Created by dozen.zhang on 2017/12/16.
 */
public class SoFastException extends Exception {
    public SoFastException(){
        super("塞入数据太快");
    }
}
