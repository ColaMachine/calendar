package cola.machine.util.rules;

import org.apache.commons.lang3.StringUtils;

import cola.machine.util.DateUtil;


public class DateValue extends Rule {
	
	public DateValue() {
		
	}
	
	@Override
	public boolean valid() throws Exception {
		if (StringUtils.isBlank(this.getValue())) {
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
