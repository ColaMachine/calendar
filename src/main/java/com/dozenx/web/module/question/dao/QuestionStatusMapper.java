package com.dozenx.web.module.question.dao;


import com.dozenx.web.module.question.bean.QuestionnaireStatus;

public interface QuestionStatusMapper {

    QuestionnaireStatus queryByUserId(Long userId);
    
    int add(QuestionnaireStatus status);
    
    int update(QuestionnaireStatus status);
    
    
    
}
