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

import cola.machine.bean.SysUser;
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

import cola.machine.bean.SysUser;
import cola.machine.service.UserService;
import cola.machine.service.ValidCodeService;
import cola.machine.util.DateUtil;
import cola.machine.util.RandomValidateCode;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.CheckBox;
import cola.machine.util.rules.DateValue;
import cola.machine.util.rules.Digits;
import cola.machine.util.rules.EmailRule;
import cola.machine.util.rules.Length;
import cola.machine.util.rules.NotEmpty;
import cola.machine.util.rules.PhoneRule;
import cola.machine.util.rules.Rule;
import cola.machine.web.listener.MySessionContext;
import core.action.ResultDTO;
import core.page.Page;
import core.util.RequestUtil;

/*@Controller
 @RequestMapping("/user")*/
public class UserController extends BaseController {/*
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    
    
    *//**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     *//*
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/UserList.html";
    }

 
    *//**
     * 说明:ajax请求角色信息
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午12:31:55
     *//*
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public Object list(HttpServletRequest request) {
        Page page = RequestUtil.getPage(request);
        if(page ==null){
             return this.getWrongResultFromCfg("err.param.page");
        }
        
HashMap<String,Object> params= new HashMap<String,Object>();
String userid = request.getParameter("userid");
if(!StringUtil.isBlank(userid)){
    params.put("userid",userid);
}
String useridLike = request.getParameter("useridLike");
if(!StringUtil.isBlank(useridLike)){
    params.put("useridLike",useridLike);
}
String loginname = request.getParameter("loginname");
if(!StringUtil.isBlank(loginname)){
    params.put("loginname",loginname);
}
String loginnameLike = request.getParameter("loginnameLike");
if(!StringUtil.isBlank(loginnameLike)){
    params.put("loginnameLike",loginnameLike);
}
String nickname = request.getParameter("nickname");
if(!StringUtil.isBlank(nickname)){
    params.put("nickname",nickname);
}
String nicknameLike = request.getParameter("nicknameLike");
if(!StringUtil.isBlank(nicknameLike)){
    params.put("nicknameLike",nicknameLike);
}
String pwd = request.getParameter("pwd");
if(!StringUtil.isBlank(pwd)){
    params.put("pwd",pwd);
}
String pwdLike = request.getParameter("pwdLike");
if(!StringUtil.isBlank(pwdLike)){
    params.put("pwdLike",pwdLike);
}
String email = request.getParameter("email");
if(!StringUtil.isBlank(email)){
    params.put("email",email);
}
String emailLike = request.getParameter("emailLike");
if(!StringUtil.isBlank(emailLike)){
    params.put("emailLike",emailLike);
}
String username = request.getParameter("username");
if(!StringUtil.isBlank(username)){
    params.put("username",username);
}
String usernameLike = request.getParameter("usernameLike");
if(!StringUtil.isBlank(usernameLike)){
    params.put("usernameLike",usernameLike);
}
String telno = request.getParameter("telno");
if(!StringUtil.isBlank(telno)){
    params.put("telno",telno);
}
String telnoLike = request.getParameter("telnoLike");
if(!StringUtil.isBlank(telnoLike)){
    params.put("telnoLike",telnoLike);
}
String createtime = request.getParameter("createtime");
if(!StringUtil.isBlank(createtime)){
    if(StringUtil.checkNumeric(createtime)){
        params.put("createtime",createtime);
    }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
        params.put("createtime",new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String createtimeBegin = request.getParameter("createtimeBegin");
if(!StringUtil.isBlank(createtimeBegin)){
    if(StringUtil.checkNumeric(createtimeBegin)){
        params.put("createtimeBegin",createtimeBegin);
    }else if(StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")){
        params.put("createtimeBegin",new Timestamp( DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String createtimeEnd = request.getParameter("createtimeEnd");
if(!StringUtil.isBlank(createtimeEnd)){
    if(StringUtil.checkNumeric(createtimeEnd)){
        params.put("createtimeEnd",createtimeEnd);
    }else if(StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")){
        params.put("createtimeEnd",new Timestamp( DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
    }
}
String status = request.getParameter("status");
if(!StringUtil.isBlank(status)){
    params.put("status",status);
}

        params.put("page",page);
        List<SysUser> users = userService.listByParams4Page(params);
        return ResultUtil.getResult(users, page);
    }
    
    
    
    *//**
     * @param request 发请求
     * @return Object
     *//*
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/UserEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/UserView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
    String id = request.getParameter("id");
HashMap<String,Object> result =new HashMap<String,Object>();
if(!StringUtil.isBlank(id)){
    SysUser bean = userService.selectByPrimaryKey(Long.valueOf(id));
    result.put("bean", bean);
}
return this.getResult(result);



    
      *//*  String id = request.getParameter("id");
        User bean = userService.selectByPrimaryKey(String.valueOf(id));
        HashMap result =new HashMap();
        result.put("bean", bean);
        return this.getResult(bean);*//*
    }

    
    *//**
     * 说明:保存角色信息
     * 
     * @param request
     * @return
     * @throws Exception
     * @return Object
     * @author dozen.zhang
     * @date 2015年11月15日下午1:33:00
     *//*
    // @RequiresPermissions(value={"auth:edit" ,"auth:add" },logical=Logical.OR)
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SysUser user =new  SysUser();
        *//*
        String userid = request.getParameter("userid");
        if(!StringUtil.isBlank(userid)){
            user.setUserid(String.valueOf(userid)) ;
        }
        
        String loginname = request.getParameter("loginname");
        if(!StringUtil.isBlank(loginname)){
            user.setLoginname(String.valueOf(loginname)) ;
        }
        
        String nickname = request.getParameter("nickname");
        if(!StringUtil.isBlank(nickname)){
            user.setNickname(String.valueOf(nickname)) ;
        }
        
        String pwd = request.getParameter("pwd");
        if(!StringUtil.isBlank(pwd)){
            user.setPwd(String.valueOf(pwd)) ;
        }
        
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            user.setEmail(String.valueOf(email)) ;
        }
        
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            user.setUsername(String.valueOf(username)) ;
        }
        
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            user.setTelno(String.valueOf(telno)) ;
        }
        
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            user.setCreatetime(Timestamp.valueOf(createtime)) ;
        }
        
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            user.setStatus(Byte.valueOf(status)) ;
        }
        *//*
        String userid = request.getParameter("userid");
        if(!StringUtil.isBlank(userid)){
            user.setUserid(userid);
        }
        String loginname = request.getParameter("loginname");
        if(!StringUtil.isBlank(loginname)){
            user.setLoginname(loginname);
        }
        String nickname = request.getParameter("nickname");
        if(!StringUtil.isBlank(nickname)){
            user.setNickname(nickname);
        }
        String pwd = request.getParameter("pwd");
        if(!StringUtil.isBlank(pwd)){
            user.setPwd(pwd);
        }
        String email = request.getParameter("email");
        if(!StringUtil.isBlank(email)){
            user.setEmail(email);
        }
        String username = request.getParameter("username");
        if(!StringUtil.isBlank(username)){
            user.setUsername(username);
        }
        String telno = request.getParameter("telno");
        if(!StringUtil.isBlank(telno)){
            user.setTelno(telno);
        }
        String createtime = request.getParameter("createtime");
        if(!StringUtil.isBlank(createtime)){
            if(StringUtil.checkNumeric(createtime)){
                user.setCreatetime(Timestamp.valueOf(createtime));
            }else if(StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")){
                user.setCreatetime(new Timestamp( DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String status = request.getParameter("status");
        if(!StringUtil.isBlank(status)){
            user.setStatus(Byte.valueOf(status));
        }

        //valid
        ValidateUtil vu = new ValidateUtil();
        String validStr="";
        vu.add("userid", userid, "userid",  new Rule[]{new Length(40)});
        vu.add("loginname", loginname, "登录名",  new Rule[]{new Length(40)});
        vu.add("nickname", nickname, "昵称",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("pwd", pwd, "密码",  new Rule[]{new Length(40),new NotEmpty()});
        vu.add("email", email, "邮箱地址",  new Rule[]{new Length(40),new NotEmpty(),new EmailRule()});
        vu.add("username", username, "姓名",  new Rule[]{new Length(45)});
        vu.add("telno", telno, "手机号码",  new Rule[]{new Length(11),new NotEmpty(),new PhoneRule()});
        vu.add("createtime", createtime, "创建时间",  new Rule[]{new DateValue("yyyy-MM-dd HH:mm:ss")});
        vu.add("status", status, "状态",  new Rule[]{new Digits(1,0),new CheckBox(new String[]{"1","2","3"})});
        validStr = vu.validateString();
        if(StringUtil.isNotEmpty(validStr)) {
            return ResultUtil.getResult(302,validStr);
        }

        return userService.save(user);
       
    }
    
    @RequestMapping(value = "/del.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        String useridStr = request.getParameter("userid");
        if(StringUtil.isBlank(useridStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String userid = String.valueOf(useridStr);
        userService.delete(userid);
        return this.getResult(SUCC);
    }
     *//**
     * 多行删除
     * @param request
     * @return
     * @author dozen.zhang
     *//*
    @RequestMapping(value = "/mdel.json")
    @ResponseBody
    public Object multiDelete(HttpServletRequest request) {
        String idStr = request.getParameter("userids");
        if(StringUtil.isBlank(idStr)){
            return this.getWrongResultFromCfg("err.param.notnull");
        }
        String idStrAry[]= idStr.split(",");
        String idAry[]=new String[idStrAry.length];
        for(int i=0,length=idAry.length;i<length;i++){
            ValidateUtil vu = new ValidateUtil();
            String validStr="";
            String userid = idStrAry[i];
            vu.add("userid", userid, "userid",  new Rule[]{new Length(40)});

            try{
                validStr=vu.validateString();
            }catch(Exception e){
                e.printStackTrace();
                validStr="验证程序异常";
                return ResultUtil.getResult(302,validStr);
            }
            
            if(StringUtil.isNotEmpty(validStr)) {
                return ResultUtil.getResult(302,validStr);
            }
            idAry[i]=String.valueOf(idStrAry[i]);
        }
       return  userService.multilDelete(idAry);
    }

    *//**
     * 导出
     * @param request
     * @return
     * @author dozen.zhang
     *//*
    @RequestMapping(value = "/export.json")
    @ResponseBody   
    public Object exportExcel(HttpServletRequest request) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        String userid = request.getParameter("userid");
        if (!StringUtil.isBlank(userid)) {
            params.put("userid", userid);
        }
        String useridLike = request.getParameter("useridLike");
        if (!StringUtil.isBlank(useridLike)) {
            params.put("useridLike", useridLike);
        }
        String loginname = request.getParameter("loginname");
        if (!StringUtil.isBlank(loginname)) {
            params.put("loginname", loginname);
        }
        String loginnameLike = request.getParameter("loginnameLike");
        if (!StringUtil.isBlank(loginnameLike)) {
            params.put("loginnameLike", loginnameLike);
        }
        String nickname = request.getParameter("nickname");
        if (!StringUtil.isBlank(nickname)) {
            params.put("nickname", nickname);
        }
        String nicknameLike = request.getParameter("nicknameLike");
        if (!StringUtil.isBlank(nicknameLike)) {
            params.put("nicknameLike", nicknameLike);
        }
        String pwd = request.getParameter("pwd");
        if (!StringUtil.isBlank(pwd)) {
            params.put("pwd", pwd);
        }
        String pwdLike = request.getParameter("pwdLike");
        if (!StringUtil.isBlank(pwdLike)) {
            params.put("pwdLike", pwdLike);
        }
        String email = request.getParameter("email");
        if (!StringUtil.isBlank(email)) {
            params.put("email", email);
        }
        String emailLike = request.getParameter("emailLike");
        if (!StringUtil.isBlank(emailLike)) {
            params.put("emailLike", emailLike);
        }
        String username = request.getParameter("username");
        if (!StringUtil.isBlank(username)) {
            params.put("username", username);
        }
        String usernameLike = request.getParameter("usernameLike");
        if (!StringUtil.isBlank(usernameLike)) {
            params.put("usernameLike", usernameLike);
        }
        String telno = request.getParameter("telno");
        if (!StringUtil.isBlank(telno)) {
            params.put("telno", telno);
        }
        String telnoLike = request.getParameter("telnoLike");
        if (!StringUtil.isBlank(telnoLike)) {
            params.put("telnoLike", telnoLike);
        }
        String createtime = request.getParameter("createtime");
        if (!StringUtil.isBlank(createtime)) {
            if (StringUtil.checkNumeric(createtime)) {
                params.put("createtime", createtime);
            } else if (StringUtil.checkDateStr(createtime, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime",
                        new Timestamp(DateUtil.parseToDate(createtime, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeBegin = request.getParameter("createtimeBegin");
        if (!StringUtil.isBlank(createtimeBegin)) {
            if (StringUtil.checkNumeric(createtimeBegin)) {
                params.put("createtimeBegin", createtimeBegin);
            } else if (StringUtil.checkDateStr(createtimeBegin, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime",
                        new Timestamp(DateUtil.parseToDate(createtimeBegin, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String createtimeEnd = request.getParameter("createtimeEnd");
        if (!StringUtil.isBlank(createtimeEnd)) {
            if (StringUtil.checkNumeric(createtimeEnd)) {
                params.put("createtimeEnd", createtimeEnd);
            } else if (StringUtil.checkDateStr(createtimeEnd, "yyyy-MM-dd HH:mm:ss")) {
                params.put("createtime",
                        new Timestamp(DateUtil.parseToDate(createtimeEnd, "yyyy-MM-dd HH:mm:ss").getTime()));
            }
        }
        String status = request.getParameter("status");
        if (!StringUtil.isBlank(status)) {
            params.put("status", status);
        }

        // 查询list集合
        List<User> list = userService.listByParams(params);
        // 存放临时文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "list.xlsx");
        String folder = request.getSession().getServletContext().getRealPath("/") + "xlstmp";
        File folder_file = new File(folder);
        if (!folder_file.exists()) {
            folder_file.mkdir();
        }
        String fileName = folder + File.separator + DateUtil.formatToString(new Date(), "yyyyMMddHHmmssSSS") + ".xlsx";
        // 得到导出Excle时清单的英中文map
        LinkedHashMap<String, String> colTitle = new LinkedHashMap<String, String>();
        colTitle.put("userid", "userid");
        colTitle.put("loginname", "登录名");
        colTitle.put("nickname", "昵称");
        colTitle.put("pwd", "密码");
        colTitle.put("email", "邮箱地址");
        colTitle.put("username", "姓名");
        colTitle.put("telno", "手机号码");
        colTitle.put("createtime", "创建时间");
        colTitle.put("status", "状态");
        List finalList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysUser sm = list.get(i);
            HashMap map = new HashMap();
            map.put("userid", list.get(i).getUserid());
            map.put("loginname", list.get(i).getLoginname());
            map.put("nickname", list.get(i).getNickname());
            map.put("pwd", list.get(i).getPwd());
            map.put("email", list.get(i).getEmail());
            map.put("username", list.get(i).getUsername());
            map.put("telno", list.get(i).getTelno());
            map.put("createtime", list.get(i).getCreatetime());
            map.put("status", list.get(i).getStatus());
            finalList.add(map);
        }
        try {
            if (cola.machine.util.ExcelUtil.getExcelFile(finalList, fileName, colTitle) != null) {
                return this.getResult(SUCC, "导出成功", fileName);
            }
            *//*
             * return new ResponseEntity<byte[]>(
             * FileUtils.readFileToByteArray(new File(fileName)), headers,
             * HttpStatus.CREATED);
             *//*
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.getResult(0, "数据为空，导出失败");

    }
    @RequestMapping(value = "/import.json")
    public void importExcel(){
        
    }*/
}
