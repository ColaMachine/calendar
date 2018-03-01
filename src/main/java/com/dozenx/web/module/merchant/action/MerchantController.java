package com.dozenx.web.module.merchant.action;

import com.cpj.swagger.annotation.*;
import com.dozenx.core.exception.ParamException;
import com.dozenx.util.JsonUtil;
import com.dozenx.util.ResultUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.auth.service.AuthService;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.LogType;
import com.dozenx.web.core.log.LogUtilFeichu;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.merchant.service.MerchantPictureService;
import com.dozenx.web.module.merchant.service.MerchantService;
import com.dozenx.web.module.timing.bean.UserTimeInfoDTO;
import com.dozenx.web.third.access.bean.AuthResult;
import com.dozenx.web.third.dbcenter.bean.PubDeviceQueryResult;
import com.dozenx.web.third.dbcenter.service.MerchantDeviceBaseService;
import com.dozenx.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dozen.zhang on 2017/2/22.
 * wifi商户接口
 */
@APIs(/*value = "/merchant" ,*/description = "商户接口")
@Controller
@RequestMapping("/merchant")
public class MerchantController  extends BaseController {
    /**日志**/
    private Logger logger = LoggerFactory.getLogger(MerchantController.class);
    /**日志码**/
    private ServiceCode serviceCode = ServiceCode.MERCHANTCONTROLLER;
    /**商户设备服务**/
    @Resource
    private MerchantDeviceBaseService merchantDeviceBaseService;
    /**商户图片服务**/
    @Resource
    private MerchantPictureService merchantPictureService;
    /**商户服务**/
    @Resource
    private MerchantService merchantService;
    /**登录接口**/
    @Resource
    private AuthService authService;
    @Resource
    private LogUtilService LogUtil;


    /**
     * 把sessionDTO中的数据转化成 jso格式传回前台
     * @param sessionDTO
     * @return HashMap 消息格式请见merchant/init.json 返回格式
     */
    public HashMap getDetailInfo(SessionDTO sessionDTO){
    HashMap result =new HashMap();
    SessionUser user  = sessionDTO.getSessionUser();
    PubDeviceQueryResult merchant =sessionDTO.getMerchant();
    UserTimeInfoDTO timeInfo = sessionDTO.getTimeInfo();
    AuthResult auth =sessionDTO.getAuthResult();
    if(merchant!=null){//商户信息
        HashMap<String,Object> merchantMap = new HashMap<>();
        merchantMap.put("merchantName",merchant.getMerchantName());
        merchantMap.put("merchantId",merchant.getMerchantId());
        result.put("merchant",merchantMap);

    }
    if(user!=null){//用户信息
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("phone",user.getPhone());
        userMap.put("role",user.getRole());
        result.put("user",userMap);

    }
    if(timeInfo!=null){//时长模块信息
        HashMap<String,Object> timeInfoMap = new HashMap<>();
        timeInfoMap.put("vip",timeInfo.isVip());
        timeInfoMap.put("canGetFreePkg",timeInfo.isCanGetFreePkg());
        timeInfoMap.put("endTime",timeInfo.getEndTime());
        result.put("timeInfo",timeInfoMap);
    }
    if(auth!=null){//认证记录信息
        result.put("auth",auth);
    }
    result.put("nowDate", new java.util.Date().getTime());//当前时间 用于显示倒计时
    result.put("topPicList", sessionDTO.getSlidelist());//滚动图片
    return result;

}

    /**
     * .用户登录接口
     *
     * @param username
     *            账号
     * @param captcha
     *            验证码
     * @param request
     *            request
     * @return ResultDTO
     * @author zhangzw
     */

