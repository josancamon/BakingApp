package com.example.santiago.bakingapp.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.santiago.bakingapp.Adapters.RecyclerStepsAdapter;
import com.example.santiago.bakingapp.Model.Step;
import com.example.santiago.bakingapp.R;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 28/01/2018.
 */

public class StepsListFragment extends Fragment implements RecyclerStepsAdapter.StepOnclickListener, LoaderManager.LoaderCallbacks<List<Step>> {

    public StepsListFragment() {
    }

    private static final String TAG = StepsListFragment.class.getSimpleName();
    private String recipeId;
    private RecyclerView recyclerView;
    private RecyclerStepsAdapter recyclerStepsAdapter;
    private static final int LOADER_ID = 1;
    private OnStepClickListener onStepClickListener;

    public interface OnStepClickListener {
        void onStepClickListener(Step step);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onStepClickListener = (OnStepClickListener) context;
        } catch (Exception e) {
            Log.d(TAG, "onAttach Error: " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = LayoutInflater.from(getActivity() ).inflate(R.layout.fragment_steps_list, container, false);
        recyclerView = rootView.findViewById(R.id.steps_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerStepsAdapter = new RecyclerStepsAdapter(getActivity(), this);
        recyclerView.setAdapter(recyclerStepsAdapter);
        loadData();
        return rootView;
    }

    private void loadData() {
        LoaderManager loaderManager = getLoaderManager();
        Loader<List<Step>> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onClick(Step stepClicked) {
        onStepClickListener.onStepClickListener(stepClicked);
        //Toast.makeText(getActivity(),stepClicked.getShortDescription(),Toast.LENGTH_SHORT).show();
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public Loader<List<Step>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Step>>(getContext()) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public List<Step> loadInBackground() {
                List<Step> steps = new ArrayList<>();
                try {
                    String json = NetworkUtils.makeHttpRequest(NetworkUtils.createUrl());
                    steps = NetworkUtils.getRecipeSteps(getContext(), json, recipeId);
                } catch (Exception e) {
                    Log.d(TAG, "loadInBackground: " + e);
                }
                return steps;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Step>> loader, List<Step> data) {
        recyclerStepsAdapter.setData(data);
    }


    @Override
    public void onLoaderReset(Loader<List<Step>> loader) {

    }
}
