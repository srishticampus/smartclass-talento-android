package com.srishti.talento;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassCourseFragment extends Fragment {

    ShimmerRecyclerView recyclerView;
    View backButton;

    RelativeLayout gatePassBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_course, container, false);


        backButton = view.findViewById(R.id.back_btn_view_course);
        recyclerView = view.findViewById(R.id.course_recycler_view_new);
        gatePassBt = view.findViewById(R.id.gate_pass_rl_bt);

        recyclerView.showShimmer();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", CourseActivity.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        viewTechnologyApiCall();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SmartClassHomeActivity.class));

            }
        });

        gatePassBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you requesting pass for any event?");
                builder.setTitle("Alert !");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), GatePassActivity.class);
                    intent.putExtra("event", true);
                    startActivity(intent);
                });

                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), GatePassActivity.class);
                    intent.putExtra("event", false);
                    startActivity(intent);
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        return view;
    }

    void viewTechnologyApiCall() {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_TECHNOLOGIES().enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();
                    recyclerView.hideShimmer();
                    if (talentoTwoRoot.status) {
                        SmartClassCourseRecyclerAdapter smartClassCourseRecyclerAdapter =
                                new SmartClassCourseRecyclerAdapter(talentoTwoRoot, getActivity());
                        try {
                            talentoTwoRoot.batch_details.remove(0);
                            smartClassCourseRecyclerAdapter.notifyItemRemoved(0);
                        }catch (Exception e){

                        }
                        recyclerView.setAdapter(smartClassCourseRecyclerAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                        recyclerView.setLayoutManager(gridLayoutManager);
                    }

                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                recyclerView.hideShimmer();
                Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}