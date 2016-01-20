package cola.machine.util.rules;

import org.apache.commons.lang.StringUtils;

import cola.machine.util.StringUtil;


public class Digits extends Rule {
	int integer;
	int fraction;
	public Digits() {
		
	}
public Digits(int integer,int fraction) {
		this.integer=integer;
		this.fraction=fraction;
	}
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.checkFloat(this.value, integer, fraction)) {
				return true;
			}
			else {
				this.setMessage("err.param.float");
				return false;
			}
		}
	}

}
