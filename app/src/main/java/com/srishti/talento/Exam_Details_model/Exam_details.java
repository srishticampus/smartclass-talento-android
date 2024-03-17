package com.srishti.talento.Exam_Details_model;

public class Exam_details {
    private String id;

    private String test_name;

    private String test_type;

    private String duration;

    private String no_of_ques;

    private String exam_access_code;

    private String exam_level;

    private String exam_company_code;

    private String exam_college_id;

    private String visibility;

    private String score;

    private int view_status;

    private String lock_status;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_name() {
        return this.test_name;
    }

    public void setTest_type(String test_type){
        this.test_type = test_type;
    }
    public String getTest_type(){
        return this.test_type;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setNo_of_ques(String no_of_ques) {
        this.no_of_ques = no_of_ques;
    }

    public String getNo_of_ques() {
        return this.no_of_ques;
    }

    public void setExam_access_code(String exam_access_code) {
        this.exam_access_code = exam_access_code;
    }

    public String getExam_access_code() {
        return this.exam_access_code;
    }

    public void setExam_level(String exam_level) {
        this.exam_level = exam_level;
    }

    public String getExam_level() {
        return this.exam_level;
    }

    public void setExam_company_code(String exam_company_code) {
        this.exam_company_code = exam_company_code;
    }

    public String getExam_company_code() {
        return this.exam_company_code;
    }

    public void setExam_college_id(String exam_college_id) {
        this.exam_college_id = exam_college_id;
    }

    public String getExam_college_id() {
        return this.exam_college_id;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public void setScore(String score){
        this.score = score;
    }
    public String getScore(){
        return this.score;
    }
    public void setView_status(int view_status){
        this.view_status = view_status;
    }
    public int getView_status(){
        return this.view_status;
    }
    public void setLock_status(String lock_status){
        this.lock_status = lock_status;
    }
    public String getLock_status(){
        return this.lock_status;
    }
}
