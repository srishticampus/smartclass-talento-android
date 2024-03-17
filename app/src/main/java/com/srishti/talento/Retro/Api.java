package com.srishti.talento.Retro;

import com.srishti.talento.AppliedJobModel.AppliedJobModelRoot;
import com.srishti.talento.ApplyJobModel.ApplyJobModelRoot;
import com.srishti.talento.CourseModel.CourseModelRoot;
import com.srishti.talento.DeleteAccountModel.DeleteAccountRoot;
import com.srishti.talento.Edit_Profile_Model.EditProfileModelRoot;
import com.srishti.talento.Exam_Details_model.ExamDetailsRoot;
import com.srishti.talento.Exam_model.ExamModelRoot;
import com.srishti.talento.Home_Category_Model.CategoryRoot;
import com.srishti.talento.JobAlertModel.JobAlertModelRoot;
import com.srishti.talento.Login_Model.Signin;
import com.srishti.talento.Notification_Count_Model.NotificationCountModelRoot;
import com.srishti.talento.Notification_Model.NotificationModelRoot;
import com.srishti.talento.PackageModel.PackageRoot;
import com.srishti.talento.PlacementModel.PlacementModelRoot;
import com.srishti.talento.SubmitAnsModel.SubmitAnsRoot;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;
import com.srishti.talento.UserProfileModel.UserProfileModelRoot;
import com.srishti.talento.ViewProfileModel.ViewProfileModelRoot;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("login.php?")
    Call<Signin> SIGNIN_CALL(@Field("username") String username, @Field("device_token") String deviceToken);

    @GET("category.php")
    Call<CategoryRoot> CATEGORY_ROOT_CALL();

    @FormUrlEncoded
    @POST("view_batch.php?")
    Call<CourseModelRoot> COURSE_MODEL_ROOT_CALL(@Field("user_id") String userId);

    @GET("package_type.php")
    Call<PackageRoot> PACKAGE_ROOT_CALL();

    @FormUrlEncoded
    @POST("View_exam.php?")
    Call<ExamDetailsRoot> EXAM_DETAILS_ROOT_CALL(@Field("category") String category, @Field("type") String type, @Field("user_id") String userId, @Field("course_id") String CourseId);

    @FormUrlEncoded
    @POST("View_question.php?")
    Call<ExamModelRoot> EXAM_MODEL_ROOT_CALL(@Field("exam_id") String examId, @Field("type") String type, @Field("category") String category);

    @FormUrlEncoded
    @POST("submitAns.php?")
    Call<SubmitAnsRoot> SUBMIT_ANS_ROOT_CALL(@Field("question_set") String questionSet,
                                             @Field("question_id") String QuesId,
                                             @Field("option") String option,
                                             @Field("answer") String answer,
                                             @Field("userid") String userId);

