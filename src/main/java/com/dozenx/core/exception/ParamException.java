package com.dozenx.core.exception;

/**
 * Created by dozen.zhang on 2017/2/24.
 */
public class ParamException extends  Exception {
    public int code ;
    public String msg;
    public ParamException(int code,String msg){
        super(code+":"+msg);
        this.code =code;
        this.msg =msg;

    }


}
