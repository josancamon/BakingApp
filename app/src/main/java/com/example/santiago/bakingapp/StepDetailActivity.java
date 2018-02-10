package com.example.santiago.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.StepDetailFragment;
import com.example.santiago.bakingapp.Model.Step;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.ChangeStepClickListener{
    private static final String TAG = StepDetailActivity.class.getSimpleName();
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String mStepId;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intent = getIntent();
        shortDescription = intent.getStringExtra("shortDescription");
        description = intent.getStringExtra("description");
        videoUrl = intent.getStringExtra("videoUrl");
        mStepId = intent.getStringExtra("id");

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepData(description,videoUrl);
        getSupportFragmentManager().beginTransaction().add(R.id.rec,stepDetailFragment).commit();

        Log.d(TAG, "step detail activity :  "+shortDescription+"- "+description+" - "+videoUrl);
    }

    @Override
    public void changeStepClickListener(Step step) {
        String description = step.getDescription();
        String videoUrl = step.getVideoUrl();
        Toast.makeText(getApplicationContext(),description,Toast.LENGTH_SHORT).show();
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepData(description,videoUrl);
        getSupportFragmentManager().beginTransaction().add(R.id.rec,stepDetailFragment).commit();

    }
}
