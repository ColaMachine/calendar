/**
 * 版权所有：公众信息
 * 项目名称:awifi-joint
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月17日
 * 文件说明: 
 */
package com.dozenx.web.message;

public enum ErrorMsg {
	SYSTEM("系统错误",1),
	PARAM("前端参数错误",2),
	SERVICE("业务级别错误",3),
	THIRD("依赖级别错误",4),
	INFO("告知",5),
	UNKNOW("系统错误",99);

	private String name;
	private int id;
	ErrorMsg(String name,int id){
		this.name=name;
		this.id =id;
	}
	public String getName(){
		return this.name();
		
	}
	public int getId(){
		return this.id;
	}
}
