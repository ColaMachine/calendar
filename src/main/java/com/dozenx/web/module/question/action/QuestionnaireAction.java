package com.dozenx.web.module.question.action;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.dozenx.web.core.Constants;
import com.dozenx.web.core.base.BaseController;
import com.dozenx.web.core.log.ResultDTO;
import com.dozenx.web.module.merchant.bean.SessionDTO;
import com.dozenx.web.module.merchant.bean.SessionUser;
import com.dozenx.web.module.question.bean.QuestionnaireStatus;
import com.dozenx.web.module.question.service.QuestStatusService;
import com.dozenx.web.module.question.service.QuestionnaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value="/questionnaire")
public class QuestionnaireAction extends BaseController {


    @Autowired
    private QuestStatusService questStatusService;

    @Autowired
    private QuestionnaireService questionnaireService;

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

   
    @ResponseBody
    @RequestMapping(value= "/isJoinQuest")
    public ResultDTO isJoinQuest(HttpServletRequest request) {
        SessionDTO sessionDTO  =(SessionDTO)request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser =sessionDTO.getSessionUser();
      //  PubUser user = getUserFromSession();
        Long userId = sessionUser.getId();
        String isAgree = request.getParameter("agree");
        int status = Integer.valueOf(isAgree);

        /*
         * QuestionnaireStatus questStatus = new QuestionnaireStatus();
         * questStatus.setUserId(userId); questStatus.setStatus(status);
         */
        try {
            int i = questStatusService.add(userId, status);

            if (i == 0) {
                return getResult(1, null, "问卷创建成功", null);
            }
            return getResult(0, null, "问卷创建失败", null);

        } catch (Exception e) {
            logger.error("QuestionnaireAction.isJoinQuest.system.exception({})", e.getMessage());
            return  getResult(0, null, e.getMessage(), null);
        }
    }
    @ResponseBody
    @RequestMapping( value= "/submitQuest")
    public ResultDTO submitQuest(HttpServletRequest request) {
        SessionDTO sessionDTO  =(SessionDTO)request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser =sessionDTO.getSessionUser();
        //  PubUser user = getUserFromSession();
        Long userId = sessionUser.getId();
        
        HashMap map =request2Map(request);
        
        
        Long merchantId = (Long) request.getSession().getAttribute("SessionMerchantId");
        String apMac = (String) request.getSession().getAttribute("SessionApMac");
        String ssid = "iausdoayusdu"; /*(String) request.getSession().getAttribute("")*/
        
      /*  String merchantIdStr = (String) map.get("merchantId");
        Long merchantId = Long.valueOf(merchantIdStr);
        String apMac = (String) map.get("apMac");
        String ssid = (String) map.get("ssid");*/
        /**
         * 问卷内容
         */

        String quest1 = (String) map.get("question1");
        String quest2 = (String) map.get("question2");
        String quest3 = (String) map.get("question3");
        String quest4 = (String) map.get("question4");
        String quest5 = (String) map.get("question5");
        String quest6 = (String) map.get("question6");
        String quest7 = (String) map.get("question7");
        String quest8 = (String) map.get("question8");
        String quest9 = (String) map.get("question9");
        String quest10 = (String) map.get("question10");
        String quest11 = (String) map.get("question11");
        String quest12 = (String) map.get("question12");
        String quest13 = (String) map.get("question13");
        String quest14 = (String) map.get("question14");
        String quest15 = (String) map.get("question15");
        String quest16 = (String) map.get("question16");
        
        if(quest1 == null || quest1 == "" || quest2 == null || quest2 == "" || quest3 == null
                || quest3 == "" || quest4 == null || quest4 == "" || quest5 == null || quest5 == "" || quest6 == null
                || quest6 == "" || quest7 == null || quest7 == "" || quest8 == null || quest8 == "" || quest9 == null
                || quest9 == "" || quest10 == null || quest10 == "" || quest11 == null || quest11 == ""
                || quest12 == null || quest12 == "" || quest13 == null || quest13 == "" || quest14 == null
                || quest14 == "" || quest15 == null || quest15 == ""){
            return  getResult(0, null, "问卷调查信息不完整", null);
        }
        

        try {
            return  questionnaireService.addQuestionnaire(userId, 2, merchantId, apMac, ssid, quest1, quest2, quest3, quest4, quest5, quest6, quest7, quest8, quest9, quest10, quest11, quest12, quest13, quest14, quest15, quest16, null, null, null);
        } catch (Exception e) {
            logger.error(e.getMessage());
           return  getResult(0, null, e.getMessage(), null);
        }
    }
    
    
    @ResponseBody
    @RequestMapping(value= "/redPackage")
    
