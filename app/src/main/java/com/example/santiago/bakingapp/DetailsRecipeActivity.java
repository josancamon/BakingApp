package com.example.santiago.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.santiago.bakingapp.Fragments.IngredientsStepsViewPagerFragment;
import com.example.santiago.bakingapp.Fragments.StepsListFragment;
import com.example.santiago.bakingapp.Model.Ingredient;
import com.example.santiago.bakingapp.Model.Step;

import java.util.ArrayList;
import java.util.List;

public class DetailsRecipeActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener {
    private static final String TAG = "DetailsRecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        IngredientsStepsViewPagerFragment fragment = new IngredientsStepsViewPagerFragment();
        fragment.setRecipeId(id);
        getSupportFragmentManager().beginTransaction().add(R.id.recipess,fragment).commit();
    }

    @Override
    public void onStepClickListener(Step step , List<Step> steps) {
        Intent intent = new Intent(this,StepDetailActivity.class);
        intent.putExtra("shortDescription",step.getShortDescription());
        intent.putExtra("description",step.getDescription());
        intent.putExtra("videoUrl",step.getVideoUrl());
        intent.putExtra("id",step.getStepId());
        intent.putParcelableArrayListExtra("steps_extra", (ArrayList<? extends Parcelable>) steps);
        startActivity(intent);
    }
}
