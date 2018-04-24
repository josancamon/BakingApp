package com.example.santiago.bakingapp.Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Santiago on 29/01/2018.
 */

public class Step implements Parcelable {
    private int mStepId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;
    private String mThumbnailUrl;

    public Step(int stepId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        mStepId = stepId;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
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


    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mStepId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbnailUrl);
    }
    private Step(Parcel in) {
        mStepId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbnailUrl = in.readString();
    }
    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
