/**
 * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年11月15日
 * 文件说明: 
 */
package com.dozenx.web.core.auth.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.MapUtils;
import com.dozenx.util.RedisUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.auth.service.UserService;
import com.dozenx.web.core.auth.sysMenu.bean.SysMenu;
import com.dozenx.web.core.auth.sysMenu.service.SysMenuService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.auth.validcode.service.ValidCodeService;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.util.TerminalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@APIs(description = "认证模块")
@Controller
@RequestMapping("/sys/auth")
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

    @Autowired
    ValidCodeService validCodeService;





}
