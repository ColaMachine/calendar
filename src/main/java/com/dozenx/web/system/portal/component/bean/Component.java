/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.system.portal.component.bean;

import java.sql.Timestamp;

public class Component {
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
    }/**备注**/
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

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    private String entity;

    private String entitySource;

    public String getEntitySource() {
        return entitySource;
    }

    public void setEntitySource(String entitySource) {
        this.entitySource = entitySource;
    }

    public String getSettingSource() {
        return settingSource;
    }

    public void setSettingSource(String settingSource) {
        this.settingSource = settingSource;
    }

    private String settingSource;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String css;



    public String setting;
}
