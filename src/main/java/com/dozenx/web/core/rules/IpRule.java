package com.dozenx.web.core.rules;


import com.dozenx.util.DateUtil;
import com.dozenx.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpRule extends Rule {
	public 	String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
			+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

	public IpRule() {
    }
	@Override
	public boolean valid() throws Exception {
		if (StringUtil.isBlank(this.getValue())) {
			return true;
		}


		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);


		if (!matcher.find()) {
			this.setMessage("请输入正确的ip地址");
			return false;
		}
		else {
			return true;
		}
	}

}
