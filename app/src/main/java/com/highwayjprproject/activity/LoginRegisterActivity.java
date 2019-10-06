package com.highwayjprproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.highwayjprproject.R;
import com.highwayjprproject.model.LoginRegisterRequest;
import com.highwayjprproject.model.LoginRegisterResponse;
import com.highwayjprproject.retrofit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.highwayjprproject.utils.Constants;
import com.highwayjprproject.utils.HighwayPrefs;
import com.highwayjprproject.utils.Utils;

public class LoginRegisterActivity extends AppCompatActivity {

    private EditText userPhoneNo;
    private Button btnSendOtp;
    String phone_number;
    private int backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        userPhoneNo = findViewById(R.id.edtTxtMobNo);
        btnSendOtp = findViewById(R.id.btnSendOtp);


        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationLoginUser();
            }
        });

    }

    private boolean validateInput() {
        boolean check = false;

        phone_number = userPhoneNo.getText().toString();


        if (phone_number.isEmpty() && userPhoneNo.length()==10) {
            userPhoneNo.setError(" enter a valid phone number ");
            check = false;
        } else {
            userPhoneNo.setError(null);
            check = true;
        }

        return check;
    }

    public void validationLoginUser() {

        if (validateInput()){

            LoginRegisterRequest loginRegisterRequest = new LoginRegisterRequest();
            loginRegisterRequest.setMobile(phone_number);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.loginUser(loginRegisterRequest, new Callback<LoginRegisterResponse>() {
                    @Override
                    public void onResponse(Call<LoginRegisterResponse> call, Response<LoginRegisterResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus() == true) {
                                Intent intent = new Intent(LoginRegisterActivity.this, MobileOtpVerificationActivity.class);
                                HighwayPrefs.putString(LoginRegisterActivity.this, Constants.USERMOBILE, phone_number);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginRegisterActivity.this, "pls Verify Otp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRegisterResponse> call, Throwable t) {
                        Toast.makeText(LoginRegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
