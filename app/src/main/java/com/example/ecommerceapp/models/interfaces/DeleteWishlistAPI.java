package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteWishlistAPI {
    @DELETE("/api/public/wishlist/{pid}/{uid}")
    Call<DeleteCartResponse> deleteWishlist(@Path("pid") int pid, @Path("uid") int uid);
}
