package com.dozenx.core.exception;

import com.dozenx.web.core.log.MessagePropertiesResolver;

/**
 * Created by dozen.zhang on 2017/11/9.
 */
public class InterfaceException extends Exception{
    public String code ;
    public String msg;
    public InterfaceException(String code, String msg) {
        super(code+":"+msg);
        this.code =code;
        this.msg =msg;
    }

    public InterfaceException(MessagePropertiesResolver messagePropertiesResolver){
        this.code = messagePropertiesResolver.code;
        this.msg = messagePropertiesResolver.msg;
    }
}
