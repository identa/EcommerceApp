package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrderResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private AddOrderData data;

    public AddOrderData getData() {
        return data;
    }

    public void setData(AddOrderData data) {
        this.data = data;
    }
}
