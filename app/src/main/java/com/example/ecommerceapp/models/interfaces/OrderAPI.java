package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.GetOrderResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderAPI {
    @GET("/api/public/order/{id}")
    Call<GetOrderResponse> getOrder(@Path("id")int id);
}