//    @FormUrlEncoded
//    @POST("submitAnswer.php?")
//    Call<SubmitResultRoot> SUBMIT_RESULT_ROOT_CALL(@Field("user_id") String userId,
//                                                   @Field("type") String type,
//                                                   @Field("exam_id") String examId,
//                                                   @Field("score") String score,
//                                                   @Field("total_question") String totalQuestion,
//                                                   @Field("total_not_attend") String toatalNotAttend,
//                                                   @Field("time_start") String timeStart,
//                                                   @Field("time_end") String timeEnd,
//                                                   @Field("category") String category);

    @FormUrlEncoded
    @POST("delete_account.php?")
    Call<DeleteAccountRoot> DELETE_ACCOUNT_ROOT_CALL(@Field("user_id") String userId);

    @GET("placed_candidate.php")
    Call<PlacementModelRoot> PLACEMENT_MODEL_ROOT_CALL();

    @GET("view_job.php")
    Call<JobAlertModelRoot> JOB_ALERT_MODEL_ROOT_CALL(@Query("user_id") String userId);

    @FormUrlEncoded
    @POST("view_profile.php?")
    Call<UserProfileModelRoot> USER_PROFILE_MODEL_ROOT_CALL(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("view_profil.php?")
    Call<ViewProfileModelRoot> VIEW_PROFILE_MODEL_ROOT_CALL(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("apply_job.php?")
    Call<ApplyJobModelRoot> APPLY_JOB_MODEL_ROOT_CALL(@Field("user_id") String userId, @Field("job_id") String jobId);

    @FormUrlEncoded
    @POST("view_user_job.php?")
    Call<AppliedJobModelRoot> APPLIED_JOB_MODEL_ROOT_CALL(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("search_job.php?")
    Call<JobAlertModelRoot> JOB_ALERT_MODEL_ROOT_CALL_SEARCH(@Field("search") String search);

    @Multipart
    @POST("edit_profile.php")
    Call<EditProfileModelRoot> EDIT_PROFILE_MODEL_ROOT_CALL(@Part("user_id") RequestBody userId,
                                                            @Part("name") RequestBody name,
                                                            @Part("last_name") RequestBody lastName,
                                                            @Part("email") RequestBody email,
                                                            @Part("phone") RequestBody phone,
                                                            @Part("place") RequestBody place,
                                                            @Part("school") RequestBody school,
                                                            @Part MultipartBody.Part file);

    @Multipart
    @POST("edit_profile.php")
    Call<EditProfileModelRoot> EDIT_PROFILE_ROOT_CALL(@Part("user_id") RequestBody userId,
                                                      @Part("name") RequestBody name,
                                                      @Part("last_name") RequestBody lastName,
                                                      @Part("email") RequestBody email,
                                                      @Part("phone") RequestBody phone,
                                                      @Part("place") RequestBody place,
                                                      @Part("school") RequestBody school);

    @FormUrlEncoded
    @POST("view_notification.php?")
    Call<NotificationModelRoot> NOTIFICATION_MODEL_ROOT_CALL(@Field("user_id") String userId);

    @GET("notification_count.php?")
    Call<NotificationCountModelRoot> NOTIFICATION_COUNT_MODEL_ROOT_CALL(@Query("user_id") String userId);


    //Talento V2.0

    @GET("login.php")
    Call<TalentoTwoRoot> TALENTO_TWO_LOGIN(@Query("phone") String phoneNumber,
                                           @Query("device_token") String deviceToken,
                                           @Query("password")String password);

    @GET("view_technologies.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_TECHNOLOGIES();

    @GET("view_profil.php")
    Call<TalentoTwoRoot> TALENTO_TWO_PROFILE(@Query("user_id") String userId);

//    @Multipart
//    @POST("edit_profile.php")
//    Call<TalentoTwoRoot> TALENTO_TWO_EDIT_PROFILE_WITH_IMAGE_CALL(@Part("user_id") RequestBody userId,
//                                                                  @Part("name") RequestBody name,
//                                                                  @Part("email") RequestBody email,
//                                                                  @Part("phone") RequestBody phone,
//                                                                  @Part("place") RequestBody place,
//                                                                  @Part("school") RequestBody school,
//                                                                  @Part MultipartBody.Part file);
//
//    @Multipart
//    @POST("edit_profile.php")
//    Call<TalentoTwoRoot> TALENTO_TWO_EDIT_PROFILE_WITHOUT_IMAGE(@Part("user_id") RequestBody userId,
//                                                                @Part("name") RequestBody name,
//                                                                @Part("email") RequestBody email,
//                                                                @Part("phone") RequestBody phone,
//                                                                @Part("place") RequestBody place,
//                                                                @Part("school") RequestBody school);

    @GET("View_exam.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_EXAM(@Query("tech_id") String techId,
                                               @Query("user_id") String userId);

    @GET("View_question.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_QUESTIONS(@Query("exam_id") String examId,
                                                    @Query("user_id") String userId,
                                                    @Query("tech_id") String techId);

    @GET("asnwer_submission.php")
    Call<TalentoTwoRoot> TALENTO_TWO_ANSWER_SUBMISSION(@Query("exam_id") String examId,
                                                       @Query("question_id") String questionId,
                                                       @Query("answer") String answer,
                                                       @Query("option") String option,
                                                       @Query("userid")String userId);

    @GET("final_submission.php")
    Call<TalentoTwoRoot> TALENTO_TWO_FINAL_ANSWER_SUBMISSION(@Query("user_id") String userId,
                                                             @Query("exam_id") String examId,
                                                             @Query("score") String score,
                                                             @Query("total_question") String totalQuestion,
                                                             @Query("total_not_attend") String toatalNotAttend,
                                                             @Query("time_start") String timeStart,
                                                             @Query("time_end") String timeEnd);

    @GET("exam_history.php")
    Call<TalentoTwoRoot> TALENTO_TWO_EXAM_HISTORY(@Query("user_id") String userId);

    @Multipart
    @POST("student_reg.php")
    Call<TalentoTwoRoot> TALENTO_TWO_USER_REG(@Part("name") RequestBody name,
                                              @Part("email") RequestBody email,
                                              @Part("phone") RequestBody phone,
                                              @Part("place") RequestBody place,
                                              @Part("school") RequestBody school,
                                              @Part("type") RequestBody courseType,
                                              @Part("technology") RequestBody batchId,
                                              @Part MultipartBody.Part file,
                                              @Part("device_token") RequestBody device_token,
                                                @Part("staff_code")RequestBody staffCode,
                                                @Part("password")RequestBody password);

    @Multipart
    @POST("edit_profile.php")
    Call<TalentoTwoRoot> TALENTO_TWO_EDIT_PROFILE(@Part("user_id")RequestBody userId,
                                                  @Part("name")RequestBody name,
                                                  @Part("email")RequestBody email,
                                                  @Part("phone")RequestBody phone,
                                                  @Part("place")RequestBody place,
                                                  @Part("school")RequestBody school,
                                                  @Part MultipartBody.Part file);
    @GET("view_profil.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_PROFILE(@Query("user_id") String userId);

    @FormUrlEncoded
    @POST("verify_exam_code.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VERIFY_EXAM_CODE(@Field("user_id") String userId,
                                                  @Field("code") String code);

    @FormUrlEncoded
    @POST("exam_result.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_EXAM_RESULT(@Field("user_id") String userId,
                                                      @Field("exam_id") String examId,
                                                      @Field("score") String score,
                                                      @Field("total_question") String totalQue,
                                                      @Field("total_not_attend") String notAttended,
                                                      @Field("time_start") String timeStart,
                                                      @Field("time_end") String timeEnd);

//    @Multipart
//    @POST("request_gatepass.php")
//    Call<TalentoTwoRoot> TALENTO_TWO_GATE_PASS(@Part("uid")RequestBody userId,
//                                                  @Part("name")RequestBody name,
//                                                  @Part("tech_id")RequestBody techId,
//                                                  @Part("start_date")RequestBody startDate,
//                                                  @Part("end_date")RequestBody endDate,
//                                                  @Part MultipartBody.Part file);

    @Multipart
    @POST("request_gatepass.php")
    Call<TalentoTwoRoot> TALENTO_TWO_GATE_PASS_REGULAR(@Part("uid")RequestBody userId,
                                               @Part("name")RequestBody name,
                                               @Part("tech_id")RequestBody techId,
                                               @Part("start_date")RequestBody startDate,
                                               @Part("end_date")RequestBody endDate,
                                               @Part("status")RequestBody status,
                                               @Part MultipartBody.Part file);

    @Multipart
    @POST("request_gatepass.php")
    Call<TalentoTwoRoot> TALENTO_TWO_GATE_PASS_EVENT(@Part("uid")RequestBody userId,
                                                       @Part("name")RequestBody name,
                                                       @Part("tech_id")RequestBody techId,
                                                       @Part("status")RequestBody status,
                                                       @Part("event_id")RequestBody eventId,
                                                       @Part MultipartBody.Part file);
    @GET("view_events.php")
    Call<TalentoTwoRoot> TALENTO_TWO_VIEW_EVENTS();

    @GET("view_gatepass_details.php")
    Call<TalentoTwoRoot> TALENTO_TWO_GATE_PASS_VIEW(@Query("userid") String userId);


}
