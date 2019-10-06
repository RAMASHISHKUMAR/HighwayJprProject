package com.highwayjprproject.retrofit;


import com.highwayjprproject.model.LoginRegisterRequest;
import com.highwayjprproject.model.LoginRegisterResponse;

import com.highwayjprproject.model.RegistrationDetailsRequest;
import com.highwayjprproject.model.RegistrationDetailsResponse;
import com.highwayjprproject.model.VerifyOtpRequest;
import com.highwayjprproject.model.VerifyOtpResponse;
import retrofit2.Callback;

public class RestClient {
    // login mob no
    public static void loginUser(LoginRegisterRequest loginRegisterRequest, Callback<LoginRegisterResponse> callback) {
        RetrofitClient.getClient().loginResponseCall(loginRegisterRequest).enqueue(callback);
    }

    // verify otp mobile
    public static void otpVerify(VerifyOtpRequest verifyOtpRequest, Callback<VerifyOtpResponse>otpResponseCallback){
        RetrofitClient.getClient().verifyOtpResponseCall(verifyOtpRequest).enqueue(otpResponseCallback);
    }
    // registration details
    public static void regDetails(RegistrationDetailsRequest registrationDetailsRequest, Callback<RegistrationDetailsResponse>registrationDetailsResponseCallback){
        RetrofitClient.getClient().regDetailsResponseCall(registrationDetailsRequest).enqueue(registrationDetailsResponseCallback);
    }



}
