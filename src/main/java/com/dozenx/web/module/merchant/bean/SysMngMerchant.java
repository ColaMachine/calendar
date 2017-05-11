package com.dozenx.web.module.merchant.bean;

import java.io.Serializable;

public class SysMngMerchant implements Serializable {
	
	/**
     * sys_mng_merchart.id: 
     * <p>
     * <code>
     * 主键自增<br>
     * </code>
     */
	private Long id;
	
	/**
     * sys_mng_merchart.uid: 
     * <p>
     * <code>
     * 用户电话<br>
     * </code>
     */
	private String uid;
	
	/**
     * sys_mng_merchart.mid: 
     * <p>
     * <code>
     * 商户电话<br>
     * </code>
     */
	private String mid;
	 
	/**
     * sys_mng_merchart.uname: 
     * <p>
     * <code>
     * 添加的管理员的昵称<br>
     * </code>
     */
	private String uname;
	
	/**
     * sys_mng_merchart.type: 
     * <p>
     * <code>
     * 类型 3-企业管理员,4-用户代理员<br>
     * </code>
     */
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
