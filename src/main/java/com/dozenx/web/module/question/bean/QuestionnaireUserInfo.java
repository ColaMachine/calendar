package com.dozenx.web.module.question.bean;

public class QuestionnaireUserInfo {
    
    /** * 用户手机号 */
    private Long userId;
    /** * 商户ID */
    private Long merchantId;
    /** * 设备mac */
    private String apMac;
    /** * 设备ssid */
    private String ssid;
    /** * 领取赠送时长 */
    private int freeLevel;
    /** * 平均消费 */
    private Float averagePay;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public String getApMac() {
        return apMac;
    }
    public void setApMac(String apMac) {
        this.apMac = apMac;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public int getFreeLevel() {
        return freeLevel;
    }
    public void setFreeLevel(int freeLevel) {
        this.freeLevel = freeLevel;
    }
    public Float getAveragePay() {
        return averagePay;
    }
    public void setAveragePay(Float averagePay) {
        this.averagePay = averagePay;
    }
    @Override
    public String toString() {
        return "QuestionnaireUserInfo [userId=" + userId + ", merchantId=" + merchantId + ", apMac=" + apMac
                + ", ssid=" + ssid + ", freeLevel=" + freeLevel + ", averagePay=" + averagePay + "]";
    }
    
    
    
    
    
    
}
