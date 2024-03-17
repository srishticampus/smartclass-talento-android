package com.srishti.talento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.srishti.talento.PackageModel.PackageRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageActivity extends LMTBaseActivity {
    RecyclerView packageRecyclerview;
    ShimmerFrameLayout shimmerFrameLayout;
    String color = "#FFFFFF";

    private final int PACKAGE_API=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        changeStatusBarColor(color);
        packageRecyclerview = findViewById(R.id.package_recyclerview);
        shimmerFrameLayout=findViewById(R.id.package_shimmer_layout);

        showPackage();
    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                Api api = ApiClient.UserData().create(Api.class);
                api.PACKAGE_ROOT_CALL().enqueue(new Callback<PackageRoot>() {
                    @Override
                    public void onResponse(Call<PackageRoot> call, Response<PackageRoot> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(PackageActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(PackageActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        PackageRoot packageRoot = response.body();
                                        PackageRecyclerAdapter packageRecyclerAdapter = new PackageRecyclerAdapter(packageRoot, getApplicationContext());

                                        shimmerFrameLayout.stopShimmer();
                                        shimmerFrameLayout.setVisibility(View.GONE);
                                        packageRecyclerview.setVisibility(View.VISIBLE);

                                        packageRecyclerview.setAdapter(packageRecyclerAdapter);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                        packageRecyclerview.setLayoutManager(linearLayoutManager);
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
                    public void onFailure(Call<PackageRoot> call, Throwable t) {
                        shimmerFrameLayout.stopShimmer();
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
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(PackageActivity.this,HomeActivity.class));
    }
}