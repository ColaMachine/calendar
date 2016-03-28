package cola.machine.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.bean.User;
import cola.machine.constants.SysConfig;
import cola.machine.service.UserService;
import cola.machine.service.ValidCodeService;
import cola.machine.util.DateUtil;
import cola.machine.util.RandomValidateCode;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.DateValue;
import cola.machine.util.rules.EmailRule;
import cola.machine.util.rules.Length;
import cola.machine.util.rules.NotEmpty;
import cola.machine.util.rules.Rule;
import cola.machine.web.listener.MySessionContext;
import core.action.ResultDTO;
import core.page.Page;
import core.util.RequestUtil;

@Controller
// @RequestMapping("/")
public class OUserController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(OUserController.class);
    @Autowired
    private UserService userService;

    /*
     * @InitBinder // 此处的参数也可以是ServletRequestDataBinder类型 public void
     * initBinder(ServletRequestDataBinder binder) throws Exception { DateFormat
     * df = new SimpleDateFormat("yyyy-MM-dd"); CustomDateEditor dateEditor =
     * new CustomDateEditor(df, true); binder.registerCustfomEditor(Date.class,
     * dateEditor); }
     */
    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String loginGet(HttpServletRequest request) {
        // String s =request.getParameter("s");
        // s.substring(12);
        // logger.debug("s");
        // System.out.println(123);
        return "/static/html/login.html";
    }

    @RequestMapping(value = "/user/listTree.json", method = RequestMethod.GET)
    public @ResponseBody ResultDTO listAllUsers() {
        List userList = new ArrayList();
        Iterator iter = MySessionContext.getMyMap().keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = MySessionContext.getMyMap().get(key);
            if (val != null) {
                HttpSession session = (HttpSession) val;
                Object userObj = session.getAttribute("user");
                session.invalidate();
                if (userObj != null) {
                    System.out.println(((User) userObj).getEmail());
                    userList.add(((User) userObj).getEmail());
                }
            }
        }
        return ResultUtil.getDataResult(userList);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index3(HttpServletRequest request) {
        request.setAttribute("path", SysConfig.PATH);
        return "/jsp/index.jsp";
    }

    /**
     * 说明:登录提交
     * 
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:39
     */
    @RequestMapping(value = "/loginPost.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO loginPost(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        String imgCaptcha = request.getParameter("picCaptcha");
        String smsCaptcha = request.getParameter("smsCaptcha");
        String sessionid = request.getParameter("sessionid");
        ValidCodeService validCodeService = new ValidCodeService();

        ResultDTO result = validCodeService.remoteValidSms(email, smsCaptcha);
        if (!result.isRight()) {
            // return result;
        }
        result = validCodeService.remoteValidImg(sessionid, imgCaptcha);
        if (!result.isRight()) {
            return result;
        }
        result = this.userService.loginValid(email, pwd);
        if (result.isRight()) {
            User user = (User) result.getData();
            request.getSession().setAttribute("user", user);
            result.setData(null);
        }
        return result;
    }

    /**
     * 说明:转到注册页面
     * 
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:33:55
     */
    @RequestMapping(value = "/register.htm", method = RequestMethod.GET)
    public String registerGet(HttpServletRequest request) {
        return "user/register.html";
    }

    /**
     * 说明:注册提交
     * 
     * @param user
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:13
     */
    @RequestMapping(value = "/registerPost.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO registerPost(User user, HttpServletRequest request) {
        // 新注册的用户激活状态为false
        // 判断邮箱是否邮箱
        // 判断用户名是否有效
        // 判断注册邮箱是否重复

        // 两次密码输入是否相同
        // 密码是否有效
        // 验证码是否有效

        ResultDTO result = this.userService.saveRegisterUser(user);// .loginValid(loginName,
                                                                   // pwd);
        if (result.isRight()) {
            HttpSession session = request.getSession();
            user.setPwd("");
            user.setStatus((byte)1);
            session.putValue("user", user);
        }
        return result;

    }

    /**
     * 说明:等待激活页面
     * 
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:34:35
     */
    @RequestMapping(value = "/waitActive.htm", method = RequestMethod.GET)
    public String waitActive(HttpServletRequest request) {
        return "/active/waitActive.html";
    }

    /**
     * 说明:激活邮件回跳页面
     * 
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月14日上午11:35:09
     */
    @RequestMapping(value = "/active.htm", method = RequestMethod.GET)
    public String active(HttpServletRequest request) {
        String activeid = request.getParameter("activeid");
        ResultDTO result;
        if (StringUtils.isNotBlank(activeid)) {
            result = this.userService.updateUserActive(activeid);
        } else {
            request.setAttribute("msg", "激活url无效");
            return "/error.jsp";
        }
        if (result.isRight()) {
            // 把用户信息传入到session 中并让他登录到首页
            User user = (User) result.getData();
            request.getSession().setAttribute("user", user);
        } else {
            // 提示激活url无效
            request.setAttribute("msg", result.getMsg());
            return "/error.jsp";
        }
        return "/active/active.jsp";
    }

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", SysConfig.PATH);
        // logger.debug("s");
        // LogUtil.debug("nihao");
        // System.out.println(123);
        return "/jsp/index.jsp";
    }

    @RequestMapping(value = "/index2", method = RequestMethod.GET)
    public String index2(HttpServletRequest request) {
        // System.out.println(request.getParameter("path"));
        // System.out.println(request.getSession().getAttribute("path"));
        // System.out.println(request.getServletContext().getAttribute("path"));
        request.setAttribute("path", "/calendar");
        return "/index.jsp";
    }

    @RequestMapping(value = "/forgetpwd.htm", method = RequestMethod.GET)
    public String forgetPwd(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        request.setAttribute("imgname", imgName);
        request.getSession().setAttribute("validatecode", code);
        // TODO 需增加回收机制 回收已经生成过的图片
        return "/login/forgetpwd.jsp";
    }

    @RequestMapping(value = "/validatecode", method = RequestMethod.GET)
    public @ResponseBody ResultDTO validateCode(HttpServletRequest request) {
        RandomValidateCode r = new RandomValidateCode();
        String[] returnStr = new String[2];
        try {
            returnStr = r.getImgRandcode();
        } catch (Exception e) {
        }
        String imgName = returnStr[0];
        String code = returnStr[1];
        ResultDTO result = new ResultDTO();
        request.getSession().setAttribute("validatecode", code);
        result.setR(1);
        result.setData(imgName);

        return result;
    }

    @RequestMapping(value = "/forgetpwd/save.json", method = RequestMethod.POST)
    public @ResponseBody ResultDTO sendPwdRstEmail(HttpServletRequest request) {
        // 生成图片
        // 得到验证码
        String validatecode = (String) request.getSession().getAttribute("validatecode");
        // 验证验证码
        String code = request.getParameter("code");
        if (!validatecode.equals(code)) {
            return this.getWrongResultFromCfg("validatecode.wrong");
        }
        String email = request.getParameter("email");
        return userService.saveSenPwdrstEmail(email);
        // 发送邮件
        // return "/login/pwdreset.jsp";
    }

    /**
     * 说明:从密码重置链接中跳转到系统的密码重置页面
     * 
     * @param id
     * @param request
     * @return
     * @author dozen.zhang
     * @date 2015年5月20日下午4:24:22
     */
    @RequestMapping(value = "/pwdrst/edit.htm", method = RequestMethod.POST)
    public String editPwdrst(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "/login/pwdreset.jsp";
    }

    @RequestMapping(value = "/pwdrst/save.htm", method = RequestMethod.POST)
    public String savePwdrst(HttpServletRequest request) {
        String pwd = request.getParameter("pwd");
        String code = request.getParameter("code");
        ResultDTO result = userService.savePwdrst(pwd, code);
        // 发送邮件
        return "/login/pwdreset.jsp";
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "/static/html/login.html";
    }
/*
    @RequestMapping(value = "/user/edit.htm", method = RequestMethod.GET)
    public String editUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/editUser";
    }

    @RequestMapping(value = "/user/view.htm", method = RequestMethod.GET)
    public String viewUser(@PathVariable String userid, HttpServletRequest request) {
        User user = this.userService.getUserByUserid(userid);
        request.setAttribute("user", user);
        return "/user/viewUser";
    }*/
    
   
 
  
    
    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("C:\\Users\\dozen.zhang\\Documents\\calendar\\src\\main\\resources\\config\\xml\\applicationContext.xml");
        Object object = ac.getBean("validCodeService");
        System.out.println(object);
    }
    
   
}