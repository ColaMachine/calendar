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
import cola.machine.bean.Role;
import cola.machine.service.AuthService;
import cola.machine.service.UserService;
import cola.machine.util.ResultUtil;

import com.awifi.core.page.Page;

import core.action.ResultDTO;

@Controller
@RequestMapping("/auth")
public class AuthController {
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    /** 权限service **/
    @Autowired
    private AuthService authService;
    /** 用户service **/
    @Resource
    private UserService userService;

    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/role/list.htm", method = RequestMethod.GET)
    public String listRoles() {
        return "/static/html/auth/listRole.html";
    }

    /**
     * @param request
     *            发请求
     * @return Object
     * @throws Exception
     *             抛出异常
     */
    /*
     * //@RequiresPermissions(value = { "auth:edit", "auth:add" }, logical =
     * Logical.OR)
     * 
     * @RequestMapping(value = "save")
     * 
     * @ResponseBody public Object save(HttpServletRequest request) throws
     * Exception {
     * 
     * // System.out.println(RequestUtil.isAjaxRequest(request)); String id =
     * request.getParameter("id"); String roleName =
     * request.getParameter("roleName"); String remarks =
     * request.getParameter("remarks"); String permissions =
     * request.getParameter("permissions"); HashMap map = new HashMap();
     * ResultDTO resultDTO = auth.Service.saveRoleAndPermission(id, roleName,
     * remarks, permissions); // 查找所有的角色 return resultDTO; }
     */
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
    // @RequiresPermissions(value={"auth:add","auth:edit","auth:view","auth:del"},logical=Logical.OR)
    @RequestMapping(value = "/role/list.json")
    @ResponseBody
    public Object list(@RequestParam(value = "curPage", required = false) Integer curPage, @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        // 查找所有的角色
        Page page = new Page();
        page.setCurPage(curPage);
        page.setPageSize(pageSize);
        page.setPagination(true);
        List<Role> roles = authService.listRole(page);
        return ResultUtil.getResult(roles, page);
    }
    
    

    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
//    @RequiresPermissions("auth:edit")
    @RequestMapping(value = "/role/edit.htm")
    public Object edit( HttpServletRequest request) {
//        request.setAttribute("roleid", id);
        // 查找所有的角色
        return "/static/html/auth/editRole.html";
    }
    @RequestMapping(value = "/role/view.htm")
    public Object viewPage( HttpServletRequest request) {
//        request.setAttribute("roleid", id);
        // 查找所有的角色
        return "/static/html/viewRole.html";
    }
    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
//    @RequiresPermissions(value = { "auth:edit", "auth:view" }, logical = Logical.OR)
    @RequestMapping(value = "/role/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        ResultDTO result = authService.getRoleAndPermissions(id);
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
    @RequestMapping(value = "/role/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {

        String id = request.getParameter("id");
        String roleName = request.getParameter("name");
        String remark = request.getParameter("remark");
        String permissions = request.getParameter("permissions");

        return authService.saveRoleAndPermission(id, roleName, remark, permissions);
    }

    /**
     * 说明:列出所有permission
     * 
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年12月21日下午5:31:22
     */
    @RequestMapping(value = "/permission/list.json")
    @ResponseBody
    public Object listAllPermissions() {
        HashMap map = new HashMap();
        return authService.listPermissions(map);
    }
    /**
     * 说明:保存permission资料
     * @param request
     * @return
     * @return Object
     * @author dozen.zhang
     * @date 2015年12月21日下午5:33:20
     */
    @RequestMapping(value = "/permission/save.json")
    @ResponseBody
    public Object savePermission(HttpServletRequest request){
        String id=request.getParameter("id");
        String code=request.getParameter("code");
        String name=request.getParameter("name");
        String pid=request.getParameter("pid");
        Permission permission=new Permission();
        permission.setId(id);
        permission.setCode(code);
        permission.setName(name);
        permission.setPid(pid);
       return  authService.savePermission(permission);
    }
    
    @RequestMapping(value = "/permission/del.json")
    @ResponseBody
    public Object delPermission(HttpServletRequest request){
        String id=request.getParameter("id");
        return  authService.deletePermission(id);
    }
    
    @RequestMapping(value = "/role/del.json")
    @ResponseBody
    public Object delRole(HttpServletRequest request){
        String id=request.getParameter("id");
        return  authService.deleteRoleById(id);
    }
    /**
     * @return Object
     */
    // @RequiresPermissions("auth:add")
/*    @RequestMapping(value = "/role/edit.htm")
    public Object add() {
        // 查找所有的角色
        return "/static/html/auth/editRole.html";
    }*/
}
