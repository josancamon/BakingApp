package com.example.santiago.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.santiago.bakingapp.Fragments.StepDetailFragment;
import com.example.santiago.bakingapp.Model.Step;

import java.util.List;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.ChangeStepClickListener{
    private static final String TAG = StepDetailActivity.class.getSimpleName();
    private String shortDescription;
    private String description;
    private String videoUrl;
    private int mStepId;
    private List<Step> stepsList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intent = getIntent();
        shortDescription = intent.getStringExtra("shortDescription");
        description = intent.getStringExtra("description");
        videoUrl = intent.getStringExtra("videoUrl");
        mStepId = intent.getIntExtra("id",0);
        stepsList=intent.getParcelableArrayListExtra("steps_extra");

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepList(stepsList);
        stepDetailFragment.setStepData(mStepId);
        getSupportFragmentManager().beginTransaction().add(R.id.rec,stepDetailFragment).commit();

        //Log.d(TAG, "step detail activity :  "+shortDescription+"- "+description+" - "+videoUrl);
    }

    @Override
    public void changeStepClickListener(int newInt) {
        int step = newInt+1;
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepList(stepsList);
        stepDetailFragment.setStepData(step);
        getSupportFragmentManager().beginTransaction().replace(R.id.rec,stepDetailFragment).commit();


    }
}
