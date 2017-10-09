package com.dozenx.web.module.timing.service;

import com.dozenx.web.core.log.ServiceCode;
import com.dozenx.web.core.log.service.LogUtilService;
import com.dozenx.web.module.timing.bean.UserCutoff;
import com.dozenx.web.module.timing.service.impl.UserCutoffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserCutoffWrapService {
    ServiceCode serviceCode = ServiceCode.USERCUTOFFWRAP_SERVICE;
    @Resource
    LogUtilService logUtilService;

    @Resource
    private UserCutoffService userCutoffService;
    
    
    private static Logger logger = LoggerFactory.getLogger(UserCutoffWrapService.class);
    /**
     * 用户时常查询
     * @param merchantId 商户id
     * @param userId 用户id
     * @return String
     * @author xhb 
     * @throws Exception
     */
    public UserCutoff merchantUserCutoffDate(Long merchantId, Long userId)  {
        if(merchantId==null){
            logger.info("商户id不能为空");
            logUtilService.param(serviceCode, 38, "userId:"+userId, "商户id不能为空",null);
            return null;
        }
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("merchantId", merchantId);
        parm.put("userId", userId);
        List<UserCutoff> ret =  userCutoffService.selectBymap(parm);
        if(ret!=null && ret.size()>=1){
            if(ret.size()>1){
                logger.error("商户用户剩余时长记录多余1条");
                logUtilService.param(serviceCode, 47, "merchantId:"+merchantId+"userId:"+userId, "商户和用户应只有一条剩余时长记录但是数据库查出"+ret.size()+"条记录",null);
            }
            return ret.get(0);
        }else{
            return null;
        }
       
    }
    
    public int update(Map<String, Object> cutoff_date) {
        Integer result  = 0 ;
        if(cutoff_date != null && !cutoff_date.equals("")){
            Long merchantId = Long.parseLong(cutoff_date.get("merchantId")+"");
            Long userId = Long.parseLong(cutoff_date.get("userId")+"");
            Long id = Long.parseLong(cutoff_date.get("id")+"");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); 
            Date cutoffDate = null;
            try {
                cutoffDate = format.parse(cutoff_date.get("cutoffDate").toString());
            } catch (ParseException e) {
              logger.error(e.getMessage());
            }
            String remarks = cutoff_date.get("remarks")+"";
            return userCutoffService.updateByPrimaryKeySelective(id,merchantId, userId, cutoffDate, remarks);
        }else{
                return result ; 
            }
    }
    
    public int add(Map<String, Object> cutoff_date) {
        Integer result  = 0 ;
        if(cutoff_date != null && !cutoff_date.equals("")){
        Long merchantId = Long.parseLong(cutoff_date.get("cutoff_date")+"");
        Long userId = Long.parseLong(cutoff_date.get("userId")+"");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); 
        Date cutoffDate = null;
        try {
            cutoffDate = format.parse(cutoff_date.get("cutoffDate").toString());
        } catch (ParseException e) {
          logger.error(e.getMessage());
        }
        String remarks = cutoff_date.get("remarks")+"";
        result = userCutoffService.insertSelective(merchantId, userId, cutoffDate, remarks);
        return result ;
    }else{
        return result ; 
    }
    }
}
