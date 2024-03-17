package com.srishti.talento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SmartClassContactUsActivity extends AppCompatActivity {
    String color = "#206cbb";
    View backBtn;
    TextView contactUsTv,srishtiMobOne,srishtiMobTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        changeStatusBarColor(color);

        srishtiMobOne=findViewById(R.id.srishti_mob_numebr_one);
        srishtiMobTwo=findViewById(R.id.srishti_mob_numebr_two);

        contactUsTv=findViewById(R.id.contact_us_btn);
        backBtn=findViewById(R.id.contact_us_back_btn_view);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SmartClassHomeActivity.class));
            }
        });

        contactUsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(new String[]{"srishticampusteam@srishtis.com"}," ");
            }
        });

        srishtiMobOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+91-9791369503";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        srishtiMobTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+91-8825558630";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
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
        startActivity(new Intent(getApplicationContext(),SmartClassHomeActivity.class));
    }
}