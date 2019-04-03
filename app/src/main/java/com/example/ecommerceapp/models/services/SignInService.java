package com.example.ecommerceapp.models.services;

import com.example.ecommerceapp.models.entities.requests.SignInRequest;

import retrofit2.Response;

public interface SignInService {
    void doSignIn(SignInRequest request);
}
