package com.betsy.chatsy.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.betsy.chatsy.weatherapp.json.JsonManager;
import com.betsy.chatsy.weatherapp.security.DeveloperKey;
import com.betsy.chatsy.weatherapp.youtube.YouTubeFailureRecoveryActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import org.json.JSONObject;


public class YouTubeSearch extends YouTubeFailureRecoveryActivity {

    YouTubePlayerView youTubeView;
    String videoID;

    private static final String ERROR_MESSAGE = "Something went wrong.Please try again";
    private static final String QUERY_YOUTUBE = "videoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Intent intent = getIntent();
        String youtubeSearch = intent.getStringExtra(getString(R.string.youtubeSearchIntent));

        youTubeView = findViewById(R.id.youtube_view);

        getVideoID(youtubeSearch);

    }

    private void getVideoID(String youtubeSearch) {

        RequestQueue requestQueue = Volley.newRequestQueue(YouTubeSearch.this);


        String url = getString(R.string.youtubeSearch) + youtubeSearch.replaceAll(" ", "+") + getString(R.string.queryYoutube) + DeveloperKey.DEVELOPER_KEY_SEARCH_API;


        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JsonManager jsonManager = new JsonManager();

                        videoID = jsonManager.getValueFromKey(response, QUERY_YOUTUBE);

                        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY_PLAYER_API, YouTubeSearch.this);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(YouTubeSearch.this, ERROR_MESSAGE, Toast.LENGTH_LONG).show();

                    }
                }

        );

        requestQueue.add(objectRequest);

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(videoID);
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


}
