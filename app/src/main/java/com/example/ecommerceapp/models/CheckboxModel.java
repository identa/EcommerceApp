package com.example.ecommerceapp.models;

public class CheckboxModel {
    private int id;
    private String type;
    private boolean isSelected;
    private String title;

    public CheckboxModel(int id, String type, boolean isSelected, String title) {
        this.id = id;
        this.type = type;
        this.isSelected = isSelected;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
