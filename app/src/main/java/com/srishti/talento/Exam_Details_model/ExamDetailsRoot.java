package com.srishti.talento.Exam_Details_model;

import java.util.List;

public class ExamDetailsRoot {
    private boolean status;

    private String message;

    private List<Exam_details> Exam_details;

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
    public void setExam_details(List<Exam_details> Exam_details){
        this.Exam_details = Exam_details;
    }
    public List<Exam_details> getExam_details(){
        return this.Exam_details;
    }
}
