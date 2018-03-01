package com.dozenx.web.core.auth.session;

/**
 * 存入session中的用户对象
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:19 2018/1/2
 * @Modified By:
 */
public class SessionUser {
    /** 用户id **/
    private Long userId;
    /** 用户名称 **/
    private String userName;
    /** 用户角色 **/
    private String role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
