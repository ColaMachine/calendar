package com.dozenx.web.core.rules;


import com.dozenx.util.StringUtil;

public class Money extends Rule {
	
	public Money() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkNumeric(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("请输入有效金额");
				return false;
			}
		}
	}

}
