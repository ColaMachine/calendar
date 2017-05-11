package com.dozenx.web.module.question.service;

import com.dozenx.web.module.question.bean.QuestionnaireDetail;
import com.dozenx.web.module.question.dao.QuestDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class QuestDetailService {

    @Autowired
    private QuestDetailMapper questDetailMapper;

    public int add(Long userId, String quest1, String quest2, String quest3, String quest4, String quest5,
            String quest6, String quest7, String quest8, String quest9, String quest10, String quest11, String quest12,
            String quest13, String quest14, String quest15, String quest16, String remark1, String remark2,
            String remark3) throws Exception {
        QuestionnaireDetail detail = new QuestionnaireDetail();
        if (userId == 0 || quest1 == null || quest1 == "" || quest2 == null || quest2 == "" || quest3 == null
                || quest3 == "" || quest4 == null || quest4 == "" || quest5 == null || quest5 == "" || quest6 == null
                || quest6 == "" || quest7 == null || quest7 == "" || quest8 == null || quest8 == "" || quest9 == null
                || quest9 == "" || quest10 == null || quest10 == "" || quest11 == null || quest11 == ""
                || quest12 == null || quest12 == "" || quest13 == null || quest13 == "" || quest14 == null
                || quest14 == "" || quest15 == null || quest15 == "") {

            throw new Exception("问卷调查参数不完整");
        }

        detail.setUserId(userId);
        detail.setQuestion1(quest1);
        detail.setQuestion2(quest2);
        detail.setQuestion3(quest3);
        detail.setQuestion4(quest4);
        detail.setQuestion5(quest5);
        detail.setQuestion6(quest6);
        detail.setQuestion7(quest7);
        detail.setQuestion8(quest8);
        detail.setQuestion9(quest9);
        detail.setQuestion10(quest10);
        detail.setQuestion11(quest11);
        detail.setQuestion12(quest12);
        detail.setQuestion13(quest13);
        detail.setQuestion14(quest14);
        detail.setQuestion15(quest15);
        detail.setQuestion16(quest16);
        detail.setRemark1(remark1);
        detail.setRemark2(remark2);
        detail.setRemark3(remark3);
        
        return questDetailMapper.add(detail);
    }

}
