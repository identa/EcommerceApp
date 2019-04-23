package com.example.ecommerceapp.models.services;

import com.example.ecommerceapp.models.entities.requests.SignUpRequest;

public interface SignUpService {
    void doSignUp(SignUpRequest request);
}
