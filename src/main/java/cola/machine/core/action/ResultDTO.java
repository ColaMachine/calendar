package cola.machine.core.action;

import cola.machine.core.page.Page;

public class ResultDTO {
	private String r;
	private Object data;
	private String msg;
	private Page page;
	
	public ResultDTO(String r, Object data, String msg){
		this.r = r;
		this.data = data;
		this.msg = msg;
	}
	
	public ResultDTO(String r, Object data, String msg, Page page){
		this.r = r;
		this.data = data;
		this.msg = msg;
		this.page = page;
	}
	
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public boolean isSucc(){
		return this.r!=null  && this.r.equals("1");
	}
}

