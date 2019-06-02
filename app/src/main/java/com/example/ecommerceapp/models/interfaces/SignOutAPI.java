package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface SignOutAPI {
    @DELETE("/api/customer/signout")
    Call<DeleteCartResponse> signOut(@Header(value = "Authorization") String token);
}
