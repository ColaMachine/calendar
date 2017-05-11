package com.dozenx.web.module.wifi.action;

import com.dozenx.util.RedisUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.module.wifi.service.WifiService;
import com.dozenx.web.third.access.service.AccessAuth;
import com.dozenx.web.third.user.service.IThirdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/28.
 */
@RequestMapping("/wifi")
public class WifiController extends BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    ServiceCode serviceCode  = ServiceCode.WifiController;
    /** * 引入AccessAuth . */
    @Resource
    private WifiService wifiService;
    @Resource
    private AccessAuth accessAuth;
    @Resource
    private AuthService authService;
    @Resource(name= "thirdUserService")
    private IThirdUserService thirdUserService;
    /** * 引入CenterPubAuth . */

    /** * UserLogic 引入 . */
    @Resource
    private WifiService userLogic;

    @Resource
    private TimeBusService timeBusService;
    @Resource
    private LogUtilService LogUtil;
    /**
     * 接入全局认证
     * 请求接入放行网络
     * @param payType
     *            支付类型
     * @param request
     *            req
     * @return ResultDTO
     * @author zzw
     */
    @RequestMapping(value = "/auth")
    @ResponseBody
    public ResultDTO auth(
            // @RequestParam(value = "captcha", required = false) String captcha,
            @RequestParam(value = "paytype", required = false) Integer payType, HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        SessionDTO sessionDTO =  (SessionDTO) request.getSession().getAttribute(
                Constants.SESSION_DTO);
        Long merchantId =sessionDTO.getMerchant().getMerchantId();
        SessionUser sessionUser = sessionDTO.getSessionUser();
        //Long userId = sessionUser.getId();
        String phone = sessionUser.getPhone();
        // String username =phone;
        String deviceId =sessionDTO.getDeviceId();
        String terMac = sessionDTO.getUserMac();
        String apMac = sessionDTO.getMerchant().getMacAddr();

        String callback = sessionDTO.getCallback();

        // request.getSession().setAttribute("SessionDeviceId", deviceId);
        // request.getSession().setAttribute("SessionTerMac", terMac);
        // request.getSession().setAttribute("SessionApMac", apMac);
        // request.getSession().setAttribute("SessionCallback", callback);
        // request.getSession().setAttribute("SessionMerchantId", merchantId);
        // 1.参数获取
        // 如果获取不到设备的参数，则先从session中获取
        //logger.info("开始认证");
        //用于前端展示
        result.put("paytype",payType);
        //前一种情况是为了预防开着浏览器从一台路由器转移到另外一台路由器

        if (StringUtil.isBlank(deviceId)) {
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 405, null,
                    "deviceId不能为空");
        }
        if (StringUtil.isBlank(terMac)) {
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 406, null, "用户mac不能为空");
        }
        if (StringUtil.isBlank(apMac)) {
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 407, null, "apMac不能为空");
        }
        if (merchantId == null) {
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 408, null,
                    "merchantId不能为空");
        }
        LogUtil.init(phone);
        request.getSession().setAttribute("SessionCallback", callback);
        //判断是否是vip
        if (timeBusService.isFreeUser(phone)) {
            LogUtil.track(serviceCode, 214, "判断是vip用户", null, null);
            result.put("isVipUser", 1);
        }
        //说明是临时放通
        if (payType != null && payType == 1) {
            try {
                // 当日认证次数判断
                String times = RedisUtil.hget(Constants.REDIS_TEMP_PASS_TIMES, phone);
                if (!(times != null && Integer.parseInt(times) >= 3)) {
                    // 临时放通
                    result.put("access_auth", accessAuth.accessAccountAuth4MD5Temporary(sessionDTO));
                    // result.putAll();
                } else {
                    result.put("msg", "已超过当日认证次数");
                }
            } catch (Exception e) {
                logger.error("access.auth.exception({})", e);
                logger.error("ms.auth.catch", e);
                StringBuilder parm = new StringBuilder();
                parm.append("deviceId=").append(deviceId).append(";merchantId=").append(merchantId).append(";terMac=")
                        .append(terMac).append(";apMac=").append(apMac).append(";username=").append(phone)
                        .append(";payType=").append(payType);
                LogUtil.track(serviceCode, 201, parm.toString(),
                        "ms.auth.catch" + e.getMessage(), "");
            }
        } else {
            try {
                // 2.通过redis缓存判断设备是否匹配
                wifiService.checkService(phone, terMac, merchantId);
                // 3.获取用户信息
                // result = userLogic.saveLogin(request, username, deviceId, terMac, apMac, merchantId);
                // 4.认证并放行网络
                result.putAll(userLogic.permitWLAN(sessionDTO));
            } catch (Exception e) {
                if ("设备信息有误，请重新登录".equals(e.getMessage())) {
                    return this.getWrongResultFromCfg("err.param.telandtermacandmerid");//getResult(0, e.getMessage());
                }
                logger.error("access.auth.exception({})", e);
                StringBuilder parm = new StringBuilder();
                parm.append("deviceId=").append(deviceId).append(";merchantId=").append(merchantId).append(";terMac=")
                        .append(terMac).append(";apMac=").append(apMac).append(";username=").append(phone)
                        .append(";payType=").append(payType);
                LogUtil.track(serviceCode, 202, parm.toString(),
                        "ms.auth.catch" + e.getMessage(), "");
                return this.getWrongResultFromCfg("err.catch");//getResult(0, e.getMessage());
            }

        }
        //logger.info("判断需要踢下线4:"+JSON.toJSONString(result));
        return getResult(result);
    }

}
