/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
 package cola.machine.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cola.machine.service.ZUserService;
import cola.machine.bean.ZUser;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import core.util.RequestUtil;
import core.action.ResultDTO;
import cola.machine.util.DateUtil;
@Controller
@RequestMapping("/zUser")
public class ZUserController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(ZUserController.class);
    /** 权限service **/
    @Autowired
    private ZUserService zUserService;
    

    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/ZUserList.html";
    }

 
    /**
     * 说明:ajax请求角色信息
     * 
     * @param curPage
     * @param pageSize
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
        HashMap params= new HashMap();
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
                params.put("id",id);
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
                params.put("email",email);
        }
        String pwd = request.getParameter("pwd");
        if(!StringUtil.isBlank(pwd)){
                params.put("pwd",pwd);
        }
        String nick = request.getParameter("nick");
        if(!StringUtil.isBlank(nick)){
                params.put("nick",nick);
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
                params.put("status",status);
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
                params.put("phone",phone);
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                params.put("createTime",createTime);
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTime",new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeBegin = request.getParameter("createTimeBegin");
        if(!StringUtil.isBlank(createTimeBegin)){
            if(StringUtil.checkNumeric(createTimeBegin)){
                params.put("createTimeBegin",createTimeBegin);
            }else if(StringUtil.checkDateStr(createTimeBegin, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeBegin",new Timestamp( DateUtil.parseToDate(createTimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createTimeEnd = request.getParameter("createTimeEnd");
        if(!StringUtil.isBlank(createTimeEnd)){
            if(StringUtil.checkNumeric(createTimeEnd)){
                params.put("createTimeEnd",createTimeEnd);
            }else if(StringUtil.checkDateStr(createTimeEnd, "yyyy-MM-dd HH:mm:ss")){
                params.put("createTimeEnd",new Timestamp( DateUtil.parseToDate(createTimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        params.put("page",page);
        List<ZUser> zUsers = zUserService.listByParams4Page(params);
        return ResultUtil.getResult(zUsers, page);
    }
    
    
    
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/ZUserEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/ZUserView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        ZUser bean = zUserService.selectByPrimaryKey(Integer.valueOf(id));
        return this.getResult(bean);
    }

    
    /**
     * 说明:保存角色信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     */
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        ZUser zUser =new  ZUser();
        /*
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
            zUser.setId(Integer.valueOf(id)) ;
        }
        
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            zUser.setEmail(String.valueOf(email)) ;
        }
        
        String pwd = request.getParameter("pwd");
        if(!StringUtil.isBlank(pwd)){
            zUser.setPwd(String.valueOf(pwd)) ;
        }
        
        String nick = request.getParameter("nick");
        if(!StringUtil.isBlank(nick)){
            zUser.setNick(String.valueOf(nick)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            zUser.setStatus(Integer.valueOf(status)) ;
        }
        
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
            zUser.setPhone(String.valueOf(phone)) ;
        }
        
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            zUser.setCreateTime(Timestamp.valueOf(createTime)) ;
        }
        */
        String id = request.getParameter("id");
        if(!StringUtil.isBlank(id)){
                zUser.setId(Integer.valueOf(id));
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
                zUser.setEmail(String.valueOf(email));
        }
        String pwd = request.getParameter("pwd");
        if(!StringUtil.isBlank(pwd)){
                zUser.setPwd(String.valueOf(pwd));
        }
        String nick = request.getParameter("nick");
        if(!StringUtil.isBlank(nick)){
                zUser.setNick(String.valueOf(nick));
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
                zUser.setStatus(Integer.valueOf(status));
        }
        String phone = request.getParameter("phone");
        if(!StringUtil.isBlank(phone)){
                zUser.setPhone(String.valueOf(phone));
        }
        String createTime = request.getParameter("createTime");
        if(!StringUtil.isBlank(createTime)){
            if(StringUtil.checkNumeric(createTime)){
                zUser.setCreateTime(Timestamp.valueOf(createTime));
            }else if(StringUtil.checkDateStr(createTime, "yyyy-MM-dd HH:mm:ss")){
                zUser.setCreateTime(new Timestamp( DateUtil.parseToDate(createTime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("id", id, "id",  new Rule[]{new Digits(10,0)});
        vu.add("email", email, "邮箱",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("pwd", pwd, "密码",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("nick", nick, "昵称",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("status", status, "状态",  new Rule[]{new Digits(10,0),new CheckBox(new String[]{"1","2","3"}),new NotEmpty()});
        vu.add("phone", phone, "手机号码",  new Rule[]{new Length(11),new Regex("^\\d{11}$")});
        vu.add("createTime", createTime, "注册时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss"),new NotEmpty()});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return zUserService.save(zUser);
    }
    
    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        Integer id = Integer.valueOf(idStr);
        zUserService.delete(id);
        return this.getResult(SUCC);
    }
}
