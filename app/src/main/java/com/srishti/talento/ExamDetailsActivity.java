package com.srishti.talento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.Exam_Details_model.ExamDetailsRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamDetailsActivity extends LMTBaseActivity {
    ShimmerRecyclerView recyclerView;
    ImageView errorImage;
    View backBtn;
    String color = "#26509C";
    private final int PACKAGE_API = 101;
    String status = "2";
    String courseImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);

        backBtn = findViewById(R.id.exam_details_back_btn_view);

        //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

        if (null == (getIntent().getStringExtra("fromCourse"))) {
            // status="2";
            // Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
        } else {
            status = getIntent().getStringExtra("fromCourse");
            //Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();

        }
//        if (null != getIntent().getStringExtra("courseIdExtra")) {
//            status = 0;
//        } else {
//            status = 1;
//        }
//String courseIdExtra=getIntent().getStringExtra("courseIdExtra");
//        if (courseIdExtra.equals("")) {
//            status = 0;
//        }

        changeStatusBarColor(color);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExamDetailsActivity.this, SmartClassHomeActivity.class);

                if (status.equals("1")) {
                    intent.putExtra("fromED", "1");
                } else {
                    intent.putExtra("fromED", "2");
                }
                startActivity(intent);
            }
        });

         courseImageUrl = getIntent().getStringExtra("course_image");

        recyclerView = findViewById(R.id.exam_details_recyclerview);

        errorImage = findViewById(R.id.error_image);
        recyclerView.showShimmer();
        showPackage();
    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
                String category = getIntent().getStringExtra("category_name");
                String typeName = "Multiple choice";
                String userId = sharedPreferences.getString("user_id", null);
                String courseId = sharedPreferences.getString("courseId", null);
                //batch id == techId == courseId
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("category_name", category);
                editor.apply();


                Api api = ApiClient.UserData().create(Api.class);

                viewExamsApiCall(courseId,userId);
               // Toast.makeText(this, courseId+userId, Toast.LENGTH_SHORT).show();

//                api.EXAM_DETAILS_ROOT_CALL(category, typeName, userId, courseId).enqueue(new Callback<ExamDetailsRoot>() {
//                    @Override
//                    public void onResponse(Call<ExamDetailsRoot> call, Response<ExamDetailsRoot> response) {
//                        myProgressDialog.dismissProgress();
//                        try {
//                            if (!response.isSuccessful()) {
//                                Toast.makeText(ExamDetailsActivity.this, "Failed! please click BACK button to exit", Toast.LENGTH_SHORT).show();
//
//                                errorImage.setVisibility(View.VISIBLE);
//                                recyclerView.hideShimmer();
//                            } else {
//                                try {
//                                    if (!response.body().getStatus()) {
//                                        Toast.makeText(ExamDetailsActivity.this, "Failed! please click BACK button to exit", Toast.LENGTH_SHORT).show();
//
//                                        errorImage.setVisibility(View.VISIBLE);
//                                        recyclerView.hideShimmer();
//                                    } else {
//
//                                        ExamDetailsRoot examDetailsRoot = response.body();
//                                        ExamDetailsRecyclerAdapter examDetailsRecyclerAdapter = new ExamDetailsRecyclerAdapter(examDetailsRoot, getApplicationContext());
//
//
//                                        recyclerView.hideShimmer();
//
//                                        recyclerView.setAdapter(examDetailsRecyclerAdapter);
//                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
//                                        recyclerView.setLayoutManager(linearLayoutManager);
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//
//                    @Override
//                    public void onFailure(Call<ExamDetailsRoot> call, Throwable t) {
//                        recyclerView.hideShimmer();
//                        myProgressDialog.dismissProgress();
//                        System.out.println("t.toString : " + t.toString());
//                        showServerErrorAlert(100);
//                        Toast.makeText(ExamDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
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

    //apiCall
    void viewExamsApiCall(String techId, String userId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_EXAM(techId, userId).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                myProgressDialog.dismissProgress();
                try {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ExamDetailsActivity.this, "Failed! please click BACK button to exit", Toast.LENGTH_SHORT).show();

                        errorImage.setVisibility(View.VISIBLE);
                        recyclerView.hideShimmer();
                    } else {
                        try {
                            if (!response.body().status) {
                                Toast.makeText(ExamDetailsActivity.this, "Failed! please click BACK button to exit", Toast.LENGTH_SHORT).show();

                                errorImage.setVisibility(View.VISIBLE);
                                recyclerView.hideShimmer();
                            } else {

                                TalentoTwoRoot talentoTwoRoot = response.body();
                                ExamDetailsRecyclerAdapter examDetailsRecyclerAdapter = new ExamDetailsRecyclerAdapter(talentoTwoRoot, getApplicationContext(),techId,courseImageUrl);


                                recyclerView.hideShimmer();

                                recyclerView.setAdapter(examDetailsRecyclerAdapter);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
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
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {

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

    @Override
    public void onBackPressed() {
//        if (getIntent().getStringExtra("").equals("")){
//            startActivity(new Intent(ExamDetailsActivity.this, HomeActivity.class));
//        }else {
//            startActivity(new Intent(ExamDetailsActivity.this,CourseActivity.class));
//        }

//        if (status == 0) {
//            startActivity(new Intent(ExamDetailsActivity.this, CourseActivity.class));
//        } else {
        Intent intent = new Intent(ExamDetailsActivity.this, SmartClassHomeActivity.class);
        if (status.equals("1")) {
            intent.putExtra("fromED", "1");
        } else {
            intent.putExtra("fromED", "2");
        }
        startActivity(intent);
//        }

    }
}