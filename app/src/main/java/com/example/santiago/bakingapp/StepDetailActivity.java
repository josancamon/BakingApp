package com.example.santiago.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.santiago.bakingapp.Fragments.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {
    private static final String TAG = StepDetailActivity.class.getSimpleName();
    private String shortDescription;
    private String description;
    private String videoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intent = getIntent();
        shortDescription = intent.getStringExtra("shortDescription");
        description = intent.getStringExtra("description");
        videoUrl = intent.getStringExtra("videoUrl");

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepData(description,videoUrl);
        getFragmentManager().beginTransaction().add(R.id.rec,stepDetailFragment).commit();

        Log.d(TAG, "step detail activity :  "+shortDescription+"- "+description+" - "+videoUrl);

    }
}
