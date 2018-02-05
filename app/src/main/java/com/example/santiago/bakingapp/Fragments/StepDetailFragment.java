package com.example.santiago.bakingapp.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class StepDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Step> {
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView stepDescription;
    private String stepDescriptionString;
    private String videoUrl;
    private ImageView nextImageView;

    private static final String TAG = StepDetailFragment.class.getSimpleName();
    public StepDetailFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_step_detail,container,false);
        nextImageView = rootView.findViewById(R.id.next_video);
        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepDetailFragment detailFragment = new StepDetailFragment();
                //detailFragment.setStepData();
            }
        });
        simpleExoPlayerView = rootView.findViewById(R.id.simple_exo_player);
        if (!videoUrl.equals("")){
            Uri uri =Uri.parse(videoUrl).buildUpon().build();
            initializePlayer(uri);
        }else{
            Bitmap noVideoImage = BitmapFactory.decodeResource(getResources(), R.drawable.card_shadow);
            //simpleExoPlayerView.setDefaultArtwork(noVideoImage);
        }
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

    @Override
    public Loader<Step> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Step>(getActivity()) {
            @Override
            public Step loadInBackground() {
                Step step;
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Step> loader, Step data) {

    }

    @Override
    public void onLoaderReset(Loader<Step> loader) {

    }
}
