package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ViewAllAPI {
    @GET("/api/public/getMVProduct/all")
    Call<HorizontalViewAllResponse> getMVProductAll();

    @GET("/api/public/getMOProduct/all")
    Call<HomePageProductResponse> getMOProductAll();
}
