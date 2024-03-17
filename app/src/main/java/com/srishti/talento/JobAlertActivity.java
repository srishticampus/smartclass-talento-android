package com.srishti.talento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.srishti.talento.JobAlertModel.JobAlertModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAlertActivity extends LMTBaseActivity {

    ShimmerRecyclerView shimmerRecyclerView;
    private final int PACKAGE_API = 101;
    String color = "#1b7bf3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_alert);

        changeStatusBarColor(color);
        shimmerRecyclerView = findViewById(R.id.job_alert_recyclerview);
        shimmerRecyclerView.showShimmer();
        showPackage();
    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", CourseActivity.MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", null);

                Api api = ApiClient.UserData().create(Api.class);
                api.JOB_ALERT_MODEL_ROOT_CALL(userId).enqueue(new Callback<JobAlertModelRoot>() {
                    @Override
                    public void onResponse(Call<JobAlertModelRoot> call, Response<JobAlertModelRoot> response) {
                        myProgressDialog.dismissProgress();
                        shimmerRecyclerView.hideShimmer();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(JobAlertActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(JobAlertActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        JobAlertModelRoot jobAlertModelRoot = response.body();
                                        JobAlertRecyclerLayout jobAlertRecyclerLayout = new JobAlertRecyclerLayout(jobAlertModelRoot, getApplicationContext());
                                        shimmerRecyclerView.setAdapter(jobAlertRecyclerLayout);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                        shimmerRecyclerView.setLayoutManager(linearLayoutManager);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(Call<JobAlertModelRoot> call, Throwable t) {
                        shimmerRecyclerView.hideShimmer();
                        Toast.makeText(JobAlertActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        myProgressDialog.dismissProgress();
                        System.out.println("t.toString : " + t);
                        showServerErrorAlert(100);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                myProgressDialog.dismissProgress();
                showServerErrorAlert(100);
            }
        } else {
            showNoInternetAlert(PACKAGE_API);
        }
    }

    @Override
    public void retryApiCall(int apiCode) {
        super.retryApiCall(apiCode);
        if (apiCode == PACKAGE_API) {
            showPackage();
        }
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