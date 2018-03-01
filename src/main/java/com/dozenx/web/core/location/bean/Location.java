package com.dozenx.web.core.location.bean;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:16 2018/2/26
 * @Modified By:
 */
public class Location {
    private int id;
    private int parentId;
    private String areaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
