package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("originalPrice")
    @Expose
    private double originalPrice;

    @SerializedName("discount")
    @Expose
    private int discount;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("inCart")
    @Expose
    private boolean inCart;

    @SerializedName("inWishlist")
    @Expose
    private boolean inWishlist;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("images")
    @Expose
    private List<ProductImageData> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isInWishlist() {
        return inWishlist;
    }

    public void setInWishlist(boolean inWishlist) {
        this.inWishlist = inWishlist;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<ProductImageData> getImages() {
        return images;
    }

    public void setImages(List<ProductImageData> images) {
        this.images = images;
    }
}
