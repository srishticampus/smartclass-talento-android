package com.srishti.talento;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.srishti.talento.Retro.Api;
import com.srishti.talento.Retro.ApiClient;
import com.srishti.talento.TalentoTwoModel.TalentoTwoRoot;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TalentoTwoRegistrationActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "required")
    private TextInputEditText regFirstNameEt;
    @NotEmpty
    @Length(max = 10, min = 10, message = "Enter a valid mobile number")
    private TextInputEditText regPhoneNumberEt;
    @NotEmpty
    @Email
    private TextInputEditText regEmailIdEt;
    @NotEmpty
    private TextInputEditText regPlaceEt;
    @NotEmpty
    private TextInputEditText regSchoolEt;
    @NotEmpty
    private MaterialAutoCompleteTextView regTypeEt;
    @NotEmpty
    private MaterialAutoCompleteTextView technologyEt;
    private TextView regBtn;

    private Validator validator;
    String[] batch_details;

    String[] batchId;

    String selectedBatchId;

    private LinearLayout changeImage;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int RESULT_LOAD_PRO_IMAGE = 106;
    private File proImageFile;

    private CircleImageView profileImage;
    @NotEmpty
    @Password(message = "length 6-10 required")
    @Length(max = 10,min = 6)
    private TextInputEditText regPasswordEt;
    @NotEmpty
    @Length(max = 10,min = 6)
    @ConfirmPassword
    private TextInputEditText regConfirmPasswordEt;
    @NotEmpty
    private TextInputEditText regStaffCodeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talento_two_registration);
        initView();

        validator = new Validator(this);
        validator.setValidationListener(this);

        try {
            selectTechApi();
        } catch (Exception e) {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


        //choose course type
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.course_type));
        regTypeEt.setAdapter(adapter);
        regTypeEt.setCursorVisible(false);
        regTypeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (regTypeEt.getText().toString().isEmpty()) {
                    regTypeEt.showDropDown();
                } else {
                    regTypeEt.setText("");
                    // Add a delay of .2 seconds (200 milliseconds)
                    int delayMillis = 200;

                    // Perform the desired action after the delay
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        // Code to be executed after the delay
                        // Insert your logic here
                        regTypeEt.showDropDown();
                    }, delayMillis);

                }

            }
        });
        regTypeEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                regTypeEt.setText(parent.getItemAtPosition(position).toString());
            }
        });

        //select technology drop down
        technologyEt.setCursorVisible(false);
        technologyEt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    technologyEt.showDropDown();
                    String course = (String) adapterView.getItemAtPosition(i);
                    technologyEt.setText(adapterView.getItemAtPosition(i).toString());
                    selectedBatchId = batchId[i];
                    // Toast.makeText(TalentoTwoRegistrationActivity.this, selectedBatchId, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }

            }
        });
        technologyEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (technologyEt.getText().toString().isEmpty()) {
                        technologyEt.showDropDown();
                    } else {
                        technologyEt.setText("");
                        // Add a delay of .2 seconds (200 milliseconds)
                        int delayMillis = 200;

                        // Perform the desired action after the delay
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            // Code to be executed after the delay
                            // Insert your logic here
                            technologyEt.showDropDown();
                        }, delayMillis);

                    }

                } catch (Exception e) {

                }
            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
//                } else {
//                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(i, RESULT_LOAD_PRO_IMAGE);
//                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext(),
                            Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_DENIED) {
                        checkPermission(
                                Manifest.permission.READ_MEDIA_IMAGES,
                                STORAGE_PERMISSION_CODE
                        );
                    } else {
                        // calling intent on below line.
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        // starting activity on below line.
                        startActivityForResult(intent, 1);
                    }
                } else {
                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_DENIED) {
                        checkPermission(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                STORAGE_PERMISSION_CODE
                        );
                    } else {
                        // calling intent on below line.
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        // starting activity on below line.
                        startActivityForResult(intent, 1);
                    }
                }

            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


    }

    private void initView() {
        regFirstNameEt = findViewById(R.id.reg_first_name_et);
        regPhoneNumberEt = findViewById(R.id.reg_phone_number_et);
        regEmailIdEt = findViewById(R.id.reg_email_id_et);
        regPlaceEt = findViewById(R.id.reg_place_et);
        regSchoolEt = findViewById(R.id.reg_school_et);
        regTypeEt = findViewById(R.id.reg_type_et);
        technologyEt = findViewById(R.id.technology_et);
        regBtn = findViewById(R.id.reg_btn);
        changeImage = findViewById(R.id.change_image_layout);
        profileImage = findViewById(R.id.edit_profile_img);
        regPasswordEt = findViewById(R.id.reg_password_et);
        regConfirmPasswordEt = findViewById(R.id.reg_confirm_password_et);
        regStaffCodeEt = findViewById(R.id.reg_staff_code_et);
    }

    @Override
    public void onValidationSucceeded() {
        regApi();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

        }

    }

    public void selectTechApi() {
        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_TECHNOLOGIES().enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        batch_details = new String[root.batch_details.size()];
                        for (int i = 0; i < root.batch_details.size(); i++) {
                            batch_details[i] = root.batch_details.get(i).batch_name;
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                                    getApplicationContext(), android.R.layout.simple_list_item_1, batch_details);
                            technologyEt.setAdapter(arrayAdapter);
                        }
                        //getting batch id
                        batchId = new String[root.batch_details.size()];
                        for (int j = 0; j < root.batch_details.size(); j++) {
                            batchId[j] = root.batch_details.get(j).batch_id;
                            final ArrayAdapter<String> batchIdArrayAdapter = new ArrayAdapter<>(
                                    getApplicationContext(), android.R.layout.simple_list_item_1, batchId);
                        }
                    } else {
                        Toast.makeText(TalentoTwoRegistrationActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TalentoTwoRegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
               // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("wtf",t.getMessage());
            }
        });
    }

    void regApi() {

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), regFirstNameEt.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), regEmailIdEt.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), regPhoneNumberEt.getText().toString());
        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), regPlaceEt.getText().toString());
        RequestBody school = RequestBody.create(MediaType.parse("text/plain"), regSchoolEt.getText().toString());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), regTypeEt.getText().toString());
        RequestBody technology = RequestBody.create(MediaType.parse("text/plain"), selectedBatchId);
        RequestBody deviceToken = RequestBody.create(MediaType.parse("text/plain"), "123456");
        RequestBody staffCode = RequestBody.create(MediaType.parse("text/plain"), regStaffCodeEt.getText().toString());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), regConfirmPasswordEt.getText().toString());
        MultipartBody.Part proImageFilePart = null;
        try {
            proImageFilePart = MultipartBody.Part.createFormData("avatar", proImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), proImageFile));

        } catch (NullPointerException e) {

        }

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_USER_REG(name, email, phone, place, school,
                type, technology, proImageFilePart,
                deviceToken, staffCode,password ).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        Toast.makeText(TalentoTwoRegistrationActivity.this, root.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TalentoTwoRegistrationActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(TalentoTwoRegistrationActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TalentoTwoRegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(TalentoTwoRegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(TalentoTwoRegistrationActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(getApplicationContext(), "Permission Already granted", Toast.LENGTH_SHORT).show();
        }

    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_PRO_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                proImageFile = new File(picturePath);

                final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(selectedImage);
                final Bitmap selectedImageBit = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImageBit);

            } catch (Exception e) {

            }
        }
    }
}