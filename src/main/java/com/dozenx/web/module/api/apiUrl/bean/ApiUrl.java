/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.api.apiUrl.bean;
import java.sql.Timestamp;
import java.util.Date;

public class ApiUrl {
    /**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }/**父id**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }/**概要**/
    private String summary;
    public String getSummary(){
        return summary;
    }    public void setSummary(String summary){
        this.summary=summary;
    }/**备注**/
    private String description;
    public String getDescription(){
        return description;
    }    public void setDescription(String description){
        this.description=description;
    }/**允许的请求类型**/
    private String consumes;
    public String getConsumes(){
        return consumes;
    }    public void setConsumes(String consumes){
        this.consumes=consumes;
    }/**响应MIME**/
    private String produces;
    public String getProduces(){
        return produces;
    }    public void setProduces(String produces){
        this.produces=produces;
    }/**创建者**/
    private String createUser;
    public String getCreateUser(){
        return createUser;
    }    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }/**是否废弃**/
    private Integer deprecated;
    public Integer getDeprecated(){
        return deprecated;
    }    public void setDeprecated(Integer deprecated){
        this.deprecated=deprecated;
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
