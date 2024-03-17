package com.srishti.talento;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SmartClassAppliedJobDetailsActivity extends AppCompatActivity {
    TextView jobTitle,companyName,qualification,jobDescription,preferredSkill,jobLocation,jobSalary,startDate,endDate;
    String color = "#FFFFFF";
    View backBtn;
    ImageView companyLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_class_applied_job_details);

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
        companyLogo=findViewById(R.id.placement_candidate_iv_home);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        endDate.setText(getIntent().getStringExtra("end_date"));
        Glide.with(getApplicationContext()).asBitmap().load(getIntent().getStringExtra("company_logo")).into(companyLogo);


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