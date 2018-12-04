package com.example.angelahe.stepcounter.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.angelahe.stepcounter.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.api.services.youtube.model.*;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    static class ViewHolder {
        YouTubePlayerView youTubePlayerView;
        TextView textView;
        ImageView imageView;
        Button playButton;
    }

    class VideoAdapter extends BaseAdapter {

        private List<SearchResult> items;
        private int[] indicators;

        public VideoAdapter(List<SearchResult> items) {
            this.items = items;
            if(items != null)
                indicators = new int[items.size()];
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
        public View getView(final int position, View view, ViewGroup viewGroup) {

            final ViewHolder holder;

//            if(view == null) {
//                Log.e("getView", "view is null, creating new view.");
//                view = getLayoutInflater().inflate(R.layout.video_layout, null);
//                holder = new ViewHolder();
//                holder.youTubePlayerView = (YouTubePlayerView) view.findViewById(R.id.single_video);
//                holder.textView = (TextView) view.findViewById(R.id.videoTitle);
//                holder.imageView = view.findViewById(R.id.video_image);
//                holder.playButton = view.findViewById(R.id.play_video);
//                view.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder) view.getTag();
//            }
            view = getLayoutInflater().inflate(R.layout.video_layout, null);

            SearchResult currRes = items.get(position);
            final String videoID = currRes.getId().getVideoId();
            String videoTitle = currRes.getSnippet().getTitle();
            String picURL = currRes.getSnippet().getThumbnails().getMedium().getUrl();

            final YouTubePlayerView youTubePlayerView = (YouTubePlayerView) view.findViewById(R.id.single_video);

            TextView textView = (TextView) view.findViewById(R.id.videoTitle);
            textView.setText(videoTitle);

            if(indicators[position] == 0) {
                final ImageView imageView = view.findViewById(R.id.video_image);
                YouTubeLoadImage youTubeLoadImage = new YouTubeLoadImage(imageView);
                youTubeLoadImage.execute(picURL);
            }

            Button playButton = view.findViewById(R.id.play_video);
            playButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//                    imageView.setVisibility(View.GONE);
                    RelativeLayout relativeParent =  (RelativeLayout) view.getParent();
                    RelativeLayout relativeChild = (RelativeLayout) relativeParent.getChildAt(0);
                    if(relativeChild.getChildCount() == 2)
                        relativeChild.removeViewAt(1);

                    youTubePlayerView.initialize("AIzaSyAfrnZpXDkBeGt8_S-yClwaEHcLbHCh-rU", new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            indicators[position] = 1;
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

