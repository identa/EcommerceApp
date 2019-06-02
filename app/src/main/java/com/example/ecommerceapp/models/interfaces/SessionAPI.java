package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SessionAPI {
    @GET("/api/public/session")
    Call<DeleteCartResponse> session(@Header(value = "Authorization") String token);
}
