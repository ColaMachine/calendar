package com.dozenx.web.core.rules;


import com.dozenx.util.StringUtil;

public class Numeric extends Rule {
	
	public Numeric() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isNumeric(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入数字");
				return false;
			}
		}
	}

}