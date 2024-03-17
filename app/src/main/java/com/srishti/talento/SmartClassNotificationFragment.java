package com.srishti.talento;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.srishti.talento.Notification_Model.NotificationModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassNotificationFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_notification, container, false);

        recyclerView = view.findViewById(R.id.notification_recycler_view);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", CourseActivity.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        Api api = ApiClient.UserData().create(Api.class);
        api.NOTIFICATION_MODEL_ROOT_CALL(userId).enqueue(new Callback<NotificationModelRoot>() {
            @Override
            public void onResponse(Call<NotificationModelRoot> call, Response<NotificationModelRoot> response) {

                if (response.isSuccessful()) {
                    NotificationModelRoot notificationModelRoot = response.body();
                    if (notificationModelRoot.status) {

                        NotificationRecyclerAdapter notificationRecyclerAdapter = new NotificationRecyclerAdapter(notificationModelRoot, getContext());
                        recyclerView.setAdapter(notificationRecyclerAdapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);

                    } else {
                        Toast.makeText(getContext(), "No Notifications", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NotificationModelRoot> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}