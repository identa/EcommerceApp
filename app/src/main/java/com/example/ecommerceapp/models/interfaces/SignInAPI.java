package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.SignInRequest;
import com.example.ecommerceapp.models.entities.responses.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInAPI {
    @POST("/api/customer/signin")
    Call<SignInResponse> signIn(@Body SignInRequest request);
}
