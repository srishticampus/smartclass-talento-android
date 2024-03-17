package com.srishti.talento;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srishti.talento.DeleteAccountModel.DeleteAccountRoot;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout deleteAccount, signoutAccount, profileSettings;
    TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        deleteAccount = findViewById(R.id.settings_delete);
        signoutAccount = findViewById(R.id.settings_signout);
        profileSettings = findViewById(R.id.settings_profile);
        usernameTV = findViewById(R.id.settings_profile_tv);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        usernameTV.setText(sharedPreferences.getString("user_name","user"));

        signoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Are you sure want to log out?").setCancelable(true).setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("user_id", );
                        editor.putBoolean("sesson", false);
                        editor.apply();
                        startActivity(new Intent(SettingsActivity.this, Login.class));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setMessage("Are you sure want to delete your Account?").setCancelable(true).setPositiveButton("delete account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", MODE_PRIVATE);
                        String userId = sharedPreferences.getString("user_id", null);

                        Api api = ApiClient.UserData().create(Api.class);
                        api.DELETE_ACCOUNT_ROOT_CALL(userId).enqueue(new Callback<DeleteAccountRoot>() {
                            @Override
                            public void onResponse(Call<DeleteAccountRoot> call, Response<DeleteAccountRoot> response) {
                                Toast.makeText(SettingsActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SettingsActivity.this, Login.class));
                            }

                            @Override
                            public void onFailure(Call<DeleteAccountRoot> call, Throwable t) {
                                Toast.makeText(SettingsActivity.this, "Error!! Try Again Later", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        profileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this, UserProfileActivity.class));
            }
        });
    }
}