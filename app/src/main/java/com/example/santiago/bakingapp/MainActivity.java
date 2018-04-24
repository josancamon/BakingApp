package com.example.santiago.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.RecipesListFragment;
import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.Model.Recipe;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;
import com.example.santiago.bakingapp.Widget.IngredientsWidgetProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipesListFragment.OnRecipeClickListener, RecipesListFragment.AddShorcut, LoaderManager.LoaderCallbacks<List<Ingredient>> {
    private static final String TAG = "MainActivity";
    private List<Ingredient> ingredients = new ArrayList<>();

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
        /*Bundle b = new Bundle();
        b.putString("id",recipeAdded.getId());
        getSupportLoaderManager().initLoader(0,b,this);
        Log.d(TAG, "addShortcut: "+ingredients);
        Bundle bundleToPass = new Bundle();
        bundleToPass.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) ingredients);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferences_string), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", recipeAdded.getId());
        editor.apply();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pinWidgetToHomeScreen(this,bundleToPass);
        }*/
    }

    private void pinWidgetToHomeScreen(Context context,Bundle b) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppWidgetManager appWidgetManager = context.getSystemService(AppWidgetManager.class);
            if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                ComponentName widgetProvider = new ComponentName(context, IngredientsWidgetProvider.class);
                Log.d(TAG, "pinWidgetToHomeScreen: "+b);
                appWidgetManager.requestPinAppWidget(widgetProvider, b, null);
            } else {
                Toast.makeText(context, "device not support pinning", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<List<Ingredient>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Ingredient>>(this) {
            @Override
            protected void onStartLoading() {
                if (args==null){
                    return;
                }
                forceLoad();
            }

            @Override
            public List<Ingredient> loadInBackground() {
                List<Ingredient> ingredients = new ArrayList<>();
                try {
                    ingredients = NetworkUtils.getRecipeIngredients(args.getString("id"));
                    Log.d(TAG, "loadInBackground: "+ingredients);
                } catch (Exception e) {
                    Log.e(TAG, "loadInBackground:error in " + e.getMessage());
                }
                return ingredients;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Ingredient>> loader, List<Ingredient> data) {
        Log.d(TAG, "onLoadFinished: "+data);
        ingredients = data;
        Log.d(TAG, "onLoadFinished: "+ingredients);
    }

    @Override
    public void onLoaderReset(Loader<List<Ingredient>> loader) {

    }

}
