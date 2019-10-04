package com.highwayjprproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import model.RegistrationDetailsRequest;
import model.RegistrationDetailsResponse;
import retrofit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.CameraUtils;
import utils.Constants;
import utils.HighwayPreface;
import utils.Utils;

public class RegistrationDetailsActivity extends AppCompatActivity {

    private ImageView imgDetailbackArrow, imgDetailProfile, imgCalenderDatePicker;
    private EditText edtTxtUserName, edtTxtUserEmail, edtTxtUserMobile, edtTxtUserDobDate, edtTxtUserAddress;
    private RadioButton userRadioMale, userRadioFemale, userRadioDiver, userRadioCustomer, userRadioMillUser;
    private String gender;
    private String userRole;
    private Button btnSubmit;

    private RadioGroup radiogroup_Gender;
    private RadioGroup radioGroup_RoleUser;

    String userName, userEmail, userMobNo, userDobDate, userAddress, userId;
    private DatePickerDialog datePickerDialog;


    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    private static final int CAMERA_CAPTURE_FRONT_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_BACK_IMAGE_REQUEST_CODE = 101;

    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;

    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    //public static final String VIDEO_EXTENSION = "mp4";

    private static String imageStoragePath;
    private String base64UserImg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);

        initView();  // finding by id
        calenderPickOperation();         //  calender picker
        radioGenderGroupOperation();    // gender group operation
        backArrowOperation();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                regDetailValidationOperation();

            }
        });


        imgDetailProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions(getApplicationContext())) {
                    captureImage();
                } else {
                    requestCameraPermission(MEDIA_TYPE_IMAGE);
                }
            }
        });
    }

    public void initView(){

        imgDetailbackArrow = findViewById(R.id.imgBackArrow);
        imgDetailProfile = findViewById(R.id.regUserImg);

        edtTxtUserName = findViewById(R.id.edtTxtInputUserName);
        edtTxtUserEmail = findViewById(R.id.edtTxtInputUserEmail);
        //  edtTxtUserMobile = findViewById(R.id.edtTxtInputUserMobile);
        edtTxtUserAddress = findViewById(R.id.edtTxtInputUserAddress);

        imgCalenderDatePicker = findViewById(R.id.dobCalenderPicker);
        edtTxtUserDobDate = findViewById(R.id.edtTxtInputUserDOB);
        // calenderDatePicker.setEnabled(false);

        userRadioMale = findViewById(R.id.radio_Male);
        userRadioFemale = findViewById(R.id.radio_female);
        userRadioDiver = findViewById(R.id.radio_Driver);
        userRadioCustomer = findViewById(R.id.radio_Customer);
        userRadioMillUser = findViewById(R.id.radio_MillUser);

        btnSubmit = findViewById(R.id.btnSubmitDetails);

        radiogroup_Gender = (RadioGroup) findViewById(R.id.radiogroup_Gender);
        radioGroup_RoleUser = (RadioGroup) findViewById(R.id.radiogroup_Role);
    }




    private void requestCameraPermission(final int type) {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                captureImage();
                            } else {
                                //captureVideo();
                            }

                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getApplicationContext(), file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);   // start the image capture Intent
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);  // get the file url
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                previewCapturedImage();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private void previewCapturedImage() {
        try {
            imgDetailProfile.setVisibility(View.VISIBLE);

            Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);

            base64UserImg = getEncoded64ImageStringFromBitmap(bitmap);

            imgDetailProfile.setImageBitmap(bitmap);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(RegistrationDetailsActivity.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }


    public void backArrowOperation() {
        imgDetailbackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void calenderPickOperation() {
        imgCalenderDatePicker.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;// mHour, mMinute;

            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RegistrationDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        //dobDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        edtTxtUserDobDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    public void radioGenderGroupOperation() {
        radiogroup_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderrg = (RadioButton) group.findViewById(checkedId);
                if (null != genderrg) {
                    gender = genderrg.getText().toString();
                    Toast.makeText(RegistrationDetailsActivity.this, genderrg.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void onUserRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {

            case R.id.radio_Customer:
                if (checked)

                    userRadioCustomer.setText("Customer");
                userRole = userRadioCustomer.getText().toString().trim();
                break;


            case R.id.radio_Driver:
                if (checked)

                    userRadioDiver.setText("Driver");       // userRadioDiver.setText("Driver");
                userRole = userRadioDiver.getText().toString().trim();
                break;


            case R.id.radio_MillUser:
                if (checked)

                    userRadioMillUser.setText("MillUser");
                userRole = userRadioMillUser.getText().toString().trim();
                break;
        }
    }

    // email validation operation perform
    public boolean inputValidation() {

        userName = edtTxtUserName.getText().toString().trim();
        userEmail = edtTxtUserEmail.getText().toString().trim();
        userDobDate = edtTxtUserDobDate.getText().toString().trim();
        userAddress = edtTxtUserAddress.getText().toString().trim();

        if (TextUtils.isEmpty(base64UserImg)) {
            Toast.makeText(this, "Pls capture the user image", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userName.isEmpty()) {
            edtTxtUserName.setError("enter a valid email address");
            return false;
        } else {
            edtTxtUserName.setError(null);
        }

        if (userEmail.isEmpty()) {
            edtTxtUserEmail.setError("enter a valid email address");
            return false;
        } else {
            edtTxtUserEmail.setError(null);
        }

        if (!validEmail(edtTxtUserEmail)) {   // email validation
            Toast.makeText(getApplicationContext(), "Enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*if (userMobNo.isEmpty()) {
            edtTxtUserMobile.setError("enter valid mobile numbere");
            return false;
        } else {
            edtTxtUserMobile.setError(null);
        }*/

        if (userDobDate.isEmpty()) {
            edtTxtUserDobDate.setError("enter a valid D.O.B");
            return false;
        } else {
            edtTxtUserDobDate.setError(null);
        }

        if (userAddress.isEmpty()) {
            edtTxtUserAddress.setError("pls enter Address");
            return false;
        } else {
            edtTxtUserAddress.setError(null);
        }

        return true;
    }

    private boolean validEmail(EditText edtTxtUserEmail) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // onClick of button perform this simplest code.

        if (edtTxtUserEmail.getText().toString().matches(emailPattern)) {
            return true;
        } else {
            return false;
        }
    }



    public void regDetailValidationOperation() {

        if (inputValidation()) {

            RegistrationDetailsRequest registrationDetailsRequest = new RegistrationDetailsRequest();
            registrationDetailsRequest.setName(userName);
            registrationDetailsRequest.setEmail(userEmail);
            registrationDetailsRequest.setDob(userDobDate);
            registrationDetailsRequest.setGender(gender);
            //registrationDetailsRequest.setRole(userRole);
            registrationDetailsRequest.setAddress(userAddress);
            registrationDetailsRequest.setBase64File(base64UserImg);   // img uploading
            userId = HighwayPreface.getString(getApplicationContext(), "id");
            registrationDetailsRequest.setUserId(userId);

            if (userRole.equalsIgnoreCase("customer")) {
                registrationDetailsRequest.setRoleId("4");

            } else if (userRole.equalsIgnoreCase("driver")) {
                registrationDetailsRequest.setRoleId("3");

            } else if (userRole.equalsIgnoreCase("millUser")) {
                registrationDetailsRequest.setRoleId("2");
            }


            Utils.showProgressDialog(getApplicationContext());

            RestClient.regDetails(registrationDetailsRequest, new Callback<RegistrationDetailsResponse>() {
                @Override
                public void onResponse(Call<RegistrationDetailsResponse> call, Response<RegistrationDetailsResponse> response) {

                    Utils.dismissProgressDialog();
                    // id= 1 admin ,2 mealUser , 3 driver , 4 customer, 5 owner
                    if (response.body() != null && response.body().getStatus()) {
                        if (response.body().getUserStatus().equalsIgnoreCase("1")) {
                                Intent intent = new Intent(RegistrationDetailsActivity.this, DashBoardActivity.class);
                               HighwayPreface.putString(getApplicationContext(),Constants.ROLEID,response.body().getRoleId());

                                startActivity(intent);
                                finish();



                        } else if (response.body().getStatus() == false) {
                            Toast.makeText(RegistrationDetailsActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistrationDetailsActivity.this, "Pls Enter your details", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RegistrationDetailsResponse> call, Throwable t) {
                    Toast.makeText(RegistrationDetailsActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }






}
