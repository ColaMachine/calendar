package com.dozenx.web.module.timing.bean;

/**
 * Created by dozen.zhang on 2017/3/2.
 */
public class UserTimeInfo {
    boolean vip;
    boolean canGetFreePkg;//free_pkg_get

    public boolean isCanGetFreePkg() {
        return canGetFreePkg;
    }

    public void setCanGetFreePkg(boolean canGetFreePkg) {
        this.canGetFreePkg = canGetFreePkg;
    }

    public boolean isVip() {
        return vip;
    }


    public void setVip(boolean vip) {
        this.vip = vip;
    }


    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    Long endTime;

}
