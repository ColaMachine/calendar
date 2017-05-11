package com.dozenx.web.module.timing.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.util.ConfigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/3/9.
 */
@Controller
@APITag(value = "时长",description = "时长支付")

@RequestMapping("/time/pay")
public class TimePayController extends BaseController {
    @Resource
    private LogUtilService LogUtil;

    ServiceCode serviceCode  = ServiceCode.TimePayController;
    /**
     * @param request
     * @return result.put("deviceinfo", broadbandAccount);//宽带账号
     *         result.put("devicemerid", merchantId);//商户id
     *         result.put("deviceusername", userName);//用户手机号码
     *         result.put("deviceurl", dqPayUrl);//电渠url
     * @author dozen.zhang
     */

    @API(/*value="/url",*/ summary="时长购买接口",parameters={

    })
    @APIResponse("123")
    @RequestMapping(value = "/url" ,method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO payUrlGet(HttpServletRequest request) {
        SessionDTO sessionDTO =  (SessionDTO) request.getSession().getAttribute(
                Constants.SESSION_DTO);
        Long merchantId =sessionDTO.getMerchant().getMerchantId();
        SessionUser sessionUser = sessionDTO.getSessionUser();
      //  Long userId = sessionUser.getId();
        String phone = sessionUser.getPhone();
        String deviceId =sessionDTO.getDeviceId();
        String terMac = sessionDTO.getUserMac();
        String apMac = sessionDTO.getMerchant().getMacAddr();

        String callback = sessionDTO.getCallback();

        if (merchantId == null ) {
            return this.getWrongResultFromCfg("err.logic.notlogging");
        }
        LogUtil.init(phone);

        try {

            if (merchantId == null || StringUtil.isBlank(phone)) {
                return getResult(FAIL, "获取用户信息失败");
            }
        } catch (Exception e) {
            LogUtil.error(serviceCode, 257, "merchantId:" + merchantId + "userName:" + phone,
                    "获取session异常" + e.getMessage(), null);
            return getResult(FAIL, "获取用户信息失败");
        }
        // 查询宽带账号
        LogUtil.track(serviceCode, 264, "merchantId:" + merchantId + "userName:" + phone,
                "开始获取宽带账号", null);
        Map map=dqUrlGet(merchantId, phone,sessionDTO.getMerchant().getBroadbandAccount());
        if (map == null) {

            LogUtil.error(serviceCode, 277, "merchantId:" + merchantId + "userName:" + phone,
                    "获取宽带账号失败", null);
            return getResult(serviceCode, LogType.SYSTEM,288, "宽带账号获取失败,请联系管理员!");
           // return ResultUtil.getResult(FAIL, "宽带账号获取失败,请联系管理员!");
        }
        return getResult(map);

    }


    /**
     * . 电蕖url参数获取
     *
     * @param merchantId
     *            商户id
     * @param userName
     *            手机号
     * @return map
     * @throws Exception
     * @author xhb
     */
    public Map<String, Object> dqUrlGet(Long merchantId, String userName,String broadbandAccount) {

        // 获取宽带账号 返回
        if (StringUtil.isBlank(broadbandAccount)) {
            return null;
        }
        /*
        url:dqurl
        code_number:宽带账号
        yxgh:商户id
        link_phone:用户手机号码
        */
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("deviceinfo", broadbandAccount);
        result.put("devicemerid", merchantId);
        result.put("deviceusername", userName);
        result.put("deviceurl", ConfigUtil.getConfig("dq.pay.url"));
        return result;
    }
}
