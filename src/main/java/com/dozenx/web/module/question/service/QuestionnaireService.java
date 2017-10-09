package com.dozenx.web.module.question.service;

import com.alibaba.fastjson.JSON;
import com.dozenx.util.DateUtil;
import com.dozenx.util.RedisUtil;
import com.dozenx.web.core.Constants;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.question.bean.QuestionnaireStatus;
import com.dozenx.web.module.question.bean.QuestionnaireUserInfo;
import com.dozenx.web.module.timing.bean.TimeConsume;
import com.dozenx.web.module.timing.service.TimeBusService;
import com.dozenx.web.module.timing.service.impl.UserConsumeService;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class QuestionnaireService {

    @Autowired
    private QuestDetailService questDetailService;

    @Autowired
    private QuestStatusService questStatusService;

    @Autowired
    private QuestUserInfoService questUserInfoService;

    @Autowired
    private UserConsumeService userConsumeService;
    
    @Autowired
    
    private TimeBusService userWifiTimeService;
    
    private static Logger logger = LoggerFactory.getLogger(QuestionnaireService.class);

    public ResultDTO addQuestionnaire(Long userId, int status, Long merchantId, String apMac, String ssid,
                                      String quest1, String quest2, String quest3, String quest4, String quest5, String quest6, String quest7,
                                      String quest8, String quest9, String quest10, String quest11, String quest12, String quest13,
                                      String quest14, String quest15, String quest16, String remark1, String remark2, String remark3) throws Exception {
        ResultDTO result = new ResultDTO();
            
        
            //添加用户问卷信息
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("consumeType", "3");
            Double allPay = userConsumeService.getUserTotalPayment(map);
            logger.info("userConsumeService.getUserTotalPayment"+ JSON.toJSONString(map));
            if(allPay == null||allPay==0){
               allPay=0D; 
            }
            Float all = Float.valueOf(String.valueOf(allPay));
            
            map.put("pageNum", 1);
            map.put("pageSize", 1);
            
            
            List<TimeConsume> list = userConsumeService.queryListByParam(map);
            
            logger.info("userConsumeService.queryListByParam"+JSON.toJSONString(map));
           // System.out.println(list.get(0).getCreateDate());
            int days = 0;
            Float average =0F;
            if(list.size()!=0){
                days =  daysBetween(list.get(0).getCreateDate()==null?new Date():list.get(0).getCreateDate(), new Date());
                days = days>1?days:1;
                if(all!=0F){
                    average = all / days;
                    BigDecimal b = new BigDecimal(average);
                    average = b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
                }
                logger.info("平均消费"+days+"天"+all+"消费");
                
                questUserInfoService.add(userId, merchantId, apMac, ssid, 0, average);
            }else{
                //throw new Exception("查询用户信息失败!"); 重新修改增加消费信息的流程逻辑
                questUserInfoService.add(userId, merchantId, apMac, ssid, 0, 0f);
            }
           
           
            
            logger.info("QuestionnaireService.questUserInfoService.add"+":用户Id"+ userId+":商户ID"+merchantId+":mac"+apMac+":ssid"+ssid+":平均消费"+average);
            
            /**
             * 修改问卷状态为提交问卷 未领红包
             */
            QuestionnaireStatus questStatus = new QuestionnaireStatus();
            questStatus.setUserId(userId);
            questStatus.setStatus(status);
            
            questStatusService.update(questStatus);
            logger.info("QuestionnaireService.questStatusService.update"+ JSON.toJSONString(questStatus));
            //添加问卷详细信息
        
            
            int i = questDetailService.add(userId, quest1, quest2, quest3, quest4, quest5, quest6, quest7, quest8,
                    quest9, quest10, quest11, quest12, quest13, quest14, quest15, quest16, null, null, null);
            if(i!=1){
                logger.error("添加问卷未成功");
                throw new Exception("添加问卷未成功");
            }
            
            result.setR(1);
            result.setMsg("添加问卷成功");
            logger.error("添加问卷成功");
        
        
        return result;
    }
    
    public ResultDTO addRedPackage(Long userId , Long merchangId) throws Exception{
        ResultDTO result = new ResultDTO();
        
     
            QuestionnaireStatus status = questStatusService.queryByUserId(userId);
            if(status==null){
                throw new Exception("获取用户信息失败!");
            }
            QuestionnaireUserInfo userInfo = questUserInfoService.queryByUserId(userId);
            logger.info("QuestionnaireUserInfo"+JSON.toJSONString(userInfo));
            if(userInfo==null){
                throw new Exception("获取用户信息失败!");
            }
            status.setStatus(3);
            questStatusService.update(status);
            
            logger.info("addRedPackage.questStatusService.update"+JSON.toJSONString(status));
            
            /*int level = userInfo.getAveragePay()>0.4F?5:1;
            level = RedisUtil.incr("redPackage")>50L?1:50;*/
            int level = 0;
            if(userInfo.getAveragePay()==0 || userInfo.getAveragePay()> 0.8){
                level = 1;
            }else if(0<userInfo.getAveragePay()&&userInfo.getAveragePay()<0.4){
                if(RedisUtil.incr(Constants.REDIS_QUESTIONNAIRE_LOW)<=50L){
                    level = 10;
                }else{
                    level = 1;
                }
            }else if(0.4 <= userInfo.getAveragePay()&&userInfo.getAveragePay()<= 0.8){
                if(RedisUtil.incr(Constants.REDIS_QUESTIONNAIRE_MIDDEL)<=50L){
                    level = 5;
                }else{
                    level = 1;
                }
            }else{
                logger.error("用户消费信息有误",userInfo.getAveragePay().toString());
                throw new Exception("用户消费信息有误");
            }
            
            
            userInfo.setFreeLevel(level);
            questUserInfoService.update(userInfo);
            logger.info("questUserInfoService.update"+JSON.toJSONString(userInfo));
            //增加到消费记录表
            TimeConsume userConsume = new TimeConsume();
            
            
            userWifiTimeService.update(merchangId, userId, level, Constants.QUESTIONNAIRE_RED_PACKAGE, 1, 0F, null, 0.0F, 0, 2);
          
            
            logger.info("userWifiTimeService.update:商户ID"+merchangId+"用户ID"+userId);
            result.setR(1);
            result.setData(level);
            result.setMsg("领取红包成功!");
       
        return result;
    }
    
    
    
    /**
     * 
    * 迭代4：TODO 
    * @Description: getEndDate
    * @param level
    * @return
    * @return Date    返回类型 
    * @author w049  
    * @date     2016年12月7日 下午5:33:25
     */
    private Date getEndDate(int level) {
        return DateUtil.dateAdd(1, new Date(), level);
    }

    /**
     * 
     * 迭代4：两个日期之间的天数
     * 
     * @Description: daysBetween
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     * @return int 返回类型
     * @author w049
     * @date 2016年12月5日 下午5:40:11
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        logger.info("计算首次充值到现在的时间:"+ "首充时间"+smdate+"现在时间"+bdate);
        int ca = 187263/878;
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    
    /**
     * 
    * 迭代11：检查问卷是否开始 
    * @Description: isQuestionnaireStart
    * @return
    * @return boolean    返回类型 
    * @author w049  
    * @date     2016年12月21日 上午11:24:53
     */
    public boolean isQuestionnaireStart(){
        String startStr = ConfigUtil.getConfig( "questionnaire.start");
        String endStr = ConfigUtil.getConfig(  "questionnaire.end");
        logger.info("开始时间:"+startStr+"结束时间:"+endStr);
        return DateUtil.compareDate( DateUtil.getNowStringDate(), startStr)&&DateUtil.compareDate(endStr, DateUtil.getNowStringDate());
    }
    
  /**
   * 
  * 迭代11：检查商户是否可以进行问卷 
  * @Description: isMerchantContains
  * @param merchantId
  * @return
  * @return boolean    返回类型 
  * @author w049  
  * @date     2016年12月21日 上午11:25:23
   */
    public boolean isMerchantContains(String merchantId){
        try{
            String merCheck = ConfigUtil.getConfig( "notcontains.merchantid");
//            logger.info("被排除的商户:"+merCheck+"   ;参与活动的商户"+merchantId);
            if(merCheck!=null&&merCheck.length()>0){
                return !(merCheck.equals(merchantId));
            }
            return true;
        }catch(Exception e){
            logger.info(e.getMessage());
            return true;
        }
       
    }
    
    
}
