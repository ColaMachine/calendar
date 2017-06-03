package com.dozenx.web.third.user.service.impl;

import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.UserService;
import com.dozenx.web.core.auth.sysUser.bean.SysUser;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.CenterPubMerchant;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.merchant.bean.SysMngMerchant;
import com.dozenx.web.module.merchant.service.MngMerchantService;
import com.dozenx.web.module.timing.bean.UserTimeInfoDTO;
import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.third.dbcenter.bean.PubUser;
import com.dozenx.web.third.dbcenter.bean.PubUserAuth;
import com.dozenx.web.module.wifi.service.WifiService;
import com.dozenx.web.third.dbcenter.service.MerchantBaseService;
import com.dozenx.web.third.dbcenter.service.UserAuthService;
import com.dozenx.web.third.dbcenter.service.UserBaseService;
import com.dozenx.web.third.user.service.IThirdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dozen.zhang on 2017/3/10.
 * 对 userAuthService 和 userBaseService的业务封装 用于园区wifi用户认证业务
 */
@Service("thirdUserService")
public class ThirdWifiAuthUserService implements IThirdUserService {
    private static final Logger logger = LoggerFactory
            .getLogger(ThirdWifiAuthUserService.class);
    ServiceCode serviceCode = ServiceCode.AUTH_SERVICE;
    /**
     * 商户管理员接口
     **/
    @Resource
    MngMerchantService mngMerchantService;
    /**
     * WiFi服务
     **/
    @Resource
    WifiService wifiService;
    /**
     * 时长总线接口
     **/
    @Resource
    private TimeBusService timeBusService;
    /**
     * 第三方用户认证接口
     **/
    @Resource
    private UserAuthService userAuthService;
    /**
     * 第三方用户基础信息接口
     **/
    @Resource
    private UserBaseService userBaseService;
    /**
     * 日志
     **/
    @Resource
    private LogUtilService LogUtil;
    /**
     * 商户基础接口
     **/
    @Resource
    private MerchantBaseService merchantBaseService;
    /**
     * 系统用户基础接口
     **/
    @Autowired
    private UserService userService;
/*    public void register(SessionUser user){

    }*/

    public SessionUser getUserByPhone(String phone) throws Exception {
        if (!StringUtil.isBlank(phone) && !StringUtil.isPhone(phone)) {
            throw new Exception("phone is null");


        }
        SessionUser sessionUser = new SessionUser();

        try {
            PubUserAuth userAuth = userAuthService.queryAuthUserByTelno(phone);
            if (userAuth != null && userAuth.getId() != null) {
                sessionUser.setId(userAuth.getUserId());
                sessionUser.setName(userAuth.getLogname());
                sessionUser.setPhone(phone);
                sessionUser.setAuthPswd(userAuth.getAuthPswd());
            } else {
                LogUtil.error(serviceCode, 102, phone,
                        "can't find auther user by phone :" + phone, null);
                return null;
            }


            // 查询用户信息
            //  Map<String, Object> param = new HashMap<String, Object>();
            // param.put("id", userAuth.getUserId());
            PubUser pubUser = userBaseService.queryByUserId(userAuth.getUserId());

         /*   LogUtil.track(serviceCode, 103, "params:"
                            + param + "results:" + JsonUtils.toJsonString(pubUser),
                    "after userServiceApi.queryById", phone);*/
            if (pubUser != null) {

                sessionUser.setFace(pubUser.getFaceInfo());
                if (pubUser.getBirthday() != null) {
                    sessionUser.setBirthday(pubUser.getBirthday().getTime());
                }
                sessionUser.setSex(StringUtil.isBlank(pubUser.getSex()) ? 0 : Integer.valueOf(pubUser.getSex()));
                sessionUser.setAddress(pubUser.getAddress());
                sessionUser.setNick(pubUser.getUserNick());


            } else {
                LogUtil.error(serviceCode, 103, phone,
                        "can't find pub user with userId:" + userAuth.getUserId(), null);

            }
        } catch (Exception e) {
            logger.error("去authservice获取auth用户信息时候报错", e);
            LogUtil.error(serviceCode, 104, phone,
                    "ThirdWifiAuthUserService getUserByPhone happen erro " + e.getMessage(), null);
        }

        //after success login init the user info in session

        return sessionUser;

    }

    public SessionUser getUserById(Long id) {
        return null;
    }

    public void updateUser(SessionUser sessionUser) throws Exception {
        PubUser user = new PubUser();
        user.setSex(String.valueOf(sessionUser.getSex()));
        user.setId(sessionUser.getId());
        user.setAddress(sessionUser.getAddress());
        user.setUserNick(sessionUser.getNick());
        if (sessionUser.getBirthday() != null && sessionUser.getBirthday() > 0) {
            user.setBirthday(new Date(sessionUser.getBirthday()));
        }
        user.setFaceInfo(sessionUser.getFace());

        userBaseService.update(user);
        // 更新session中的用户信息

    }

    public void addUser(SessionUser sessionUser) throws Exception {
        PubUserAuth user = userAuthService.queryAuthUserByTelno(sessionUser.getPhone());
        if (user == null) {
            //直接注册吗
            userAuthService.addAuthUser(sessionUser.getPhone(), "123456");
        }
    }

