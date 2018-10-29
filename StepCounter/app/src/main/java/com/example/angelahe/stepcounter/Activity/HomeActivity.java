package com.example.angelahe.stepcounter.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

import org.w3c.dom.Text;

import java.util.Calendar;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    // This is for ring progress bar
    RingProgressBar ringProgressBar;

    int progress = 0; // need to get calorie from database

    Handler myHandlr = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0) {
                if (progress < 100) {
                    progress++;
                    ringProgressBar.setProgress(progress);
                }
            }
        }
    };


    // This is for the step counter
    TextView tv_steps;
    TextView distance;

    SensorManager sensorManager;

    boolean running = false;

    String username;

    Button checked;

    Button incomplete;

    User currentUser;

    int calorie;

    int dailyGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for ring progress bar
        setContentView(R.layout.activity_home);
        ringProgressBar = findViewById(R.id.progress_bar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.e("Current User name", "get name " + username);
        currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);

        // display calorie
        calorie = currentUser.getCalorie();

        dailyGoal = currentUser.getDailyGoal();
        progress = calorie * 100 / dailyGoal;
        Log.e("calorie: ", ""+calorie);
        Log.e("calorie: ", ""+progress);
        TextView mTextView = (TextView) findViewById(R.id.totalCalorieValue);
        mTextView.setText(String.valueOf(currentUser.getCalorie() + "/" + dailyGoal));
        ringProgressBar.setProgress(progress);
        Log.e("Current Calorie", "We have " + calorie);
        new Thread(new Runnable() {
            @Override
            public void run() {
                myHandlr.sendEmptyMessage(0);
            }
        }).start();

        checked = (Button) findViewById(R.id.checked);
        checked.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //User c urrentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        int currentDays = currentUser.getBadge();
                        if (currentDays != 0 && currentDays % 7 == 0) {
                            startActivity(new Intent(HomeActivity.this, Congratulations.class));
                        }
                        Log.e("currentDays", String.valueOf(currentDays));
                        currentUser.addOne();
                        Log.e("currentDays", String.valueOf(currentDays));
                        TextView mTextView = (TextView) findViewById(R.id.totalCalorieValue);
                        mTextView.setText(String.valueOf(currentUser.getCalorie()));
                        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                        Toast.makeText(HomeActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
                    }
                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar offCal = Calendar.getInstance();
                offCal.set(Calendar.HOUR_OF_DAY, 23);
                offCal.set(Calendar.MINUTE, 59);
                offCal.set(Calendar.SECOND, 00);
                Intent offIntent = new Intent(HomeActivity.this,CheckReceiver.class);
                offIntent.putExtra("username", username);
                offIntent.setAction(CheckReceiver.ALUM_SCREEN_OFF);
                PendingIntent offPending = PendingIntent.getBroadcast(HomeActivity.this, 1,
                        offIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setRepeating(AlarmManager.RTC, offCal.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, offPending);
                Log.e("checking badge event", "set");
            }
        }).start();

        // UserCheck
        incomplete = (Button) findViewById(R.id.incomplete);
        incomplete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        int currentDays = currentUser.getBadge();
                        Log.e("currentDays", String.valueOf(currentDays));
                        currentUser.setZero();
                        Log.e("currentDays", String.valueOf(currentDays));
                        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                        Toast.makeText(HomeActivity.this, "Sorry! But Keep Going Tomorrow", Toast.LENGTH_SHORT).show();
                    }
                });

        // for home and window bar
        tv_steps = (TextView) findViewById(R.id.tv_steps);
        distance = (TextView) findViewById(R.id.distancLength);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        break;
                    case R.id.AddMorePlan:
                        Intent intent = new Intent(HomeActivity.this, DailyPlan.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        break;
                    case R.id.Settings:
                        Intent intent1 = new Intent(HomeActivity.this, ViewProfile.class);
                        intent1.putExtra("username", username);
                        startActivity(intent1);
                        break;
                }

                return false;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            Log.e("Please work", "please work =here");
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        // if you unregister the hardware will stop detecting steps
        // sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            Log.e("It should work here", "It should work here");
            Log.e("Current Step is ", "current step" + event.values[0]);
            tv_steps.setText(String.valueOf(event.values[0]));
            distance.setText(String.valueOf(event.values[0]*0.5));
            currentUser.setDailySteps(event.values[0]);
            MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
