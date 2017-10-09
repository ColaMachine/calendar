/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.sysRolePermission.bean;

public class SysRolePermission {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**角色id**/
    private Long rid;
    public Long getRid(){
        return rid;
    }    public void setRid(Long rid){
        this.rid=rid;
    }/**权限id**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }
}
