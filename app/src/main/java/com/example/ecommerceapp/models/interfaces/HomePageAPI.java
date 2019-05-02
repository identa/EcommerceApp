package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.GetCatResponse;
import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.entities.responses.SliderResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HomePageAPI {
    @GET("/api/public/getCampaign")
    Call<SliderResponse> getSlider();

    @GET("/api/public/getMVProduct")
    Call<HomePageProductResponse> getMVProduct();

    @GET("/api/public/getMOProduct")
    Call<HomePageProductResponse> getMOProduct();

    @GET("/api/public/cat")
    Call<GetCatResponse> getCat();
}
