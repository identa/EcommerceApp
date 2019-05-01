package com.example.ecommerceapp.models.entities.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddOrderReq {
    @SerializedName("orders")
    @Expose
    private List<AddOrderRequest> orders;

    public List<AddOrderRequest> getOrders() {
        return orders;
    }

    public void setOrders(List<AddOrderRequest> orders) {
        this.orders = orders;
    }
}
