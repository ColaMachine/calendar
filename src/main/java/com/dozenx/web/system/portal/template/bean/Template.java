/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.system.portal.template.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Template {
    /**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**模板名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }/**描述**/
    private String remark;
    public String getRemark(){
        return remark;
    }    public void setRemark(String remark){
        this.remark=remark;
    }/**图片**/
    private String face;
    public String getFace(){
        return face;
    }    public void setFace(String face){
        this.face=face;
    }/**创建时间**/
    private Timestamp createtime;
    public Timestamp getCreatetime(){
        return createtime;
    }    public void setCreatetime(Timestamp createtime){
        this.createtime=createtime;
    }
}
