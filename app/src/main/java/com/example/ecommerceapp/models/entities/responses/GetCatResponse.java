package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCatResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private List<GetCatData> data;

    public List<GetCatData> getData() {
        return data;
    }

    public void setData(List<GetCatData> data) {
        this.data = data;
    }
}
