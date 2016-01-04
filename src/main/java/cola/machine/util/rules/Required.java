package cola.machine.util.rules;

public class Required extends Rule{
	
	public Required(){
		
	}
	
	public boolean valid(){
		if(this.getValue()==null || this.getValue().equals("")){
			this.setMessage("err.param.nonull");
			return false;
		}else {
			return true;
		}
	}
}
