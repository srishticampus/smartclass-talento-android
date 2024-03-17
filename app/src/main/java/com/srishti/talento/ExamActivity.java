package com.srishti.talento;

import static com.srishti.talento.Constants.CORRECT_ANSWERS;
import static com.srishti.talento.Constants.QUE_NOT_ATTEND;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.SubmitAnsModel.SubmitAnsRoot;
import com.srishti.talento.TalentoTwoModel.QuestionDetail;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamActivity extends AppCompatActivity {
    int mCurrentPosition = 1;
    ArrayList<QuestionDetail> mQuestionsList;
    int selectedOptionPosition = 0;
    int correctAnswers = 0;
    int status = 1;
    ImageView queNotAdded;
    String color = "#ffffff";
    int queNotAttend = 0;

    String queNo;
    String timeStart;
    TextView questionId;

    TextView coutdownTv;
    CountDownTimer countDownTimer;
    Long timeLeftInMilliSecond;


    private TextView tvQuestion;
    private ImageView ivImage;
    private LinearLayout llProgressDetails;
    private ProgressBar progressBar, examProgressBar;
    private TextView tvProgress;
    private TextView tvOptionOne;
    private TextView tvOptionTwo;
    private TextView tvOptionThree;
    private TextView tvOptionFour;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        changeStatusBarColor(color);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        //Start time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        timeStart = simpleDateFormat.format(calendar.getTime());

        //CountDown
        coutdownTv = findViewById(R.id.countdown_text);
        String duration = getIntent().getStringExtra("duration");
        Long TIME_LEFT_IN_MILLISECOND = Long.valueOf(duration) * 60000;
        timeLeftInMilliSecond = TIME_LEFT_IN_MILLISECOND;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
        String examId = sharedPreferences.getString("exam_id", null);
        String type = sharedPreferences.getString("typeName", null);
        String category = sharedPreferences.getString("category_name", null);
        String userId = sharedPreferences.getString("user_id", null);
       // Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        String techId = getIntent().getStringExtra("techId");


        Api api = ApiClient.UserData().create(Api.class);

        viewQuestionApiCall(examId, userId, techId);

//        api.EXAM_MODEL_ROOT_CALL(examId, type, category).enqueue(new Callback<ExamModelRoot>() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onResponse(Call<ExamModelRoot> call, Response<ExamModelRoot> response) {
//
//                ExamModelRoot questionModel = response.body();
//
//                examProgressBar.setVisibility(View.GONE);
//
//                mQuestionsList = new ArrayList<>();
//                if (questionModel.getStatus()) {
//                    for (int i = 0; i < questionModel.getQuestion_details().size(); i++) {
//                        mQuestionsList.add(questionModel.getQuestion_details().get(i));
//                    }
//                    setQuestion();
//                    // count downTimer
//                    startCountDown();
//
//                } else {
//                    status = 0;
//                    Toast.makeText(ExamActivity.this, "No questions are added", Toast.LENGTH_SHORT).show();
//                    examProgressBar.setVisibility(View.GONE);
//                    queNotAdded.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ExamModelRoot> call, Throwable t) {
//                status = 0;
//                examProgressBar.setVisibility(View.GONE);
//                queNotAdded.setVisibility(View.VISIBLE);
//            }
//        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setQuestion() {

        QuestionDetail question = mQuestionsList.get(mCurrentPosition - 1);
        defaultOptionsView();

        if (mCurrentPosition == mQuestionsList.size()) {
            btnSubmit.setText("FINISH");
        } else {
            btnSubmit.setText("SUBMIT");
        }

        progressBar.setMax(mQuestionsList.size());
        progressBar.setProgress(mCurrentPosition);
        tvProgress.setText(mCurrentPosition + "/" + progressBar.getMax());
        tvQuestion.setText(question.mainques);


        tvOptionOne.setText(question.opt1);
        tvOptionTwo.setText(question.opt2);
        tvOptionThree.setText(question.opt3);
        tvOptionFour.setText(question.opt4);
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMilliSecond, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliSecond = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMilliSecond = Long.valueOf(0);
                updateCountDownText();
                Intent intent = new Intent(ExamActivity.this, ResultActivity.class);

                //Stop time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String timeEnd = simpleDateFormat.format(calendar.getTime());


                intent.putExtra("time_start", timeStart);
                intent.putExtra("time_end", timeEnd);
                intent.putExtra(CORRECT_ANSWERS, correctAnswers);
                intent.putExtra(QUE_NOT_ATTEND, queNotAttend);
                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size());
                startActivity(intent);

            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) ((timeLeftInMilliSecond / 1000) / 60);
        int seconds = (int) ((timeLeftInMilliSecond / 1000) % 60);

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        coutdownTv.setText(timeFormatted);

        if (timeLeftInMilliSecond < 30000) {
            coutdownTv.setTextColor(Color.RED);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void defaultOptionsView() {

        ArrayList<TextView> options = new ArrayList<>();
        options.add(0, tvOptionOne);
        options.add(1, tvOptionTwo);
        options.add(2, tvOptionThree);
        options.add(3, tvOptionFour);
        for (TextView option : options) {
            option.setTextColor(Color.parseColor("#7A8089"));
            option.setTypeface(Typeface.DEFAULT);
            option.setBackground(getDrawable(R.drawable.default_option_border_bg));
        }
    }


    private void initView() {
        tvQuestion = findViewById(R.id.tv_question);
        //   ivImage = findViewById(R.id.iv_image);
        llProgressDetails = findViewById(R.id.ll_progress_details);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        tvOptionOne = findViewById(R.id.tv_option_one);
        tvOptionTwo = findViewById(R.id.tv_option_two);
        tvOptionThree = findViewById(R.id.tv_option_three);
        tvOptionFour = findViewById(R.id.tv_option_four);
        btnSubmit = findViewById(R.id.btn_submit);
        queNotAdded = findViewById(R.id.que_not_added);
        examProgressBar = findViewById(R.id.exam_progressbar);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void answerView(int answer, int drawable) {

        switch (answer) {
            case 1:
                tvOptionOne.setBackground(getDrawable(drawable));
                break;
            case 2:
                tvOptionTwo.setBackground(getDrawable(drawable));
                break;
            case 3:
                tvOptionThree.setBackground(getDrawable(drawable));
                break;
            case 4:
                tvOptionFour.setBackground(getDrawable(drawable));
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void selectedOptionView(TextView tv, int selectedOptionNum) {
        defaultOptionsView();
        selectedOptionPosition = selectedOptionNum;

        tv.setTextColor(
                Color.parseColor("#ffffff")
        );
        // tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackground(getDrawable(R.drawable.selected_option_border_bg));


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void optionOneClick(View view) {
        selectedOptionView(tvOptionOne, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void optionTwoClick(View view) {
        selectedOptionView(tvOptionTwo, 2);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void optionThreeClick(View view) {
        selectedOptionView(tvOptionThree, 3);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void optionFourClick(View view) {
        selectedOptionView(tvOptionFour, 4);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void submitButtonClick(View view) {
        if (selectedOptionPosition == 0) {
            mCurrentPosition++;

            queNotAttend++;
            Log.e("QNA", String.valueOf(queNotAttend));

            if (mCurrentPosition <= mQuestionsList.size()) {
                setQuestion();
            } else {
                Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                countDownTimer.cancel();
                //Stop time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String timeEnd = simpleDateFormat.format(calendar.getTime());


                intent.putExtra("time_start", timeStart);
                intent.putExtra("time_end", timeEnd);
                intent.putExtra(QUE_NOT_ATTEND, queNotAttend);
                intent.putExtra(CORRECT_ANSWERS, correctAnswers);
                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size());
                startActivity(intent);
                finish();
            }

        } else {
            QuestionDetail question = mQuestionsList.get(mCurrentPosition - 1);
            if (!question.ans.equals(String.valueOf(selectedOptionPosition))) {
                // answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg);
            } else {
                correctAnswers++;
                Log.e("ANS", String.valueOf(correctAnswers));
            }
            // answerView(Integer.parseInt(question.getAns()), R.drawable.correct_option_border_bg);
            if (mCurrentPosition == mQuestionsList.size()) {
                // btnSubmit.setText("FINISH");
                Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                countDownTimer.cancel();

                //Stop time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                String timeEnd = simpleDateFormat.format(calendar.getTime());


                intent.putExtra("time_start", timeStart);
                intent.putExtra("time_end", timeEnd);
                intent.putExtra(CORRECT_ANSWERS, correctAnswers);
                intent.putExtra(QUE_NOT_ATTEND, queNotAttend);
                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size());
                startActivity(intent);
                finish();
            } else {
                // btnSubmit.setText("GO TO NEXT QUESTION");
                examProgressBar.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);

                String questionSet = getIntent().getStringExtra("question_set_id");
                String questioId = question.id;
                String userId = sharedPreferences.getString("user_id", null);
                String option = String.valueOf(selectedOptionPosition);
                String ans = question.ans;


                ansSubmissionSingleQuestion(questionSet,questioId,option,ans,userId);

              //  Api api = ApiClient.UserData().create(Api.class);

//                api.SUBMIT_ANS_ROOT_CALL(questionSet, questioId, option, ans, userId).enqueue(new Callback<SubmitAnsRoot>() {
//                    @Override
//                    public void onResponse(Call<SubmitAnsRoot> call, Response<SubmitAnsRoot> response) {
//                        SubmitAnsRoot submitAnsRoot = response.body();
//                        examProgressBar.setVisibility(View.GONE);
//                        Toast.makeText(ExamActivity.this, "Answer Submitted", Toast.LENGTH_SHORT).show();
//
//                        mCurrentPosition++;
//                        if (mCurrentPosition <= mQuestionsList.size()) {
//                            setQuestion();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<SubmitAnsRoot> call, Throwable t) {
//                        examProgressBar.setVisibility(View.GONE);
//                        Toast.makeText(ExamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            selectedOptionPosition = 0;
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
        if (status == 0) {
            super.onBackPressed();
        } else {

        }
    }

    void viewQuestionApiCall(String examId, String userId, String techId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_QUESTIONS(examId, userId, techId).enqueue(new Callback<TalentoTwoRoot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();

                    examProgressBar.setVisibility(View.GONE);

                    mQuestionsList = new ArrayList<>();
                    if (talentoTwoRoot.status) {
                        for (int i = 0; i < talentoTwoRoot.questionDetails.size(); i++) {
                            mQuestionsList.add(talentoTwoRoot.questionDetails.get(i));
                        }
                        setQuestion();
                        // count downTimer
                        startCountDown();

                    } else {
                        status = 0;
                        Toast.makeText(ExamActivity.this, "No questions are added", Toast.LENGTH_SHORT).show();
                        examProgressBar.setVisibility(View.GONE);
                        queNotAdded.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                status = 0;
                examProgressBar.setVisibility(View.GONE);
                queNotAdded.setVisibility(View.VISIBLE);
            }
        });
    }

    void ansSubmissionSingleQuestion(String examId, String questionId, String selectedOption,String correctAns, String userId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_ANSWER_SUBMISSION(examId,questionId,selectedOption,correctAns,userId).enqueue(new Callback<TalentoTwoRoot>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();
                    if (talentoTwoRoot.status) {
                        examProgressBar.setVisibility(View.GONE);
                        Toast.makeText(ExamActivity.this, "Answer Submitted", Toast.LENGTH_SHORT).show();

                        mCurrentPosition++;
                        if (mCurrentPosition <= mQuestionsList.size()) {
                            setQuestion();
                        }

                    } else {
                        Toast.makeText(ExamActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ExamActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(ExamActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}