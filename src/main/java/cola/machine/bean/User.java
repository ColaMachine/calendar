package cola.machine.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class User {
	@NotNull (message="userid不能为空") 
	private String userid;
	private String loginname;
	private String nickname;
	@NotNull(message="用户名不能为空") @Length(min=2,max=15,message="用户名长度为2~15")
	private String username;
	@NotNull(message="密码不能为空") @Length(min=6,max=20,message="密码长度为6~20")
	private String pwd;
	@NotNull(message="邮箱不能为空")  @Email (message="请填写真实的邮箱")
	private String email;
	
	/** 
	 *是否激活
	 */
	private boolean active;
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/*
	private String firstname;

	private String lastname;
	*/
	private String telno;

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
/*
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
