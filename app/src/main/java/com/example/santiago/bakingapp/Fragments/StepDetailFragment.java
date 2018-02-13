package com.example.santiago.bakingapp.Fragments;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santiago.bakingapp.Model.Step;
import com.example.santiago.bakingapp.R;
import com.example.santiago.bakingapp.Utilities.NetworkUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 29/01/2018.
 */

public class StepDetailFragment extends Fragment {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView stepDescription;
    private String stepDescriptionString;
    private String videoUrl;
    private ImageView nextImageView;
    private ImageView previousImageView;
    private ChangeStepClickListener changeStepClickListener;
    private int mStepIndex;
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private List<Step> stepsList;

    public StepDetailFragment() {
    }

    public interface ChangeStepClickListener {
        void changeStepClickListener(int newId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changeStepClickListener = (ChangeStepClickListener) context;
        } catch (Exception e) {
            Log.d(TAG, "onAttach: " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_step_detail, container, false);
        nextImageView = rootView.findViewById(R.id.next_video);
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStepIndex + 1 == stepsList.size()) {
                    Toast.makeText(container.getContext(), "Recipe Completed", Toast.LENGTH_SHORT).show();
                } else {
                    changeStepClickListener.changeStepClickListener(mStepIndex + 1);
                }
            }
        });
        previousImageView = rootView.findViewById(R.id.previous_video);
        previousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStepIndex == 0) {
                    Toast.makeText(container.getContext(), "This is the first Step", Toast.LENGTH_SHORT).show();
                } else {
                    changeStepClickListener.changeStepClickListener(mStepIndex - 1);
                }
            }
        });
        simpleExoPlayerView = rootView.findViewById(R.id.simple_exo_player);
        if (!videoUrl.equals("")) {
            Uri uri = Uri.parse(videoUrl).buildUpon().build();
            initializePlayer(uri);
        } else {
        }
        //needed to get the video url and the description by an intent
        stepDescription = rootView.findViewById(R.id.step_description);
        stepDescription.setText(stepDescriptionString);
        return rootView;
    }
    //3105645037

    private void initializePlayer(Uri uri) {
        if (simpleExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!videoUrl.equals("")) {
            simpleExoPlayer.stop();
        }
    }

    public void setStepList(List<Step> stepss) {
        stepsList = stepss;
    }

    public void setStepData(int stepId) {
        mStepIndex = stepId - 1;
        Step actualStep = stepsList.get(mStepIndex);
        if (actualStep.getVideoUrl() != null && !actualStep.getVideoUrl().equals("")) {
            videoUrl = actualStep.getVideoUrl();
        } else {
            videoUrl = "";
        }
        stepDescriptionString = actualStep.getDescription();
    }
}
