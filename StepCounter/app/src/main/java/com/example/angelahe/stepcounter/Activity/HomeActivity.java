package com.example.angelahe.stepcounter.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.GraphView;

public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    // This is for ring progress bar
    RingProgressBar ringProgressBar;
    SensorManager sensorManager;


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

    boolean running = false;

    String username;

    Button checked;

    Button incomplete;

    Button trialRun;

    User currentUser;

    int calorie;

    int dailyGoal;

    // This is for request
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }

        // for ring progress bar
        setContentView(R.layout.activity_home);
        ringProgressBar = findViewById(R.id.progress_bar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.e("Current User name", "get name " + username);
        currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);


        // display nutrition suggestions
        float weight = currentUser.getWeight();

        String workoutGoal = currentUser.getWorkoutGoal();
        int suggestedProtin;
        int suggestedFat;
        int suggestedCarb;

        if(workoutGoal.equals("Losing fat")){
            suggestedProtin = (int) (0.3 * weight);
            suggestedCarb = 152;
            suggestedFat = (int) (suggestedCarb * 0.3);
        }
        else{
            suggestedProtin = (int) (0.35 * weight);
            suggestedCarb = 310;
            suggestedFat = (int) (suggestedCarb * 0.3);
        }

        Log.e("suggestion: ", ""+suggestedCarb);


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

        // UserCheck
        trialRun = (Button) findViewById(R.id.button_Trial);
        trialRun.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, TrialActivity.class);
                        //intent.putExtra("username", username);
                        startActivity(intent);
                    }
                });

        // for home and window bar
        tv_steps = (TextView) findViewById(R.id.tv_steps);
        distance = (TextView) findViewById(R.id.distancLength);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // firebase authentification


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
                    case R.id.Map:
                        Intent intent2 = new Intent(HomeActivity.this, MapsActivity.class);
                        intent2.putExtra("username", username);
                        startActivity(intent2);
                        break;
                }

                return false;
            }
        });

        ArrayList<Integer> history = (ArrayList)currentUser.getHistory();
        Calendar calendar = Calendar.getInstance();
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d1 = calendar.getTime();


        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, history.get(0)),
                new DataPoint(d2, history.get(1)),
                new DataPoint(d3, history.get(2)),
                new DataPoint(d4, currentUser.getCalorie())

        });
        graph.addSeries(series);
        series.setDrawDataPoints(true);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d4.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);



    }



    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            if (countSensor != null) {
                sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            if (running) {
                tv_steps.setText(String.valueOf(event.values[0]));
                distance.setText(String.valueOf(event.values[0] * 0.5));
                currentUser.setDailySteps(event.values[0]);
                MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Request Permission for location
    public void requestPermission() {
        new AlertDialog.Builder(this).setTitle("Permission needed")
                .setMessage("This permission is needed to get the data from the sensor")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(HomeActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        if(requestCode == REQUEST_EXTERNAL_STORAGE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
