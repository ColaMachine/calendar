package com.dozenx.web.module.merchant.bean;

import com.dozenx.util.StringUtil;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.bean.UserTimeInfo;
import com.dozenx.web.third.access.bean.AuthResult;
import com.dozenx.web.third.dbcenter.bean.PubDeviceQueryResult;

import java.util.List;

/**
 * Created by dozen.zhang on 2017/2/24.
 * 用于缓存商户用户信息防止每次都去查
 * 在MerchantController的index 首页信息中会去查
 */
public class SessionDTO {
    String userType ;
    String token ;
    String url ;
    UserTimeInfo timeInfo ;
    private static final long serialVersionUID = 350504050668708L;
    String callback;

    String requestIpPort;
    String deviceId;
    String mobilePhone;
    String userMac;
    String userIP;
    String gwAddress ;
    String gwPort ;
    String nasName ;
    List<String > slidelist;
    SessionUser sessionUser;

    public AuthResult getAuthResult() {
        return authResult;
    }

    public void setAuthResult(AuthResult authResult) {
        this.authResult = authResult;
    }

    AuthResult authResult;
    public void copy(SessionDTO dto){


        if(dto==null){
            return ;
        }
        if(dto.getLogId()!=null){
            this.setLogId(dto.getLogId());
        }
        if(StringUtil.isNotBlank(dto.getDeviceId()) && StringUtil.isNotBlank(deviceId) &&  !deviceId.equals(dto.getDeviceId())){
            this.setMerchant(null);
        }
        if(StringUtil.isNotBlank(dto.getDeviceId())){
            this.setDeviceId(dto.getDeviceId());
        }
        if(StringUtil.isNotBlank(dto.getMobilePhone())){
            this.setMobilePhone(dto.getMobilePhone());
        }
        if(StringUtil.isNotBlank(dto.getUserMac())){
            this.setUserMac(dto.getUserMac());
        }
        if(StringUtil.isNotBlank(dto.getUserIP())){
            this.setUserIP(dto.getUserIP());
        }
        if(StringUtil.isNotBlank(dto.getGwAddress())){
            this.setGwAddress(dto.getGwAddress());
        }
        if(StringUtil.isNotBlank(dto.getGwPort())){
            this.setGwPort(dto.getGwPort());
        }
        if(StringUtil.isNotBlank(dto.getNasName())){
            this.setNasName(dto.getNasName());
        }
    }
    String roleType;
    String  logId ;
    boolean freePkgGeted =true;

    public boolean isFreePkgGeted() {
        return freePkgGeted;
    }

    public void setFreePkgGeted(boolean freePkgGeted) {
        this.freePkgGeted = freePkgGeted;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
    String requestIp ;

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestIpPort() {
        return requestIpPort;
    }

    public void setRequestIpPort(String requestIpPort) {
        this.requestIpPort = requestIpPort;
    }
    private UserCutoff userCutoff;

    public UserCutoff getUserCutoff() {
        return userCutoff;
    }

    public void setUserCutoff(UserCutoff userCutoff) {
        this.userCutoff = userCutoff;
    }


    public SessionUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public List<String> getSlidelist() {
        return slidelist;
    }

    public void setSlidelist(List<String> slidelist) {
        this.slidelist = slidelist;
    }

    public PubDeviceQueryResult getMerchant() {
        return merchant;
    }

    public void setMerchant(PubDeviceQueryResult merchant) {
        this.merchant = merchant;
    }

    private PubDeviceQueryResult merchant;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getGwAddress() {
        return gwAddress;
    }

    public void setGwAddress(String gwAddress) {
        this.gwAddress = gwAddress;
    }

    public String getGwPort() {
        return gwPort;
    }

    public void setGwPort(String gwPort) {
        this.gwPort = gwPort;
    }

    public String getNasName() {
        return nasName;
    }

    public void setNasName(String nasName) {
        this.nasName = nasName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public UserTimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(UserTimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }


    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}
