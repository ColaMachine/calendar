package cola.machine.util.rules;

import org.apache.commons.lang.StringUtils;

import com.joint.core.constants.ErrorMessage;

public class Numeric extends Rule {
	
	public Numeric() {
		
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
				this.setMessage(ErrorMessage.RULE_NUMERIC_ERROR);
				return false;
			}
		}
	}

}
