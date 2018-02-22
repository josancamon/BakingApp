package com.example.santiago.bakingapp.Widget;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.santiago.bakingapp.Fragments.IngredientsListFragment;

/**
 * Created by Santiago on 21/02/2018.
 */

public class IngredientsWidgetService extends IntentService {

    public static final String ACTION_WIDGET_RECIPES = "com.example.android.mygarden.action.action_widget_recipes";

    public IngredientsWidgetService() {
        super("IngredientsWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            String action = intent.getAction();
            if (ACTION_WIDGET_RECIPES.equals(action)){
                handleActionRecipes();
            }
        }
    }
    private void handleActionRecipes() {
    }
}
