package com.srishti.talento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.srishti.talento.JobAlertModel.JobAlertModelRoot;
import com.srishti.talento.Notification_Count_Model.NotificationCountModelRoot;
import com.srishti.talento.PlacementModel.PlacementModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.todkars.shimmer.ShimmerRecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SmartClassHomeFragment extends Fragment {
    ShimmerRecyclerView placementRecyclerView;
    ViewPager viewPager;

    RelativeLayout itJobcellLt, companyLt, collegeLt, campusLt, homeViewPagerLoadingRl;
    ImageView notificationIcon;

    TextView notificationCountTv;

    //new
    LinearLayout llPagerDots;
    SmartClassHomeViewPagerAdapter smartClassHomeViewPagerAdapter;
    ImageView[] ivArrayDotsPager;
//


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_class_home, container, false);


        notificationIcon = view.findViewById(R.id.notification_icon_home);
        notificationCountTv = view.findViewById(R.id.tvCartCountNotification);
        placementRecyclerView = view.findViewById(R.id.home_placement_recycler);
        viewPager = view.findViewById(R.id.home_view_pager);
        llPagerDots = view.findViewById(R.id.pager_dots);

        homeViewPagerLoadingRl = view.findViewById(R.id.home_view_pager_loading);

        itJobcellLt = view.findViewById(R.id.home_it_jobcell_layout);
        companyLt = view.findViewById(R.id.home_company_layout);
        collegeLt = view.findViewById(R.id.home_college_layout);
        campusLt = view.findViewById(R.id.home_campus_layout);

        //drawer image update
        DuoDrawerLayout navigationView = getActivity().findViewById(R.id.drawer);
        View headerView=navigationView.getMenuView();
        CircleImageView profileImage = headerView.findViewById(R.id.userImage);
        TextView userNameDrawer=headerView.findViewById(R.id.nav_drawer_user_name);
        SharedPreferences sharedPreferencesone = getActivity().getSharedPreferences("category", getActivity().MODE_PRIVATE);
        Glide.with(getActivity()).asBitmap().load(sharedPreferencesone.getString("Img",null)).into(profileImage);
        userNameDrawer.setText(sharedPreferencesone.getString("user_name","User"));


        placementRecyclerView.showShimmer();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("category", CourseActivity.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);
        String category = sharedPreferences.getString("category_name", "noid");
       // Log.i("userid",userId);

        Api api = ApiClient.UserData().create(Api.class);

        //notification count checking api

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

        String itjobcell = "itjobcell";
        String company = "company";
        String college = "college";
        String campus = "srishti campus";


        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                SmartClassNotificationFragment smartClassNotificationFragment = new SmartClassNotificationFragment();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassNotificationFragment).addToBackStack(null).commit();
            }
        });

        itJobcellLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExamDetailsActivity.class);
                intent.putExtra("category_name", itjobcell);

                startActivity(intent);
            }
        });
        companyLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExamDetailsActivity.class);
                intent.putExtra("category_name", company);
                startActivity(intent);
            }
        });
        collegeLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExamDetailsActivity.class);
                intent.putExtra("category_name", college);
                startActivity(intent);
            }
        });
        campusLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),CourseActivity.class);
//                intent.putExtra("category_name",campus);
//                startActivity(intent);

                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                SmartClassCourseFragment smartClassCourseFragment = new SmartClassCourseFragment();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassCourseFragment).addToBackStack(null).commit();

            }
        });


//        setupPagerIndidcatorDots();
//
//
//        api.PLACEMENT_MODEL_ROOT_CALL().enqueue(new Callback<PlacementModelRoot>() {
//            @Override
//            public void onResponse(Call<PlacementModelRoot> call, Response<PlacementModelRoot> response) {
//                placementRecyclerView.hideShimmer();
//                PlacementModelRoot placementModelRoot = response.body();
//                SmartClassPlacementAdapterHome smartClassPlacementAdapterHome = new SmartClassPlacementAdapterHome(placementModelRoot, getContext());
//                placementRecyclerView.setAdapter(smartClassPlacementAdapterHome);
//                LinearLayoutManager linearLayoutManagerNew = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
//                placementRecyclerView.setLayoutManager(linearLayoutManagerNew);
//            }
//
//            @Override
//            public void onFailure(Call<PlacementModelRoot> call, Throwable t) {
//                placementRecyclerView.hideShimmer();
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        api.JOB_ALERT_MODEL_ROOT_CALL(userId).enqueue(new Callback<JobAlertModelRoot>() {
//            @Override
//            public void onResponse(Call<JobAlertModelRoot> call, Response<JobAlertModelRoot> response) {
//
//                homeViewPagerLoadingRl.setVisibility(View.GONE);
//                viewPager.setVisibility(View.VISIBLE);
//                JobAlertModelRoot jobAlertModelRoot = response.body();
//                SmartClassHomeViewPagerAdapter smartClassHomeViewPagerAdapter = new SmartClassHomeViewPagerAdapter(getContext(), jobAlertModelRoot);
//                viewPager.setAdapter(smartClassHomeViewPagerAdapter);
//
////                TabLayout tabLayout=view.findViewById(R.id.home_view_pager_tab_layout);
////                tabLayout.setupWithViewPager(viewPager,true);
////
////                for(int i=0; i < tabLayout.getTabCount(); i++) {
////                    View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
////                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
////                    p.setMargins(0, 0, 50, 0);
////                    tab.requestLayout();
////                }
//
//                //new
//
//
//                ivArrayDotsPager[0].setImageResource(R.drawable.tab_layout_selected_dot);
//
//                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        for (int i = 0; i < ivArrayDotsPager.length; i++) {
//                            ivArrayDotsPager[i].setImageResource(R.drawable.tab_layout_default_dot);
//                        }
//                        ivArrayDotsPager[position].setImageResource(R.drawable.tab_layout_selected_dot);
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//
//                    }
//                });
//
//                //new
//
//            }
//
//            @Override
//            public void onFailure(Call<JobAlertModelRoot> call, Throwable t) {
//                homeViewPagerLoadingRl.setVisibility(View.GONE);
//                viewPager.setVisibility(View.VISIBLE);
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;


    }


    //new

    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[3];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.tab_layout_default_dot);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }
    }

    //new
}