package com.example.santiago.bakingapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.santiago.bakingapp.Model.Step;
import com.example.santiago.bakingapp.R;
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

/**
 * Created by Santiago on 29/01/2018.
 */

public class StepDetailFragment extends Fragment {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView stepDescription;
    private String stepDescriptionString;
    private String videoUrl;
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    public StepDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_step_detail,container,false);
        simpleExoPlayerView = rootView.findViewById(R.id.simple_exo_player);
        Uri uri =Uri.parse(videoUrl).buildUpon().build();
        initializePlayer(uri);
        //needed to get the video url and the description by an intent
        stepDescription = rootView.findViewById(R.id.step_description);
        stepDescription.setText(stepDescriptionString);
        return rootView;
    }
    private void initializePlayer(Uri uri){
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
        simpleExoPlayer.stop();
    }
    public void setStepData(String stepDescriptionReceived,String videoUrlReceived){
        if (videoUrlReceived!= null&& !videoUrlReceived.equals("")){
            videoUrl = videoUrlReceived;
        }else{
            videoUrl = "";
        }
        stepDescriptionString = stepDescriptionReceived;
    }
}
