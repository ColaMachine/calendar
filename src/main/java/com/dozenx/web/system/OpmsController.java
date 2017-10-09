package com.dozenx.web.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by dozen.zhang on 2016/10/14.
 */
@Controller
@RequestMapping("/opms")
public class OpmsController {
    @RequestMapping(value = "/*")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response) {
        //request.getRequestDispatcher("")
        return "/static/html/SysRoleList.html";
    }
}
