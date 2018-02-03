package com.example.santiago.bakingapp.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santiago.bakingapp.Model.Recipe;
import com.example.santiago.bakingapp.R;
import com.example.santiago.bakingapp.Adapters.RecyclerRecipesAdapter;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 28/01/2018.
 */

public class RecipesListFragment extends Fragment implements RecyclerRecipesAdapter.RecipesOnClickListener, LoaderManager.LoaderCallbacks<List<Recipe>> {
    private static final String TAG = RecipesListFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerRecipesAdapter recyclerRecipesAdapter;
    OnRecipeClickListener onRecipeClickListener;
    private static final int LOADER_ID = 1;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipeClicked);
    }

    public RecipesListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onRecipeClickListener = (OnRecipeClickListener) context;
        } catch (Exception e) {
            Log.d(TAG, "onAttach error  : " + e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerRecipesAdapter = new RecyclerRecipesAdapter(container.getContext(), this);
        recyclerView.setAdapter(recyclerRecipesAdapter);
        //recyclerRecipesAdapter.setData(insertFakeData());
        loadData();
        return rootView;
    }
    private void loadData(){
        getLoaderManager().initLoader(LOADER_ID,null,this);
        LoaderManager loaderManager = getLoaderManager();
        Loader<List<Recipe>> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onClick(Recipe recipeClicked) {
        onRecipeClickListener.onRecipeClick(recipeClicked);
    }
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Recipe>>(getContext()) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public List<Recipe> loadInBackground() {
                List<Recipe> recipes = new ArrayList<>();
                try {
                    String json = NetworkUtils.makeHttpRequest(NetworkUtils.createUrl());
                    recipes = NetworkUtils.getJsonRecipes(getContext(),json);
                } catch (Exception e) {
                    Log.e(TAG, "error inload in background: "+ e.getMessage());
                }
                return recipes;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        recyclerRecipesAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }



}
