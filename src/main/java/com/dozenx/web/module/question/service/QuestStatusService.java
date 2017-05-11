package com.dozenx.web.module.question.service;

import com.dozenx.web.module.question.bean.QuestionnaireStatus;
import com.dozenx.web.module.question.dao.QuestionStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestStatusService {

    @Autowired
    private QuestionStatusMapper questionStatusMapper;
    
    public QuestionnaireStatus queryByUserId(Long userId)throws Exception{
        
        if(userId <= 0){
            throw new Exception("用户ID不合法");
        }
        return questionStatusMapper.queryByUserId(userId);
    }
    
    
    public int add( Long userId , int status )throws Exception{
        if(userId <=0){
            throw new Exception("用户ID不合法");
        }
        if(status > 3){
            throw new Exception("问卷状态不合法");
        }
        QuestionnaireStatus questStatus = new QuestionnaireStatus();
        questStatus.setUserId(userId);
        questStatus.setStatus(status);
        return questionStatusMapper.add(questStatus);
    }
    
    
    public int update(QuestionnaireStatus status)throws Exception{
        if(status.getUserId() <=0){
            throw new Exception("用户ID不合法");
        }
        if(status.getStatus() > 3){
            throw new Exception("问卷状态不合法");
        }
        return questionStatusMapper.update(status);
    }
    
    
    
}
