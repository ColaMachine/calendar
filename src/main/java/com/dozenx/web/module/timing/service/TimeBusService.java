package com.dozenx.web.module.timing.service;

import com.dozenx.util.JsonUtils;
import com.dozenx.util.RedisUtil;
import com.dozenx.util.StringUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.timing.bean.OnlineUser;
import com.dozenx.web.module.timing.bean.TimeConsume;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.bean.UserTimeInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dozen.zhang on 2017/3/1.
 */
@Service
public class TimeBusService {


    ServiceCode serviceCode = ServiceCode.TIMEBUS_SERVICE;
    private static Logger logger = LoggerFactory.getLogger(TimeBusService.class);
    @Resource(name="CenterOnlineUserService")
    private ICenterOnlineUserService centerOnlineUserService;
    /**
     * @param username
     *            账号
     * @return boolean
     * @author 宋展辉 2016年3月23日 上午9:32:40
     */
   /* public boolean isFreeUser(String username) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("telephone", username);
        param.put("nowTime", (System.currentTimeMillis() / 1000));

            int flag =  centerOnlineUserService.queryOnlineUserCount(param);

            return flag > 0;

    }
*/


    @Resource
    private UserCutoffWrapService userCutoffWrapService;
    /** * 引入 IUserConsumeService . */
    @Resource
    private UserConsumeWrapService userConsumeWrapService;
    @Resource
    private LogUtilService LogUtil;
    /**
     * . 用户上网时长跟新 用户消费记录创建
     *
     * @param merchantId
     *            商户id
     * @param userId
     *            用户id
     * @param days
     *            增加天数
     * @param packageId
     *            套餐包id
     * @param packageNum
     *            套餐包数量
     * @param totalNum
     *            套餐价值
     * @param orderId
     *            对应订单编号
     * @param payNum
     *            实付金额
     * @return int
     * @author xhb
     * @throws Exception
     */
    public int update(Long merchantId, Long userId, int days, Long packageId, Integer packageNum, Float totalNum,
                      String orderId, Float payNum, int type, int payType) throws Exception {
//        Map<String, Object> cutoff_date = new HashMap<String, Object>();
        TimeConsume consume = new TimeConsume();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (payType == 1) {
            if (StringUtil.isBlank(orderId)) {
                return 0;
            }
        }
        // int cutoff_date_return = 0;
        // int consume_return = 0;
        Date nowtime = new Date();
        Date startime = nowtime;
        if (merchantId == null || userId == null) {
            return 0;
        }
        // cutoff_date.put("merchantId", merchantId);
        // cutoff_date.put("userId", userId);
        consume.setMerchantId(merchantId);
        consume.setUserId( userId);
        // 查询用户在商家下的上网时长

        LogUtil.track(serviceCode, 94, JsonUtils.toJsonString(consume),
                "begin iUserCutoffService.queryListByParam", null);
        String jsonStr = "";
        UserCutoff userCutoff;
        try {
            userCutoff=  userCutoffWrapService.merchantUserCutoffDate(merchantId, userId);
            //  jsonStr = iUserCutoffService.queryListByParam(JSON.toJSONString(consume));

            LogUtil.track(serviceCode, 101, jsonStr,
                    "after  iUserCutoffService.queryListByParam", null);

        } catch (Exception e) {
            logger.error("error when iUserCutoffService.queryListByParam" + JsonUtils.toJsonString(consume), e);
            return 0;
        }
        // List<UserCutoff> userCutoff = JSON.parseArray(jsonStr, UserCutoff.class);
        Calendar calendar = new GregorianCalendar();
        consume.setPackageId( packageId);
        consume.setPackageNum( packageNum);
        consume.setTotalNum(  totalNum);
        consume.setOrderId( orderId);
        consume.setPayNum( payNum);
        consume.setAddDay( days);
        consume.setConsumeType( payType);
        consume.setRemarks( "");
       /* consume.setGlobalKey("msp_" + merchantId + userId);
        consume.put("globalValue", System.currentTimeMillis());*/
        if (userCutoff!=null) {
            // 存在记录
            LogUtil.track(serviceCode, 123, jsonStr, "has record ", null);

            if (userCutoff.getCutoffDate() != null && userCutoff.getCutoffDate().getTime() > nowtime.getTime()) {
                // 如果还有上网时长则增加时长
                startime = userCutoff.getCutoffDate();
                calendar.setTime(userCutoff.getCutoffDate());
                calendar.add(Calendar.DATE, days);

            } else {
                // 如果时长过期则当前时间起算
                calendar.setTime(nowtime);
                calendar.add(Calendar.DATE, days);
            }
            // cutoff_date.put("id", ret.getId());
            // cutoff_date.put("remarks", "");
            // cutoff_date.put("cutoffDate",
            // sdf.format(calendar.getTime()));

            consume.setBeginDate(startime);
            consume.setEndDate(calendar.getTime());
           // consume.setCutoffDate(calendar.getTime());
            // 修改用户在商家下的上网时长
            // cutoff_date_return = iUserCutoffService.update(JSON
            // .toJSONString(cutoff_date));
        } else {
            LogUtil.track(serviceCode, 123, jsonStr, "has no record begin add", null);

            // 新增用户在商家下的上网时长
            calendar.setTime(nowtime);
            calendar.add(Calendar.DATE, days);
            // cutoff_date.put("cutoffDate", sdf.format(calendar.getTime()));
            // cutoff_date.put("remarks", "");
            // cutoff_date_return = iUserCutoffService.add(JSON
            // .toJSONString(cutoff_date));
            consume.setBeginDate(startime);
            consume.setEndDate(calendar.getTime());

        }
        // 新增消费记录
        LogUtil.track(serviceCode, 147, JsonUtils.toJsonString(consume),
                "begin iUserConsumeService.addCompConsume  ", null);

        try {
       ;
            userConsumeWrapService.addCompConsume(consume);
        } catch (Exception e) {
            // logger.e
            logger.error("iUserConsumeService.addCompConsume fail", e);
            LogUtil.third(serviceCode, 154, e.getMessage(),
                    "iUserConsumeService.addCompConsume fail ", null);

        }
        LogUtil.track(serviceCode, 157, "", "iUserConsumeService.addCompConsume success ",
                null);

        return 1;
    }

