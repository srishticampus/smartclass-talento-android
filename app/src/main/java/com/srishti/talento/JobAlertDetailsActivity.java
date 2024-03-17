package com.srishti.talento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.srishti.talento.ApplyJobModel.ApplyJobModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAlertDetailsActivity extends AppCompatActivity {

    TextView jobTitle,companyName,qualification,jobDescription,preferredSkill,jobLocation,jobSalary,startDate,endDate,jobApply;
    String color = "#FFFFFF";
    View backBtn;
    ImageView companyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_alert_details);

        changeStatusBarColor(color);

        backBtn=findViewById(R.id.job_details_back_btn_view);

        jobTitle=findViewById(R.id.job_alert_details_title);
        companyName=findViewById(R.id.job_alert_details_title_company_name);
      //  qualification=findViewById(R.id.job_alert_details_qualification_tv);
        jobDescription=findViewById(R.id.job_alert_details_job_description_tv);
        preferredSkill=findViewById(R.id.job_alert_details_job_skill_tv);
        jobLocation=findViewById(R.id.job_alert_details_job_location_tv);
        jobSalary=findViewById(R.id.job_alert_details_job_salary_tv);
        startDate=findViewById(R.id.job_alert_details_job_appli_start_date_tv);
        endDate=findViewById(R.id.job_alert_details_job_appli_end_date_tv);
        jobApply=findViewById(R.id.job_apply_btn);
        companyLogo=findViewById(R.id.company_logo_job_alert);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(JobAlertDetailsActivity.this,SmartClassHomeActivity.class);
                intent.putExtra("JobDetailsActivity","fromJobDetailsActivity");
                startActivity(intent);
            }
        });


        jobTitle.setText(getIntent().getStringExtra("job_title"));
        companyName.setText(getIntent().getStringExtra("company_name"));
       // qualification.setText(getIntent().getStringExtra("qualification"));
        jobDescription.setText(getIntent().getStringExtra("job_description"));
        preferredSkill.setText(getIntent().getStringExtra("prefered_skill"));
        jobLocation.setText(getIntent().getStringExtra("job_location"));
        jobSalary.setText(getIntent().getStringExtra("job_salary"));
        startDate.setText(getIntent().getStringExtra("start_date"));
        Glide.with(getApplicationContext()).asBitmap().load(getIntent().getStringExtra("company_logo")).into(companyLogo);
        endDate.setText(getIntent().getStringExtra("end_date"));
        int applyStatus= getIntent().getIntExtra("apply_status",0);

        if (applyStatus==1){
            jobApply.setText("APPLIED");
        }else {
            jobApply.setText("APPLY NOW");
        }


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        String jobId=getIntent().getStringExtra("jobId");

       // Toast.makeText(getApplicationContext(), userId+jobId, Toast.LENGTH_SHORT).show();

        Api api = ApiClient.UserData().create(Api.class);

        jobApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               api.APPLY_JOB_MODEL_ROOT_CALL(userId,jobId).enqueue(new Callback<ApplyJobModelRoot>() {
                   @Override
                   public void onResponse(Call<ApplyJobModelRoot> call, Response<ApplyJobModelRoot> response) {

                       ApplyJobModelRoot applyJobModelRoot=response.body();

                       if (response.isSuccessful()){
                           if (applyStatus==1){
                               Toast.makeText(getApplicationContext(), "Already Applied", Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(getApplicationContext(), "Applied", Toast.LENGTH_SHORT).show();
                               jobApply.setText("APPLIED");
                               Intent intent=new Intent(JobAlertDetailsActivity.this,SmartClassHomeActivity.class);
                               intent.putExtra("JobDetailsActivity","fromJobDetailsActivity");
                               startActivity(intent);
                           }
                       }else {
                           Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<ApplyJobModelRoot> call, Throwable t) {
                       Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

    }
    // changing status bar color
    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
}