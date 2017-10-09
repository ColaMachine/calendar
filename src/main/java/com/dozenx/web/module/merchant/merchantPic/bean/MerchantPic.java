/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.merchant.merchantPic.bean;

public class MerchantPic {
    /**编号**/
    private Integer id;
    public Integer getId(){
        return id;
    }    public void setId(Integer id){
        this.id=id;
    }/**槽位**/
    private Integer slot;
    public Integer getSlot(){
        return slot;
    }    public void setSlot(Integer slot){
        this.slot=slot;
    }/**路径**/
    private String path;
    public String getPath(){
        return path;
    }    public void setPath(String path){
        this.path=path;
    }/**商户id**/
    private Long merid;
    public Long getMerid(){
        return merid;
    }    public void setMerid(Long merid){
        this.merid=merid;
    }
}
