package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private List<OrderDetailsData> data;

    public List<OrderDetailsData> getData() {
        return data;
    }

    public void setData(List<OrderDetailsData> data) {
        this.data = data;
    }
}
