/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.module.buy.orderDetail.bean;

import java.sql.Timestamp;

public class OrderDetail {
    /**主键**/
    private Long id;
    public Long getId(){
        return id;
    }    public void setId(Long id){
        this.id=id;
    }/**订单id**/
    private Long orderId;
    public Long getOrderId(){
        return orderId;
    }    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }/**商品id**/
    private Long goodId;
    public Long getGoodId(){
        return goodId;
    }    public void setGoodId(Long goodId){
        this.goodId=goodId;
    }/**商品数量**/
    private Integer num;
    public Integer getNum(){
        return num;
    }    public void setNum(Integer num){
        this.num=num;
    }/**商品单价**/
    private Integer price;
    public Integer getPrice(){
        return price;
    }    public void setPrice(Integer price){
        this.price=price;
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
