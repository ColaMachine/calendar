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
import cola.machine.service.UserService;
import cola.machine.util.ResultUtil;

import com.awifi.core.page.Page;

import core.action.ResultDTO;
@Controller
@RequestMapping("/smsEach")
public class SmsEachController extends BaseController{
    /** 日志 **/
    private Logger logger = LoggerFactory.getLogger(SmsEachController.class);
    /** 权限service **/
    @Autowired
    private SmsEachService smsEachService;
    

    /**
     * 说明: 跳转到角色列表页面
     * 
     * @return
     * @return String
     * @author dozen.zhang
     * @date 2015年11月15日下午12:30:45
     */
    @RequestMapping(value = "/smsEach/list.htm", method = RequestMethod.GET)
    public String list() {
        return "/static/html/smsEach/listSmsEach.html";
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
    @RequestMapping(value = "/smsEach/list.json")
    @ResponseBody
    public Object list(@RequestParam(value = "curPage", required = false) Integer curPage, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        // 查找所有的角色
        Page page = new Page(curPage,pageSize);
        HashMap params =new HashMap();
        params.put("page",page);
        List<SmsEach> smsEachs = smsEachService.list(params);
        return ResultUtil.getResult(smsEachs, page);
    }
    
    

    /**
     * @param id 参数
     * @param request 发请求
     * @return Object
     */
    @RequestMapping(value = "/smsEach/edit.htm")
    public Object edit( HttpServletRequest request) {
        // 查找所有的角色
        return "/static/html/smsEach/editsmsEach.html";
    }
    @RequestMapping(value = "/smsEach/view.htm")
    public Object viewPage( HttpServletRequest request) {
        return "/static/html/smsEach/viewsmsEach.html";
    }
   
      @RequestMapping(value = "/smsEach/view.json")
    @ResponseBody
    public Object view(HttpServletRequest request) {
        String id = request.getParameter("id");
        SmsEach bean = smsEachService.selectByPrimaryKey(id);
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
    @RequestMapping(value = "/smsEach/save.json")
    @ResponseBody
    public Object save(HttpServletRequest request) throws Exception {
        SmsEach smsEach =new  SmsEach();
            String id = request.getParameter("id");
            smsEach.setId(id   ) ;
            String batchId = request.getParameter("batchId");
            smsEach.setBatchId(batchId   ) ;
            String phone = request.getParameter("phone");
            smsEach.setPhone(phone   ) ;
            String sendtime = request.getParameter("sendtime");
            smsEach.setSendtime(sendtime   ) ;
            String status = request.getParameter("status");
            smsEach.setStatus(status   ) ;
            String reason = request.getParameter("reason");
            smsEach.setReason(reason   ) ;
        return smsEachService.save(smsEach);
    }
}
