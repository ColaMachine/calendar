/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.bean;
import java.sql.Timestamp;
import java.util.Date;

public class SysMenu {
    /**编号**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**父菜单**/
    private Long pid;
    public Long getPid(){
        return pid;
    }    public void setPid(Long pid){
        this.pid=pid;
    }    private String SysMenu_name;
    public String getSysMenu_name(){
        return SysMenu_name;
    }    public void setSysMenu_name(String SysMenu_name){
        this.SysMenu_name=SysMenu_name;
    }/**菜单名称**/
    private String name;
    public String getName(){
        return name;
    }    public void setName(String name){
        this.name=name;
    }/**菜单代码**/
    private String code;
    public String getCode(){
        return code;
    }    public void setCode(String code){
        this.code=code;
    }/**权限**/
    private String permission;
    public String getPermission(){
        return permission;
    }    public void setPermission(String permission){
        this.permission=permission;
    }/**资源对应URL**/
    private String url;
    public String getUrl(){
        return url;
    }    public void setUrl(String url){
        this.url=url;
    }/**排序id**/
    private Integer order;
    public Integer getOrder(){
        return order;
    }    public void setOrder(Integer order){
        this.order=order;
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
    }
}