package com.example.angelahe.stepcounter.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.TimePicker;

import com.example.angelahe.stepcounter.R;

import java.util.Calendar;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Spinner spinner;
    private TimePicker timePicker, timePicker2;
    private int min, hour;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        mTextMessage = (TextView) findViewById(R.id.message);
        spinner = findViewById(R.id.spinner);
        timePicker = findViewById(R.id.simpleTimePicker);
        final Calendar calendar = Calendar.getInstance();
        min = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        timePicker.setHour(hour);
        timePicker.setMinute(min);

        timePicker2 = findViewById(R.id.simpleTimePicker2);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timePicker2.setHour(hourOfDay);
                timePicker2.setMinute(minute);
            }
        });

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Others")){

                    ConstraintLayout layout = findViewById(R.id.container);

                    EditText exercise = new EditText(AddExerciseActivity.this);
                    EditText calorie = new EditText(AddExerciseActivity.this);
                    layout.addView(exercise);
                    layout.addView(calorie);
                    exercise.setHint("Running");
                    calorie.setHint("Calorie consumption per hour");




                }
            }
        });




    }

    public void onSendMessage(View view) {
        Intent intent = new Intent(this, DailyPlan.class);
        startActivity(intent);
    }

}