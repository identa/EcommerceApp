package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditCartResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private EditCartData data;

    public EditCartData getData() {
        return data;
    }

    public void setData(EditCartData data) {
        this.data = data;
    }
}
