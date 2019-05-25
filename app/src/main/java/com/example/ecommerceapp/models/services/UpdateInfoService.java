package com.example.ecommerceapp.models.services;

import com.example.ecommerceapp.models.entities.requests.UpdateInfoRequest;
import com.example.ecommerceapp.models.entities.requests.UpdatePassRequest;

public interface UpdateInfoService {
    void updateInfo(int id, UpdateInfoRequest request);
    void updatePass(int id, UpdatePassRequest request);
}
