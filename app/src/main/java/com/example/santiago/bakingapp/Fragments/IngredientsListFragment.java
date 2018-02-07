package com.example.santiago.bakingapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.santiago.bakingapp.Adapters.RecyclerIngredientsAdapter;
import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 28/01/2018.
 */

public class IngredientsListFragment extends Fragment {
    private String mRecipeId;
    public IngredientsListFragment(){}


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients_list,container,false);
        RecyclerView recyclerView = rootView.findViewById(R.id.ingrediets_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerIngredientsAdapter recyclerRecipesAdapter = new RecyclerIngredientsAdapter(getActivity());
        recyclerView.setAdapter(recyclerRecipesAdapter);
        recyclerRecipesAdapter.setData(insertFakeData());
        return rootView;
    }
    public void setRecipeId(String id){
        mRecipeId = id;
    }
    public List<Ingredient> insertFakeData() {
        List<Ingredient> fakeRecipesDataList = new ArrayList<>();
        Ingredient recipe = new Ingredient( "1", "cups", "milk");
        fakeRecipesDataList.add(recipe);
        fakeRecipesDataList.add(recipe);
        fakeRecipesDataList.add(recipe);
        fakeRecipesDataList.add(recipe);
        fakeRecipesDataList.add(recipe);
        fakeRecipesDataList.add(recipe);
        return fakeRecipesDataList;
    }
}