    /**
     * . 增加用户上网时长
     *
     * @param merchantId
     *            商户id
     * @param userId
     *            用户id
     * @param minute
     *            时间（分）
     * @return int
     * @throws Exception
     * @author xhb
     */
    public int addTime(Long merchantId, Long userId, int minute) throws Exception {
        Map<String, Object> cutoff_date = new HashMap<String, Object>();
        Map<String, Object> consume = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int cutoff_date_return = 0;
        Date nowtime = new Date();
        if (merchantId == null || userId == null) {
            return 0;
        }
        cutoff_date.put("merchantId", merchantId);
        cutoff_date.put("userId", userId);
        consume.put("merchantId", merchantId);
        consume.put("userId", userId);
        // 查询用户在商家下的上网时长

        UserCutoff userCutoff;

        userCutoff=  userCutoffWrapService.merchantUserCutoffDate(merchantId, userId);


        Calendar calendar = new GregorianCalendar();
        if (userCutoff!=null) {
            // 存在记录

            if (userCutoff.getCutoffDate() != null && userCutoff.getCutoffDate().getTime() > nowtime.getTime()) {
                return 1;
            } else {
                // 如果时长过期则当前时间起算
                calendar.setTime(nowtime);
                calendar.add(Calendar.MINUTE, minute);
            }
            cutoff_date.put("id", userCutoff.getId());
            cutoff_date.put("remarks", "");
            cutoff_date.put("cutoffDate", sdf.format(calendar.getTime()));
            // 修改用户在商家下的上网时长

            return  userCutoffWrapService.update(cutoff_date);

            //cutoff_date_return = iUserCutoffService.update(JSON.toJSONString(cutoff_date));


        } else {
            // 新增用户在商家下的上网时长
            calendar.setTime(nowtime);
            calendar.add(Calendar.MINUTE, minute);
            cutoff_date.put("cutoffDate", sdf.format(calendar.getTime()));
            cutoff_date.put("remarks", "");
            cutoff_date_return = userCutoffWrapService.add(cutoff_date);
        }
        return cutoff_date_return;
    }

    /**
     * . 当日过期毫秒剩余
     * 临时放通需要用
     * @return long
     * @throws ParseException
     * @author xhb
     */
    public int todaySecondsLeft() throws ParseException {
        Date date = new Date();
        date.setDate(date.getDate() + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(sdf.format(date));
        return (int) ((date.getTime() - System.currentTimeMillis()) / 1000);
    }

    @Resource
    private OnlineWrapService onlineService;
    /**
     * @param username
     *            账号
     * @return boolean
     * @author 宋展辉 2016年3月23日 上午9:32:40
     */
    public boolean isFreeUser(String username) throws Exception {


        OnlineUser onlineUser =new OnlineUser();
        onlineUser.setTelephone(username);
        onlineUser.setNowTime(System.currentTimeMillis() / 1000);
        int flag =onlineService.queryOnlineUserCount(onlineUser);
        return flag > 0;

    }


    public UserTimeInfoDTO getUserTimeInfo(Long userId, String phone, Long merchantId) throws Exception {
        boolean isVipUser = isFreeUser(phone);
        boolean canGetFreePkg = userConsumeWrapService.canGetFreePkg(userId, merchantId);
        if(canGetFreePkg){
            RedisUtil.hset(Constants.REDIS_PKG_GET, userId+""+merchantId,"ok");
        }
        UserTimeInfoDTO userTimeInfo =new UserTimeInfoDTO() ;
        userTimeInfo.setCanGetFreePkg(canGetFreePkg);
        userTimeInfo.setVip(isVipUser);
       UserCutoff userCutoff=  userCutoffWrapService.merchantUserCutoffDate(merchantId, userId);
        if(userCutoff!=null){
            userTimeInfo.setEndTime(userCutoff.getCutoffDate().getTime());
        }
        return userTimeInfo;
    }




}
