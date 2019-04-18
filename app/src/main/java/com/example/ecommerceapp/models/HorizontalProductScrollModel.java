package com.example.ecommerceapp.models;

public class HorizontalProductScrollModel {
    private int id;

    private String imageLink;

    private String title;

    private String desc;

    private double price;

    public HorizontalProductScrollModel(int id, String imageLink, String title, String desc, double price) {
        this.id = id;
        this.imageLink = imageLink;
        this.title = title;
        this.desc = desc;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
