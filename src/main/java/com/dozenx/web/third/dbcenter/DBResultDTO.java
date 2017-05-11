package com.dozenx.web.third.dbcenter;

/**
 * Created by dozen.zhang on 2017/2/27.
 */
public class DBResultDTO {
    public boolean isSuc() {
        return suc;
    }

    public void setSuc(boolean suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    boolean suc;
    String msg;
    String code ;
    String rs;
}
