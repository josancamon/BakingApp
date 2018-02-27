package com.example.santiago.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Santiago on 27/01/2018.
 */

public class Ingredient implements Parcelable {
    private String mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient(String quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mQuantity);
        parcel.writeString(mMeasure);
        parcel.writeString(mIngredient);
    }
    private Ingredient(Parcel in) {
        mQuantity = in.readString();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }
    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
