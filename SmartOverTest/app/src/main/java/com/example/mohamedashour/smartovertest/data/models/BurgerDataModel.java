package com.example.mohamedashour.smartovertest.data.models;

import com.google.gson.annotations.SerializedName;

public class BurgerDataModel {
    @SerializedName("ref")
    private String ref;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("price")
    private Integer price;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
