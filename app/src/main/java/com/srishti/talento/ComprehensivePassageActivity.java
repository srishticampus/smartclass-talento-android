package com.srishti.talento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.srishti.talento.Exam_model.ExamModelRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprehensivePassageActivity extends AppCompatActivity {
    Button button;
    TextView passageTv;
    ImageView imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprehensive_passage);
        button = findViewById(R.id.examBt);
        passageTv = findViewById(R.id.tv_passage);
        imageView = findViewById(R.id.no_response_image);
        progressBar = findViewById(R.id.progressBarComp);

        String queNo = getIntent().getStringExtra("que_no");
        String duration = getIntent().getStringExtra("duration");
        String queSetId = getIntent().getStringExtra("question_set_id");


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
        String examId = sharedPreferences.getString("exam_id", null);
        String type = sharedPreferences.getString("typeName", null);
        String category = sharedPreferences.getString("category_name", null);

        Api api = ApiClient.UserData().create(Api.class);
        api.EXAM_MODEL_ROOT_CALL(examId, type,category).enqueue(new Callback<ExamModelRoot>() {
            @Override
            public void onResponse(Call<ExamModelRoot> call, Response<ExamModelRoot> response) {

                ExamModelRoot examModelRoot = response.body();
                String passage = examModelRoot.getMessage();
                passageTv.setText(passage);
                progressBar.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
                        intent.putExtra("que_no", queNo);
                        intent.putExtra("duration", duration);
                        intent.putExtra("question_set_id", queSetId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ExamModelRoot> call, Throwable t) {
                imageView.setVisibility(View.VISIBLE);
                Toast.makeText(ComprehensivePassageActivity.this, "Question not added! please click BACK button to exit", Toast.LENGTH_SHORT).show();
            }
        });

    }
}