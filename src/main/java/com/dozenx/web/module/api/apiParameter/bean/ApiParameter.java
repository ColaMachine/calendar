/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.api.apiParameter.bean;
import java.sql.Timestamp;
import java.util.Date;

public class ApiParameter {
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
    }/**父参数**/
    private String fater;
    public String getFater(){
        return fater;
    }    public void setFater(String fater){
        this.fater=fater;
    }/**父id**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }/**类型**/
    private String type;
    public String getType(){
        return type;
    }    public void setType(String type){
        this.type=type;
    }/**数据类型**/
    private String dataType;
    public String getDataType(){
        return dataType;
    }    public void setDataType(String dataType){
        this.dataType=dataType;
    }/**是否必填**/
    private Integer required;
    public Integer getRequired(){
        return required;
    }    public void setRequired(Integer required){
        this.required=required;
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
