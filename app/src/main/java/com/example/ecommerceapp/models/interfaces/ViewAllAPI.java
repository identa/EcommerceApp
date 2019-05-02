package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ViewAllAPI {
    @GET("/api/public/getMVProduct/all")
    Call<HorizontalViewAllResponse> getMVProductAll();

    @GET("/api/public/getMOProduct/all")
    Call<HomePageProductResponse> getMOProductAll();

    @GET("/api/public/product")
    Call<HorizontalViewAllResponse> search(@Query("search") String query);

    @GET("/api/public/cat/{id}")
    Call<HorizontalViewAllResponse> showCat(@Path("id") int id);
}
