package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetRatingResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private GetRatingResp data;

    public GetRatingResp getData() {
        return data;
    }

    public void setData(GetRatingResp data) {
        this.data = data;
    }
}
