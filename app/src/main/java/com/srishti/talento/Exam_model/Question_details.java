package com.srishti.talento.Exam_model;

public class Question_details {
    private String id;

    private String mainques;

    private String subques;

    private String opt1;

    private String opt2;

    private String opt3;

    private String opt4;

    private String ans;

    private String exam_details_id;

    private String test_name;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setMainques(String mainques){
        this.mainques = mainques;
    }
    public String getMainques(){
        return this.mainques;
    }
    public void setSubques(String subques){
        this.subques = subques;
    }
    public String getSubques(){
        return this.subques;
    }
    public void setOpt1(String opt1){
        this.opt1 = opt1;
    }
    public String getOpt1(){
        return this.opt1;
    }
    public void setOpt2(String opt2){
        this.opt2 = opt2;
    }
    public String getOpt2(){
        return this.opt2;
    }
    public void setOpt3(String opt3){
        this.opt3 = opt3;
    }
    public String getOpt3(){
        return this.opt3;
    }
    public void setOpt4(String opt4){
        this.opt4 = opt4;
    }
    public String getOpt4(){
        return this.opt4;
    }
    public void setAns(String ans){
        this.ans = ans;
    }
    public String getAns(){
        return this.ans;
    }
    public void setExam_details_id(String exam_details_id){
        this.exam_details_id = exam_details_id;
    }
    public String getExam_details_id(){
        return this.exam_details_id;
    }
    public void setTest_name(String test_name){
        this.test_name = test_name;
    }
    public String getTest_name(){
        return this.test_name;
    }
}
