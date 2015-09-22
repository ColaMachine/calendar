package cola.machine.calendar.user.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.calendar.base.service.BaseService;
import cola.machine.calendar.user.bean.User;
import cola.machine.calendar.user.service.UserService;
import cola.machine.constants.SysConfig;
import cola.machine.util.MapUtil;
import cola.machine.util.RandomValidateCode;


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
		return "/login/login.html";
	}
	
	
	/**
	 * 说明:登录提交
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:33:39
	 */
	@RequestMapping(value = "/loginPost" ,method=RequestMethod.POST)
	public @ResponseBody HashMap loginPost(HttpServletRequest request) {
		String email = request.getParameter("email");
		String pwd=request.getParameter("pwd");
		HashMap returnMap= this.userService.loginValid(email, pwd);
		if (MapUtils.getString(returnMap, SysConfig.AJAX_RESULT).equals(
				SysConfig.AJAX_SUCC)) {
			User user = new User();
			user.setEmail(MapUtils.getString(returnMap, "email"));
			user.setUsername(MapUtils.getString(returnMap, "username"));
			user.setUserid(MapUtils.getString(returnMap, "userid"));
			user.setActive(MapUtils.getBooleanValue(returnMap, "active"));
			request.getSession().putValue("user", user);
		}
		return returnMap;
	}
	/**
	 * 说明:转到注册页面
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:33:55
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String  registerGet(HttpServletRequest request) {
		return "user/register.html";
	}
	
	/**
	 * 说明:注册提交 
	 * @param user
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:34:13
	 */
	@RequestMapping(value = "/registerPost" ,method=RequestMethod.POST)
	public @ResponseBody HashMap registerPost( User user, HttpServletRequest request) {
		//新注册的用户激活状态为false
		//判断邮箱是否邮箱
		//判断用户名是否有效
		//判断注册邮箱是否重复
		
		//两次密码输入是否相同
		//密码是否有效
		//验证码是否有效
		
		HashMap returnMap= this.userService.saveRegisterUser(user);//.loginValid(loginName, pwd);
		if(MapUtils.getString(returnMap, BaseService.RESULT).equals(BaseService.SUCC)){
			HttpSession session= request.getSession();
			user.setPwd("");
			user.setActive(false);
			session.putValue("user",user);
		}
		return returnMap;
		
	}
	/**
	 * 说明:等待激活页面
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:34:35
	 */
	@RequestMapping(value = "/waitActive", method = RequestMethod.GET)
	public String waitActive(HttpServletRequest request) {
		return "/active/waitActive.jsp";
	}
	/**
	 * 说明:激活邮件回跳页面
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月14日上午11:35:09
	 */
	@RequestMapping(value = "/active/{activeid}", method = RequestMethod.GET)
	public String  active(@PathVariable String activeid, HttpServletRequest request) {
		HashMap returnMap = new HashMap();
		if(StringUtils.isNotBlank(activeid)){
			returnMap =this.userService.updateUserActive(activeid);
		}else{
			request.setAttribute("msg","激活url无效");
			return "/error.jsp";
		}
		if(returnMap.get(SysConfig.AJAX_RESULT).equals(SysConfig.AJAX_SUCC)){
			//把用户信息传入到session 中并让他登录到首页
			User user =(User) returnMap.get("user");
			request.getSession().setAttribute("user", user);
		}else{
			//提示激活url无效
			request.setAttribute("msg",MapUtils.getString(returnMap, SysConfig.AJAX_MSG));
			return "/error.jsp";
		}
		return "/active/active.jsp";
	}
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String  index(HttpServletRequest request) {
		return "/index.jsp";
	}
	
	@RequestMapping(value = "/forgetpwd", method = RequestMethod.GET)
	public String forgetPwd(HttpServletRequest request){
	 	RandomValidateCode r=new RandomValidateCode();
    	String returnStr=r.getRandcode();
    	String[] s= returnStr.split("_");
    	String imgName= s[0];
    	String code =s[1];
    	request.setAttribute("imgname",imgName);
    	request.getSession().setAttribute("validatecode",code);
    	//TODO 需增加回收机制 回收已经生成过的图片
		return "/login/forgetpwd.jsp";
	}
	

	@RequestMapping(value = "/validatecode", method = RequestMethod.GET)
	public @ResponseBody HashMap  validateCode(HttpServletRequest request){
		RandomValidateCode r=new RandomValidateCode();
    	String returnStr=r.getRandcode();
    	
    	String[] s= returnStr.split("_");
    	String imgName= s[0];
    	String code =s[1];
    	
    	request.getSession().setAttribute("validatecode", code);
    	
    	HashMap returnMap= new HashMap();
    	returnMap.put("imgsrc", imgName);
    	
    	return returnMap;
	}
	
	@RequestMapping(value = "/forgetpwd/save", method = RequestMethod.POST)
	public @ResponseBody HashMap  sendPwdRstEmail(HttpServletRequest request){
		//生成图片
		//得到验证码
		String validatecode =(String)request.getSession().getAttribute("validatecode");
		//验证验证码
		String code = request.getParameter("code");
		if(!validatecode .equals(code)){
			return MapUtil.createResultMap(false, "验证码不正确");
		}
		String email = request.getParameter("email");
		return 	userService.saveSenPwdrstEmail(email);
		//发送邮件
		//return "/login/pwdreset.jsp";
	}
	/**
	 * 说明:从密码重置链接中跳转到系统的密码重置页面
	 * @param id
	 * @param request
	 * @return
	 * @author dozen.zhang
	 * @date 2015年5月20日下午4:24:22
	 */
	@RequestMapping(value = "/pwdrst/edit", method = RequestMethod.POST)
	public String editPwdrst(@PathVariable String id,HttpServletRequest request){
		request.setAttribute("id",id);
		return "/login/pwdreset.jsp";
	}
	
	
	@RequestMapping(value = "/pwdrst/save", method = RequestMethod.POST)
	public String savePwdrst(HttpServletRequest request){
		String pwd=request.getParameter("pwd");
		String code=request.getParameter("code");
	HashMap returnMap=	userService.savePwdrst(pwd,code);
		//发送邮件
		return "/login/pwdreset.jsp";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return "/login";
	}
	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String editUser(@PathVariable String userid, HttpServletRequest request){
		User user = this.userService.getUserByUserid(userid);
		request.setAttribute("user", user);
		return "/user/editUser";
	}
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public String viewUser(@PathVariable String userid, HttpServletRequest request){
		User user = this.userService.getUserByUserid(userid);
		request.setAttribute("user", user);
		return "/user/viewUser";
	}
	
}
