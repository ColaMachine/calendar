/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.merchant.merchantNotice.bean;
import java.sql.Timestamp;
import java.util.Date;

public class MerchantNotice {
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
    }/**消息**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }/**商户id**/
    private Long merid;
    public Long getMerid(){
        return merid;
    }    public void setMerid(Long merid){
        this.merid=merid;
    }
}
