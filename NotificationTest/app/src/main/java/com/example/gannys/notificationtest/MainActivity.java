package com.example.gannys.notificationtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
//        EditText editText = findViewById(R.id.editText);
//        TimePicker timePicker = findViewById(R.id.timePicker);

//        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
//        intent.putExtra("note", "this is the extra");
//
//        // pending intent
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        // hardcode time
//        int hour = 0;
//        int min = 13;
//        int sec = 0;
//        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, hour);
//        startTime.set(Calendar.MINUTE, min);
//        startTime.set(Calendar.SECOND, 0);
//
//        long milliStartTime = startTime.getTimeInMillis();
//
//        alarm.set(AlarmManager.RTC_WAKEUP, milliStartTime, alarmIntent);
//
//        Toast.makeText(this, "This is alarm", Toast.LENGTH_SHORT).show();
//
//        System.out.println("fsdfasdfasdfasdfasdfasf");

        SetNotification setter = new SetNotification();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        setter.setWithTime(MainActivity.this, hour, min +1, sec );

        System.out.println(hour + " : " + min + " : " + sec);

//        System.out.println(Build.VERSION.SDK_INT + "===========" + Build.VERSION.RELEASE);


    }

}
