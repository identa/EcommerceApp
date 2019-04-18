package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageProductResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private List<HomePageProductData> data;

    public List<HomePageProductData> getData() {
        return data;
    }

    public void setData(List<HomePageProductData> data) {
        this.data = data;
    }
}
