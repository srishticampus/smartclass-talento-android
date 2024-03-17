package com.srishti.talento;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.srishti.talento.JobAlertModel.JobAlertModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmartclassSearchActivity extends LMTBaseActivity {

    View backBtnView;
    String color = "#FFFFFF";
    TextInputEditText search;
    RecyclerView recyclerView;
    private final int PACKAGE_API = 101;
    TextView noResultFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartclass_search);

        backBtnView=findViewById(R.id.back_btn_view);
        search=findViewById(R.id.search_et);
        recyclerView=findViewById(R.id.search_recycler_view);
        noResultFound=findViewById(R.id.no_result_found_tv);

        changeStatusBarColor(color);

        backBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SmartclassSearchActivity.this, SmartClassHomeActivity.class));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               // String searchChangedText= charSequence.toString();
               // performSearch(searchChangedText);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
       search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
               if (i == EditorInfo.IME_ACTION_SEARCH) {
                   String searchTxt=search.getText().toString();
                   performSearch(searchTxt);
                   return true;
               }
               return false;
           }
       });
    }

    private void performSearch(String searchText) {

        if (cd.isConnectingToInternet()) {
            try {

                myProgressDialog.setProgress(false);

                Api api = ApiClient.UserData().create(Api.class);
                api.JOB_ALERT_MODEL_ROOT_CALL_SEARCH(searchText).enqueue(new Callback<JobAlertModelRoot>() {
                    @Override
                    public void onResponse(Call<JobAlertModelRoot> call, Response<JobAlertModelRoot> response) {
                        myProgressDialog.dismissProgress();
                        try {
                            if (!response.isSuccessful()) {
                                Toast.makeText(SmartclassSearchActivity.this, "Failed" + response.message(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    if (!response.body().status) {
                                        recyclerView.setVisibility(View.GONE);
                                        noResultFound.setVisibility(View.VISIBLE);
                                        Toast.makeText(SmartclassSearchActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
                                    } else {

                                        recyclerView.setVisibility(View.VISIBLE);
                                        noResultFound.setVisibility(View.GONE);

                                        JobAlertModelRoot jobAlertModelRoot=response.body();
                                        SmartClassSearchRecycler jobAlertRecyclerLayout=new SmartClassSearchRecycler(jobAlertModelRoot,getApplicationContext());
                                        recyclerView.setAdapter(jobAlertRecyclerLayout);
                                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                        recyclerView.setLayoutManager(linearLayoutManager);
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
                    public void onFailure(Call<JobAlertModelRoot> call, Throwable t) {
                        Toast.makeText(SmartclassSearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(SmartclassSearchActivity.this, SmartClassHomeActivity.class));
    }
}