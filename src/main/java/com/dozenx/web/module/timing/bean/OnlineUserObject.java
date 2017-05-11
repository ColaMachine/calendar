package com.dozenx.web.module.timing.bean;


import java.io.Serializable;
import java.util.Date;



public class OnlineUserObject  extends BaseEntity implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 3528001077598143386L;
    /**
     * 序列化
     */
    private long id;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 标志位
     */
    private String processFlg;
    /**
     * 开始时间
     */
    private long startTime;
    /**
     * 结束时间
     */
    private long endTime;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 当前时间
     */
    private long nowTime;
    /**
     * 商户id
     */
    private long merchantId;
    /**
     * 商户名
     */
    private String merchantName;
    /**
     * 省份
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 县
     */
    private String countyName;
    
    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public long getNowTime() {
        return nowTime;
    }

    public void setNowTime(long nowTime) {
        this.nowTime = nowTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProcessFlg() {
        return processFlg;
    }

    public void setProcessFlg(String processFlg) {
        this.processFlg = processFlg;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
