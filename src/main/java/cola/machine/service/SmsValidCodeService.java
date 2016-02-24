package cola.machine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cola.machine.util.CacheUtil;
import cola.machine.util.HttpRequestUtil;
import cola.machine.util.PropertiesUtil;
import cola.machine.util.RandomValidateCode;
import cola.machine.util.ResultUtil;
import cola.machine.util.SmsUtil;
import cola.machine.util.StringUtil;
import core.action.ResultDTO;

@Service("smsValidCodeService")
public class SmsValidCodeService {
    /** 
     *日志类
     */
    private static final Logger log = LoggerFactory.getLogger(SmsValidCodeService.class);

    /**
     * @param systemCode
     * @param phone
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getSmsValidCode(String systemCode, String phone) {
        // 验证systemcode
        // TODO 验证systemcode
        ResultDTO result = validSystemNo(systemCode);
        if (!result.isRight()) {
            return result;
        }

        // 验证phone
        if (StringUtil.isBlank(phone)) {
            return ResultUtil.getResult(302, "手机号不能为空");
        }
        if (!StringUtil.isPhone(phone)) {
            return ResultUtil.getResult(302, "手机号格式错误");
        }
        // 取缓存中业务+手机号 的value
        String mapValue = (String) CacheUtil.getInstance().readCache(systemCode + phone, String.class);
        // 验证指定时间内只能发送一次

        Long nowTime = new Date().getTime();
        if (mapValue != null) {
            String[] mapValueAry = mapValue.split("-");

            // 验证码生存周期
            try {
                String liveTime = PropertiesUtil.get("validcode.live.time");
                if (nowTime < (Long.valueOf(mapValueAry[1]) + Integer.valueOf(liveTime))) {
                    // 超时
                    return ResultUtil.getResult(100, "重复获取验证码过快");
                }
            } catch (Exception e) {
                log.error("validcode.live.time");
                return ResultUtil.getResult(100, "验证码生存周期配置有误");
            }
        }

        // 发送验证码
        Random random = new Random();
        int vcLen = 0;
        try {
            String lenStr = PropertiesUtil.get("validcode.length");
            vcLen = Integer.valueOf(lenStr);
        } catch (Exception e) {
            log.error("validcode.length is not number");
            return ResultUtil.getResult(100, "验证码长度配置有误");
        }
        int validCode = random.nextInt(9999);
        int a = validCode;
        int count = 0;
        while (a != 0) {
            count++;
            a /= 10;
        }
        String finalCode = "";
        for (int i = 0; i < (vcLen - count); i++) {
            finalCode += "0";
        }
        finalCode += validCode;

        // 返回验证码
        HashMap map = new HashMap();
        // map.put("code", finalCode);
        SmsUtil smsUtil = new SmsUtil();
        String rc = smsUtil.sendSms(finalCode, phone, (short) 1);
        if ("fail".equals(rc)) {
            return ResultUtil.getResult(302, ResultUtil.fail, "短信发送失败");
        }
        result = ResultUtil.getDataResult(map);
        if (result.isRight()) {
            // 塞入缓存系统
            CacheUtil.getInstance().writeCache(systemCode + phone, finalCode + "-" + nowTime);
        }
        return result;
    }

    /**
     * 验证短信验证码是否正确
     * 
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO smsValidCode(String systemCode, String phone, String code) {
        // 参数验证
        if (StringUtil.isBlank(systemCode) || StringUtil.isBlank(phone) || StringUtil.isBlank(code))
        // 查看该手机号是否是在异常名单当中
        {
            return ResultUtil.getResult(302, "参数不能为空");
        }
        ResultDTO result = validSystemNo(systemCode);
        if (!result.isRight()) {
            return result;
        }
        // 获取缓存中的数据
        // 取缓存中业务+手机号 的value
        String cacheValue = (String) CacheUtil.getInstance().readCache(systemCode + phone, String.class);
        if (cacheValue == null) {
            return ResultUtil.getResult(302, "验证码过期");
        }
        
        // 获取上次访问时间
        String[] mapValueAry = cacheValue.split("-");
        if (mapValueAry.length < 2) {
            return ResultUtil.getResult(302, "缓存格式错误");
        }

        Long timeStamp = Long.valueOf(mapValueAry[1]);
        String realCode = mapValueAry[0];
        // 如果时间间隔少于1秒 进入警告处理流程

        // 验证码是否过期
        Long nowTime = new Date().getTime();
        try {
            if ((timeStamp + Integer.valueOf(PropertiesUtil.get("validcode.live.time"))) < nowTime) {
                return ResultUtil.getResult(302, "验证码过期");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(302, "生存周期配置错误");
        }
        // 验证短信验证码是否相同
        if (code.equals(mapValueAry[0])) {
            return ResultUtil.getSuccResult();
            //销毁该验证码
            
        } else {
            return ResultUtil.getResult(302, "验证码不正确");
        }
    }

    /**
     * 验证系统代号
     * @param systemNo
     * @return
     * @author dozen.zhang
     */
    public ResultDTO validSystemNo(String systemNo) {
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(302, "系统编号有误");
        }
        String systemNoStrs = "";
        try {
            systemNoStrs = PropertiesUtil.get("valid.code.systemno");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return ResultUtil.getResult(302, "systemNo配置信息异常");
        }
        if (systemNoStrs.indexOf(systemNo) < 0) {
            return ResultUtil.getResult(302, "系统编号有误");
        }
        return ResultUtil.getSuccResult();
    }

    /**
     * 获取图片验证码
     * 
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getPicValidCode(String systemCode, String sessionid) {
        // 验证systemcode
        ResultDTO result = validSystemNo(systemCode);
        if (!result.isRight()) {
            return result;
        }

        Long nowTime = new Date().getTime();

        // 验证单独id
        if (StringUtil.isBlank(sessionid)) {
            UUID uuid = UUID.randomUUID();
            sessionid = uuid.toString();
        } else {
            // 取缓存中业务+手机号 的value
            String mapValue = (String) CacheUtil.getInstance().readCache(systemCode + sessionid, String.class);
            // 验证指定时间内只能发送一次 防止攻击

            if (mapValue != null) {
                String[] mapValueAry = mapValue.split("-");
                try {
                    if (nowTime > (Long.valueOf(mapValueAry[1])
                            + Integer.valueOf(PropertiesUtil.get("validcode.live.time")))) {
                        // 超时
                        return ResultUtil.getResult(100, "刷新验证码过快" + (nowTime - (Long.valueOf(mapValueAry[1])
                                + Integer.valueOf(PropertiesUtil.get("validcode.live.time")))));
                    }

                    if (nowTime < (Long.valueOf(mapValueAry[1])
                            + Integer.valueOf(PropertiesUtil.get("pic.validcode.time.interval")))) {
                        // 刷新过快
                        return ResultUtil.getResult(100, "刷新验证码过快" + (nowTime - (Long.valueOf(mapValueAry[1])
                                + Integer.valueOf(PropertiesUtil.get("validcode.live.time")))));
                    }
                } catch (Exception e) {
                    log.error("validcode.live.time");
                    return ResultUtil.getResult(100, "验证码生存周期配置有误");
                }

                // 开始清理之前的key
                // 也可以不清理CacheUtil.getInstance().clearCache(systemCode+uuuid);
                // 开始清理图片文件

            }
        }
        // 生成验证码
        String finalCode = "";// = genCode();
        // 发送验证码
        int vcLen = 0;
        try {
            String lenStr = PropertiesUtil.get("validcode.length");
            vcLen = Integer.valueOf(lenStr);
        } catch (Exception e) {
            log.error("validcode.length is not number");
            return ResultUtil.getResult(100, "验证码生存周期配置有误");
        }
        RandomValidateCode rvc = new RandomValidateCode();
        String codeAry[];
        try {
            codeAry = rvc.getImgRandcode(vcLen, systemCode + sessionid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(302, "验证码生成错误");
        }

        finalCode = codeAry[1];
        String fileName = codeAry[0];

        // 返回验证码
        HashMap map = new HashMap();
        // map.put("code", finalCode);
        map.put("img", fileName);
        map.put("sessionid", sessionid);
        result = ResultUtil.getDataResult(map);
        // 如果生成验证码成功
        if (result.isRight()) {
            // 塞入缓存系统
            try {
                CacheUtil.getInstance().writeCache(systemCode + sessionid, finalCode + "-" + nowTime,
                        Integer.valueOf(PropertiesUtil.get("validcode.live.time")) / 1000);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.getResult(302, "缓存失败");
            }
        }
        return result;
    }

    /**
     * 验证图片验证码
     * 
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public Object picValidCode(String systemCode, String phone, String code) {

        return this.smsValidCode(systemCode, phone, code);
    }

    public String genCode() throws Exception {
        Random random = new Random();
        int vcLen = 0;
        try {
            vcLen = Integer.valueOf(PropertiesUtil.get("validcode.length"));
        } catch (Exception e) {
            log.error("validcode.length is not number");
            throw new Exception("验证码长度配置有误");
        }
        int validCode = random.nextInt(9999);
        int a = validCode;
        int count = 0;
        while (a != 0) {
            count++;
            a /= 10;
        }
        String finalCode = "";
        for (int i = 0; i < count; i++) {
            finalCode += "0";
        }
        finalCode += validCode;
        return finalCode;
    }

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(0, 1);
        System.out.println(123);
        ;
    }

    public ResultDTO remoteValidImg(String sessionid, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(sessionid)) {
            return ResultUtil.getResult(ResultUtil.fail, "sessionid不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&sessionid=%s&code=%s", systemNo, sessionid, code);
        String jsonStr = HttpRequestUtil.sendGet("http://127.0.0.1:8080/code/img/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }

    public ResultDTO remoteValidSms(String phone, String code) {
        // 获取systemcode
        if (StringUtil.isBlank(phone)) {
            return ResultUtil.getResult(ResultUtil.fail, "sessionid不能为空");
        }
        if (StringUtil.isBlank(code)) {
            return ResultUtil.getResult(ResultUtil.fail, "验证码不能为空");
        }
        String systemNo;
        try {
            systemNo = PropertiesUtil.get("valid_code_system_no");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置错误");
        }
        if (StringUtil.isBlank(systemNo)) {
            return ResultUtil.getResult(ResultUtil.fail, "系统代号配置不能为空");
        }
        String params = String.format("systemno=%s&phone=%s&code=%s", systemNo, phone, code);
        String jsonStr = HttpRequestUtil.sendGet("http://127.0.0.1:8080/code/sms/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }

}
