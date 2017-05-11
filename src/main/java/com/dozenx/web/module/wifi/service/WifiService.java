package com.dozenx.web.module.wifi.service;

import com.dozenx.util.JsonUtils;
import com.dozenx.util.RedisUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.service.OnlineWrapService;

import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.module.timing.service.UserCutoffWrapService;
import com.dozenx.web.third.access.service.AccessAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
@Service
public class WifiService {

    /** * 日志打印 . */
    private static Logger logger = LoggerFactory.getLogger(WifiService.class);
    ServiceCode serviceCode  = ServiceCode.WIFI_SERVICE;
    @Resource
    private OnlineWrapService onlineService;
    @Resource
    UserCutoffWrapService userCutoffWrapService;
    @Resource
    private AccessAuth accessAuth;

    @Resource
    private TimeBusService timeBusService;
    @Resource
    private LogUtilService LogUtil;
    /**
     * . 接入认证放行
     *

     *            request
     * @return Map
     * @throws Exception
     * @author 宋展辉 2016年1月27日 上午10:25:44
     */
    public Map<String, Object> permitWLAN(SessionDTO sessionDTO)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Long merchantId = sessionDTO.getMerchant().getMerchantId();
            SessionUser sessionUser  = sessionDTO.getSessionUser();
            if(sessionUser!=null)
                LogUtil.track(serviceCode, 711, null, JsonUtils.toJsonString(sessionUser), null);
            String username = sessionUser.getPhone();
            Long userId = sessionUser.getId();
            if(sessionDTO.getTimeInfo()!=null && sessionDTO.getTimeInfo().isVip()){
            //if (timeBusService.isFreeUser(username)) {
                result.put("access_auth", accessAuth.accessAccountAuth4MD5(sessionDTO));
                return result;
            }
            //找出商户和用户对应的消费时长剩余记录
            //sessionDTO.getTimeInfo();
            UserCutoff merchant_user_cutoff = userCutoffWrapService
                    .merchantUserCutoffDate(merchantId, userId);
            // 是否放行网络
            if(merchant_user_cutoff!=null )
            {
                if (merchant_user_cutoff.getCutoffDate().getTime() > (new Date()).getTime()) {
                    result.put("access_auth",
                            accessAuth.accessAccountAuth4MD5(sessionDTO));

                    //logger.info("判断需要踢下线3:"+JSON.toJSONString(result));

                    result.put("user_cutoff",merchant_user_cutoff);//把用户的上网时长放入到返回结果中
                    sessionDTO.setUserCutoff(merchant_user_cutoff);

                }else{
                    result.put("have_no_time","1");
                }

            }else{
                result.put("have_no_time","1");//从未购买过时间
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return result;
    }

    /**
     * . 免认证时检测设备信息是否匹配
     *
     * @param username
     *            账号
     * @param deviceId
     *            设备id
     * @param terMac
     *            手机mac
     * @param apMac
     *            设备mac
     * @param merchantId
     *            商户id
     * @throws Exception
     * @author 宋展辉 2016年1月27日 上午11:04:51
     */
    /*public void checkService(String username, String terMac, Long merchantId)
            throws Exception {
        String code = terMac + "," + merchantId;
        String value = RedisUtil.hget(Constants.REDIS_TEL_TO_TERMAC_MER, username);
        if (!code.equals(value)) {
            LogUtil.info(serviceCode, 202, "username:" + username
                            + " termac:" + terMac + " merchantid:" + merchantId,
                    "not equal redis user_device " + value, "");
            throw new Exception("设备信息有误，请重新登录");
        }
    }*/



    /**
     * . 登录成功将账号与设备信息存入redis缓存
     *
     * @param username
     *            用户名
     * @param terMac
     *            手机mca
     * @param merchantId
     *            商户id
     * @throws Exception
     */
    public void setDeviceCache(String username, String terMac, Long merchantId) throws Exception {
        RedisUtil.hset(Constants.REDIS_TEL_TO_TERMAC_MER, username, terMac + "," + merchantId);
    }
    /**
     * . 免认证时检测设备信息是否匹配
     *
     * @param username
     *            账号
     * @param terMac
     *            手机mac
     * @param merchantId
     *            商户id
     * @throws Exception
     * @author 宋展辉 2016年1月27日 上午11:04:51
     */
    public void checkService(String username, String terMac, Long merchantId)
            throws Exception {
        String code = terMac + "," + merchantId;
        String value = RedisUtil.hget(Constants.REDIS_TEL_TO_TERMAC_MER, username);
        if (!code.equals(value)) {
            LogUtil.info(serviceCode, 202, "username:" + username
                            + " termac:" + terMac + " merchantid:" + merchantId,
                    "not equal redis user_device " + value, "");
            throw new Exception("设备信息有误，请重新登录");
        }
    }
}