    @Override
    public void loginByPhoneAndSMS(SessionDTO sessionDTO, String phone, String captcha) throws Exception {
        Long merchantId = sessionDTO.getMerchant().getMerchantId();

        if (merchantId == null)
            throw new Exception("商户id为空");
        String terMac = sessionDTO.getUserMac();


        //第三方的用户接口不应该把代码写在主业务逻辑里 删除
        SessionUser sessionUser = sessionDTO.getSessionUser();
        SessionUser thirdUser = getUserByPhone(phone);
        if (thirdUser != null) {
            sessionUser.setId(thirdUser.getId());
            sessionUser.setAuthPswd(thirdUser.getAuthPswd());
            sessionUser.setFace(thirdUser.getFace());
            sessionUser.setPhone(thirdUser.getPhone());
            sessionUser.setAddress(thirdUser.getAddress());
            sessionUser.setNick(thirdUser.getNick());
            sessionUser.setBirthday(thirdUser.getBirthday());
        } else {
            logger.error("auth接口没有查到该用户");
            throw new Exception("业务逻辑错误");
        }
        //同步数据到本地数据
        SysUser sysUser = userService.getUserById(sessionUser.getId());//.getUserByTelno(phone);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNkname(sessionUser.getNick());
            sysUser.setAddress(sessionUser.getAddress());
            sysUser.setTelno(sessionUser.getPhone());
            sysUser.setPassword(sessionUser.getAuthPswd());
            sysUser.setFace(sessionUser.getFace());
            if (sessionUser.getBirthday() != null && sessionUser.getBirthday() != 0) {
                sysUser.setBirth(new Date(sessionUser.getBirthday()));
            }
            sysUser.setOutId(sessionUser.getId());
            sysUser.setId(sessionUser.getId());
            userService.save(sysUser);
        } /*else {


            //已经有数据了
            //sysUser.setOutId(sessionUser.getId());
        }*/


        //第三方的业务系统的用户和本系统进行同步

        // 清除商户session
           /*     request.getSession().removeAttribute(
                        CommonDefine.SESSION_MERCHANT);*/
        // 如果是商家用户，再取一下商家用户信息
                /*String jsonStr = merchantServiceApi.queryById(merchantId);
                LogUtil.track(serviceCode, 105,
						"merchantId:" + merchantId + ";result:" + jsonStr,
						"after merchantServiceApi.queryById", username);*/
        CenterPubMerchant connectMer = null;
        try {
            connectMer = merchantBaseService.queryById(merchantId);
        } catch (Exception e) {
            LogUtil.track(serviceCode, 500,
                    merchantId + "", "merchantBaseService.queryById" + e.getMessage(),
                    null);
        }
        if (connectMer == null) {
            LogUtil.track(serviceCode, 505,
                    "login can't find merchant with id:" + merchantId, "",
                    null);
            throw new Exception("没有找到对应商户:" + merchantId);
        }

        if (connectMer.getUserId() != null
                && connectMer.getUserId().equals(sessionUser.getId())
                && connectMer.getStatus() == 1) {
                  /*  LogUtil.track(serviceCode, 105,
                            "merchantId:" + merchantId +"userid:"+connectMer.getUserId(),
                            "is merchant manager", phone);*/
            // result.put("role",Constants.SESSION_MERCHANT);
            // result.put(Constants.SESSION_MERCHANT, connectMer);
            sessionDTO.getSessionUser().setRole(Constants.ROLE_MERCHANT_MANAGER);
                  /*  request.getSession().setAttribute(
                            CommonDefine.SESSION_MERCHANT, connectMer);*/
        } else {
            //该商户下的角色关系
            HashMap<String, String> params = new HashMap<>();
            params.put("uid", phone);
            List<SysMngMerchant> list = mngMerchantService.listByParams(params);
            if (list != null && list.size() > 0) {
                //String localType = list.get(0).getType();//代理管理员
                sessionDTO.getSessionUser().setRole(Constants.ROLE_MERCHANT_AGENT_MANAGER);
            }
        }

        UserTimeInfoDTO userTimeInfo = timeBusService.getUserTimeInfo(sessionDTO.getSessionUser().getId(), phone, merchantId);
        sessionDTO.setTimeInfo(userTimeInfo);
           /*     if(userTimeInfo!=null && userTimeInfo.isCanGetFreePkg()){
                    RedisUtil.hset(Constants.REDIS_PKG_GET, sessionDTO.getSessionUser().getId() + "" + merchantId, "ok");
                }*/

        // result.put("timeInfo", userTimeInfo);
        // 4.认证并放行网络
        LogUtil.track(serviceCode, 104, null, "before permitWLAN", null);
        //如果该用户有时长并且大于当前时间 或者是vip 用户 应当放行网络
        //result.putAll(wifiService.permitWLAN(sessionDTO));
        wifiService.permitWLAN(sessionDTO);
        wifiService.setDeviceCache(phone, terMac, merchantId);
        // 关联商户id
    }


}
