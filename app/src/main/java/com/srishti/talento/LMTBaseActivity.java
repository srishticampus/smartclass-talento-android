package com.srishti.talento;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LMTBaseActivity extends AppCompatActivity implements
        NetworkChangeReceiver.Internet {
    OnBackButtonClickedListener backButtonClickedListener;
    public ProgressDialog progressDialog;
    public AppPreferences appPrefes;
    public ConnectionDetector cd;
    public MyProgressDialog myProgressDialog;
    public Toast mToast;

    @Override
    protected void onCreate(Bundle arg0) {

        // Show status bar
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(arg0);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        appPrefes = new AppPreferences(this, getResources().getString(R.string.app_name));
        cd = new ConnectionDetector(this);
        myProgressDialog = new MyProgressDialog(this);
        NetworkChangeReceiver.internet = this;

    }

    public void showOneTimeToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void net() {

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (backButtonClickedListener != null)
            backButtonClickedListener.onBackButtonClicked();
        else
            super.onBackPressed();
    }

    public void hidekey() {
        // TODO Auto-generated method stub
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), 0);
    }


    public interface OnBackButtonClickedListener {
        void onBackButtonClicked();
    }

    public void backpress() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }



    public void afterparse(String response, int value) {
        // TODO Auto-generated method stub

    }

    /**
     * *
     * OkHttp network operation
     */
    public void okhttp(final String url, final int value, boolean progrss) {
        // TODO Auto-generated method stub
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                System.out.println("result===" + result);
                afterparse(result, value);
                progressDialog.cancel();
            }
        }.execute();
    }

    /**
     * *
     * Receive location address
     */
    protected void locationname(Location location) {
        // TODO Auto-generated method stub
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
                + location.getLatitude() + "," + location.getLongitude()
                + "&sensor=true";
        okhttp(url, 2, true);
    }

    protected boolean checkvalidate(EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].getText().toString().equals("")) {
                editTexts[i].setError("Error!!");
                return false;
            }
        }
        return true;
    }

    public final boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public void showNoInternetAlert(final int apiCode) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_no_internet);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            final TextView tvRetry = dialog.findViewById(R.id.tvRetry);
            final TextView tvClose = dialog.findViewById(R.id.tvClose);
            tvRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    retryApiCall(apiCode);
                }
            });
            tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showServerErrorAlert(final int apiCode) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_server_error);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            final TextView tvRetry = dialog.findViewById(R.id.tvRetry);
            final TextView tvClose = dialog.findViewById(R.id.tvClose);
            tvRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    retryApiCall(apiCode);
                }
            });
            tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRequestSuccessDialog(String title, String message, String button, final int code) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_popup);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        final TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvCancel.setText(button);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccessDialogClick(code);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showAlertDialog(String title, String message, String okButton, String cancelButton, final int code) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        final TextView tvOk = dialog.findViewById(R.id.tvOk);
        final TextView tvCancel = dialog.findViewById(R.id.tvCancel);
        tvTitle.setText(title);
        tvMessage.setText(message);
        tvOk.setText(okButton);
        tvCancel.setText(cancelButton);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAlertOkButton(code);
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void retryApiCall(int apiCode) {

    }

    public void onClickAlertOkButton(int apiCode) {

    }

    public void onSuccessDialogClick(int apiCode) {

    }
}
