package com.srishti.talento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.srishti.talento.CourseModel.CourseModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseActivity extends LMTBaseActivity {

    ShimmerRecyclerView recyclerView;
    String color = "#1b7bf3";
    private final int PACKAGE_API = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        changeStatusBarColor(color);
        recyclerView=findViewById(R.id.course_shimmer_recycler_view);
        recyclerView.showShimmer();
        showPackage();
    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("category",CourseActivity.MODE_PRIVATE);
                String userId=sharedPreferences.getString("user_id",null);


                Api api = ApiClient.UserData().create(Api.class);
                api.COURSE_MODEL_ROOT_CALL(userId).enqueue(new Callback<CourseModelRoot>() {
                    @Override
                    public void onResponse(Call<CourseModelRoot> call, Response<CourseModelRoot> response) {
                        myProgressDialog.dismissProgress();
                        recyclerView.hideShimmer();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(CourseActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(CourseActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        CourseModelRoot courseModelRoot = response.body();
                                        CourseRecyclerAdapter courseRecyclerAdapter=new CourseRecyclerAdapter(courseModelRoot,getApplicationContext());
                                        recyclerView.setAdapter(courseRecyclerAdapter);
                                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                        recyclerView.setLayoutManager(linearLayoutManager);
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
                    public void onFailure(Call<CourseModelRoot> call, Throwable t) {
                        recyclerView.hideShimmer();
                        Toast.makeText(CourseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CourseActivity.this,SmartClassHomeActivity.class));
    }
}