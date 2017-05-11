package com.dozenx.web.third.dbcenter.service;

import com.dozenx.util.JsonUtils;
import com.dozenx.util.MD5Util;
import com.dozenx.util.StringUtil;
import com.dozenx.web.third.dbcenter.bean.PubUserAuth;
import com.dozenx.web.third.dbcenter.util.AwifiHttpRequestUtil;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
@Service
public class UserAuthService {
    private final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

/**
 * String json
 {
 "userId":100, //用户主表ID(Long)
 "authPlatform":1, //认证平台(Integer)  网管:1,接入:2, 微站:3,TOG:4,TOC:5,APP:6
 "logname":"logname", //登录名(String)
 "telphone":"telphone", //手机号码(String)
 "wechat":"wechat", //微信(String)
 "email":"email", //电子邮箱(String)
 "userCard":"userCard", //身份证(String)
 "authPswd":"authPswd", //登录密码(String)
 "userType":"userType", //认证用户类型(String)
 "safetyCode":"safetyCode", //安全码(String)
 }

 userId,logname,telphone,wechat ,email,userCard必须有一个不为空，并且只有一个不为空
 */
    /**
     * query　auth user by param
     * @param param
     * @return
     * @throws Exception
     */
    public  List<PubUserAuth>  queryAuthUserByParam(Map param) throws Exception {

        String url = ConfigUtil.getConfig("UserAuthService_url")+"userauth?params={json}";
        String jsonStr= AwifiHttpRequestUtil.sendGet(url,param);
        List<PubUserAuth> results = JsonUtils.toList(jsonStr, PubUserAuth.class);
        return results;
    }

    /**
     * query by telphone no
     * @param telno
     * @return
     * @throws Exception
     */
    public PubUserAuth queryAuthUserByTelno(String telno) throws Exception {

        Map param =new HashMap();
        param.put("telphone",telno);
        List<PubUserAuth> results =queryAuthUserByParam(param);
        if(results!=null || results.size()>0){
            return results.get(0);
        }
        return null;
    }
    /**
     * 获得数据中心des秘钥
     * token(String) 加密后（DES加密）的字符串（由：13位时间戳+5为随机数+双方约定密码），不能为空
     *
     * @author kjl
     * @date 2015-11-7
     */
    public static String getCenterToken(String randNum) throws Exception {
        StringBuilder data = new StringBuilder().append(System.currentTimeMillis()).append(randNum).append(ConfigUtil.getConfig("TRANSFER_SAFETY_PASSWORD"));
        return StringUtil.encryptByDes(data.toString(), ConfigUtil.getConfig("TRANSFER_SAFETY_SECRETKEY"));
    }

    /**
     * 增加用户
     * @param phone
     * @param password
     * @throws Exception
     */
    public void addAuthUser(String phone,String password) throws Exception {

        String url = ConfigUtil.getConfig("UserAuthService_url")+"user-base/userauth";
        Map param =new HashMap();
        param.put("telphone",phone);

        String randNum =StringUtil.getRandomDigitString(5);

        param.put("safetyCode",randNum);

        param.put("authPlatform",Integer.valueOf(ConfigUtil.getConfig("addAuthUser_authPlatform")));

        param.put("token",getCenterToken(randNum));

        param.put("authPswd",MD5Util.getStringMD5String(password));

        param.put("seed",randNum);

       /* String json
        {
            "seed":"seed",//双方约定随机码
            "token":"token",//token
            "authPlatform":1, //认证平台(Integer)  网管:1,接入:2, 微站:3,TOG:4,TOC:5,APP:6
            "userId":100, //用户主表ID(Long)
            "logname":"logname", //登录名(String)
            "telphone":"telphone", //手机号码(String)
            "wechat":"wechat", //微信(String)
            "email":"email", //电子邮箱(String)
            "userCard":"userCard", //身份证(String)
            "authPswd":"authPswd", //登录密码(String)
            "userType":"userType", //认证用户类型(String)
            "safetyCode":"safetyCode", //安全码(String)
            "createDate":1479264597000, //创建时间(Date)
            "status":1, //认证用户状态(Integer)
            "statusDate":1479264597000, //修改时间(Date)
        }*/
        String result = AwifiHttpRequestUtil.sendPost(url,param);
        int newId = Integer.valueOf(result);
        if(newId <=0){
            logger.error("addAuthUser faill");
            throw new Exception("addAuthUser faill");
        }

    }
}
