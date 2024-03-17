package com.srishti.talento.Exam_model;

import java.util.List;

public class ExamModelRoot {
    private boolean status;

    private String message;

    private List<Question_details> question_details;

    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean getStatus(){
        return this.status;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setQuestion_details(List<Question_details> question_details){
        this.question_details = question_details;
    }
    public List<Question_details> getQuestion_details(){
        return this.question_details;
    }
}
