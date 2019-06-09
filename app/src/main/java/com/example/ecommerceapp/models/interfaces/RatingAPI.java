package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.RatingRequest;
import com.example.ecommerceapp.models.entities.responses.RatingResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RatingAPI {
    @POST("/api/public/rating/{pid}/{uid}")
    Call<RatingResponse> rating(@Path("pid") int pid, @Path("uid") int uid, @Body RatingRequest request);

    @PUT("/api/public/rating/{pid}/{uid}")
    Call<RatingResponse> updateRating(@Path("pid") int pid, @Path("uid") int uid, @Body RatingRequest request);
}
