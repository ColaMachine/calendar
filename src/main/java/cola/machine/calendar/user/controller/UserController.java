package cola.machine.calendar.user.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.calendar.base.service.BaseService;
import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.service.UserService;


@Controller
//@RequestMapping("/")
public class UserController {
private final Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	/*@InitBinder
	// 此处的参数也可以是ServletRequestDataBinder类型
	public void initBinder(ServletRequestDataBinder binder) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}*/
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String  loginGet(HttpServletRequest request) {
		return "/login/login.jsp";
	}
	
	
	@RequestMapping(value = "/loginPost" ,method=RequestMethod.POST)
	public @ResponseBody
	HashMap loginPost(HttpServletRequest request) {
		String email = request.getParameter("email");
		String pwd=request.getParameter("pwd");
		HashMap returnMap= this.userService.loginValid(email, pwd);
		
		User user =new User();
		user.setEmail(MapUtils.getString(returnMap, "email"));
		user.setUsername(MapUtils.getString(returnMap, "username"));
		user.setUserid(MapUtils.getString(returnMap, "userid"));
		request.getSession().putValue("user","userMap");
		return returnMap;
	}
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String  registerGet(HttpServletRequest request) {
		return "user/register.html";
	}
	
	@RequestMapping(value = "/registerPost" ,method=RequestMethod.POST)
	public @ResponseBody
	HashMap registerPost( User user,
			HttpServletRequest request) {
		HashMap returnMap= this.userService.saveRegisterUser(user);//.loginValid(loginName, pwd);
		if(MapUtils.getString(returnMap, BaseService.RESULT).equals(BaseService.SUCCESS)){
			HttpSession session= request.getSession();
			user.setPwd("");
			session.putValue("user",user);
		}
		return returnMap;
		
	}
	@RequestMapping(value = "/waitActive", method = RequestMethod.GET)
	public String waitActive(HttpServletRequest request) {
		return "/active/waitActive.jsp";
	}
	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String  active(HttpServletRequest request) {
		return "/active/active.jsp";
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String  index(HttpServletRequest request) {
		return "/index.jsp";
	}
	
}
