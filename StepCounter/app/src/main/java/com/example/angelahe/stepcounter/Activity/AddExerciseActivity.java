package com.example.angelahe.stepcounter.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.TimePicker;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.R;

import java.util.Calendar;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Spinner spinner;
    private TimePicker timePicker, timePicker2;
    private int min, hour;
    private String username;
    private int startmin, endmin, starthour, endhour;
    private String exercisename = "";

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
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        mTextMessage = (TextView) findViewById(R.id.message);
        spinner = findViewById(R.id.spinner);
        timePicker = findViewById(R.id.simpleTimePicker);
        final Calendar calendar = Calendar.getInstance();
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

        final TextInputLayout exercise = findViewById(R.id.textInputLayout);
        exercise.setVisibility(View.INVISIBLE);
        final TextInputLayout calorie = findViewById(R.id.textInputLayout2);
        calorie.setVisibility(View.INVISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Others")) {

//                    ConstraintLayout layout = findViewById(R.id.container);
//
//                    EditText exercise = new EditText(AddExerciseActivity.this);
//                    EditText calorie = new EditText(AddExerciseActivity.this);
//                    layout.addView(exercise);
//                    layout.addView(calorie);
//                    exercise.setHint("Running");
//                    calorie.setHint("Calorie consumption per hour");
//                    exercise.setId(R.id.exercise_input);
////                    exercise.setTop(100);
////                    exercise.setLeft(30);
////                    calorie.setTop(100);
////                    calorie.setLeft(60);
//                    calorie.setId(R.id.calorie_input);
//
//                    ConstraintSet constraintSet = new ConstraintSet();
//                    constraintSet.clone(layout);
//                    constraintSet.connect(layout.getId(), ConstraintSet.BOTTOM, exercise.getId(), ConstraintSet.TOP, 20);
//                    constraintSet.connect(layout.getId(), ConstraintSet.BOTTOM, calorie.getId(), constraintSet.TOP, 40);
//                    constraintSet.applyTo(layout);

                    exercise.setVisibility(View.VISIBLE);
                    calorie.setVisibility(View.VISIBLE);

                } else {
                    exercise.setVisibility(View.INVISIBLE);
                    calorie.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void onSendMessage(View view) {
        exercisename = spinner.getSelectedItem().toString();
        timePicker = findViewById(R.id.simpleTimePicker);
        starthour = timePicker.getHour();
        startmin = timePicker.getMinute();
        String startTime = String.valueOf(starthour) + "," + String.valueOf(startmin);
        Log.d("UsernameinExercise", "a" + username);
        Log.d("wtf", "wtf");
        Log.d("Starttime is ", "b" + String.valueOf(starthour) + " : " + String.valueOf(startmin));
        Log.d("exercise name is ", "c" + exercisename);
        int imageId = 0;
        switch (exercisename) {
            case "Walking":
                imageId = R.drawable.walking;
                break;
            case "Swimming":
                imageId = R.drawable.swimming;
                break;
            case "Running":
                imageId = R.drawable.running;
                break;
            case "Weight-lifting":
                imageId = R.drawable.weighlift;
                break;
            case "Bicycling":
                imageId = R.drawable.bicycle;
                break;
            default:
                imageId = R.drawable.running;
                break;
        }

        Exercise exercise = new Exercise(username, exercisename, startTime, startTime, imageId);
        MainActivity.exerciseRoomDatabase.ExerciseDao().addExercise(exercise);
        Intent intent = new Intent(this, DailyPlan.class);
        intent.putExtra("username", username);
        startActivity(intent);

        //set notification
        SetNotification setter = new SetNotification();
        setter.setWithTime(this, starthour, startmin, 0, exercisename);
    }

}
