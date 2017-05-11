package com.dozenx.web.module.question.bean;

public class QuestionnaireStatus {
    
    private Long userId;
    
    private int status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QuestionnaireStatus [userId=" + userId + ", status=" + status + "]";
    }
    
    
    
    
}
