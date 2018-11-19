package com.example.angelahe.stepcounter.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {
    String curUrl;
    MusicPlayer(String c){
        curUrl = c;
    }
    public void playMusic() throws IOException {
        Log.d("in function", "playMusic: ");
        curUrl = "http://www.hochmuth.com/mp3/Haydn_Adagio.mp3";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(curUrl);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}