    public ResultDTO redPackage(HttpServletRequest request){
        //String merchantIdStr = request.getParameter("merchantId");

        SessionDTO sessionDTO  =(SessionDTO)request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser =sessionDTO.getSessionUser();
        //  PubUser user = getUserFromSession();
        Long userId = sessionUser.getId();
        Long merchantId = sessionDTO.getMerchant().getMerchantId();
        try {
            return questionnaireService.addRedPackage(userId ,merchantId);
        } catch (Exception e) {
            logger.error("QuestionnaireAction.redPackage.system.exception({})", e.getMessage());
            return  getResult(0, null, e.getMessage(), null); 
        }
        
    }
    
    @ResponseBody
    @RequestMapping(value= "/checkUserStatus")
    public ResultDTO checkUserStatus(HttpServletRequest request) {
        // String userIdStr = request.getParameter("userId");
        // 得到用户的id
        SessionDTO sessionDTO  =(SessionDTO)request.getSession().getAttribute(Constants.SESSION_DTO);
        SessionUser sessionUser =sessionDTO.getSessionUser();
        //  PubUser user = getUserFromSession();
        Long userId = sessionUser.getId();
        if (userId == 01) {
            getResult(0, null, "用户未登录", null);
        }
        //对商户进行排除
        
        String merchantIdStr = request.getParameter("merchantId");
        if(merchantIdStr == null || "".equals(merchantIdStr)){
            return getResult(1,0,"缺少必要参数",null);
        }
        
        if(!questionnaireService.isMerchantContains(merchantIdStr)){
            return getResult(1,0,"园区没有参与",null);
        }
        
        // 得到问卷进程
        QuestionnaireStatus status = null;
        try {
            status = questStatusService.queryByUserId(userId);
            //进行时间的判断
            if(!questionnaireService.isQuestionnaireStart()){
                return getResult(1, 0, "活动未开始", null);
            }
            if (status == null) {
                return getResult(1, 1, "用户未参与问卷", null);
            } else if (status.getStatus() == 0) {
                return getResult(1, 0, "用户不想参与问卷", null);
            } else if (status.getStatus() == 1) {
                return getResult(1, 2, "用户参与,未提交问卷", null);
            } else if (status.getStatus() == 2) {
                return getResult(1, 3, "用户参与,未领取红包", null);
            } else if (status.getStatus() == 3) {
                return getResult(1, 4, "用户参与问卷完成", null);
            }
            
            
            return  getResult(0, null, "数据异常", null);

        } catch (Exception e) {
            logger.error("QuestionnaireAction.checkUserQuestStatus.system.exception({})", e.getMessage());
            return getResult(0, null, e.getMessage(), null);
        }
    }
    
    public  HashMap request2Map(HttpServletRequest request) {
        HashMap map = new HashMap();
         Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            
        
          String paramName = (String) paramNames.nextElement();

          String[] paramValues = request.getParameterValues(paramName);
          if (paramValues.length == 1) {
            String paramValue = paramValues[0];
            if (paramValue.length() != 0) {
             // System.out.println("参数：" + paramName + "=" + paramValue);
                if(paramName.startsWith("question")){
                    String names[]= paramName.split("-");
                    map.put(names[0], map.get(names[0])==null?paramValue: (map.get(names[0])+","+paramValue));
                    
                    
                }
             
            }
          }
        }
        return map;
    }

    

}
