package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.entities.responses.EditCartResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeleteCartAPI {
    @DELETE("/api/public/cart/delete/{id}")
    Call<DeleteCartResponse> deleteCart(@Path("id") int id);

    @PUT("/api/public/cart/edit/{id}/{qty}")
    Call<EditCartResponse> editCart(@Path("id") int id, @Path("qty") int qty);
}
