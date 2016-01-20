package cola.machine.util.rules;


import cola.machine.util.DateUtil;
import cola.machine.util.StringUtil;


public class DateValue extends Rule {
	
	public DateValue() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if (StringUtil.isBlank(this.getValue())) {
			return true;
		}
			
		java.util.Date dtValue = DateUtil.parseToDateTry(this.getValue());
		
		if (dtValue == null) {
			this.setMessage("err.param.date");
			return false;
		}
		else {
			return true;
		}
	}

}
