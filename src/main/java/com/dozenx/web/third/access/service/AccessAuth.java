package com.dozenx.web.third.access.service;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.*;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.module.timing.service.UserCutoffWrapService;
import com.dozenx.web.third.access.bean.AuthResult;
import com.dozenx.web.third.access.bean.DevParms;
import com.dozenx.web.third.access.bean.Kickuseroffline;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * . 接入认证
 *
 */
@Service
public class AccessAuth {
    
    org.slf4j.Logger redisLog = LoggerFactory.getLogger("redis");
    ServiceCode serviceCode  = ServiceCode.ACCESSAUTH_SERVICE;
    /** * 日志打印 . */
    private static Logger logger = LoggerFactory.getLogger(AccessAuth.class);
    /** * 引入CenterPubAuth . */
    @Resource
    private UserCutoffWrapService userCutoffWrapService;
    /** * 引入UserWifiTime . */
    @Resource
    private TimeBusService userWifiTime;
    /** * access.auth.url .接入认证接口 */
    @Value("${access.account.auth.url:}")
    private String accessAccountAuthUrl;
    /** * access.kickuseroffline.url .接入放行接口 */
    @Value("${access.kickuseroffline.url:}")
    private String accesskickuserofflineUrl;
    @Resource
    private LogUtilService LogUtil;
    
    public static void main(String args[]){
        System.out.println(new java.util.Date().getTime());

        System.out.println(DateUtil.formatToString(new java.util.Date(1492116322l*1000),"yyyy-MM-dd HH:mm:ss"));
        StringBuffer param = new StringBuffer();
        String userName = "13958173965";
        String version="1.0.7";
        String globalKey ="mspauth" ;
        String devId="FatAP_31_20160405b52922b8-5559-4785-886a-c7cdd490b585";
        String usermac ="B4B676787CBA";
        param.append("username=").append(userName).append("&version=")
        .append(version)
        .append("&globalKey=")
        .append(globalKey)
        .append("&globalValue=").append(userName).append("&password=").append("123456").append("&deviceId=")
        .append(devId).append("&terMac=").append(usermac).append("&callback=").append("dadfasdf");
        System.out.println("http://auth.51awifi.com/auth/accountauth.htm?"+param.toString());
        //String httpresult = HttpRequestUtil.sendGet("http://auth.51awifi.com/auth/accountauth.htm", param.toString(), request);
        
    }


