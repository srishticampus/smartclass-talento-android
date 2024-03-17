package com.srishti.talento;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.srishti.talento.JobAlertModel.JobAlertModelRoot;
import com.srishti.talento.Notification_Count_Model.NotificationCountModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassJobAlertFragment extends Fragment {

    ShimmerRecyclerView recyclerView;
    ImageView notificationIcon;
    TextView notificationCountTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_job_alert, container, false);

        recyclerView=view.findViewById(R.id.job_alert_recycler_view_new);
        notificationCountTv=view.findViewById(R.id.notification_count_job);
        notificationIcon=view.findViewById(R.id.notification_icon_job);
        recyclerView.showShimmer();

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                SmartClassNotificationFragment smartClassNotificationFragment = new SmartClassNotificationFragment();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassNotificationFragment).addToBackStack(null).commit();
            }
        });


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", CourseActivity.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        Api api= ApiClient.UserData().create(Api.class);


        api.NOTIFICATION_COUNT_MODEL_ROOT_CALL(userId).enqueue(new Callback<NotificationCountModelRoot>() {
            @Override
            public void onResponse(Call<NotificationCountModelRoot> call, Response<NotificationCountModelRoot> response) {
                if (response.isSuccessful()){
                    NotificationCountModelRoot notificationCountModelRoot=response.body();
                    if (notificationCountModelRoot.status){
                        notificationCountTv.setVisibility(View.VISIBLE);
                        notificationCountTv.setText(Integer.toString(notificationCountModelRoot.notification_count));
                    }else {
                        notificationCountTv.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(getContext(), "Error Loading Notification Count", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationCountModelRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Error Loading Notification Count", Toast.LENGTH_SHORT).show();
            }
        });



        api.JOB_ALERT_MODEL_ROOT_CALL(userId).enqueue(new Callback<JobAlertModelRoot>() {
            @Override
            public void onResponse(Call<JobAlertModelRoot> call, Response<JobAlertModelRoot> response) {
                JobAlertModelRoot jobAlertModelRoot=response.body();
                recyclerView.hideShimmer();
                JobAlertRecyclerLayout jobAlertRecyclerLayout=new JobAlertRecyclerLayout(jobAlertModelRoot,getContext());
                recyclerView.setAdapter(jobAlertRecyclerLayout);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onFailure(Call<JobAlertModelRoot> call, Throwable t) {
                recyclerView.hideShimmer();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}