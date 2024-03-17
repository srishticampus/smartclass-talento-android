package com.srishti.talento;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class MyProgressDialog {
    private final Dialog progressDialog;
    final GifMovieView gif1;

    public MyProgressDialog(Activity activity) {
        progressDialog = new Dialog(activity, R.style.AppTheme);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        progressDialog.setContentView(R.layout.progress);
        gif1 = progressDialog.findViewById(R.id.gif1);
        gif1.setMovieResource(R.drawable.progress);

    }

    public void setProgress(boolean cancellable ) {
        progressDialog.setCancelable(cancellable);
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgress() {
        if (progressDialog.isShowing()){
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
