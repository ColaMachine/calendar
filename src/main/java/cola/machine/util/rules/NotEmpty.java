package cola.machine.util.rules;


public class NotEmpty extends Rule{
	

	
	public NotEmpty(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null || this.getValue().trim().equals("") ){
				message = "err.param.notnull";
				return false;
			}else {
				return true;
			}
		
	}
}
