package com.srishti.talento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.UserProfileModel.UserProfileModelRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends LMTBaseActivity {

    ImageView userImage;
    TextView username, userSubStatus, userPhone, userMailid;
    private final int PACKAGE_API = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userImage = findViewById(R.id.profile_iv);
        username = findViewById(R.id.profile_username);
        userSubStatus = findViewById(R.id.profile_user_sub_status_tv);
        userPhone = findViewById(R.id.profile_phone_number);
        userMailid = findViewById(R.id.profile_email_id);
        showPackage();
    }

    private void showPackage() {
        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", null);

                Api api = ApiClient.UserData().create(Api.class);
                api.USER_PROFILE_MODEL_ROOT_CALL(userId).enqueue(new Callback<UserProfileModelRoot>() {
                    @Override
                    public void onResponse(Call<UserProfileModelRoot> call, Response<UserProfileModelRoot> response) {
                        myProgressDialog.dismissProgress();

                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(UserProfileActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        Toast.makeText(UserProfileActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                                    } else {

                                        UserProfileModelRoot userProfileModelRoot = response.body();
                                        username.setText(userProfileModelRoot.profile_details.user_name);
                                        userSubStatus.setText(userProfileModelRoot.profile_details.sub_status);
                                        userPhone.setText(userProfileModelRoot.profile_details.phone);
                                        userMailid.setText(userProfileModelRoot.profile_details.user_emailid);
                                        Glide.with(getApplicationContext()).asBitmap().load(userProfileModelRoot.profile_details.picture_url).into(userImage);
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
                    public void onFailure(Call<UserProfileModelRoot> call, Throwable t) {

                        Toast.makeText(UserProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
}