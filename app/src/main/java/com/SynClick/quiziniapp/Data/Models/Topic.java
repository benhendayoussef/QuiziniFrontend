package com.SynClick.quiziniapp.Data.Models;

import android.graphics.Bitmap;

public class Topic {
    String id;
    String name,imageUrl,colorRef,description;
    Bitmap image;
    double userRank;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Topic(String id, String name, String imageUrl, String colorRef, String description,double userRank) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.colorRef = colorRef;
        this.description = description;
        this.userRank = userRank;
    }

    public double getUserRank() {
        return userRank;
    }

    public void setUserRank(double userRank) {
        this.userRank = userRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColorRef() {
        return colorRef;
    }

    public void setColorRef(String colorRef) {
        this.colorRef = colorRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
