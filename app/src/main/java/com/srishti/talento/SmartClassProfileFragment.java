package com.srishti.talento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassProfileFragment extends Fragment {
    LinearLayout linearLayout, createCv;
    TextView userName, educationTv, mailTv, mobileTv, notificationCountTv;
    CircleImageView profileIv;
    ImageView coverIv, notificationIcon;
    RecyclerView recyclerView;
    RecyclerView gatePassRecyclerView;
    String firstName, lastName, place, profileImage;
    FrameLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_profile, container, false);

        linearLayout = view.findViewById(R.id.edit_profile_linear_layout);
        userName = view.findViewById(R.id.profile_username);
        educationTv = view.findViewById(R.id.education_txt);
        mailTv = view.findViewById(R.id.mail_id_profile);
        mobileTv = view.findViewById(R.id.mobile_number_profile);
        profileIv = view.findViewById(R.id.profile_image);
        coverIv = view.findViewById(R.id.user_cover_image);
        recyclerView = view.findViewById(R.id.applied_jobs_recycler_view);
        notificationCountTv = view.findViewById(R.id.notification_count_profile);
        notificationIcon = view.findViewById(R.id.notification_icon_profile);
        layout = view.findViewById(R.id.profile_layout);
        createCv = view.findViewById(R.id.create_cv_layout);
        gatePassRecyclerView = view.findViewById(R.id.gate_pass_list_rv);

        //drawer image update
        DuoDrawerLayout navigationView = getActivity().findViewById(R.id.drawer);
        View headerView = navigationView.getMenuView();
//        if (navigationView.isDrawerOpen()){
//            CircleImageView profileImageOne = headerView.findViewById(R.id.userImage);
//            SharedPreferences sharedPreferencesOne = getActivity().getSharedPreferences("category", getActivity().MODE_PRIVATE);
//            Glide.with(getActivity()).asBitmap().load(sharedPreferencesOne.getString("Img",null)).into(profileImageOne);
//        }
        CircleImageView profileImageOne = headerView.findViewById(R.id.userImage);
        TextView userNameDrawer = headerView.findViewById(R.id.nav_drawer_user_name);
        SharedPreferences sharedPreferencesOne = getActivity().getSharedPreferences("category", getActivity().MODE_PRIVATE);
        Glide.with(getActivity()).asBitmap().load(sharedPreferencesOne.getString("Img", null)).into(profileImageOne);
        userNameDrawer.setText(sharedPreferencesOne.getString("user_name", "User"));


        createCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(layout, "This feature is under maintenance", Snackbar.LENGTH_LONG);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                snackbar.show();
            }
        });

//notification icon click
//        notificationIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
//                SmartClassNotificationFragment smartClassNotificationFragment = new SmartClassNotificationFragment();
//                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassNotificationFragment).addToBackStack(null).commit();
//            }
//        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", getContext().MODE_PRIVATE);

        String userId = sharedPreferences.getString("user_id", null);
        // Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();


        //notification count api
//        api.NOTIFICATION_COUNT_MODEL_ROOT_CALL(userId).enqueue(new Callback<NotificationCountModelRoot>() {
//            @Override
//            public void onResponse(Call<NotificationCountModelRoot> call, Response<NotificationCountModelRoot> response) {
//                if (response.isSuccessful()){
//                    NotificationCountModelRoot notificationCountModelRoot=response.body();
//                    if (notificationCountModelRoot.status){
//                        notificationCountTv.setVisibility(View.VISIBLE);
//                        notificationCountTv.setText(Integer.toString(notificationCountModelRoot.notification_count));
//                    }else {
//                        notificationCountTv.setVisibility(View.GONE);
//                    }
//                }else {
//                    Toast.makeText(getContext(), "Error Loading Notification Count", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NotificationCountModelRoot> call, Throwable t) {
//                Toast.makeText(getContext(), "Error Loading Notification Count", Toast.LENGTH_SHORT).show();
//            }
//        });

        viewProfileApiCall(userId);
        gatePassHistoryApi(userId);

