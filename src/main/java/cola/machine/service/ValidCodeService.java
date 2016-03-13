package cola.machine.service;

import java.util.*;

import cola.machine.bean.SmsHistory;
import cola.machine.bean.SmsValidCode;
import cola.machine.common.msgbox.ErrorMsg;
import cola.machine.config.Config;
import cola.machine.config.SystemValidCodeConfig;
import cola.machine.config.ValidCodeConfig;
import cola.machine.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import core.action.ResultDTO;

@Service("validCodeService")
public class ValidCodeService {

    /**
     * 日志类
     */
    private static final Logger log = LoggerFactory.getLogger(ValidCodeService.class);

    /**
     * @param systemCode 系统名称
     * @param phone 手机号码
     * @return ResutDTO{r:xx,msg:xxx,data:{code:xxx}}
     * @author dozen.zhang
     */
    public ResultDTO getSmsValidCode(String systemCode, String phone) {
        int methodCode = 1;
        try {
            // 验证systemcode
            ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
            SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
            ResultDTO result;
            /*
             * ResultDTO result = validSystemNo(systemCode); if
             * (!result.isRight()) { return result; }
             */
            if (systemConfig == null) {
                return ResultUtil.getResult(methodCode, ErrorMsg.PARAM_ERROR, 301, "系统代号不正确");
            }
            // 验证phone
            if (StringUtil.isBlank(phone)) {
                return ResultUtil.getResult(methodCode, ErrorMsg.PARAM_ERROR, 302, "手机号不能为空");
            }
            if (!StringUtil.isPhone(phone)) {
                return ResultUtil.getResult(methodCode, ErrorMsg.PARAM_ERROR, 303, "手机号格式错误");
            }
            // 取缓存中业务+手机号 的value
            // String mapValue = (String)
            // CacheUtil.getInstance().readCache(systemCode + phone,
            // String.class);
            String json = RedisUtil.get(systemCode + phone);
            SmsHistory history = JSON.parseObject(json, SmsHistory.class);

            // 验证指定时间内只能发送一次
            Long nowTime = new Date().getTime();
            if (history != null) {
                // String[] mapValueAry = mapValue.split("-");
                // 验证码生存周期
                if (nowTime > (history.getLast() + Integer.valueOf(systemConfig.getSmsLiveTime()))) {
                    // 超时
                    // 应该把缓存中的清理掉
                    //CacheUtil.getInstance().clearCache(systemCode + phone);
                    /*
                     * return ResultUtil.getResult(100, "验证码已过期" + (nowTime -
                     * (Long.valueOf(mapValueAry[1]) +
                     * Integer.valueOf(PropertiesUtil
                     * .get("validcode.live.time")))));
                     */
                }
                //验证是否刷新过快
                if (nowTime < (history.getLast() + Integer.valueOf(systemConfig.getSmsRefreshTime()))) {
                    return ResultUtil.getResult(methodCode, ErrorMsg.INFO, 304, "验证码刷新过快");
                }
                //验证是否在指定时间
                if (!handleHistorys(history, systemConfig.getMaxRequestTime(), systemConfig.getRangeTime())) {
                    return ResultUtil.getResult(methodCode, ErrorMsg.INFO, 305, "发送次数过多");
                }
            } else {
                history = new SmsHistory();
            }
            // 发送验证码
            Random random = new Random();
            int vcLen = systemConfig.getSmsLength();
            String finalCode = "";
            if (systemConfig.getSmsCharType() == 1) {
                finalCode = StringUtil.getRandomDigitString(vcLen);
            } else {
                finalCode = StringUtil.getRandomAlphaString(vcLen);
            }
            // 返回验证码
            HashMap map = new HashMap();
            map.put("code", finalCode);
            SmsUtil smsUtil = new SmsUtil();
            String rc = smsUtil.sendSms(finalCode, phone, (short) 1);
            SmsValidCode validCode =new SmsValidCode();
            validCode.setPhone(phone);
            if ("fail".equals(rc)) {
                return ResultUtil.getResult(methodCode, ErrorMsg.THIRD_PART_ERROR, 307, "短信发送失败");
            }
            result = ResultUtil.getDataResult(map);
            if (result.isRight()) {
                // 塞入缓存系统
                history.getTimes().offer(nowTime);
                history.setLast(nowTime);
                String insertJson = JSON.toJSONString(history);
                RedisUtil.set(systemCode + phone, insertJson);
                // CacheUtil.getInstance().writeCache(systemCode + phone,
                // finalCode + "-" +
                // nowTime,Integer.valueOf(systemConfig.getSmsLiveTime()));
            }
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.getResult(methodCode, ErrorMsg.UNKNOW, 308, "系统繁忙");
        }
    }

    public boolean handleHistorys(SmsHistory s, int times, Long longtime) {
        LinkedList list = s.getTimes();
        Long now = new Date().getTime();
        while (list.peek() != null && (Long) list.peek() < (now - longtime)) {
            list.poll();
        }
        if (list.size() > times) {
            return false;
        }
        return true;
    }

