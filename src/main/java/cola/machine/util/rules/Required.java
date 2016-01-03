package cola.machine.util.rules;

import com.joint.core.constants.ErrorMessage;

public class Required extends Rule{
	
	public Required(){
		
	}
	
	public boolean valid(){
		if(this.getValue()==null || this.getValue().equals("")){
			this.setMessage(ErrorMessage.RULE_REQUIRED_ERROR);
			return false;
		}else {
			return true;
		}
	}
}
