package com.example.santiago.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.example.santiago.bakingapp.MainActivity;
import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.R;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Santiago on 12/02/2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {
    SharedPreferences sharedPreferences;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<Ingredient> ingredientList) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        views.setTextViewText(R.id.title_w, recipeName);
        views.removeAllViews(R.id.container_w);
        if (ingredientList!=null){
            for (Ingredient ingredient : ingredientList) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                        R.layout.list_item_ingredient);
                ingredientView.setTextViewText(R.id.ingredient_name,ingredient.getIngredient());
                ingredientView.setTextViewText(R.id.ingredient_quantity,ingredient.getQuantity());
                ingredientView.setTextViewText(R.id.ingredient_measure,ingredient.getMeasure());
                views.addView(R.id.container_w, ingredientView);
            }
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        List<Ingredient> ingredients ;
        sharedPreferences = context.getSharedPreferences("preferences",
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String result = sharedPreferences.getString("ingredients",null);
        Ingredient[] arrayIngredient = gson.fromJson(result,Ingredient[].class);
        ingredients = Arrays.asList(arrayIngredient);
        ingredients = new ArrayList<>(ingredients);
        String recipeName = sharedPreferences.getString("recipe_name",null);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
