/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package cola.machine.action;

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

import cola.machine.bean.Permission;
import cola.machine.service.AuthService;
import cola.machine.service.PermissionService;
import cola.machine.service.UserService;
import cola.machine.util.ResultUtil;

import com.awifi.core.page.Page;

import core.action.ResultDTO;
@Controller
@RequestMapping("/permission")
public class PermissionController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(PermissionController.class);
    /** 权限service **/
    @Autowired
    private PermissionService permissionService;
    

    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/permission/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/permission/listPermission.html";
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
    @RequestMapping(value = "/permission/list.json")
    @ResponseBody
    public Object list(@RequestParam(value = "curPage", required = false) Integer curPage, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        // 查找所有的角色
        Page page = new Page(curPage,pageSize);
        HashMap params =new HashMap();
        params.put("page",page);
        List<Permission> permissions = permissionService.list(params);
        return ResultUtil.getResult(permissions, page);
    }
    
    

    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/permission/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/permission/editpermission.html";
    }
    @RequestMapping(value = "/permission/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/permission/viewpermission.html";
    }
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/permission/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        ResultDTO result = permissionService.selectByPrimaryKey(id);
        return result;
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
    @RequestMapping(value = "/permission/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
     Permission permission =new  Permission();
        
            String id = request.getParameter("id");
            permission.setId(id   ) ;
        
            String code = request.getParameter("code");
            permission.setCode(code   ) ;
        
        return permissionService.save(permission);
    }
}

