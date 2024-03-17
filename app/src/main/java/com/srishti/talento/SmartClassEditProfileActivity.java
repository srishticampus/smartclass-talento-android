package com.srishti.talento;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
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

public class SmartClassEditProfileActivity extends AppCompatActivity implements Validator.ValidationListener {
    private View backBtnEditProfileBtn;
    private LinearLayout changeImageLayoutLl;
    private CircleImageView editProfileImgIv;
    @NotEmpty
    private TextInputEditText nameEt;
    @NotEmpty
    @Length(max = 10, min = 10, message = "Enter a valid mobile number")
    private TextInputEditText phoneNumberEt;
    @NotEmpty
    @Email
    private TextInputEditText emailEt;
    @NotEmpty
    private TextInputEditText placeEt;
    @NotEmpty
    private TextInputEditText schoolEt;
    private TextView editProfileSaveDetailsBtn;

    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int RESULT_LOAD_PRO_IMAGE = 106;
    private File proImageFile;
    private Validator validator;

//    View backBtn;
//    TextInputEditText firstNameEdt, lastname, phoneNumber, emailId, placeEdt, schoolEdt;
//    CircleImageView profileImage;
//    LinearLayout changeImage;
//    TextView saveDetailsBtn;
//    public static final int PICK_IMAGE = 1;
//
//    Integer ImageSource = 0;
//    private final int OPEN_MEDIA_PICKER = 300;
//    private final int CAMERA_REQUEST = 301;
//    private final int ASK_WRITE_PERMISSION = 201;
//    private final int ASK_CAMERA_PERMISSION = 202;
//    private final int ASK_CAMERA_WRITE_PERMISSION = 203;
//    private File imageFile;
//    private String selectedImagePath;
//    private GetFilePath getFilePath;
//    String picturePath = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_class_edit_profile);

//        firstNameEdt = findViewById(R.id.edit_profile_first_name);
//        lastname = findViewById(R.id.edit_profile_last_name);
//        phoneNumber = findViewById(R.id.edit_profile_phone_number);
//        emailId = findViewById(R.id.edit_profile_email_id);
//        placeEdt = findViewById(R.id.edit_profile_place);
//        schoolEdt = findViewById(R.id.edit_profile_school);
//        profileImage = findViewById(R.id.edit_profile_img);
//        backBtn = findViewById(R.id.back_btn_edit_profile);
//        changeImage = findViewById(R.id.change_image_layout);
//        saveDetailsBtn = findViewById(R.id.edit_profile_save_details_btn);
//
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        try {
//            firstNameEdt.setText(getIntent().getStringExtra("firstName"));
//            lastname.setText(getIntent().getStringExtra("lastName"));
//            //phoneNumber.setText(getIntent().getStringExtra("mobileNumber"));
//            emailId.setText(getIntent().getStringExtra("mailId"));
//            placeEdt.setText(getIntent().getStringExtra("place"));
//            schoolEdt.setText(getIntent().getStringExtra("education"));
//            Glide.with(getApplicationContext()).asBitmap().load(getIntent().getStringExtra("img")).into(profileImage);
//        } catch (Exception e) {
//
//        }
//
//
//        changeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Intent intent = new Intent();
////                intent.setType("image/*");
////                intent.setAction(Intent.ACTION_GET_CONTENT);
////                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//
//                // showImageUploadDialog();
//
//                ImageSource = 1;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                    if (isPermisiionGranted()) {
//                        //Toast.makeText(getActivity(), "Permission already Granted", Toast.LENGTH_SHORT).show();
//
//                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        photoPickerIntent.setType("image/*");
//                        startActivityForResult(photoPickerIntent, OPEN_MEDIA_PICKER);
//
//                    } else {
//                        takePermisssion();
//                    }
//
//                } else {
//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, OPEN_MEDIA_PICKER);
//                }
//
//
//            }
//        });
//
//
//        saveDetailsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
//                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//                if ((firstNameEdt.getText().toString().isEmpty())) {
//                    firstNameEdt.setError("mandatory");
//                } else if ((lastname.getText().toString().isEmpty())) {
//                    lastname.setError("mandatory");
//                } else if (!(emailId.getText().toString().matches(emailPattern)) && (emailId.getText().toString().isEmpty())) {
//                    emailId.setError("mandatory");
////                } else if ((phoneNumber.getText().toString().isEmpty()) && !(phoneNumber.getText().toString().trim().length() < 10)) {
////                    phoneNumber.setError("mandatory");
//                } else if ((placeEdt.getText().toString().isEmpty())) {
//                    placeEdt.setError("mandatory");
//                } else if ((schoolEdt.getText().toString().isEmpty())) {
//                    schoolEdt.setError("mandatory");
//                } else {
//                    RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("user_id", null));
//                    RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), firstNameEdt.getText().toString());
//                    RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), lastname.getText().toString());
//                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailId.getText().toString());
//                    RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("mobileNumber"));
//                    RequestBody place = RequestBody.create(MediaType.parse("text/plain"), placeEdt.getText().toString());
//                    RequestBody school = RequestBody.create(MediaType.parse("text/plain"), schoolEdt.getText().toString());
////                     File file=imageFile;
//                    if ("null".equals(picturePath)) {
//                        // Toast.makeText(getApplicationContext(), "No file", Toast.LENGTH_SHORT).show();
//                        //apiCall(userId, firstName, lastName, email, phone, place, school);
//
//                         talentoTwoProfileUpdateWithOutImage(userId, firstName, lastName, email, phone, place, school);
//
//                    } else {
//                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("avatar", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile));
//                        // apiCall(userId, firstName, lastName, email, phone, place, school, filePart);
//
//                        talentoTwoProfileUpdateWithImage(userId, firstName, lastName, email, phone, place, school, filePart);
//                    }
//                    // Log.i("IMG",picturePath);
//
//                }
//
//
////                if (picturePath.equals("")) {
////
////                    Toast.makeText(getApplicationContext(), "no_image", Toast.LENGTH_SHORT).show();
////                    Log.d("image", "no_image");
////
////                    // apiCall(userId, firstName, lastName, email, phone, place, school);
////                    finish();
////                } else {
////                    Log.d("image", "image");
////
////
////                    apiCall(userId, firstName, lastName, email, phone, place, school, filePart);
////                    finish();
////                }
//
//            }
//        });

        initView();

        validator = new Validator(this);
        validator.setValidationListener(this);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "");

