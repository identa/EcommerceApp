package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.CartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CartAPI {
    @GET("/api/public/cart/get/{id}")
    Call<CartResponse> getCart(@Path("id") int id);
}