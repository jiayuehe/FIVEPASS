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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelahe.stepcounter.R;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    // This is for ring progress bar
    RingProgressBar ringProgressBar;

    int progress = 50; // need to get calorie from database

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for ring progress bar
        setContentView(R.layout.activity_home);
        ringProgressBar = findViewById(R.id.progress_bar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                myHandlr.sendEmptyMessage(0);
            }
        }).start();


        // for home and window bar
        tv_steps = (TextView) findViewById(R.id.tv_steps);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Today:
                        break;
                    case R.id.AddMorePlan:
                        Intent intent = new Intent(HomeActivity.this, DailyPlan.class);
                        startActivity(intent);
                        break;
                    case R.id.Settings:
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