        viewProfileApi(userId);

        backBtnEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeImageLayoutLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_PRO_IMAGE);
                }
            }
        });

        editProfileSaveDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

    }

    private void initView() {
        backBtnEditProfileBtn = findViewById(R.id.back_btn_edit_profile_btn);
        changeImageLayoutLl = findViewById(R.id.change_image_layout_ll);
        editProfileImgIv = findViewById(R.id.edit_profile_img_iv);
        nameEt = findViewById(R.id.name_et);
        phoneNumberEt = findViewById(R.id.phone_number_et);
        emailEt = findViewById(R.id.email_et);
        placeEt = findViewById(R.id.place_et);
        schoolEt = findViewById(R.id.school_et);
        editProfileSaveDetailsBtn = findViewById(R.id.edit_profile_save_details_btn);
    }

    @Override
    public void onValidationSucceeded() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("category", getApplicationContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "");
        editProfileApi(userId);

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

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(SmartClassEditProfileActivity.this, new String[]{permission}, requestCode);
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
                editProfileImgIv.setImageBitmap(selectedImageBit);

            } catch (Exception e) {

            }
        }
    }

    public void editProfileApi(String userId) {

        RequestBody reqUserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), nameEt.getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailEt.getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), phoneNumberEt.getText().toString());
        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), placeEt.getText().toString());
        RequestBody school = RequestBody.create(MediaType.parse("text/plain"), schoolEt.getText().toString());
        MultipartBody.Part proImageFilePart = null;
        try {
            proImageFilePart = MultipartBody.Part.createFormData("avatar", proImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), proImageFile));

        } catch (NullPointerException e) {

        }

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_EDIT_PROFILE(reqUserId, name, email, phone, place, school, proImageFilePart).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {

                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        Toast.makeText(SmartClassEditProfileActivity.this, root.message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
                        intent.putExtra("EditProfile", "fromEditProfile");
                        startActivity(intent);
                    }else {
                        Toast.makeText(SmartClassEditProfileActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SmartClassEditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(SmartClassEditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewProfileApi(String userId) {

        Api api = ApiClient.UserData().create(Api.class);
        api.TALENTO_TWO_VIEW_PROFILE(userId).enqueue(new Callback<TalentoTwoRoot>() {
            @Override
            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
                if (response.isSuccessful()) {
                    TalentoTwoRoot root = response.body();
                    if (root.status) {
                        try {
                            nameEt.setText(root.userDetails.get(0).name);
                            phoneNumberEt.setText(root.userDetails.get(0).phone);
                            emailEt.setText(root.userDetails.get(0).email);
                            placeEt.setText(root.userDetails.get(0).place);
                            schoolEt.setText(root.userDetails.get(0).school);

                        } catch (Exception e) {

                        }
                    } else {
                        Toast.makeText(SmartClassEditProfileActivity.this, root.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SmartClassEditProfileActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
                Toast.makeText(SmartClassEditProfileActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void apiCall(RequestBody userId, RequestBody firstName, RequestBody lastName, RequestBody email, RequestBody phone, RequestBody place, RequestBody school, MultipartBody.Part filePart) {
//
//        Api api = ApiClient.UserData().create(Api.class);
//        api.EDIT_PROFILE_MODEL_ROOT_CALL(userId, firstName, lastName, email, phone, place, school, filePart).enqueue(new Callback<EditProfileModelRoot>() {
//            @Override
//            public void onResponse(Call<EditProfileModelRoot> call, Response<EditProfileModelRoot> response) {
//                if (response.isSuccessful()) {
//                    EditProfileModelRoot editProfileModelRoot = response.body();
//                    if (editProfileModelRoot.status) {
//                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                        intent.putExtra("EditProfile", "fromEditProfile");
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EditProfileModelRoot> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void apiCall(RequestBody userId, RequestBody firstName, RequestBody lastName, RequestBody email, RequestBody phone, RequestBody place, RequestBody school) {
//
//        Api api = ApiClient.UserData().create(Api.class);
//        api.EDIT_PROFILE_ROOT_CALL(userId, firstName, lastName, email, phone, place, school).enqueue(new Callback<EditProfileModelRoot>() {
//            @Override
//            public void onResponse(Call<EditProfileModelRoot> call, Response<EditProfileModelRoot> response) {
//                if (response.isSuccessful()) {
//                    EditProfileModelRoot editProfileModelRoot = response.body();
//                    if (editProfileModelRoot.status) {
//                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                        intent.putExtra("EditProfile", "fromEditProfile");
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EditProfileModelRoot> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    void talentoTwoProfileUpdateWithImage(RequestBody userId, RequestBody firstName, RequestBody lastName,
//                                          RequestBody email, RequestBody phone,
//                                          RequestBody place, RequestBody school, MultipartBody.Part filePart) {
//
//        Api api = ApiClient.UserData().create(Api.class);
//        api.TALENTO_TWO_EDIT_PROFILE_WITH_IMAGE_CALL(userId, firstName, lastName, email, phone, place, school, filePart).enqueue(new Callback<TalentoTwoRoot>() {
//            @Override
//            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
//                if (response.isSuccessful()) {
//                    TalentoTwoRoot talentoTwoRoot = response.body();
//                    if (talentoTwoRoot.status) {
//                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                        intent.putExtra("EditProfile", "fromEditProfile");
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
//    void talentoTwoProfileUpdateWithOutImage(RequestBody userId, RequestBody firstName,
//                                             RequestBody lastName, RequestBody email,
//                                             RequestBody phone, RequestBody place, RequestBody school) {
//        Api api = ApiClient.UserData().create(Api.class);
//        api.TALENTO_TWO_EDIT_PROFILE_WITHOUT_IMAGE(userId, firstName, lastName, email, phone, place, school).enqueue(new Callback<TalentoTwoRoot>() {
//            @Override
//            public void onResponse(Call<TalentoTwoRoot> call, Response<TalentoTwoRoot> response) {
//                if (response.isSuccessful()) {
//                    TalentoTwoRoot talentoTwoRoot = response.body();
//                    if (talentoTwoRoot.status) {
//                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), SmartClassHomeActivity.class);
//                        intent.putExtra("EditProfile", "fromEditProfile");
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TalentoTwoRoot> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
//    private boolean isPermisiionGranted() {
//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
//            // return Environment.isExternalStorageManager();
//            return true;
//        } else {
//            int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
//        }
//    }
//
//    public void takePermisssion() {
//        askPermissionForRead();
//        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
//
//
//    }
//
//    private boolean shouldAskPermission() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    @TargetApi(23)
//    private void askPermissionForWrite() {
//        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
//        requestPermissions(perms, ASK_WRITE_PERMISSION);
//    }
//
//    @TargetApi(23)
//    private void askPermissionForRead() {
//        String[] perms = {"android.permission.READ_EXTERNAL_STORAGE"};
//        requestPermissions(perms, 101);
//    }
//
//    @TargetApi(23)
//    private void askPermissionCamera() {
//        String[] perms = {"android.permission.CAMERA"};
//        int permsRequestCode = ASK_CAMERA_PERMISSION;
//        requestPermissions(perms, permsRequestCode);
//    }
//
//    @TargetApi(23)
//    private void askPermissionForWriteCamera() {
//        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
//        requestPermissions(perms, ASK_CAMERA_WRITE_PERMISSION);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == OPEN_MEDIA_PICKER) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK && data != null) {
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                if ("".equals(picturePath)) {
//                    picturePath = "null";
//                } else {
//                    imageFile = new File(picturePath);
//                    Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_SHORT).show();
//                    profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                }
//
//
//            }
//        }
//    }
//
//    private boolean isValidMail(String email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private boolean isValidMobile(String phone) {
//        return android.util.Patterns.PHONE.matcher(phone).matches();
//    }


}