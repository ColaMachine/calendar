/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.goods.bean;

import java.sql.Timestamp;

public class Goods {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**标题**/
    private String title;
    public String getTitle(){
        return title;
    }    public void setTitle(String title){
        this.title=title;
    }/**副标题**/
    private String subtitle;
    public String getSubtitle(){
        return subtitle;
    }    public void setSubtitle(String subtitle){
        this.subtitle=subtitle;
    }/**来源**/
    private String src;
    public String getSrc(){
        return src;
    }    public void setSrc(String src){
        this.src=src;
    }/**正文**/
    private String content;
    public String getContent(){
        return content;
    }    public void setContent(String content){
        this.content=content;
    }/**状态**/
    private Integer status;
    public Integer getStatus(){
        return status;
    }    public void setStatus(Integer status){
        this.status=status;
    }/**备注**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }/**创建人**/
    private Long creator;
    public Long getCreator(){
        return creator;
    }    public void setCreator(Long creator){
        this.creator=creator;
    }/**封面**/
    private String pic;
    public String getPic(){
        return pic;
    }    public void setPic(String pic){
        this.pic=pic;
    }/**商品链接**/
    private String url;
    public String getUrl(){
        return url;
    }    public void setUrl(String url){
        this.url=url;
    }/**创建人姓名**/
    private String creatorname;
    public String getCreatorname(){
        return creatorname;
    }    public void setCreatorname(String creatorname){
        this.creatorname=creatorname;
    }/**创建时间**/
    private Timestamp createtime;
    public Timestamp getCreatetime(){
        return createtime;
    }    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }/**更新时间**/
    private Timestamp updatetime;
    public Timestamp getUpdatetime(){
        return updatetime;
    }    public void setUpdatetime(Timestamp updatetime){
        this.updatetime=updatetime;
    }
}
