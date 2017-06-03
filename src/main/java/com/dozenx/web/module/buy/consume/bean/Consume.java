/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.buy.consume.bean;
import java.sql.Timestamp;
import java.util.Date;

public class Consume {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**商户id**/
    private Long merId;
    public Long getMerId(){
        return merId;
    }    public void setMerId(Long merId){
        this.merId=merId;
    }/**用户id**/
    private Long userId;
    public Long getUserId(){
        return userId;
    }    public void setUserId(Long userId){
        this.userId=userId;
    }/**实付金额**/
    private Integer money;
    public Integer getMoney(){
        return money;
    }    public void setMoney(Integer money){
        this.money=money;
    }/**订单id**/
    private Long orderId;
    public Long getOrderId(){
        return orderId;
    }    public void setOrderId(Long orderId){
        this.orderId=orderId;
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
