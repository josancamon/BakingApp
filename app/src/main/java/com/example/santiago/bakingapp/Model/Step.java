package com.example.santiago.bakingapp.Model;

/**
 * Created by Santiago on 29/01/2018.
 */

public class Step {
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;

    public Step(String shortDescription, String description, String videoUrl){
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }
}
