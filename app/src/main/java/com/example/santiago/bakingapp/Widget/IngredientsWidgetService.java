package com.example.santiago.bakingapp.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.santiago.bakingapp.Fragments.IngredientsListFragment;
import com.example.santiago.bakingapp.Model.Ingredient;

import java.util.List;

/**
 * Created by Santiago on 21/02/2018.
 */

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String ACTION_WIDGET_RECIPES = "com.example.android.mygarden.action.action_widget_recipes";
    List<Ingredient> mIngredients;
    public IngredientsWidgetService(List<Ingredient> ingredients){
        mIngredients = ingredients;
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        List<Ingredient> ingredientsReceived =  intent.getParcelableArrayListExtra("list_ingredients");
        return new ListRemoteViewsFactory(getApplicationContext(),ingredientsReceived);
    }
    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
        List<Ingredient> mIngredients;
        Context mContext;
        public ListRemoteViewsFactory(Context context, List<Ingredient> ingredients){
            mContext = context;
            mIngredients = ingredients;
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
            return null;
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
