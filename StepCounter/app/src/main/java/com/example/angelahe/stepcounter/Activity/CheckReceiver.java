package com.example.angelahe.stepcounter.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.User;

public class CheckReceiver extends BroadcastReceiver {
    public static final String ALUM_SCREEN_ON = "screenOn";
    public static final String ALUM_SCREEN_OFF = "screenOff";
    private static final String TAG = "CheckReceiver";
    private String username = null;
    private User currentUser;

    @Override
    public void onReceive(Context context, Intent intent){
        // TODO Auto-generated method stub
        Log.e(TAG, "get braodcast action:"+intent.getAction());
        if(intent.getAction().equals(ALUM_SCREEN_OFF)){
            Log.e(TAG, "WE ARE HERE");
            username = intent.getStringExtra("username");
            currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
            // check for badge
            if(currentUser.getCalorieConsumptioon() < currentUser.getDailyGoal()){
                Log.e("msg: "," set badge to zero");
                currentUser.setZero();
                MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
            }
            // set zero
            currentUser.setCalorie(0);
            MainActivity.myAppDatabase.UserDao().updateUser(currentUser);

        }
    }
}