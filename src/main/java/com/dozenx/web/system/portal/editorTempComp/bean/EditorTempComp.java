/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.system.portal.editorTempComp.bean;

public class EditorTempComp {
    /**编号**/
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**模板id**/
    private Long templateId;
    public Long getTemplateId(){
        return templateId;
    }    public void setTemplateId(Long templateId){
        this.templateId=templateId;
    }/**组件id**/
    private Long componentId;
    public Long getComponentId(){
        return componentId;
    }    public void setComponentId(Long componentId){
        this.componentId=componentId;
    }/**配置**/
    private String config;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    private String html;
    public String getConfig(){
        return config;
    }    public void setConfig(String config){
        this.config=config;
    }
}
