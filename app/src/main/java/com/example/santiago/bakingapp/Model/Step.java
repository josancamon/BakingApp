package com.example.santiago.bakingapp.Model;

import android.graphics.Bitmap;

/**
 * Created by Santiago on 29/01/2018.
 */

public class Step {
    private int mStepId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private Bitmap mImageBitmap;

    public Step(int stepId,String shortDescription, String description, String videoUrl, Bitmap imageBitmap) {
        mStepId = stepId;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mImageBitmap = imageBitmap;
    }
    public int getStepId() {
        return mStepId;
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

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }
}
