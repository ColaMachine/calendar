package cola.machine.core.page;

import java.util.List;

import com.awifi.core.page.Page;


public class ListPage<T> {
	private Page page;

	private List<T> list;
	
	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
