package com.srishti.talento;

import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.srishti.talento.Home_Category_Model.CategoryRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends LMTBaseActivity {
    ShimmerRecyclerView recyclerView;
    // ShimmerFrameLayout shimmerFrameLayout;
    ImageView homeMenuTv;
    TextView userName;
    String color = "#1b7bf3";

    private final int PACKAGE_API = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeStatusBarColor(color);
        recyclerView = findViewById(R.id.home_recyclerview);
        userName = findViewById(R.id.username);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        userName.setText("Hi "+sharedPreferences.getString("user_name", "user"));

        SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("token", getApplicationContext().MODE_PRIVATE);
        String deviceToken=sharedPreferences1.getString("token",null);
        Log.i("token",deviceToken);
       // Toast.makeText(getApplicationContext(), deviceToken, Toast.LENGTH_SHORT).show();


        homeMenuTv = findViewById(R.id.home_menu);
        homeMenuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), homeMenuTv);
                popupMenu.getMenuInflater().inflate(R.menu.poupup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.one:
                                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                                break;
                            case R.id.two:
                                startActivity(new Intent(HomeActivity.this,PlacementActivity.class));
                                break;
                            case R.id.three:
                                startActivity(new Intent(HomeActivity.this,JobAlertActivity.class));
                                break;
                            case R.id.four:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        recyclerView.showShimmer();
        //   shimmerFrameLayout = findViewById(R.id.shimmer_layout_home);
        showPackage();

    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                Api api = ApiClient.UserData().create(Api.class);
                api.CATEGORY_ROOT_CALL().enqueue(new Callback<CategoryRoot>() {
                    @Override
                    public void onResponse(Call<CategoryRoot> call, Response<CategoryRoot> response) {
                        myProgressDialog.dismissProgress();
                        recyclerView.hideShimmer();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(HomeActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(HomeActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        CategoryRoot categoryRoot = response.body();

                                        HomeRecyclerAdapter homeRecyclerAdapter = new HomeRecyclerAdapter(categoryRoot, getApplicationContext());

//                                        shimmerFrameLayout.stopShimmer();
//                                        shimmerFrameLayout.setVisibility(View.GONE);
//                                        recyclerView.setVisibility(View.VISIBLE);

                                        recyclerView.setAdapter(homeRecyclerAdapter);

                                        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                        GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
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
                    public void onFailure(Call<CategoryRoot> call, Throwable t) {
//                        shimmerFrameLayout.stopShimmer();
                        recyclerView.hideShimmer();
                        Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        shimmerFrameLayout.stopShimmer();
    }

}