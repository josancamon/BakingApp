package com.example.santiago.bakingapp.Model;

/**
 * Created by Santiago on 27/01/2018.
 */

public class Recipe {
    private String mId;
    private String mRecipeName;
    private String mServings;

    public Recipe(String id, String recipeName, String servings){
        mId =id;
        mRecipeName = recipeName;
        mServings = servings;
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
}
