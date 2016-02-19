package cola.machine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cola.machine.util.CacheUtil;
import cola.machine.util.PropertiesUtil;
import cola.machine.util.RandomValidateCode;
import cola.machine.util.ResultUtil;
import cola.machine.util.StringUtil;
import core.action.ResultDTO;
@Service("smsValidCodeService")
public class SmsValidCodeService {
    private static final Logger log = LoggerFactory.getLogger(SmsValidCodeService.class);
    /**
     * @param systemCode
     * @param phone
     * @return
     * @author dozen.zhang
     */
    public  ResultDTO getSmsValidCode(String systemCode, String phone) {
        //验证systemcode
        //TODO 验证systemcode
        //验证phone
        if(StringUtil.isPhone(phone)){
            return ResultUtil.getWrongResultFromCfg("err.param.format");
        }
        //取缓存中业务+手机号 的value
        String mapValue = (String)CacheUtil.getInstance().readCache(systemCode+phone, String.class);
        //验证指定时间内只能发送一次
        
        Long nowTime = new Date().getTime();
        if(mapValue!=null){
            String[] mapValueAry = mapValue.split("-");
            
            //验证码生存周期
            try {
                if(nowTime<(Integer.valueOf(mapValueAry[1])+Integer.valueOf(PropertiesUtil.get("validcode.live.time")))){
                    //超时
                    return ResultUtil.getResult(100,"1分钟内不能重复获取验证码");
                }
            } catch (Exception e) {
                log.error("validcode.live.time");
                return ResultUtil.getResult(100,"验证码生存周期配置有误");
            }
        }
       
       
        //发送验证码
        Random random =new Random();
        int vcLen=0;
        try {
            vcLen=Integer.valueOf(PropertiesUtil.get("validcode.length"));
        } catch (Exception e) {
            log.error("validcode.length is not number");
            return ResultUtil.getResult(100,"验证码生存周期配置有误");
        }
        int validCode = random.nextInt(9999);
        int a= validCode;
        int count=0;
        while (a != 0)
        {
            count++;
            a /= 10;
        }
        String finalCode= "";
        for(int i=0;i<count;i++){
            finalCode+= "0";
        }
        finalCode+=validCode;
      
        //返回验证码
        HashMap map =new HashMap();
        //map.put("code", finalCode);
        ResultDTO result =  ResultUtil.getDataResult(map);
        if(result.isRight()){
            //塞入缓存系统
            CacheUtil.getInstance().writeCache(systemCode+phone,finalCode+"-"+nowTime );
        }
        return result;
    }
    
    /**
     * 验证短信验证码是否正确
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO smsValidCode(String systemCode,String phone,String code){
        //查看该手机号是否是在异常名单当中
        
        //获取缓存中的数据
        //取缓存中业务+手机号 的value
        String value = (String)CacheUtil.getInstance().readCache(systemCode+phone, String.class);
        if(value==null){
            return ResultUtil.getResult(302, "不存在验证码");
        }
        String mapValue= String.valueOf(value);
        if(StringUtil.isBlank(mapValue)){
            return ResultUtil.getResult(302, "原始验证码为空");
        }
        //获取上次访问时间
        String[] mapValueAry = mapValue.split("-");
        if(mapValueAry.length<2){
            return ResultUtil.getResult(302, "缓存格式错误");
        }
        
        Long timeStamp = Long.valueOf(mapValueAry[1]);
        String realCode = mapValueAry[0];
        //如果时间间隔少于1秒 进入警告处理流程
        
        //验证码是否过期
        Long nowTime =new Date().getTime();
        if((timeStamp+60000)<nowTime){
            return ResultUtil.getResult(302, "验证码格式错误");
        }
        //验证短信验证码是否相同
        if(code.equals(mapValueAry[0])){
            return ResultUtil.getSuccResult();
        }else{
            return ResultUtil.getResult(302, "验证码不正确");
        }
    }

    /**
     * 获取图片验证码
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getPicValidCode(String systemCode, String phone) {
        //验证systemcode
        
        //TODO 验证systemcode
        
        //验证phone
        if(StringUtil.isPhone(phone)){
            return ResultUtil.getWrongResultFromCfg("err.param.format");
        }
        //取缓存中业务+手机号 的value
        String mapValue = (String)CacheUtil.getInstance().readCache(systemCode+phone, String.class);
        //验证指定时间内只能发送一次
        Long nowTime = new Date().getTime();
        if(mapValue!=null){
            String[] mapValueAry = mapValue.split("-");
            
            //验证码生存周期
            try {
                if(nowTime<(Integer.valueOf(mapValueAry[1])+Integer.valueOf(PropertiesUtil.get("validcode.live.time")))){
                    //超时
                    return ResultUtil.getResult(100,"1分钟内不能重复获取验证码");
                }
            } catch (Exception e) {
                log.error("validcode.live.time");
                return ResultUtil.getResult(100,"验证码生存周期配置有误");
            }
        }
        //生成验证码
         String finalCode="";// = genCode();
        //发送验证码

        RandomValidateCode rvc =new RandomValidateCode();
        String codeAry[];
        try {
            codeAry=rvc.getRandcode();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(302, "验证码生成错误");
        } 
        
        finalCode= codeAry[1];
        String fileName = codeAry[0];
       
        //返回验证码
        HashMap map =new HashMap();
        //map.put("code", finalCode);
        map.put("img", fileName);
        ResultDTO result =  ResultUtil.getDataResult(map);
        //如果生成验证码成功
        if(result.isRight()){
            //塞入缓存系统
            CacheUtil.getInstance().writeCache(systemCode+phone,finalCode+"-"+nowTime );
        }
        return result;
    }

    /**
     * 验证图片验证码
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public Object picValidCode(String systemCode, String phone, String code) {
        
        return this.smsValidCode(systemCode, phone,code);
    }
    public String genCode() throws Exception{
        Random random =new Random();
        int vcLen=0;
        try {
            vcLen=Integer.valueOf(PropertiesUtil.get("validcode.length"));
        } catch (Exception e) {
            log.error("validcode.length is not number");
            throw new Exception("验证码生存周期配置有误");
        }
        int validCode = random.nextInt(9999);
        int a= validCode;
        int count=0;
        while (a != 0)
        {
            count++;
            a /= 10;
        }
        String finalCode= "";
        for(int i=0;i<count;i++){
            finalCode+= "0";
        }
        finalCode+=validCode;
        return finalCode;
    }
    public static void main(String[] args) {
        ArrayList list =new ArrayList();
        list.add(0, 1);
        System.out.println(123);;
    }

}
