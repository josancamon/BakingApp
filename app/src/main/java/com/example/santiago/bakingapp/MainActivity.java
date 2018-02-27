package com.example.santiago.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.RecipesListFragment;
import com.example.santiago.bakingapp.Model.Recipe;
import com.example.santiago.bakingapp.Widget.IngredientsWidgetProvider;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.OnRecipeClickListener, RecipesListFragment.AddShorcut {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipesListFragment recipesFragment = new RecipesListFragment();
            fragmentManager.beginTransaction().add(R.id.recipe_list, recipesFragment).commit();
        }
        //new async().execute();
    }

    @Override
    public void onRecipeClick(Recipe recipeClicked) {
        Toast.makeText(this, recipeClicked.getRecipeName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailsRecipeActivity.class);
        intent.putExtra("id", recipeClicked.getId());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void addShortcut(Recipe recipeAdded) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pinWidgetToHomeScreen(this);
        }
    }

    private void pinWidgetToHomeScreen(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppWidgetManager appWidgetManager = context.getSystemService(AppWidgetManager.class);
            if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                ComponentName widgetProvider = new ComponentName(context, IngredientsWidgetProvider.class);
                appWidgetManager.requestPinAppWidget(widgetProvider, null, null);
            } else {
                Toast.makeText(context, "device not support pinning", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
