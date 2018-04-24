package com.example.santiago.bakingapp.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.R;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Santiago on 21/02/2018.
 */

public class IngredientsWidgetService extends RemoteViewsService implements
        LoaderManager.LoaderCallbacks<List<Ingredient>>{

    String id;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        List<Ingredient> ingredientsReceived =  intent.getParcelableArrayListExtra("list_ingredients");
        Log.d(TAG, "onGetViewFactory: "+"here is correccct");

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferences_string),Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id","0");
        Log.d(TAG, "getViewAt: " +id);
        return new ListRemoteViewsFactory(this.getApplicationContext(),null);
    }

    @Override
    public Loader<List<Ingredient>> onCreateLoader(final int id, final Bundle args) {
        return new AsyncTaskLoader<List<Ingredient>>(this) {
            @Override
            public List<Ingredient> loadInBackground() {
                List<Ingredient> ingredients = new ArrayList<>();
                try {
                    ingredients =NetworkUtils.getRecipeIngredients(args.getString("id"));
                }catch (Exception e){
                    Log.e(TAG, "loadInBackground:error "+e.getMessage());
                }
                return ingredients;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Ingredient>> loader, List<Ingredient> data) {
        Log.d(TAG, "onLoadFinished: "+data.size());
    }

    @Override
    public void onLoaderReset(Loader<List<Ingredient>> loader) {

    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        private static final String TAG = "ListRemoteViewsFactory";
        List<Ingredient> mIngredients;
        Context mContext;
        public ListRemoteViewsFactory(Context context, List<Ingredient> ingredients){
            Log.d(TAG, "ListRemoteViewsFactory: correct");
            mIngredients = ingredients;
            mContext = context;
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mIngredients==null) return 0;
            return mIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            //SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_file_key),Context.MODE_PRIVATE);
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_app_widget);
            //String ss = sharedPreferences.getString("string","not found");
            //Log.d(TAG, "getViewAt: " +ss);
            views.setTextViewText(R.id.ingredients_widget_container,"its me");
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
