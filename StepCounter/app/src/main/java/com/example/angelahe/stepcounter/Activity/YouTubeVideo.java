package com.example.angelahe.stepcounter.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.angelahe.stepcounter.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.api.services.youtube.model.*;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class YouTubeVideo extends YouTubeBaseActivity {

    private Context context;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video);

        context = getApplicationContext();

        Log.e("YouTube", "onCreate: staring.");

        Intent intent = getIntent();
        String exerciseName = intent.getStringExtra("exerciseName");

        YouTubeSearch youTubeSearch = new YouTubeSearch(context);
//        AsyncTask<String, Void, List<SearchResult>> execute =
        try {
            List<SearchResult> searchResults = youTubeSearch.execute(exerciseName).get();
            ListView listView = findViewById(R.id.videos);
            videoAdapter = new VideoAdapter(searchResults);
            listView.setAdapter(videoAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    class VideoAdapter extends BaseAdapter {

        private List<SearchResult> items;

        public VideoAdapter(List<SearchResult> items) {
            this.items = items;
        }


        @Override
        public int getCount() {
            if(items != null)
                return items.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            if(view == null) {
                Log.e("getView", "view is null, creating new view.");
                view = getLayoutInflater().inflate(R.layout.video_layout, null);
            }

            SearchResult currRes = items.get(position);
            final String videoID = currRes.getId().getVideoId();
            String videoTitle = currRes.getSnippet().getTitle();

            final YouTubePlayerView youTubePlayerView = (YouTubePlayerView) view.findViewById(R.id.single_video);

//            TextView textView = youTubePlayerView.findViewById(R.id.videoTitle);
//            textView.setText(videoTitle);

            Button playButton = view.findViewById(R.id.play_video);
            playButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    youTubePlayerView.initialize("AIzaSyAfrnZpXDkBeGt8_S-yClwaEHcLbHCh-rU", new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(videoID);
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                            Log.e("YouTube", "onInitializationFailure: failed.");
                        }
                    });
                }
            });

            return view;
        }
    }

}
