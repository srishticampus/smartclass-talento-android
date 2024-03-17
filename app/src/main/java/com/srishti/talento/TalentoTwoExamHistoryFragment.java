package com.srishti.talento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TalentoTwoExamHistoryFragment extends Fragment {

    RecyclerView mainRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_talento_two_exam_history, container, false);

        mainRecyclerView = view.findViewById(R.id.recycler_view_main);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", getContext().MODE_PRIVATE);

        String userId = sharedPreferences.getString("user_id", null);

        examHistoryApi(userId);

        return view;
    }

    void examHistoryApi(String userId) {
        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_EXAM_HISTORY(userId).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        LinearLayoutManager layout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        mainRecyclerView.setLayoutManager(layout);
                        ExamApplicationAdapter examApplicationAdapter = new ExamApplicationAdapter(getContext(), root);
                        mainRecyclerView.setAdapter(examApplicationAdapter);
                    }else {
                        Toast.makeText(getContext(), root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}