package com.example.angelahe.stepcounter.Activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    // This is for ring progress bar
    RingProgressBar ringProgressBar;

    int progress = 0; // need to get calorie from database

    Handler myHandlr = new Handler(){
        @Override
        public void handleMessage(Message message){
            if(message.what == 0){
                if(progress < 100){
                    progress ++;
                    ringProgressBar.setProgress(progress);
                }
            }
        }
    };


    // This is for the step counter
    TextView tv_steps;

    SensorManager sensorManager;

    boolean running = false;

    String username;

    Button checked;

    Button incomplete;

    User currentUser;

    int calorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for ring progress bar
        setContentView(R.layout.activity_home);
        ringProgressBar = findViewById(R.id.progress_bar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);

        // display calorie
        calorie = currentUser.getCalorie();
        // assume 1000 is the daily goal
        progress = calorie/10;
        TextView mTextView = (TextView) findViewById(R.id.totalCalorieValue);
        mTextView.setText(String.valueOf(currentUser.getCalorie()));
        ringProgressBar.setProgress(progress);
        Log.e("Current Calorie" , "We have " + calorie);
        new Thread(new Runnable() {
            @Override
            public void run() {
                myHandlr.sendEmptyMessage(0);
            }
        }).start();

        checked = (Button)findViewById(R.id.checked);
        checked.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        int currentDays = currentUser.getBadge();
                        if(currentDays != 0 && currentDays % 7 == 0){
                            startActivity(new Intent(HomeActivity.this,Congratulations.class));
                        }
                        Log.e("currentDays", String.valueOf(currentDays));
                        currentUser.addOne();
                        Log.e("currentDays", String.valueOf(currentDays));
                        TextView mTextView = (TextView) findViewById(R.id.totalCalorieValue);
                        mTextView.setText(String.valueOf(currentUser.getCalorie()));
                        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                        Toast.makeText(HomeActivity.this,"Congratulations!", Toast.LENGTH_SHORT).show();
                    }
                });

        // UserCheck
        incomplete = (Button)findViewById(R.id.incomplete);
        incomplete.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        int currentDays = currentUser.getBadge();
                        Log.e("currentDays", String.valueOf(currentDays));
                        currentUser.setZero();
                        Log.e("currentDays", String.valueOf(currentDays));
                        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                        Toast.makeText(HomeActivity.this,"Sorry! But Keep Going Tomorrow", Toast.LENGTH_SHORT).show();
                    }
                });

        // for home and window bar
        tv_steps = (Button) findViewById(R.id.incomplete);

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
            tv_steps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
