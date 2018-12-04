package com.example.angelahe.stepcounter.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

public class YouTubeSearch extends AsyncTask<String, Void, List<SearchResult>> {

    //private View view;
    private Context context;
    //private VideoAdapter videoAdapter;
    private static YouTube youtube;
    private static final String YOUTUBE_API_KEY = "AIzaSyAfrnZpXDkBeGt8_S-yClwaEHcLbHCh-rU";
    private List<SearchResult> searchResultList = null;

    public YouTubeSearch(Context context) {
        //this.view = view;
        this.context = context;
    }

    @Override
    protected List<SearchResult> doInBackground(String... params){

        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("USCFit").build();

            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(YOUTUBE_API_KEY);
            search.setQ(params[0] + " tutorial");
            Log.e("TAG doInBackground", params[0]);
            search.setType("video");
            search.setMaxResults(new Long(10));

            SearchListResponse searchResponse = search.execute();
            searchResultList = searchResponse.getItems();

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return searchResultList;
    }

}


