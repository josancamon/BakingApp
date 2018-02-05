package com.example.santiago.bakingapp.Model;

import android.graphics.Bitmap;

/**
 * Created by Santiago on 27/01/2018.
 */

public class Recipe {
    private String mId;
    private String mRecipeName;
    private String mServings;
    private Bitmap mImageBitmap;

    public Recipe(String id, String recipeName, String servings,Bitmap imageBitmap){
        mId =id;
        mRecipeName = recipeName;
        mServings = servings;
        mImageBitmap = imageBitmap;
    }

    public String getId() {
        return mId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public String getServings() {
        return mServings;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }
}
