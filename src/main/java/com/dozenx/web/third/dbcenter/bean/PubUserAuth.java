package com.dozenx.web.third.dbcenter.bean;

import java.util.Date;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
public class PubUserAuth {
    private Long id;//主键
    private Integer authPlatform;// 认证平台
    private Long userId;// 用户编号，关联到用户表里面的主键
    private String logname;//用户名（登录账号）
    private String telphone;//用户手机号码
    private String wechat;//用户微信账号
    private String email;//用户邮箱
    private String userCard;//用户身份证
    private String authPswd;//认证密码
    private String userType;//用户类型，1：普通用户，2：商户
    private String safetyCode;//安全码
    private Date createDate;//创建时间
    private String token;
String seed;

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuthPlatform() {
        return authPlatform;
    }

    public void setAuthPlatform(Integer authPlatform) {
        this.authPlatform = authPlatform;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getAuthPswd() {
        return authPswd;
    }

    public void setAuthPswd(String authPswd) {
        this.authPswd = authPswd;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSafetyCode() {
        return safetyCode;
    }

    public void setSafetyCode(String safetyCode) {
        this.safetyCode = safetyCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(Date unlockDate) {
        this.unlockDate = unlockDate;
    }

    private Integer status;// 状态（1:正常,2:冻结/锁定,9:注销）
    private Date statusDate;//状态时间
    private Date unlockDate;//自动解锁时间
}
