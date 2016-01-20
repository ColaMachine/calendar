package cola.machine.util.rules;


public class NotNull extends Rule{
	

	
	public NotNull(){
	}

	@Override
	public boolean valid() throws Exception{
		if(this.getValue() == null ){
			
				message = "err.param.notnull";
				return false;
			}else {
				return true;
			}
		
	}
}
