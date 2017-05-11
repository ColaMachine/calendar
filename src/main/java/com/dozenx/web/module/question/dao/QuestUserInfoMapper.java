package com.dozenx.web.module.question.dao;


import com.dozenx.web.module.question.bean.QuestionnaireUserInfo;

public interface QuestUserInfoMapper {
    
    
    int add(QuestionnaireUserInfo info);
    
    QuestionnaireUserInfo queryByUserId(Long userId);
    
    int update(QuestionnaireUserInfo info);
    
    
    
}
