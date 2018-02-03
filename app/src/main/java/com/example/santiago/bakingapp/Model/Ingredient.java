package com.example.santiago.bakingapp.Model;

/**
 * Created by Santiago on 27/01/2018.
 */

public class Ingredient {
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
}
