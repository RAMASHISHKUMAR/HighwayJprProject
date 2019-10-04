package com.highwayjprproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import model.VerifyOtpRequest;
import model.VerifyOtpResponse;
import retrofit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.HighwayPreface;

public class MobileOtpVerificationActivity extends AppCompatActivity {
    private EditText verifyPin;
    private Button btnVerify;
    private ImageView backImage;
    private TextView timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_verification);


        verifyPin = findViewById(R.id.verifyPin_edittext);
        backImage = findViewById(R.id.back_arrow_OTP);
        btnVerify = findViewById(R.id.verifyPin_btn);
        timer = findViewById(R.id.timmer_textview);


        timerInOtp();                          // time count dowan of otp
        backArrowOperationOnOtpVerifypage();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPinOperation(); // otp option perform
            }
        });


    }

    public void backArrowOperationOnOtpVerifypage() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void timerInOtp() {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("Resend otp");
            }

        }.start();

    }

    public void verifyPinOperation() {

        boolean check = true;
        String otpNumber = verifyPin.getText().toString().trim();
        String Usermobilenumber = HighwayPreface.getString(getApplicationContext(), "phone_number");

        if (otpNumber.isEmpty()) {
            verifyPin.setError("enter a valid otp");
            check = false;
        } else {
            verifyPin.setError(null);
        }

        if (check) {
            if (verifyPin != null) {
                VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
                verifyOtpRequest.setOtp(otpNumber);
                verifyOtpRequest.setMobile(Usermobilenumber);

                RestClient.otpVerify(verifyOtpRequest, new Callback<VerifyOtpResponse>() {
                    @Override
                    public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus()) {
                                Intent intent = new Intent(MobileOtpVerificationActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                Toast.makeText(MobileOtpVerificationActivity.this, "Wlcm to Highway", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (TextUtils.isEmpty(response.body().getName())) {
                                Intent intent = new Intent(MobileOtpVerificationActivity.this, RegistrationDetailsActivity.class);

                                //  HighwayPreface.getBoolean(getApplicationContext(),Constants.LOGGED_IN,true);
                                HighwayPreface.putString(getApplicationContext(), Constants.NAME, response.body().getName());
                                HighwayPreface.putString(getApplicationContext(), Constants.USERMOBILE, response.body().getMobile());
                                HighwayPreface.putString(getApplicationContext(), Constants.IMAGE, response.body().getImage());
                                HighwayPreface.putString(getApplicationContext(), Constants.EMAIL, response.body().getEmail());
                                HighwayPreface.putString(getApplicationContext(), Constants.GENDER, response.body().getGender());
                                HighwayPreface.putString(getApplicationContext(), Constants.ROLEID, response.body().getRoleId());
                                HighwayPreface.putString(getApplicationContext(), Constants.ADDRESS, response.body().getAddress());

                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(MobileOtpVerificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                        Toast.makeText(MobileOtpVerificationActivity.this, "Otp verification failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }


    }


}
