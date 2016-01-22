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

import cola.machine.service.SmsBatchService;
import cola.machine.bean.SmsBatch;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;

import core.action.ResultDTO;

@Controller
@RequestMapping("/smsBatch")
public class SmsBatchController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SmsBatchController.class);
    /** 权限service **/
    @Autowired
    private SmsBatchService smsBatchService;
    

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
        return "/static/html/SmsBatchList.html";
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
        HashMap params =new HashMap();
        params.put("page",page);
        List<SmsBatch> smsBatchs = smsBatchService.listByParams4Page(params);
        return ResultUtil.getResult(smsBatchs, page);
    }
    
    
    
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/SmsBatchEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/SmsBatchView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        SmsBatch bean = smsBatchService.selectByPrimaryKey(Integer.valueOf(id));
        return this.getResult(1, bean,"");
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
        SmsBatch smsBatch =new  SmsBatch();
            String id = request.getParameter("id");
            smsBatch.setId(Integer.valueOf(id)) ;
            String total = request.getParameter("total");
            smsBatch.setTotal(Integer.valueOf(total)) ;
            String succ = request.getParameter("succ");
            smsBatch.setSucc(Integer.valueOf(succ)) ;
            String fail = request.getParameter("fail");
            smsBatch.setFail(Integer.valueOf(fail)) ;
            String status = request.getParameter("status");
            smsBatch.setStatus(Integer.valueOf(status)) ;
            String phone = request.getParameter("phone");
            smsBatch.setPhone(String.valueOf(phone)) ;
            String content = request.getParameter("content");
            smsBatch.setContent(String.valueOf(content)) ;
            String sendTime = request.getParameter("sendTime");
            smsBatch.setSendTime(Timestamp.valueOf(sendTime)) ;
            String excuteTime = request.getParameter("excuteTime");
            smsBatch.setExcuteTime(Timestamp.valueOf(excuteTime)) ;
        //valid
           ValidateUtil vu = new ValidateUtil();
    String validStr="";
vu.add("id", id, "id",  new Rule[]{new Digits()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("total", total, "总发送数据",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("succ", succ, "成功数量",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("fail", fail, "失败数量",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("status", status, "状态",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("phone", phone, "手机号码",  new Rule[]{new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("content", content, "短信内容",  new Rule[]{new Length(500),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("sendTime", sendTime, "定时发送",  new Rule[]{new Regex("yyyy-MM-dd HH:mm:ss")});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("excuteTime", excuteTime, "执行时间",  new Rule[]{new Regex("yyyy-MM-dd HH:mm:ss")});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}

        return smsBatchService.save(smsBatch);
    }
}
