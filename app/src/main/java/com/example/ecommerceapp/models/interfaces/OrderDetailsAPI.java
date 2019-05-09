package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderDetailsAPI {
    @GET("/api/public/order/detail/{id}")
    Call<OrderDetailsResponse> getOrderDetails(@Path("id") int id);
}
