package com.srishti.talento;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView spalshTv, layoutTitle;
    String color = "#FFFFFF";
    RelativeLayout relativeLayout;
    ImageView imageView;
    LinearLayout loginbt;
    int clickCount = 1;
    TextView loginTxt;
    EditText editTextMobile;
    String mobile;
    String mVerificationId;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextView registerBtTv;

    private static final int MY_REQUEST_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor(color);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        relativeLayout = findViewById(R.id.login_layout);
        imageView = findViewById(R.id.imageView2);
        loginbt = findViewById(R.id.login_bt);
        loginTxt = findViewById(R.id.login_bt_new);
        editTextMobile = findViewById(R.id.editTextMobile);
        progressBar = findViewById(R.id.verify_progressbar);
        mAuth = FirebaseAuth.getInstance();
        layoutTitle = findViewById(R.id.layout_title);
        registerBtTv = findViewById(R.id.user_reg_tv);

        spalshTv = findViewById(R.id.textView9);
        String text = "<font color=#000>POWERED BY</font><font color=#00168F> SRISHTI INNOVATIVE</font>";
        spalshTv.setText(Html.fromHtml(text));

        UpdateApp();

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickCount == 1) {


                    if (editTextMobile.getText().toString().isEmpty() || editTextMobile.getText().toString().length() < 10) {
                        editTextMobile.setError("Enter a valid mobile");
                        editTextMobile.requestFocus();
                    } else {


                        mobile = editTextMobile.getText().toString().trim();
                        // sendVerificationCode(mobile);
                        editTextMobile.setText("");
                        editTextMobile.setHint("Enter Your Password");
                        editTextMobile.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                        //for setting max length of edit text to 6

//                        InputFilter[] editFilters = editTextMobile.getFilters();
//                        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
//                        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
//                        newFilters[editFilters.length] = new InputFilter.LengthFilter(6);
//                        editTextMobile.setFilters(newFilters);


                        layoutTitle.setText("Verify Password");
                        loginbt.setVisibility(View.GONE);

                        //TODO CHanged here
                        progressBar.setVisibility(View.GONE);
                        loginbt.setVisibility(View.VISIBLE);
                        clickCount = 2;
                        loginTxt.setText("LOGIN");

                    }

                } else if (clickCount == 2) {

                    if (editTextMobile.getText().toString().trim().isEmpty()) {
                        editTextMobile.setError("Enter Valid Password");
                        editTextMobile.requestFocus();
                    } else {
                        try {
                            //verifying the code entered manually
                            // String code = editTextMobile.getText().toString().trim();
                            String password = editTextMobile.getText().toString();
                            //verifyVerificationCode(code);
//                            Intent i = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            startActivity(i);
                            loginApiCall(mobile, "28882982", password);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
                if (sharedPreferences.getBoolean("sesson", false)) {
                    Intent i = new Intent(MainActivity.this, SmartClassHomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
//                    Intent i = new Intent(MainActivity.this, Login.class);
//                    startActivity(i);
//                    finish();

                    spalshTv.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        }, 3000);

        registerBtTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TalentoTwoRegistrationActivity.class);
                startActivity(intent);

            }
        });

    }

    public void UpdateApp(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setTitle("Update Talento");
                alertDialogBuilder.setCancelable(false);
               // alertDialogBuilder.setIcon(R.drawable.icons_google_play);
                alertDialogBuilder.setMessage("Talento App recommends that you update to the latest version for a seamless & enhanced performance of the app.");
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }
                    }
                });

                alertDialogBuilder.show();

            } else {

            }
        });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mobile) // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextMobile.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            progressBar.setVisibility(View.GONE);
            loginbt.setVisibility(View.VISIBLE);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    // verification github
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
//                            Intent intent = new Intent(VerifyPhoneActivity.this, HomeActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);


                            try {
                                SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("token", getApplicationContext().MODE_PRIVATE);
                                String deviceToken = sharedPreferences1.getString("token", "1233444");


                                // loginApiCall(mobile, "1234567");
//
//                                Api api = ApiClient.UserData().create(Api.class);
//                                api.SIGNIN_CALL(mobile, deviceToken).enqueue(new Callback<Signin>() {
//                                    @Override
//                                    public void onResponse(Call<Signin> call, Response<Signin> response) {
//                                        try {
//                                            if (response.body().status) {
//
//                                                Signin signin = response.body();
//                                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
//                                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                                editor.putString("user_id", signin.user_details.id);
//                                                editor.putString("user_name", signin.user_details.user_name + " " + signin.user_details.user_last_name);
//                                                editor.putString("Img", signin.user_details.profile_pic);
//                                                editor.putBoolean("sesson", true);
//                                                editor.apply();
//                                                Intent i = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                                                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                                                startActivity(i);
//                                            } else {
//                                                Toast.makeText(getApplicationContext(), "User Not Registered", Toast.LENGTH_LONG).show();
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Signin> call, Throwable t) {
//                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {

                            //verification unsuccessful.. display an error message
                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity_layout), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
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


    void loginApiCall(String mobile, String deviceToken, String password) {
        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_LOGIN(mobile, deviceToken, password).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()) {
                    TalentoTwoRoot talentoTwoRoot = response.body();
                    if (talentoTwoRoot.status) {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_id", talentoTwoRoot.userDetails.get(0).id);
                        editor.putString("user_name", talentoTwoRoot.userDetails.get(0).name + " " + talentoTwoRoot.userDetails.get(0).last_name);
                        editor.putString("Img", talentoTwoRoot.userDetails.get(0).picture_url);
                        editor.putBoolean("sesson", true);
                        editor.apply();
                        Intent i = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), talentoTwoRoot.message, Toast.LENGTH_LONG).show();

                    }
                }

//                try {
//                    if (response.body().status) {
//
//                        TalentoTwoRoot talentoTwoRoot = response.body();
//
//                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("user_id", talentoTwoRoot.userDetails.id);
//                        editor.putString("user_name", talentoTwoRoot.userDetails.name + " " + talentoTwoRoot.userDetails.last_name);
//                        editor.putString("Img", talentoTwoRoot.userDetails.picture_url);
//                        editor.putBoolean("sesson", true);
//                        editor.apply();
//                        Intent i = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                        startActivity(i);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "User Not Registered", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}