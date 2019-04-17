package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private SignInData data;

    public SignInData getData() {
        return data;
    }

    public void setData(SignInData data) {
        this.data = data;
    }
}