    public ResultDTO smsValidCode(String systemCode, String phone, String code) {
        return validCode(systemCode, phone, code, true);
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
    public ResultDTO validCode(String systemCode, String phone, String code, boolean isSms) {
        // 参数验证
        if (StringUtil.isBlank(systemCode) || StringUtil.isBlank(phone) || StringUtil.isBlank(code))
        // 查看该手机号是否是在异常名单当中
        {
            return ResultUtil.getResult(302, "参数不能为空");
        }

        if (isSms && !StringUtil.isPhone(phone)) {
            return ResultUtil.getResult(302, "手机号码错误");
        }

        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        if (systemConfig == null) {
            return ResultUtil.getResult(302, "业务代码错误");
        }

        int len = systemConfig.getSmsLength();
        int type = systemConfig.getSmsCharType();
        int liveTime = systemConfig.getSmsLiveTime();
        if (!isSms) {
            len = systemConfig.getImgLength();
            type = systemConfig.getImgCharType();
            liveTime = systemConfig.getImgLiveTime();
        }
        // 验证长短
        if (code.length() != len) {
            return ResultUtil.getResult(302, "验证码错误");
        }
        // 验证字符
        if (type == 1 && !StringUtil.checkNumeric(code)) {
            return ResultUtil.getResult(302, "验证码错误");
        } else if (type == 2 && !StringUtil.checkAlphaNumeric(code)) {
            return ResultUtil.getResult(302, "验证码错误");
        }

        // 获取缓存中的数据
        // 取缓存中业务+手机号 的value
        String cacheValue = (String) CacheUtil.getInstance().readCache(systemCode + phone, String.class);
        if (cacheValue == null) {
            return ResultUtil.getResult(302, "验证码错误");
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
            if ((timeStamp + liveTime) < nowTime) {
                return ResultUtil.getResult(302, "验证码错误");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtil.getResult(302, "生存周期配置错误");
        }
        // 验证短信验证码是否相同
        if (code.equals(mapValueAry[0])) {
            CacheUtil.getInstance().clearCache(systemCode + phone);
            return ResultUtil.getSuccResult();
            // 销毁该验证码
        } else {
            return ResultUtil.getResult(302, "验证码不正确");
        }
    }

    /**
     * 验证系统代号
     * 
     * @param systemNo
     * @return
     * @author dozen.zhang
     */
    /*
     * public ResultDTO validSystemNo(String systemNo) { if
     * (StringUtil.isBlank(systemNo)) { return ResultUtil.getResult(302,
     * "系统编号有误"); } String systemNoStrs = ""; try { systemNoStrs =
     * PropertiesUtil.get("valid.code.systemno"); } catch (Exception e1) { //
     * TODO Auto-generated catch block e1.printStackTrace(); return
     * ResultUtil.getResult(302, "systemNo配置信息异常"); } if
     * (systemNoStrs.indexOf(systemNo) < 0) { return ResultUtil.getResult(302,
     * "系统编号有误"); } return ResultUtil.getSuccResult(); }
     */

    /**
     * 获取图片验证码
     *
     * @param systemCode
     * @param phone
     * @param code
     * @return
     * @author dozen.zhang
     */
    public ResultDTO getImgValidCode(String systemCode, String sessionid) {
        ValidCodeConfig defaultConfig = Config.getInstance().getValidCode();
        SystemValidCodeConfig systemConfig = defaultConfig.getSystems().get(systemCode);
        // 验证systemcode
        ResultDTO result;
        /*
         * ResultDTO result = validSystemNo(systemCode); if (!result.isRight())
         * { return result; }
         */
        if (systemConfig == null) {
            return ResultUtil.getResult(302, "系统代号不正确");
        }

        // ResultDTO result = validSystemNo(systemCode);
        // if (!result.isRight()) {
        // return result;
        // }

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
                    if (nowTime > (Long.valueOf(mapValueAry[1]) + Integer.valueOf(systemConfig.getImgLiveTime()))) {
                        // 超时
                        // 应该把缓存中的清理掉
                        CacheUtil.getInstance().clearCache(systemCode + sessionid);
                        // return ResultUtil.getResult(100, "验证码失效"
                        // );//+(nowTime - (Long.valueOf(mapValueAry[1]) +
                        // Integer.valueOf(PropertiesUtil.get("validcode.live.time"))))
                    }

                    if (nowTime < (Long.valueOf(mapValueAry[1]) + Integer.valueOf(systemConfig.getImgRefreshTime()))) {
                        // 刷新过快
                        return ResultUtil.getResult(100, "刷新验证码过快");
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
        int vcLen = systemConfig.getImgLength();
        String finalCode = "";
        if (systemConfig.getImgCharType() == 1) {
            finalCode = StringUtil.getRandomDigitString(vcLen);
        } else {
            finalCode = StringUtil.getRandomAlphaString(vcLen);
        }
        // 发送验证码

        RandomValidateCode rvc = new RandomValidateCode();
        String codeAry[];
        try {
            codeAry = rvc.getImgRandcode(finalCode, systemCode + sessionid);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getResult(302, "验证码生成错误");
        }

        finalCode = codeAry[1];
        String fileName = codeAry[0];
        log.info(finalCode);
        // 返回验证码
        HashMap map = new HashMap();
        // map.put("code", finalCode);
        map.put("img", fileName);
        map.put("sessionid", sessionid);
        map.put("imgdata", codeAry[2]);
        result = ResultUtil.getDataResult(map);
        // 如果生成验证码成功
        if (result.isRight()) {
            // 塞入缓存系统
            try {
                CacheUtil.getInstance().writeCache(systemCode + sessionid, finalCode + "-" + nowTime, systemConfig.getImgLiveTime() / 1000);
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
    public ResultDTO imgValidCode(String systemCode, String phone, String code) {

        return this.validCode(systemCode, phone, code, false);
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
        String jsonStr = HttpRequestUtil.sendGet("http://127.0.0.1:8080/code/img/b/valid.json", params);
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
        String jsonStr = HttpRequestUtil.sendGet("http://127.0.0.1:8080/code/sms/b/valid.json", params);
        ResultDTO result = JSON.parseObject(jsonStr, ResultDTO.class);
        return result;
        // TODO Auto-generated method stub
    }

}
