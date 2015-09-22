package cola.machine.util;

import org.apache.commons.lang.StringUtils;

import cola.machine.common.msgbox.MsgReturn;

public class RegexUtil {
	public static MsgReturn isEmail(String email){
		if(email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			return  new MsgReturn(true,"");
		}else{
			return new MsgReturn(false,"请输入有效的邮箱地址");
		}
	}
	
	/**
	 * 说明:判断是否正确
	 * @param pwd
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月20日下午5:00:33
	 */
	public static MsgReturn isPwd(String pwd){
		if(StringUtils.isBlank(pwd)  ){
			 return new MsgReturn(false,"密码不能为空");
		}else if(pwd.length() <6 || pwd.length()>15){
			 return new MsgReturn(false,"密码长度在6~15个字符");
		}
		 return  new MsgReturn(true,"");
	}
	
}
