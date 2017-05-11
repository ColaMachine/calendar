package com.dozenx.web.third.dbcenter.bean;

import java.util.Date;

/**
 * Created by dozen.zhang on 2017/3/1.
 */
public class PubUser {
    private Long id;//主键
    private String authId;//认证用户名
    private String telphone;//认证手机号
    private String wechat;//微信
    private String email;//邮箱
    private String userCard;//身份证号码
    private String userNick;//用户昵称
    private String userRealname;//用户姓名
    private String sex;//性别
    private String political;//政治面貌
    private String nativeStr;//籍贯

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserRealname() {
        return userRealname;
    }

    public void setUserRealname(String userRealname) {
        this.userRealname = userRealname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getNativeStr() {
        return nativeStr;
    }

    public void setNativeStr(String nativeStr) {
        this.nativeStr = nativeStr;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getIntegralUsable() {
        return integralUsable;
    }

    public void setIntegralUsable(Long integralUsable) {
        this.integralUsable = integralUsable;
    }

    public Long getIntegralUsed() {
        return integralUsed;
    }

    public void setIntegralUsed(Long integralUsed) {
        this.integralUsed = integralUsed;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaceInfo() {
        return faceInfo;
    }

    public void setFaceInfo(String faceInfo) {
        this.faceInfo = faceInfo;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAbhor() {
        return abhor;
    }

    public void setAbhor(String abhor) {
        this.abhor = abhor;
    }

    public String getLeisurePlace() {
        return leisurePlace;
    }

    public void setLeisurePlace(String leisurePlace) {
        this.leisurePlace = leisurePlace;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(Long totalExp) {
        this.totalExp = totalExp;
    }

    public String getBadges() {
        return badges;
    }

    public void setBadges(String badges) {
        this.badges = badges;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    private Date birthday;//生日
    private Long integralUsable;//可用积分
    private Long integralUsed;//已消费积分
    private String phone;//联系电话
    private String address;//联系地址
    private String faceInfo;//头像
    private String college;//毕业院校
    private String major;//专业
    private String company;//当前就业单位
    private String industry;//行业/职业
    private String post;//岗位
    private String hobby;//爱好
    private String abhor;//厌恶
    private String leisurePlace;//休闲场所
    private Long level;//等级
    private Long totalExp;//总经验
    private String badges;//徽章
    private String remarks;//备注说明
    private Date createDate;//创建时间
}