//        api.VIEW_PROFILE_MODEL_ROOT_CALL(userId).enqueue(new Callback<ViewProfileModelRoot>() {
//            @Override
//            public void onResponse(Call<ViewProfileModelRoot> call, Response<ViewProfileModelRoot> response) {
//
//
//                ViewProfileModelRoot viewProfileModelRoot = response.body();
//
//                if (response.isSuccessful()) {
//
//
//                    Glide.with(getContext()).asBitmap().load(viewProfileModelRoot.user_details.picture_url).into(profileIv);
//                    Glide.with(getContext()).asBitmap().load(viewProfileModelRoot.user_details.picture_url).into(coverIv);
//
//                    userName.setText(viewProfileModelRoot.user_details.name + " " + viewProfileModelRoot.user_details.last_name);
//                    profileImage = viewProfileModelRoot.user_details.picture_url;
//
//                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", getContext().MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("Img", profileImage);
//                    editor.putString("user_name", userName.getText().toString());
//                    editor.apply();
//
//                    firstName = viewProfileModelRoot.user_details.name;
//                    lastName = viewProfileModelRoot.user_details.last_name;
//                    place = viewProfileModelRoot.user_details.place;
//
//                    educationTv.setText(viewProfileModelRoot.user_details.school);
//                    mailTv.setText(viewProfileModelRoot.user_details.email);
//                    mobileTv.setText(viewProfileModelRoot.user_details.phone);
//                } else {
//                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ViewProfileModelRoot> call, Throwable t) {
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        //applied job api call
//        api.APPLIED_JOB_MODEL_ROOT_CALL(userId).enqueue(new Callback<AppliedJobModelRoot>() {
//            @Override
//            public void onResponse(Call<AppliedJobModelRoot> call, Response<AppliedJobModelRoot> response) {
//                AppliedJobModelRoot appliedJobModelRoot = response.body();
//                if (response.isSuccessful()) {
//                    SmartClassAppliedJobRecyclerAdapter adapter = new SmartClassAppliedJobRecyclerAdapter(appliedJobModelRoot, getContext());
//                    recyclerView.setAdapter(adapter);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                } else {
//                    //  Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AppliedJobModelRoot> call, Throwable t) {
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SmartClassEditProfileActivity.class);
                try {
                    intent.putExtra("userName", userName.getText().toString());
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("education", educationTv.getText().toString());
                    intent.putExtra("mailId", mailTv.getText().toString());
                    intent.putExtra("mobileNumber", mobileTv.getText().toString());
                    intent.putExtra("place", place);
                    if (!profileImage.isEmpty()) {
                        intent.putExtra("img", profileImage);
                    }
                } catch (Exception e) {

                }

                startActivity(intent);
            }
        });

        return view;
    }

    void viewProfileApiCall(String userId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_PROFILE(userId).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();

                    if (talentoTwoRoot.status) {
                        try {
                            Glide.with(getContext()).asBitmap().load(talentoTwoRoot.userDetails.get(0).picture_url).into(profileIv);
                            Glide.with(getContext()).asBitmap().load(talentoTwoRoot.userDetails.get(0).picture_url).into(coverIv);
                        } catch (Exception e) {

                        }

                        userName.setText(talentoTwoRoot.userDetails.get(0).name + " " + talentoTwoRoot.userDetails.get(0).last_name);
                        profileImage = talentoTwoRoot.userDetails.get(0).picture_url;

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Img", profileImage);
                        editor.putString("user_name", userName.getText().toString());
                        editor.apply();

                        firstName = talentoTwoRoot.userDetails.get(0).name;
                        lastName = talentoTwoRoot.userDetails.get(0).last_name;
                        place = talentoTwoRoot.userDetails.get(0).place;

                        educationTv.setText(talentoTwoRoot.userDetails.get(0).school);
                        mailTv.setText(talentoTwoRoot.userDetails.get(0).email);
                        mobileTv.setText(talentoTwoRoot.userDetails.get(0).phone);
                    } else {
                        Toast.makeText(getContext(), "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    void gatePassHistoryApi(String userId) {
       // Toast.makeText(getContext(), userId, Toast.LENGTH_SHORT).show();
        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_GATE_PASS_VIEW(userId).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()){
                    TalentoTwoRoot root = response.body();
                    if (root.status){
                        GatePassHistoryAdapter gatePassHistoryAdapter = new GatePassHistoryAdapter(root,getContext());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        gatePassRecyclerView.setLayoutManager(linearLayoutManager);
                        gatePassRecyclerView.setAdapter(gatePassHistoryAdapter);
                    }else {
                       Toast.makeText(getContext(),  "No Gate Pass Available",Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getContext(),  "Error Loading",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Error Loading", Toast.LENGTH_SHORT).show();
            }
        });
    }
}