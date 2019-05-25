package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateInfoResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private UpdateInfoData data;

    public UpdateInfoData getData() {
        return data;
    }

    public void setData(UpdateInfoData data) {
        this.data = data;
    }
}
