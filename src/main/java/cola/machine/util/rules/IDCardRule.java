package cola.machine.util.rules;

import org.apache.commons.lang.StringUtils;

import cola.machine.util.StringUtil;


public class IDCardRule extends Rule {
	
	public IDCardRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtil.isID(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("err.param.format.email");
				return false;
			}
		}
	}

}
