package com.example.angelahe.stepcounter.Activity;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.angelahe.stepcounter.Database.ExerciseRoomDatabase;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;
import com.example.angelahe.stepcounter.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    // This is for database
    public static UserRoomDatabase myAppDatabase;
    public static ExerciseRoomDatabase exerciseRoomDatabase;
    // This is for the splash screen
    private static int SPLAH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), UserRoomDatabase.class, "userdb").allowMainThreadQueries().build();
        exerciseRoomDatabase = Room.databaseBuilder(getApplicationContext(), ExerciseRoomDatabase.class, "exercisedb").allowMainThreadQueries().build();
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, UserRegisterActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLAH_TIME_OUT);
    }

}
