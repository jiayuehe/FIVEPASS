package com.example.angelahe.stepcounter.Activity;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.angelahe.stepcounter.Database.ExerciseRoomDatabase;
import com.example.angelahe.stepcounter.Database.ExerciseType;
import com.example.angelahe.stepcounter.Database.ExerciseTypeDatabase;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;
import com.example.angelahe.stepcounter.R;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity{
    // This is for database
    public static UserRoomDatabase myAppDatabase;
    public static ExerciseRoomDatabase exerciseRoomDatabase;
    public static ExerciseTypeDatabase exerciseTypeDatabase;
    // This is for the splash screen
    private static int SPLAH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), UserRoomDatabase.class, "userdb").allowMainThreadQueries().build();
        exerciseRoomDatabase = Room.databaseBuilder(getApplicationContext(), ExerciseRoomDatabase.class, "exercisedb").allowMainThreadQueries().build();
        exerciseTypeDatabase = Room.databaseBuilder(getApplicationContext(), ExerciseTypeDatabase.class, "typedb").
                addCallback(new RoomDatabase.Callback(){
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db){
                        super.onCreate(db);



                    }
        }).allowMainThreadQueries().build();
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Walking", 250));
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Swimming", 600));
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Running", 600));
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Weight-lifting", 380));
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Bicycling", 650));
        exerciseTypeDatabase.ExerciseTypeDao().addExercise(new ExerciseType("Others", 0));

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
