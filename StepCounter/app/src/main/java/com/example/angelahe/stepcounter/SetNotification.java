package com.example.angelahe.stepcounter;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class SetNotification {
    public void setWithTime(Context context, int hour, int min, int sec){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("note", "this is the extra");

        // pending intent
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // hardcode time
//        hour = 16;
//        min = 8;
        //sec = 0;
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, min);
        startTime.set(Calendar.SECOND, sec);

        long milliStartTime = startTime.getTimeInMillis();

        alarm.set(AlarmManager.RTC_WAKEUP, milliStartTime, alarmIntent);

        Toast.makeText(context, "This is alarm", Toast.LENGTH_SHORT).show();

        System.out.println("Time: " + hour + " : " + min);
    }
}
