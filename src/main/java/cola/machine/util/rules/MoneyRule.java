package cola.machine.util.rules;

import org.apache.commons.lang.StringUtils;


public class MoneyRule extends Rule {
	
	public MoneyRule() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if(this.getValue() == null || this.getValue().equals("")){
			return true;
		}else{
			if (StringUtils.isNumeric(this.getValue())) {
				return true;
			}
			else {
				this.setMessage("err.param.numeric");
				return false;
			}
		}
	}

}
