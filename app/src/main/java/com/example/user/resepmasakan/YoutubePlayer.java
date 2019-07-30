package com.example.user.resepmasakan;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayer extends YouTubeBaseActivity{
    public static final String API_KEY = "AIzaSyCzmM_Fh3CxZMdXyFkauBrPz0gdjYGsPuA";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public String VIDEO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        // Initializing YouTube player view
        //Initializing and adding YouTubePlayerFragment
       VIDEO_ID = getIntent().getStringExtra("videoid");
        FragmentManager fm = getFragmentManager();
        String tag = YouTubePlayerFragment.class.getSimpleName();
        YouTubePlayerFragment playerFragment = (YouTubePlayerFragment) fm.findFragmentByTag(tag);
        if (playerFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            playerFragment = YouTubePlayerFragment.newInstance();
            ft.add(android.R.id.content, playerFragment, tag);
            ft.commit();
    }
        playerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(VIDEO_ID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YoutubePlayer.this, "Error while initializing YouTubePlayer.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
