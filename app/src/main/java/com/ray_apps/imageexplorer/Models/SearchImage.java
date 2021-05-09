package com.ray_apps.imageexplorer.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//Contains A list of Unsplash Images
public class SearchImage {

    @SerializedName("results")
    private List<UnsplashImage> imageList = null;

    public List<UnsplashImage> getImageList(){
        return imageList;
    }
}
