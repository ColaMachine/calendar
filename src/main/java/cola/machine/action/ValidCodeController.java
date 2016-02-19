package cola.machine.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cola.machine.service.SmsValidCodeService;
import core.action.ResultDTO;
@Controller
@RequestMapping("/code")
public class ValidCodeController {
    @Autowired
    private SmsValidCodeService smsValidCodeService;
    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/sms/get")
    @ResponseBody
    public Object smsGet(HttpServletRequest request ,HttpServletResponse response){
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemCode");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
             smsValidCodeService.getSmsValidCode(systemCode,phone);
       
        result.setData(null);
        
        
        String callbackParam = request.getParameter("callback");
        try {
            response.getOutputStream().write(new String(callbackParam+"("+JSON.toJSONString(result)+")").getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //将result转成JSON串输出
        return result;
    }
    
    @RequestMapping(value = "/img/get")
    @ResponseBody
    public void imgGet(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String sessionkey =request.getParameter("phone");
        String appid = request.getParameter("appid");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
                smsValidCodeService.getPicValidCode(appid,sessionkey);
          // result.setData(null);
          
           if(result.isRight()){
               Object data=result.getData();
               HashMap map =(HashMap)data;
               map.put("code", "");
               map.remove("code");
               result.setData(map);
           }
           String callbackParam = request.getParameter("callback");
           try {
               response.setHeader("Content-Type", "application/javascript;charset=UTF-8");
               response.getOutputStream().write(new String(callbackParam+"("+JSON.toJSONString(result)+")").getBytes());
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           
    }
    
    
    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/sms/b/get")
    @ResponseBody
    public Object smsBehindGet(HttpServletRequest request){
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemCode");
        //调用生成验证码服务 返回验证码 或者失败原因
        
        ResultDTO result = 
             smsValidCodeService.getSmsValidCode(systemCode,phone);
        
        return result;
    }
    
    @RequestMapping(value = "/img/b/get")
    @ResponseBody
    public Object imgBehindGet(HttpServletRequest request){
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemCode");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
                smsValidCodeService.getPicValidCode(systemCode,phone);
            return result;
    }
    
    @RequestMapping(value = "/validSmsCode.json")
    @ResponseBody
    public Object validCode(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String systemCode = request.getParameter("systemCode");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        return smsValidCodeService.smsValidCode(systemCode, phone, code);
    }
    
}