    @API(summary="用户登录接口",
            description=" 此方法的主要功能是 用户在访问首页后 登录用 此处需要注意的是如果session中没有值 建议用户重传portal参数",parameters={
            @Param(name="username", description="用户名", dataType= DataType.PHONE,required = true),
            @Param(name="captcha", description="验证码", dataType= DataType.CAPTCHA,required = true),
    })
    @APIResponseBody(value="{ \"r\": 0, \"data\": { \"now_date\": 1489975765652, \"merchant\": {\"merchantId\": 5597, \"merchantName\": \"凌云公寓\", 'broadbandAccount': '宽带账号电话号码' }, user:{'phone':13958173965,name:'用户名'},timeInfo:{'vip':'是否vip',canGetFreePgk:'1:是能领取免费礼包 0:不能领取',endTime:'1489975765652时长时间到期时间''},\"topPicList\": [\"123.jpg\"] }, \"msg\": \"错误原因\" }\n}")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO login(@RequestParam(value = "username", required = true) String username,//手机号码
                          @RequestParam(value = "captcha", required = false) String captcha,//验证码
                          HttpServletRequest request) throws Exception {
        // 写日志记录
        // 在进入controller的时候调用一次该方法 传入用户名
        LogUtilFeichu.init(username);
        //this.redisLog.info(JSON.toJSONString(redisMap));
        // 拼接传入参数
        SessionDTO sessionDTO =(SessionDTO)request.getSession().getAttribute(Constants.SESSION_DTO);
        String param = "username:" + username + ",captcha:" + captcha ;
        // 之后调用该方法即可 最后一个username 可以为空
        LogUtilFeichu.track(serviceCode, 101, param, "enter login save", username);

        // LogUtil.log(ServiceCode.MSLOGINCONTROLLER_SAVE, returnCode,
        // methodCode, param, msg, username);
        // 1.url-session中的参数判断
        // 如果userMac为空不能登陆
        if (sessionDTO==null || StringUtil.isBlank(sessionDTO.getDeviceId()) ||sessionDTO .getMerchant() ==null || sessionDTO.getMerchant().getId()==null ) {
            logger.error("用户登录时遇到sessionDto参数错误");
            LogUtilFeichu.track(serviceCode, 102,
                    JsonUtil.toJsonString(sessionDTO),
                    "miss param", username);
            return this.getResult(1,"缺少用户商户参数");
        }

        // 如果获取不到设备的参数，则先从session中获取


        if (StringUtil.isBlank(username)) {
            logger.info("手机号码不能为空");
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 103, null, "手机号码不能为空");
        }

