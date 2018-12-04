package com.example.angelahe.stepcounter.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.angelahe.stepcounter.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class YouTubeLoadImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public YouTubeLoadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(strings[0]);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap res) {
        if(imageView != null)
            imageView.setImageBitmap(res);
    }
}
