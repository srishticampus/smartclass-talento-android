package com.srishti.talento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class SmartClassHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    String color = "#FFFFFF";
    TextView userName;

    DuoDrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_class_home);

        changeStatusBarColor(color);

        bottomNavigationView = findViewById(R.id.home_btm_nav);


        init();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_bt_nav_icon);

        if ("fromEditProfile".equals(getIntent().getStringExtra("EditProfile"))){
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            SmartClassProfileFragment smartClassProfileFragment = new SmartClassProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassProfileFragment).commit();
        }
        if ("fromJobDetailsActivity".equals(getIntent().getStringExtra("JobDetailsActivity"))){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            SmartClassJobAlertFragment smartClassJobAlertFragment = new SmartClassJobAlertFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassJobAlertFragment).commit();
        }
        if ("1".equals(getIntent().getStringExtra("fromED"))){
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            SmartClassCourseFragment smartClassCourseFragment = new SmartClassCourseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassCourseFragment).commit();
        }
        if ("2".equals(getIntent().getStringExtra("fromED"))){
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            SmartClassHomeFragment smartClassHomeFragment = new SmartClassHomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassHomeFragment).commit();
        }

    }

    private void init() {


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        userName = menuView.findViewById(R.id.nav_drawer_user_name);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        userName.setText(sharedPreferences.getString("user_name", "User"));


        CircleImageView profileImage = menuView.findViewById(R.id.userImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                SmartClassProfileFragment smartClassProfileFragment = new SmartClassProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassProfileFragment).commit();
                drawerLayout.closeDrawer();

            }
        });

        Glide.with(getApplicationContext()).asBitmap().load(sharedPreferences.getString("Img",null)).into(profileImage);
      // Toast.makeText(getApplicationContext(),sharedPreferences.getString("Img",null) , Toast.LENGTH_SHORT).show();

        ImageView closeDrawer = menuView.findViewById(R.id.drawer_close_iv);
        closeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer();
            }
        });

        LinearLayout publicProfileLayout = menuView.findViewById(R.id.public_profile_layout);
        publicProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                SmartClassProfileFragment smartClassProfileFragment = new SmartClassProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassProfileFragment).commit();
                drawerLayout.closeDrawer();
            }
        });

        LinearLayout profileSettings = menuView.findViewById(R.id.profile_settings_layout);
        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                SmartClassProfileFragment smartClassProfileFragment = new SmartClassProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassProfileFragment).commit();
                drawerLayout.closeDrawer();
            }
        });

        LinearLayout jobAlertLayout = menuView.findViewById(R.id.job_alert_layout);
        jobAlertLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                SmartClassJobAlertFragment smartClassJobAlertFragment = new SmartClassJobAlertFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassJobAlertFragment).commit();
                drawerLayout.closeDrawer();
            }
        });
        LinearLayout placementLayout = menuView.findViewById(R.id.placement_update_layout);
        placementLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                SmartClassPlacementFragment smartClassPlacementFragment = new SmartClassPlacementFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassPlacementFragment).commit();
                drawerLayout.closeDrawer();
            }
        });


        LinearLayout contactUsLayout = menuView.findViewById(R.id.contact_us_linear_layout);
        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SmartClassHomeActivity.this, SmartClassContactUsActivity.class);
                startActivity(intent);
            }
        });

    }


    SmartClassCourseFragment homeFragment = new SmartClassCourseFragment();
    TalentoTwoExamHistoryFragment jobAlertFragment = new TalentoTwoExamHistoryFragment();
    SmartClassPlacementFragment smartClassPlacementFragment = new SmartClassPlacementFragment();
    SmartClassProfileFragment smartClassProfileFragment = new SmartClassProfileFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_bt_nav_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, homeFragment).commit();
                return true;
            case R.id.profile_bt_nav_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassProfileFragment).commit();
                return true;
            case R.id.job_alert_bt_nav_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, jobAlertFragment).commit();
                return true;
            case R.id.testimonials_bt_nav_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, smartClassPlacementFragment).commit();
                return true;
            case R.id.search_bt_nav_icon:
                startActivity(new Intent(getApplicationContext(), SmartclassSearchActivity.class));
                return true;
        }

        return false;
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

        AlertDialog.Builder builder =new AlertDialog.Builder(SmartClassHomeActivity.this);
        builder.setMessage("Are You Sure Want To Exit ?");
        builder.setTitle("Alert ! ");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}