        if (!StringUtil.isPhone(username)) {
            logger.info("手机号码格式错误");
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 104, null, "手机号码格式错误");
        }
        if (StringUtil.isBlank(sessionDTO.getUserMac()) ) {
            logger.info("手机mac不能为空");
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 105, null, "手机mac不能为空");
        }

        if (sessionDTO.getMerchant() == null || sessionDTO.getMerchant().getMerchantId() == null) {
            logger.info("商户id不能为空");
            return ResultUtil.getResult(serviceCode, LogType.PARAM, 107, null, "商户id不能为空");
        }


       /* try {*/
            // 如果是免认证一键登录 需要先认证 再放通
            /*
             * if(!StringUtil.isBlank(authed)){ userLogic.saveReg(username,
             * "123456"); }
             */
            // 3.对用户信息做相关处理

            ResultDTO resultDTO = authService.loginByPhoneAndSms(sessionDTO, username,captcha);
            if(!resultDTO.isRight()){
                return resultDTO;
            }
         Map<String,Object> result = (Map<String,Object>)resultDTO.getData();
            SessionUser user  = sessionDTO.getSessionUser();
            String requestIp =  RequestUtil.getIp(request);

            String requestIpPort = request.getHeader("X-Forwarded-Port");
            sessionDTO.setRequestIp(requestIp);
            sessionDTO.setRequestIpPort(requestIp);
            //authService.initWifiUserInfo(sessionDTO);

 /*

            // 4.认证并放行网络
            LogUtil.track(serviceCode, 104, result.toString(), "before permitWLAN", username);

            //如果该用户有时长并且大于当前时间 或者是vip 用户 应当放行网络
            //result.putAll(wifiService.permitWLAN(sessionDTO));
            wifiService.permitWLAN(sessionDTO);*/
            return getResult(getDetailInfo(sessionDTO));
       /* } catch (Exception e) {
            logger.error("ms.log in.save.controller.system.exception({})", e);
            // return getResult(0, e.getMessage());
            return ResultUtil.getResult(serviceCode, LogType.SERVICE, 109, e.getMessage(),
                    e.getMessage());
        }*/
    }

    /**
     * 商户首页信息获取
     * @param request
     * @return
     * @throws Exception
     */
    @API(summary="商户首页信息获取",
            description="MerchantController.java:index 此方法应该还没有应用场景 预留",parameters={

            @Param(name="logId", description="日志id", dataType= DataType.STRING,required = true),
            @Param(name="deviceId" , description="设备id", dataType= DataType.STRING,required = true),
            @Param(name="mobilePhone" , description="手机号码", dataType= DataType.PHONE,required = false),
            @Param(name="userMac" , description="设备mac地址", dataType= DataType.MAC_SHORT,required = true),
            @Param(name="userIP" , description="用户ip",dataType= DataType.IP,required = true),
            @Param(name="gwAddress" , description="设备内网地址", dataType=DataType.IP,required = true),
            @Param(name="gwPort" , description="设备端口", dataType=DataType.PORT,required = true),
            @Param(name="nasName" , description="设备名称", dataType=DataType.STRING, type="string"),
            @Param(name="userType" , description="userType",dataType=DataType.ARRAY,items="NEW_USER,EXEMP_AUTH_USER" ,required = true),
            @Param(name="token" , description="token", dataType=DataType.STRING),
            @Param(name="url" , description="url", dataType=DataType.URL),
    })
    @APIResponse(value="{ \"r\": 0, \"data\": { \"now_date\": 1489975765652, \"merchant\": {\"merchantId\": 5597, \"merchantName\": \"凌云公寓\", 'broadbandAccount': '宽带账号电话号码' }, user:{'phone':13958173965,name:'用户名'},timeInfo:{'vip':'是否vip',canGetFreePgk:'1:是能领取免费礼包 0:不能领取',endTime:'1489975765652时长时间到期时间''},\"topPicList\": [\"123.jpg\"] }, \"msg\": \"错误原因\" }\n}")

    @RequestMapping(value = "index" ,method = RequestMethod.GET)
    @ResponseBody
    public Object indexGet(HttpServletRequest request) throws Exception {
        SessionDTO sessionDTO =  (SessionDTO) request.getSession().getAttribute(
                Constants.SESSION_DTO);
        Long merchantId =sessionDTO.getMerchant().getMerchantId();
        SessionUser sessionUser = sessionDTO.getSessionUser();
        return getResult(getDetailInfo(sessionDTO));
    }
    /**
     * 登录页接口
     * 此方法的主要功能是 step 1接受页面portal参数,加入到sessionDTO中 ,并初始化商户信息(存入到session中,如果有就不用),并把商户 用户信息返回给页面
     * 测试urll:
     * http://127.0.0.1:8081/static/html/merchant/index.html?logId=logId&deviceId=FatAP_31_201603181c537d5c-2333-4611-9d0d-3f5db479e4a6&mobilePhone=13958173965&userMac=1C184A15A5D9&userIP=192.168.10.15&gwAddress=192.168.10.1&gwPort=2020&nasName=&userType=NEW_USER&token=token_test&url=www.163.com
     * @param paramDTO 页面参数
     * @param request request
     * @return ResultDTO {asdfasdfasdf}
     * @author zzw
     */
    @API(summary="商户首页信息初始化",
            description="MerchantController.java:index 此方法的主要功能是 step 1接受页面portal参数,加入到sessionDTO中 ,并初始化商户信息(存入到session中,如果有就不用),并把商户 用户信息返回给页面",parameters={

            @Param(name="logId", description="日志id", dataType= DataType.STRING,required = true),
            @Param(name="deviceId" , description="设备id", dataType= DataType.STRING,required = true),
            @Param(name="mobilePhone" , description="手机号码", dataType= DataType.PHONE,required = false),
            @Param(name="userMac" , description="设备mac地址", dataType= DataType.MAC_SHORT,required = true),
            @Param(name="userIP" , description="用户ip",dataType= DataType.IP,required = true),
            @Param(name="gwAddress" , description="设备内网地址", dataType=DataType.IP,required = true),
            @Param(name="gwPort" , description="设备端口", dataType=DataType.PORT,required = true),
            @Param(name="nasName" , description="设备名称", dataType=DataType.STRING, type="string"),
            @Param(name="userType" , description="userType",dataType=DataType.ARRAY,items="NEW_USER,EXEMP_AUTH_USER" ,required = true),
            @Param(name="token" , description="token", dataType=DataType.STRING),
            @Param(name="url" , description="url", dataType=DataType.URL),
    })
    @APIResponse(value="{ \"r\": 0, \"data\": { \"now_date\": 1489975765652, \"merchant\": {\"merchantId\": 5597, \"merchantName\": \"凌云公寓\", 'broadbandAccount': '宽带账号电话号码' }, user:{'phone':13958173965,name:'用户名'},timeInfo:{'vip':'是否vip',canGetFreePgk:'1:是能领取免费礼包 0:不能领取',endTime:'1489975765652时长时间到期时间''},\"topPicList\": [\"123.jpg\"] }, \"msg\": \"错误原因\" }\n}")
    @RequestMapping(value = "index",method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO index(SessionDTO paramDTO, HttpServletRequest request) throws Exception {
        int code =serviceCode.ordinal()*1000+40;
        /*
        String logId = request.getParameter("logId");
        String deviceId = request.getParameter("deviceId");
        String mobilePhone = request.getParameter("mobilePhone");
        String userMac = request.getParameter("userMac");
        String userIP = request.getParameter("userIP");
        String gwAddress = request.getParameter("gwAddress");
        String gwPort = request.getParameter("gwPort");
        String nasName = request.getParameter("nasName");
        String userType = request.getParameter("userType");
        String token = request.getParameter("token");
        String url = request.getParameter("url");*/
        //Map<String, Object> ret = new HashMap<String, Object>();
        //step 1接受页面portal参数,加入到sessionDTO中
        // 检查session中是否有 dto了

        SessionDTO sessionDTO = (SessionDTO) request.getSession().getAttribute(Constants.SESSION_DTO);
        if(sessionDTO ==null){//第一次访问
            sessionDTO = new SessionDTO();
            request.getSession().setAttribute(Constants.SESSION_DTO, paramDTO);
        }else {//非第一次访问首页
            if (sessionDTO != null && paramDTO!=null && paramDTO.getDeviceId()!=null){//如果dto不为空 那么把request中的值也带进去
                sessionDTO.copy(paramDTO);
            }else if (sessionDTO == null && paramDTO!=null && paramDTO.getDeviceId()!=null ) {
                request.getSession().setAttribute(Constants.SESSION_DTO, paramDTO);
                sessionDTO = paramDTO;
            }else if(sessionDTO == null && (paramDTO==null || paramDTO.getDeviceId()==null) ){
                //用户上报的信息缺失deviceid
                throw new ParamException(code,"this param dto should not be null and meantime the sessionDto is also null");
            }
        }
        //根据设备查询商户信息
        //如果session为空 || session中的account_id为空 || url中的account_id 改变 || url中的devId改变， 则更新session
        //step 2 并初始化商户信息(存入到session中,如果有就不用)
        //检查原来的deviceId 和现在的DeviceId 是否一致 如果不一致 还要重新根据deviceId查询商户信息
        if(sessionDTO.getMerchant()==null){
           /* HashMap<String, Object> map = new HashMap<>();
            map.put("deviceId", ParamDTOInSession.getDeviceId());*/
            //找到商户id 找到商户名称 找到一个设备的mac
            PubDeviceQueryResult pubDeviceQueryResult = merchantDeviceBaseService.queryDetailById(sessionDTO.getDeviceId());
            if (pubDeviceQueryResult == null) {
                logger.error("this deviceId can't find merchantInfo:"+sessionDTO.getDeviceId());
                throw new ParamException(code,"this deviceId can't find merchantInfo:"+sessionDTO.getDeviceId());
            }
            sessionDTO.setMerchant(pubDeviceQueryResult);
        }
        //ret.put("merchant", ParamDTOInSession.getMerchant());
        // 查询轮播图片，按orderNum排序
        if(sessionDTO.getSlidelist()==null){
            List<String> slidelist = merchantPictureService.getSliderList(sessionDTO.getMerchant().getId());
            sessionDTO.setSlidelist(slidelist);
        }
        /**
         *
         * now_date
         * topPicList
         *  loginUser
         *
         *
         */
     /*   ret.put("now_date", new java.util.Date());
        ret.put("topPicList", ParamDTOInSession.getSlidelist());*/

        // 更新session中的param_dto中的merchantId

        ;
        // 商户登录的情况下才设置这个session
        // request.getSession().setAttribute(MsCommonDefine.SESSION_MERCHANT, merchant);


        // 查询商户应用
        // 查询照片墙，按时间排序
        // List<CenterPubMerchantPicture> album = getAlbum(merchant.getId(), 1, MsCommonDefine.PIC_PAGE_NUM);


        //ret.put("album", album);
        // ret.put("LoginURL", LoginURL);
        //            // 免认证登录
        //            if (paramDTO.getIs_authed() != null && paramDTO.getIs_authed().equals("true")) {
        //                userLogic.getRelieveLogin(request, response, merchant.getId());
        //            }
        // 用户是否已登录

      /*  if(ParamDTOInSession.getSessionUser()!=null){
            Map map = this.authService.initWifiUserInfo(ParamDTOInSession);
            ret.putAll(map);
        }
*/
        //ret.put("loginUser", ParamDTOInSession.getSessionUser());
        return getResult(getDetailInfo(sessionDTO));
    }
}
