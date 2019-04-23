package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCartResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private AddCartData data;

    public AddCartData getData() {
        return data;
    }

    public void setData(AddCartData data) {
        this.data = data;
    }
}
