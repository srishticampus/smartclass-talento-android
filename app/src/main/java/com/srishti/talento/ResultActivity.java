package com.srishti.talento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private TextView correct, wrongTv, notAns;
    private ImageView ivTrophy;
    private TextView tvCongratulations;
    private TextView tvName;
    private TextView tvScore;
    private Button btnFinish;
    String color = "#ffffff";

    private RelativeLayout progressBarLayout;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //restrict screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        changeStatusBarColor(color);
        initView();
        //  showPackage();
        finalResultSubmission();


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ResultActivity.this, ParentHome.class));
                Intent i = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
                startActivity(i);
            }
        });

    }

    void showResult() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
        String examId = sharedPreferences.getString("exam_id", null);
        String type = sharedPreferences.getString("typeName", null);
        String userId = sharedPreferences.getString("user_id", null);
        String category = sharedPreferences.getString("category_name", null);


        String totalQuestions = String.valueOf(getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 0));
        String correctAnswers = String.valueOf(getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0));
        String timeStart = getIntent().getStringExtra("time_start");
        String timeStop = getIntent().getStringExtra("time_end");
        String queNotAttend = String.valueOf(getIntent().getIntExtra(Constants.QUE_NOT_ATTEND, 0));

        int correctAns = getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0);
        int totalQue = getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 0);
        int notAnswered = getIntent().getIntExtra(Constants.QUE_NOT_ATTEND, 0);
        try {
            int wrongAns = totalQue - correctAns - notAnswered;
            String wrongAnswer = String.valueOf(wrongAns);
            wrongTv.setText(wrongAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_EXAM_RESULT(userId, examId, correctAnswers,
                totalQuestions, queNotAttend,
                timeStart, timeStop).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        correct.setText(correctAnswers);
                        // Toast.makeText(ResultActivity.this, correctAns, Toast.LENGTH_SHORT).show();
                        notAns.setText(queNotAttend);

                        tvScore.setText("Your Score is " + root.answerDetails.get(0).score + " out of " + root.answerDetails.get(0).total_mark);
                        btnFinish.setVisibility(View.VISIBLE);
                        progressBarLayout.setVisibility(View.GONE);

                    } else {
                        progressBarLayout.setVisibility(View.GONE);
                        Toast.makeText(ResultActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResultActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    progressBarLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                progressBarLayout.setVisibility(View.GONE);
            }
        });


    }


    void finalResultSubmission() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
        String examId = sharedPreferences.getString("exam_id", null);
        String type = sharedPreferences.getString("typeName", null);
        String userId = sharedPreferences.getString("user_id", null);
        String category = sharedPreferences.getString("category_name", null);


        String totalQuestions = String.valueOf(getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 0));
        String correctAnswers = String.valueOf(getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0));
        String timeStart = getIntent().getStringExtra("time_start");
        String timeStop = getIntent().getStringExtra("time_end");
        String queNotAttend = String.valueOf(getIntent().getIntExtra(Constants.QUE_NOT_ATTEND, 0));

        int correctAns = getIntent().getIntExtra(Constants.CORRECT_ANSWERS, 0);
        int totalQue = getIntent().getIntExtra(Constants.TOTAL_QUESTIONS, 0);
        int notAnswered = getIntent().getIntExtra(Constants.QUE_NOT_ATTEND, 0);
        try {
            int wrongAns = totalQue - correctAns - notAnswered;
            String wrongAnswer = String.valueOf(wrongAns);
            wrongTv.setText(wrongAnswer);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        correct.setText(correctAnswers);
//        notAns.setText(queNotAttend);
//
//        tvScore.setText("Your Score is " + correctAnswers + " out of " + totalQuestions);

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_FINAL_ANSWER_SUBMISSION(userId, examId, correctAnswers, totalQuestions,
                queNotAttend, timeStart, timeStop).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();
                    if (talentoTwoRoot.status) {
                        Toast.makeText(ResultActivity.this, "Result Submitted", Toast.LENGTH_SHORT).show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                showResult();
                            }
                        }, 3000);

                        // Toast.makeText(ResultActivity.this, userId, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
//                Log.e("ERROR",t.getMessage());
            }
        });


    }

    private void initView() {
        tvCongratulations = findViewById(R.id.tv_congratulations);
        tvScore = findViewById(R.id.tv_score);
        btnFinish = findViewById(R.id.btn_finish);
        correct = findViewById(R.id.tv_correct);
        wrongTv = findViewById(R.id.tv_wrong);
        notAns = findViewById(R.id.tv_not_answered);
        progressBarLayout = findViewById(R.id.progressBarLayout);
        progressBar = findViewById(R.id.progressBar);
    }

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

    }
}