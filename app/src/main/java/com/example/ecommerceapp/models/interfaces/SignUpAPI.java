package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.SignInRequest;
import com.example.ecommerceapp.models.entities.requests.SignUpRequest;
import com.example.ecommerceapp.models.entities.responses.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpAPI {
    @POST("/api/customer/signup")
    Call<SignInResponse> signUp(@Body SignUpRequest request);
}
