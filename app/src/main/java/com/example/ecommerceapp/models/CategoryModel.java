package com.example.ecommerceapp.models;

public class CategoryModel {
    private int id;

    private String iconLink;

    private String name;

    public CategoryModel(int id, String iconLink, String name) {
        this.id = id;
        this.iconLink = iconLink;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
