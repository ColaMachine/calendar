package com.dozenx.web.core.auth.dao;


import com.dozenx.web.core.auth.sysUser.bean.SysUser;

import java.util.List;

public interface UserMapper {
	public int countAll();
	
	public SysUser selectUserByUsername(String username);

	public List<SysUser> fuzzyQuery(String name);

	public SysUser selectUserByEmail(String email);

	public List<SysUser> selectAll();

    public SysUser selectUserByTelno(String telno);

    public int countUserByEmail(String email);

    public int countUserByTelno(String telno);

	public void resetPwd(SysUser user);

	public void updateStatus(SysUser user);

	/**
	 * 根据微信id 获取用户信息
	 * @param openid
	 * @return
     */
	public SysUser selectUserByWeichat(String openid);
}
