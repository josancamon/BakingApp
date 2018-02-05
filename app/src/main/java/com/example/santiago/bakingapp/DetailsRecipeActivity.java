package com.example.santiago.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.IngredientsStepsFragment;
import com.example.santiago.bakingapp.Model.Step;

public class DetailsRecipeActivity extends AppCompatActivity implements IngredientsStepsFragment.OnStepClickListener {
    private static final String TAG = "DetailsRecipeActivity";
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        IngredientsStepsFragment fragment = new IngredientsStepsFragment();
        fragment.setRecipeId(getApplicationContext(),id);
        getSupportFragmentManager().beginTransaction().add(R.id.recipess,fragment).commit();
    }

    @Override
    public void onStepClickListener(Step step) {
        Intent intent = new Intent(this,StepDetailActivity.class);
        intent.putExtra("shortDescription",step.getShortDescription());
        intent.putExtra("description",step.getDescription());
        intent.putExtra("videoUrl",step.getVideoUrl());
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
