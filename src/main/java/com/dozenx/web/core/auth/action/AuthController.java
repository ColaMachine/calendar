/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.action;

import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.service.UserService;
import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.log.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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

    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping(value = "/menu/list.json")
    @ResponseBody
    public ResultDTO listMenu(HttpServletRequest request){
        String id=request.getParameter("id");
        SysUser user = (SysUser)request.getSession().getAttribute("user");
        return  ResultUtil.getDataResult(authService.listMenusByUserid(user.getId()));
    }


}
