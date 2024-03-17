package com.srishti.talento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.srishti.talento.PlacementModel.PlacementModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacementActivity extends LMTBaseActivity {

    RecyclerView shimmerRecyclerView;
    RecyclerView newRecyclerView;
    private final int PACKAGE_API = 101;
    String color = "#1b7bf3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        changeStatusBarColor(color);
        shimmerRecyclerView=findViewById(R.id.placement_recyclerview);
        newRecyclerView=findViewById(R.id.placement_recyclerview_new);

//        shimmerRecyclerView.showShimmer();
        showPackage();

    }
    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                Api api = ApiClient.UserData().create(Api.class);
                api.PLACEMENT_MODEL_ROOT_CALL().enqueue(new Callback<PlacementModelRoot>() {
                    @Override
                    public void onResponse(Call<PlacementModelRoot> call, Response<PlacementModelRoot> response) {
                        myProgressDialog.dismissProgress();
//                        shimmerRecyclerView.hideShimmer();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(PlacementActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(PlacementActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        PlacementModelRoot placementModelRoot = response.body();
                                        PlacementRecyclerAdapter placementRecyclerAdapter=new PlacementRecyclerAdapter(placementModelRoot,getApplicationContext());
                                        shimmerRecyclerView.setAdapter(placementRecyclerAdapter);
                                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false);
                                        shimmerRecyclerView.setLayoutManager(linearLayoutManager);

                                      //  NewPlacementAdapter newPlacementAdapter=new NewPlacementAdapter(placementModelRoot,getApplicationContext());
                                      //  newRecyclerView.setAdapter(newPlacementAdapter);
                                        LinearLayoutManager linearLayoutManagerNew=new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false);
                                        newRecyclerView.setLayoutManager(linearLayoutManagerNew);

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
                    public void onFailure(Call<PlacementModelRoot> call, Throwable t) {
//                        shimmerRecyclerView.hideShimmer();
                        Toast.makeText(PlacementActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void buttonClick(View view) {
        shimmerRecyclerView.scrollToPosition(3);
    }
}