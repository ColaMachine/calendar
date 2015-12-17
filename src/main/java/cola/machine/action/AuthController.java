/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.bean.Role;
import cola.machine.service.AuthService;
import cola.machine.util.ResultUtil;

import com.awifi.core.page.Page;

@Controller
@RequestMapping("auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	/**
	 * 说明: 跳转到角色列表页面
	 * @return
	 * @return String
	 * @author dozen.zhang
	 * @date 2015年11月15日下午12:30:45
	 */
	@RequestMapping(value = "/listRoles.html" ,method=RequestMethod.GET)
	public String listRoles(){
		return "jsp/system/listRole.html";
	}
	
	
	/**
	 * 说明:ajax请求角色信息
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月15日下午12:31:55
	 */
	//@RequiresPermissions(value={"auth:add","auth:edit","auth:view","auth:del"},logical=Logical.OR) 
	/*@RequestMapping(value = "listPage")
	@ResponseBody
	public Object list(@RequestParam(value = "curpage",required=false)Integer curPage,
			@RequestParam(value = "pagesize",required=false)Integer pageSize){
		
		//查找所有的角色
		Page page =new Page();
		page.setCurPage(curPage);
		page.setPageSize(pageSize);
	
		List<Role> roles= authService.listRole(page);
		return ResultUtil.getResult(roles, page);
	}*/

	/**
	 * 说明:保存角色信息
	 * @param request
	 * @return
	 * @throws Exception
	 * @return Object
	 * @author dozen.zhang
	 * @date 2015年11月15日下午1:33:00
	 */
//	@RequiresPermissions(value={"auth:edit" ,"auth:add"  },logical=Logical.OR)
	/*@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws Exception{
		
		String id =request.getParameter("id");
		String roleName= request.getParameter("code");
		String remarks = request.getParameter("name");
		String permissions=request.getParameter("permissions");
	
		return authService.saveRoleAndPermission(id,roleName,remarks,permissions);
	}*/
}
