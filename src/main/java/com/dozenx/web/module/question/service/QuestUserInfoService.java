package com.dozenx.web.module.question.service;

import com.dozenx.web.module.question.bean.QuestionnaireUserInfo;
import com.dozenx.web.module.question.dao.QuestUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestUserInfoService {

    @Autowired
    private QuestUserInfoMapper questUserInfoMapper;

    public int add(Long userId, Long merchantId, String apMac, String ssid, int freeLevel, float averagePay)
            throws Exception {

        if (userId == 0L || merchantId == 0L || apMac == null || apMac == "" || ssid == "" || ssid == null
                ) {
            
            throw new Exception("参数不完整");
            
        }

        QuestionnaireUserInfo userInfo = new QuestionnaireUserInfo();
        userInfo.setUserId(userId);
        userInfo.setMerchantId(merchantId);
        userInfo.setApMac(apMac);
        userInfo.setSsid(ssid);
        userInfo.setFreeLevel(freeLevel);
        userInfo.setAveragePay(averagePay);

        return questUserInfoMapper.add(userInfo);
    }
    
    
    public QuestionnaireUserInfo queryByUserId(Long userId)throws Exception{
        if(userId<=0){
            throw new Exception("用户ID不合法");
        }
        return questUserInfoMapper.queryByUserId(userId);
    }
    
    
    public int update(QuestionnaireUserInfo userInfo)throws Exception{
        return questUserInfoMapper.update(userInfo);
    }
    

}
