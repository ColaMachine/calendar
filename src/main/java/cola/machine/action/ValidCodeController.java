package cola.machine.action;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cola.machine.service.ValidCodeService;
import core.action.ResultDTO;
@Controller
@RequestMapping("/code")
public class ValidCodeController {
    @Autowired
    private ValidCodeService validCodeService;
    /**
     * 第三方系统前端请求获取短信验证码
     * @param request
     * @return
     * @author dozen.zhang
     */
    @RequestMapping(value = "/sms/get.json")
    @ResponseBody
    public JSONPObject smsGet(HttpServletRequest request ,HttpServletResponse response){
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemno");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
             validCodeService.getSmsValidCode(systemCode,phone);
       
        result.setData(null);
        
        
        String callbackParam = request.getParameter("callback");
       /* try {
            response.setHeader("Content-Type", "application/javascript;charset=UTF-8");
            String jsonp= new String(callbackParam+"("+JSON.toJSONString(result)+")))))");
            response.getOutputStream().write(jsonp.getBytes("UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //将result转成JSON串输出
       // return result;
        JSONPObject object =new JSONPObject(callbackParam,result);
        return object; 
    }
    
    @RequestMapping(value = "/img/get.json")
    @ResponseBody
    public void imgGet(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String systemno =request.getParameter("systemno");
        String sessionid = request.getParameter("sessionid");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
                validCodeService.getImgValidCode(systemno,sessionid);
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
               response.getOutputStream().write(new String(callbackParam+"("+JSON.toJSONString(result)+")").getBytes("UTF-8"));
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
    @RequestMapping(value = "/sms/b/get.json")
    @ResponseBody
    public Object smsBehindGet(HttpServletRequest request){
        String timeStamp = request.getParameter("timeStamp");
        String appId =request.getParameter("appid");
        String phone =request.getParameter("phone");
        String systemCode = request.getParameter("systemCode");
        //调用生成验证码服务 返回验证码 或者失败原因
        
        ResultDTO result = 
             validCodeService.getSmsValidCode(systemCode,phone);
        
        return result;
    }
    
    @RequestMapping(value = "/img/b/get.json")
    @ResponseBody
    public Object imgBehindGet(HttpServletRequest request){
        String systemno =request.getParameter("systemno");
        String key = request.getParameter("key");
        //调用生成验证码服务 返回验证码 或者失败原因
        ResultDTO result = 
                validCodeService.getImgValidCode(systemno,key);
            return result;
    }
    
    @RequestMapping(value = "/sms/valid.json")
    @ResponseBody
    public Object smsValidCode(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String systemno = request.getParameter("systemno");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        return validCodeService.smsValidCode(systemno, phone, code);
    }
    @RequestMapping(value = "/img/valid.json")
    @ResponseBody
    public Object imgValidCode(HttpServletRequest request){
        String systemno = request.getParameter("systemno");
        String sessionid = request.getParameter("sessionid");
        String code = request.getParameter("code");
        //调用生成验证码服务 返回验证码 或者失败原因
        return validCodeService.imgValidCode(systemno, sessionid, code);
    }
}
