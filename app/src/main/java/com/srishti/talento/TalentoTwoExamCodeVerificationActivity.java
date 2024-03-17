package com.srishti.talento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalentoTwoExamCodeVerificationActivity extends AppCompatActivity {

    private EditText editTextText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talento_two_exam_code_verification);
        initView();

        String queNo = getIntent().getStringExtra("que_no");
        String duration = getIntent().getStringExtra("duration");
        String questionSetId = getIntent().getStringExtra("question_set_id");
        String techId = getIntent().getStringExtra("techId");

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Your Code", Toast.LENGTH_SHORT).show();
                } else {
                    apiCall(userId, editTextText.getText().toString(), queNo, duration, questionSetId, techId);
                }
            }
        });




    }

    void apiCall(String userId, String code, String queNo, String duration, String questionSetId, String techId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VERIFY_EXAM_CODE(userId, code).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        Toast.makeText(TalentoTwoExamCodeVerificationActivity.this, root.message,
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TalentoTwoExamCodeVerificationActivity.this, ExamTermsAndCondition.class);
                        intent.putExtra("que_no", queNo);
                        intent.putExtra("duration", duration);
                        intent.putExtra("question_set_id", questionSetId);
                        intent.putExtra("techId", techId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TalentoTwoExamCodeVerificationActivity.this, root.message,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TalentoTwoExamCodeVerificationActivity.this,
                            "Please Try Again", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {

                Toast.makeText(TalentoTwoExamCodeVerificationActivity.this,
                        t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void initView() {
        editTextText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
    }
}