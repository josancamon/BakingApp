package com.example.santiago.bakingapp.Fragments;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.R;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;
import com.example.santiago.bakingapp.Widget.IngredientsWidgetProvider;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Santiago on 7/02/2018.
 */

public class IngredientsStepsViewPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Ingredient>> {
    private static final String TAG = IngredientsStepsViewPagerFragment.class.getSimpleName();
    private ViewPager viewPager;
    private String mRecipeId;
    private ProgressBar progressBar;
    private FloatingActionButton fabWidget;
    private List<Ingredient> ingredientsToWidget;
    private String recipeName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_and_steps_view_pager, container, false);
        progressBar = rootView.findViewById(R.id.progress_bar);
        android.support.v7.widget.Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        progressBar.setVisibility(View.GONE);
        fabWidget = rootView.findViewById(R.id.fab_widget);
        fabWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWidgetIngredients();
            }
        });
        viewPager = rootView.findViewById(R.id.ingredients_steps_view_pager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getFragmentManager());
        adapterViewPager.setRecipeId(mRecipeId);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setAdapter(adapterViewPager);

        return rootView;
    }
    private void loadWidgetIngredients(){
        getLoaderManager().initLoader(0,null,this);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private String mRecipeId;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
                    ingredientsListFragment.setRecipeId(mRecipeId);
                    return ingredientsListFragment;
                case 1: // Fragment # 0 - This will show FirstFragment
                    StepsListFragment stepsListFragment = new StepsListFragment();
                    if (mRecipeId!=null){
                        stepsListFragment.setRecipeId(Integer.valueOf(mRecipeId));
                    }
                    return stepsListFragment;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ingredients";
                case 1:
                    return "Steps";
                default:
                    return "nn";
            }
        }

        public void setRecipeId(String id) {
            mRecipeId = id;
        }

    }

    public void setRecipeId(String id) {
        mRecipeId = id;
    }
    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }

    @Override
    public Loader<List<Ingredient>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Ingredient>>(getContext()) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public List<Ingredient> loadInBackground() {
                List<Ingredient> ingredients = null;
                try {
                   ingredients= NetworkUtils.getRecipeIngredients(mRecipeId);
                }catch (Exception e){
                    Log.d(TAG, "loadInBackground: ");
                }
                return ingredients;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Ingredient>> loader, List<Ingredient> data) {
        if (data!=null){
            //ingredientsToWidget = data;
            SharedPreferences sharedPreferences = getActivity()
                    .getSharedPreferences("preferences", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String list = gson.toJson(data);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ingredients",list);
            editor.putString("recipe_name",recipeName);
            editor.apply();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
            Bundle bundle = new Bundle();
            int appWidgetId = bundle.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            IngredientsWidgetProvider.updateAppWidget(getActivity(), appWidgetManager, appWidgetId, recipeName,
                    data);
            Toast.makeText(getActivity(), "Added " + " to Widget.   updated", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onLoaderReset(Loader<List<Ingredient>> loader) {

    }
    // ...
}

