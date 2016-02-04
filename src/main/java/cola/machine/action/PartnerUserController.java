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

import cola.machine.service.PartnerUserService;
import cola.machine.bean.PartnerUser;
import cola.machine.util.ResultUtil;
import cola.machine.util.ValidateUtil;
import cola.machine.util.rules.*;
import core.page.Page;

import cola.machine.util.StringUtil;
import cola.machine.util.ValidateUtil;

import core.action.ResultDTO;

@Controller
@RequestMapping("/partnerUser")
public class PartnerUserController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(PartnerUserController.class);
    /** 权限service **/
    @Autowired
    private PartnerUserService partnerUserService;
    

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
        return "/static/html/PartnerUserList.html";
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
    public Object list(@RequestParam(value = "curPage", required = false) Integer curPage, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        // 查找所有的角色
        // 查找所有的角色
        if(curPage==null ){
            return this.getWrongResultFromCfg("err.param.notnull.curPage");
        }
        if(pageSize==null ){
            return this.getWrongResultFromCfg("err.param.notnull.pageSize");
        }
        //Page page = new Page(curPage,pageSize);
        Page page =new Page();
        page.setCurPage(curPage);
        page.setPageSize(pageSize);
        HashMap params =new HashMap();
        params.put("page",page);
        List<PartnerUser> partnerUsers = partnerUserService.listByParams4Page(params);
        return ResultUtil.getResult(partnerUsers, page);
    }
    
    
    
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/PartnerUserEdit.html";
    }
    @RequestMapping(value = "/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/PartnerUserView.html";
    }
   
      @RequestMapping(value = "/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        PartnerUser bean = partnerUserService.selectByPrimaryKey(Integer.valueOf(id));
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
        PartnerUser partnerUser =new  PartnerUser();
            String id = request.getParameter("id");
            partnerUser.setId(Integer.valueOf(id)) ;
            String userId = request.getParameter("userId");
            partnerUser.setUserId(Integer.valueOf(userId)) ;
            String partnerId = request.getParameter("partnerId");
            partnerUser.setPartnerId(Long.valueOf(partnerId)) ;
        //valid
           ValidateUtil vu = new ValidateUtil();
    String validStr="";
vu.add("id", id, "id",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("userId", userId, "用户id",  new Rule[]{new Digits(),new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}
vu.add("partnerId", partnerId, "合作伙伴Id",  new Rule[]{new NotEmpty()});
 validStr = vu.validateString();
if(StringUtil.isNotEmpty(validStr)) {
return ResultUtil.getResult(302,validStr);
}

        return partnerUserService.save(partnerUser);
    }
}