    /**
     * . 调用接入的全局认证接口 踢掉通账户用户 更新redis 信息 密码已加密
     * 
    * sessionDTO
     * @return String
     * @author xhb
     */
    public Map<String, Object> accessAccountAuth4MD5(SessionDTO sessionDTO) {
        SessionUser seesionUser =sessionDTO.getSessionUser();
        String username = seesionUser.getPhone();
        String password = seesionUser.getAuthPswd();
        String deviceId = sessionDTO.getDeviceId();
        String terMac =sessionDTO.getUserMac();
        String callback ="";
        String apMac = sessionDTO.getMerchant().getMacAddr();
        String ip = sessionDTO.getRequestIp();
        String port =sessionDTO.getRequestIpPort();
        Long merchantId = sessionDTO.getMerchant().getMerchantId();
        Long userId = sessionDTO.getSessionUser().getId();

        Jedis jedis = null;
        StringBuffer param = new StringBuffer();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            jedis = RedisUtil.getResource();
            param.append("username=").append(username).append("&version=")
                    .append(ConfigUtil.getConfig( "access.account.parm.version"))
                    .append("&globalKey=")
                    .append(ConfigUtil.getConfig("access.account.parm.globalKey"))
                    .append("&globalValue=")
                    .append(ConfigUtil.getConfig( "access.account.parm.globalValue"))
                    .append("&password=").append(password).append("&deviceId=").append(deviceId).append("&terMac=")
                    .append(terMac).append("&callback=").append(callback);
            LogUtil.track(serviceCode, 320, ConfigUtil.getConfig("access.account.auth.url")+" param:"+param.toString(),
                    "send request to access.account.auth.url start:", "");
            /*
             * String httpresult = HttpRequestUtil.sendGet(PropertiesOperateUtil
             * .GetConfig("config", "access.account.auth.url"), param
             * .toString(), request);
             */
            String httpresult = HttpRequestUtil.sendGet(ConfigUtil.getConfig("access.account.auth.url"), param.toString(), ip,port);
            LogUtil.track(serviceCode, 321, param.toString(),
                    "send request to access.account.auth.url return msg:" + httpresult, "");
            
         /*   HashMap redisMap =(HashMap)request.getSession().getAttribute("redisMap");   */
           
            // 成功返回
            if (!httpresult.equals("400")) {
                // 有返回值
                result.put("auth", httpresult);
                // 用户踢下线

                int startIndex = httpresult.indexOf("{");
                httpresult = (String) httpresult.substring(startIndex);
                AuthResult connectMer = JSON.parseObject(httpresult, AuthResult.class);
                sessionDTO.setAuthResult(connectMer);
             /*   redisMap.put("resultCode", connectMer.getResultCode());
                logger.info("正在日志请求"+JSON.toJSONString(redisMap));
                redisLog.info(JSON.toJSONString(redisMap));*/
                if(ConfigUtil.getConfig("test").equals("true")){
                    connectMer.setResultCode("0");
                }
                if (connectMer.getResultCode().equals("0")) {
                    // 踢掉用户其他设备上网认证
                    DevParms oldDevParam = new DevParms();
                    String redisParms = jedis.hget(Constants.REDIS_TEL_TO_APMACTERMACDEVID, username);
                    if (StringUtil.isNotBlank(redisParms)) {
                        oldDevParam = JSON.parseObject(jedis.hget(Constants.REDIS_TEL_TO_APMACTERMACDEVID, username), DevParms.class);
                    }
                    // 是否需要T用户下线

                    if (!((apMac.equals(oldDevParam.getApMac())) && (terMac.equals(oldDevParam.getTerMac()))
                            && (username.equals(oldDevParam.getUsername())))) {
                        logger.info("判断需要踢下线");
                        result.put("kickoff", 1);// 页面端增加提醒

                        logger.info("判断需要踢下线2:" + result.get("kickoff"));
                        kickuseroffline(oldDevParam.getDeviceId(), oldDevParam.getApMac(), oldDevParam.getTerMac(), username);
                    }
                    // 跟新用户的mac信息到redis
                    DevParms newDevParms = new DevParms();
                    newDevParms.setUsername(username);
                    newDevParms.setApMac(apMac);
                    newDevParms.setDeviceId(deviceId);
                    newDevParms.setTerMac(terMac);
                    jedis.hset(Constants.REDIS_TEL_TO_APMACTERMACDEVID, username, JSON.toJSONString(newDevParms));
                    // 过期时间设置
                    if(sessionDTO.getTimeInfo().isVip()){

                    }else {

                        Long outtime = sessionDTO.getTimeInfo().getEndTime();

                        if (StringUtil.isNotBlank(redisParms)) {
                            jedis.zrem(Constants.REDIS_TIME_TO_DEVPARAM, redisParms);
                        }
                        jedis.zadd(Constants.REDIS_TIME_TO_DEVPARAM, (outtime), JSON.toJSONString(newDevParms));

                    }

                } else {
                    logger.error("access.auth.accountauth.return: " + httpresult);
                    LogUtil.track(serviceCode, 321, param.toString(),
                            "access.auth.accountauth.return: " + httpresult, "");
                }
            } else {
                LogUtil.track(serviceCode, 321, param.toString(),
                        "access.auth.accountauth.catch: " + param.toString(), "");
                logger.error("access.auth.accountauth.catch: " + param.toString());
            }
        } catch (Exception e) {
            LogUtil.track(serviceCode, 321, param.toString(),
                    "access.account.auth4.MD5.catch" + e.getMessage(), "");
            logger.error("access.auth.accountauth.catch: ", e);
        } finally {
            RedisUtil.returnResource(jedis);
        }
        LogUtil.track(serviceCode, 322, JSON.toJSONString(result),
                "send request to access.account.auth.url end:", "");
        return result;
    }
    
     

    /**
     * 临时放通 调用接入的全局认证接口 密码已加密
     * 
     * @return String
     * @author xhb
     */
    public Map<String, Object> accessAccountAuth4MD5Temporary(SessionDTO sessionDTO) {

        SessionUser seesionUser =sessionDTO.getSessionUser();
        String username = seesionUser.getPhone();
        String password = seesionUser.getAuthPswd();
        String deviceId = sessionDTO.getDeviceId();
        String terMac =sessionDTO.getUserMac();
        String callback ="";
        String apMac = sessionDTO.getMerchant().getMacAddr();
        String ip = sessionDTO.getRequestIp();
        String port =sessionDTO.getRequestIpPort();

        Jedis jedis = null;
        StringBuffer param = new StringBuffer();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            jedis = RedisUtil.getResource();
            if (jedis == null) {
                LogUtil.error(serviceCode, 405, param.toString(),
                        "jedis is null", "");
            }
            param.append("username=").append(username).append("&version=")
                    .append(ConfigUtil.getConfig( "access.account.parm.version"))
                    .append("&globalKey=")
                    .append(ConfigUtil.getConfig("access.account.parm.globalKey"))
                    .append("&globalValue=")
                    .append(ConfigUtil.getConfig( "access.account.parm.globalValue"))
                    .append("&password=").append(password).append("&deviceId=").append(deviceId).append("&terMac=")
                    .append(terMac).append("&callback=").append(callback);
           // logger.info("param.toString():  " + param.toString());
            LogUtil.track(serviceCode, 300, param.toString(),
                    "send request to access.account.auth.url start:", "");
            /*
             * String httpresult = HttpRequestUtil.sendGet(PropertiesOperateUtil
             * .GetConfig("config", "access.account.auth.url"), param
             * .toString(), request);
             */
            String httpresult = HttpRequestUtil.sendGet(accessAccountAuthUrl, param.toString(),ip,port);
            
            //HashMap redisMap =(HashMap)request.getSession().getAttribute("redisMap");   
            
            
            LogUtil.track(serviceCode, 301,
                    param.toString() + "--- " + httpresult, "access.account.auth4.MD5.temporary", "");
            //logger.info("httpret:  " + httpresult);

            if (!httpresult.equals("400")) {
                // 有返回值
                result.put("auth", httpresult);//设备总线返回结果
                httpresult = (String) httpresult.subSequence(9, httpresult.length() - 1);
                AuthResult connectMer = JSON.parseObject(httpresult, AuthResult.class);
                sessionDTO.setAuthResult(connectMer);
                //redisMap.put("resultCode", connectMer.getResultCode());
                //logger.info("正在日志请求"+JSON.toJSONString(redisMap));
                //redisLog.info(JSON.toJSONString(redisMap));
                if (connectMer.getResultCode().equals("0")) {
                  
                    String oldRedisDevParam = jedis.hget(Constants
                            .REDIS_TEL_TO_APMACTERMACDEVID, username);// 以用户为主键的
                    String newRedisDevParam="";
                    // 跟新用户的mac信息到redis
                    //UserTimeChacheService.updatePhoneAndDeviceInfoByTel(username,apMac,deviceId,terMac);
                    DevParms devParms = new DevParms();
                    devParms.setUsername(username);// {username:,apMap:,deviceId:,terMac:}
                    devParms.setApMac(apMac);
                    devParms.setDeviceId(deviceId);
                    devParms.setTerMac(terMac);
                    newRedisDevParam = JSON.toJSONString(devParms);
                    jedis.hset(Constants.REDIS_TEL_TO_APMACTERMACDEVID, username, newRedisDevParam);
                    LogUtil.track(serviceCode, 304, newRedisDevParam,
                            "access.account.auth4.MD5.temporary:", "");
                    // 过期时间设置
                    if (StringUtil.isBlank(oldRedisDevParam)) {
                        // //挤下线
                       
                       // redisParms = JSON.toJSONString(devParms);
                       
                        accessAuthTimeUpdateTemprary(username, jedis, newRedisDevParam);
                        result.put("addTime", "ok");
                    } else {
                        // 挤下线
                        DevParms oldDevParm = JSON.parseObject(oldRedisDevParam, DevParms.class);
                        // 是否需要T用户下线
                        if (!((apMac.equals(oldDevParm.getApMac())) && (terMac.equals(oldDevParm.getTerMac())))) {
                            LogUtil.track(serviceCode, 408,
                                    "ACCESSAUTH_ACCESSACCOUNTAUTH4MD5TEMPORARY_kill",
                                    oldRedisDevParam + " - " + apMac + " - " + terMac, null);
                            kickuseroffline(oldDevParm.getDeviceId(), oldDevParm.getApMac(), oldDevParm.getTerMac(), username);
                        }
                        //redisParms = JSON.toJSONString(devParms);
                        LogUtil.track(serviceCode, 402, "", newRedisDevParam, null);
                       
                        // if (value < (System.currentTimeMillis())) {
                        accessAuthTimeUpdateTemprary(username, jedis, newRedisDevParam);
                        result.put("addTime", "ok");
                        // }
                    }
                } else {
                    result.put("addTime", "fail");
                    logger.error("access.auth.accountauth.return: " + httpresult);
                }
            } else {
                logger.error("access.auth.accountauth.catch: " + param.toString());
            }
        } catch (Exception e) {
            LogUtil.track(serviceCode, 302, param.toString(),
                    "access.account.auth4.MD5.temporary.catch" + e.getMessage(), "");
            logger.error("access.auth.accountauth.catch: ", e);
        } finally {
            RedisUtil.returnResource(jedis);
        }
        LogUtil.track(serviceCode, 303, JSON.toJSONString(result),
                "send request to access.account.auth.url end:", "");
        return result;
    }

    /**
     * 临时放通次数跟新
     * 
     * @param username
     * @param jedis
     * @param redisParms
     * @throws ParseException
     * @author xhb
     */
    private void accessAuthTimeUpdateTemprary(String username, Jedis jedis, String redisParms) throws Exception {
        LogUtil.track(serviceCode, 403, "",
                jedis.zscore(Constants.REDIS_TIME_TO_DEVPARAM, redisParms) + "", null);
        Calendar outtime = Calendar.getInstance();
        outtime.add(Calendar.MINUTE, 10);
        // jedis.zrem("msp_zdd_8", redisParms);
        jedis.zadd(Constants.REDIS_TIME_TO_DEVPARAM, outtime.getTimeInMillis(), redisParms);//redisParms
        String times = jedis.hget(Constants.REDIS_TEMP_PASS_TIMES, username);
        // 设置当前
        int num = 0;
        if (StringUtil.isBlank(times)) {
            num = 1;
        } else {
            num = Integer.parseInt(times) + 1;
        }
        // 设置当前日期过期次数
        jedis.hset(Constants.REDIS_TEMP_PASS_TIMES, username, num + "");
        // 设置当前晚上过期
        jedis.expire(Constants.REDIS_TEMP_PASS_TIMES, userWifiTime.todaySecondsLeft());
    }

    /**
     * 接入踢用户接口
     * 
     * @param username
     *            用户名
     * @return Map
     * @author xhb
     */
    public int kickuseroffline(String devId, String devMac, String mac, String username) {
        StringBuffer param = new StringBuffer();
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> parms = new HashMap<String, Object>();
        String oldmac = mac;
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getResource();
           /* if (StringUtil.isNotEmpty(mac) && mac.length() == 12) {
                mac = mac.toLowerCase();
                  StringBuffer newMac = new StringBuffer();
              newMac.append(mac.charAt(0)).append(mac.charAt(1)).append(":").append(mac.charAt(2))
                        .append(mac.charAt(3)).append(":").append(mac.charAt(4)).append(mac.charAt(5)).append(":")
                        .append(mac.charAt(6)).append(mac.charAt(7)).append(":").append(mac.charAt(8))
                        .append(mac.charAt(9)).append(":").append(mac.charAt(10)).append(mac.charAt(11));
                parms.put("mac", newMac.toString());
            }*/
            parms.put("userMac", mac.toUpperCase());
            parms.put("version", "1.0");
            parms.put("platform", "MSP");
            parms.put("devId", devId);
            parms.put("devMac", devMac.toUpperCase());
            param.append("data=").append(JSON.toJSONString(parms));
            LogUtil.track(serviceCode, 310, param.toString(),
                    "send request to access.kickuseroffline start:", username);
            /*
             * String httpresult =
             * HttpRequestUtil.sendPost(PropertiesOperateUtil
             * .GetConfig("config", "access.kickuseroffline.url"), param
             * .toString());
             */
            String httpresult = HttpRequestUtil.sendPost(accesskickuserofflineUrl, param.toString());
            logger.info("httpresult: " + httpresult);// 00505311
            LogUtil.track(serviceCode, 311, httpresult, "access.account.auth4.MD5.temporary",
                    "");
            // 异常数据

            if (!httpresult.equals("400")) {
                LogUtil.track(serviceCode, 487, "踢下线成功" + httpresult, null, null);
                AuthResult connectMer = JSON.parseObject(httpresult, AuthResult.class);
                //sessionDTO.setAuthResult(connectMer);
                result.put("kickuseroffline", httpresult);
                SmsUtil smsUtil =new SmsUtil();
                smsUtil.sendSms("抱歉，您的园区上网手机账号已在第二台终端登录上网，将终止该手机账号在第一台终端的上网服务，敬请谅解。", username,(short)3);
                if (connectMer.getResultCode().equals("")) {
                    if (StringUtil.isNotBlank(connectMer.getData())) {
                        Kickuseroffline kickuseroffline = JSON.parseObject(connectMer.getData(), Kickuseroffline.class);
                        if (StringUtil.isNotBlank(kickuseroffline.getToken())) {
                            return 1;
                        } else {
                            logger.info("httpresult.connectMer.getData() null: ");
                        }
                    }
                }
            } else {// 501503
                LogUtil.error(serviceCode, 503, null, "踢下线失败" + httpresult, null);
                DevParms devParms = new DevParms();
                devParms.setApMac(devMac);
                devParms.setDeviceId(devId);
                devParms.setTerMac(oldmac);
                devParms.setUsername(username);

                jedis.hset("msp_10", mac + "," + devMac, JSON.toJSONString(devParms));
                logger.error("access.kickuseroffline.catch: " + parms.toString());
            }
        } catch (Exception e) {
            LogUtil.track(serviceCode, 312, param.toString(),
                    "access.kickuseroffline.catch" + e.getMessage(), "");
            logger.error("access.kickuseroffline.Exception: " + e.getMessage());
        } finally {
            RedisUtil.returnResource(jedis);
        }
        LogUtil.track(serviceCode, 313, JSON.toJSONString(result),
                "send request to access.kickuseroffline end:", "");
        return 0;
    }

  
    /*
     * public static void main(String[] args) { Jedis jedis =
     * com.util.redis.RedisUtil.getResource(); <<<<<<< .mine Set<String> allUser
     * = jedis.zrangeByScore("msp_zdd_8", 0,
     * System.currentTimeMillis()+2*24*60*60*1000); ======= Set<String> allUser
     * = jedis.zrangeByScore("msp_zdd_8", 0,
     * System.currentTimeMillis()+1000*60*10); >>>>>>> .r19017 Map<String,
     * String> catchUser = jedis.hgetAll("msp_10"); // Set<String> allowUser =
     * jedis.hkeys("9"); // allUser.removeAll(allowUser); // jedis.hdel("msp_9",
     * "15868124581"); System.out.println(JSON.toJSONString(allUser));
     * System.out.println(JSON.toJSONString(catchUser)); for (String user :
     * allUser) { if(StringUtil.isNotEmpty(user)){ try{
     * logger.info("kickuseroffline:"+JSON.toJSONString(user)); DevParms
     * devParms = JSON.parseObject(user, DevParms.class);
     * System.out.println(JSON.toJSONString(devParms));
     * System.out.println("send"); }catch(Exception e){ logger.error(user+
     * "用户踢下线异常: ",e); } } } System.out.println(jedis.hgetAll("msp_9"));
     * Set<String> allUser = jedis.zrangeByScore("msp_zdd_8", 0,
     * System.currentTimeMillis()); RedisUtil.returnResource(jedis); }
     */
}
