package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrderResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private List<GetOrderData> data;

    public List<GetOrderData> getData() {
        return data;
    }

    public void setData(List<GetOrderData> data) {
        this.data = data;
    }
}
