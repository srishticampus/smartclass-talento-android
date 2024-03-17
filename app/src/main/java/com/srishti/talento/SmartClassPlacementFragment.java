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

import com.srishti.talento.Notification_Count_Model.NotificationCountModelRoot;
import com.srishti.talento.PlacementModel.PlacementModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassPlacementFragment extends Fragment {

    ShimmerRecyclerView placementRecyclerview,placementrecyclerviewTwo;
    ImageView notificationIcon;
    TextView notificationCountTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_placement, container, false);

        placementRecyclerview = view.findViewById(R.id.placement_recyclerview);
        placementrecyclerviewTwo = view.findViewById(R.id.placement_recyclerview_two);
        notificationCountTv=view.findViewById(R.id.notification_count_placement);
        notificationIcon=view.findViewById(R.id.notification_icon_placement);

        placementrecyclerviewTwo.showShimmer();
        placementRecyclerview.showShimmer();

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

        Api api = ApiClient.UserData().create(Api.class);

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



        api.PLACEMENT_MODEL_ROOT_CALL().enqueue(new Callback<PlacementModelRoot>() {
            @Override
            public void onResponse(Call<PlacementModelRoot> call, Response<PlacementModelRoot> response) {

                placementRecyclerview.hideShimmer();

                PlacementModelRoot placementModelRoot = response.body();
                PlacementRecyclerAdapter placementRecyclerAdapter = new PlacementRecyclerAdapter(placementModelRoot, getContext());
                placementRecyclerview.setAdapter(placementRecyclerAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false) {
                    @Override
                    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                        lp.width = (int) (getWidth() / 1.2);
                        return true;
                    }
                };
                linearLayoutManager.scrollToPositionWithOffset(placementRecyclerAdapter.getItemCount() - (placementRecyclerAdapter.getItemCount() - 2), 75);
                placementRecyclerview.setLayoutManager(linearLayoutManager);
//                placementRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false) {
//                    @Override
//                    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
//                        // force height of viewHolder here, this will override layout_height from xml
//                        lp.width = (int) (getWidth() / 1.2);
//                        return true;
//                    }
//                });


                //autoscroll
//                placementRecyclerview.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Call smooth scroll
//                        placementRecyclerview.smoothScrollToPosition(placementRecyclerAdapter.getItemCount() - (placementRecyclerAdapter.getItemCount() - 2));
//
//                    }
//                });
                //autoscroll
//                placementRecyclerview.setLayoutManager(linearLayoutManager);
//                placementRecyclerview.suppressLayout(true);

                RecyclerViewOnItemClick recyclerViewOnItemClick = new RecyclerViewOnItemClick() {
                    @Override
                    public void recyclerViewItemClick(int position) {
                        // placementRecyclerview.smoothScrollToPosition(position);

                        linearLayoutManager.scrollToPositionWithOffset(position, 75);

                    }
                };

                placementrecyclerviewTwo.hideShimmer();
                NewPlacementAdapter newPlacementAdapter = new NewPlacementAdapter(placementModelRoot, getContext(), recyclerViewOnItemClick);
                placementrecyclerviewTwo.setAdapter(newPlacementAdapter);
                LinearLayoutManager linearLayoutManagerNew = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                placementrecyclerviewTwo.setLayoutManager(linearLayoutManagerNew);

            }

            @Override
            public void onFailure(Call<PlacementModelRoot> call, Throwable t) {
                placementrecyclerviewTwo.hideShimmer();
                placementRecyclerview.hideShimmer();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}