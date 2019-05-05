package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddressResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private GetAddressData data;

    public GetAddressData getData() {
        return data;
    }

    public void setData(GetAddressData data) {
        this.data = data;
    }
}
