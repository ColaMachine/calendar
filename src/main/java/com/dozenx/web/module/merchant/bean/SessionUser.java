package com.dozenx.web.module.merchant.bean;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
public class SessionUser {
    String face;
    Long id;
 /*   Long authId;
    Long pubId;*/

  /*  public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getPubId() {
        return pubId;
    }

    public void setPubId(Long pubId) {
        this.pubId = pubId;
    }*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    String address;
    int sex;//1男 2女 0未知
    Long birthday;
    String nick;
    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    private String role;
   // private boolean isVipUser;
    private String authPswd;
    String phone;
    String name;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

/*


    public boolean isVipUser() {
        return isVipUser;
    }

    public void setVipUser(boolean vipUser) {
        isVipUser = vipUser;
    }
*/



    public String getAuthPswd() {
        return authPswd;
    }

    public void setAuthPswd(String authPswd) {
        this.authPswd = authPswd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
