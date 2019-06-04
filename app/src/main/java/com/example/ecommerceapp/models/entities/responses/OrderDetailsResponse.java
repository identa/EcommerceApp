package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private OrderDetailResponse data;

    public OrderDetailResponse getData() {
        return data;
    }

    public void setData(OrderDetailResponse data) {
        this.data = data;
    }
}
