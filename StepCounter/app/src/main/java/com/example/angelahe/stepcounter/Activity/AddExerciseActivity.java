package com.example.angelahe.stepcounter.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.TimePicker;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

import java.util.Calendar;

public class AddExerciseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Spinner spinner;
    private TimePicker timePicker, timePicker2;
    private int min, hour, month, year, day;
    private String username;
    private String date_string;
    private int startmin, endmin, starthour, endhour;
    private String exercisename = "";
    private TextView tvDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

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
        min = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Set default time in time picker
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

        // Set default date in date picker
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String temp = month+1 + "/" + day + "/" + year;
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setText(temp);

        // EditText invisible unless users choose a customized activity
        final TextInputLayout exercise = findViewById(R.id.textInputLayout);
        exercise.setVisibility(View.INVISIBLE);
        final TextInputLayout calorie = findViewById(R.id.textInputLayout2);
        calorie.setVisibility(View.INVISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Others")) {
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

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar tempCalendar = Calendar.getInstance();
                year = tempCalendar.get(Calendar.YEAR);
                month = tempCalendar.get(Calendar.MONTH);
                day = tempCalendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddExerciseActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener, year, month, day);
                DatePicker datePicker = dialog.getDatePicker();
                datePicker.setMinDate(tempCalendar.getTimeInMillis());
                tempCalendar.add(Calendar.DATE, 7);
                datePicker.setMaxDate(tempCalendar.getTimeInMillis());
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int _year, int _month, int dayOfMonth) {
                month = _month;
                year = _year;
                day = dayOfMonth;
                date_string = month+1 + "/" + dayOfMonth + "/" + year;
                tvDate.setText(date_string);

            }
        };

    }

    public void onSendMessage(View view) {
        exercisename = spinner.getSelectedItem().toString();

        timePicker = findViewById(R.id.simpleTimePicker);
        starthour = timePicker.getHour();
        startmin = timePicker.getMinute();
        endhour = timePicker2.getHour();
        endmin = timePicker2.getMinute();
        String currentDate = tvDate.getText().toString();
        int calorie = 0;
        String startTime = String.valueOf(starthour) + ":" + String.valueOf(startmin);
        String endTime = String.valueOf(endhour) + ":" + String.valueOf(endmin);
        Log.d("UsernameinExercise", "a" + username);
        Log.d("wtf", "wtf");
        Log.d("Starttime is ", "b" + String.valueOf(starthour) + " : " + String.valueOf(startmin));
        Log.d("exercise name is ", "c" + exercisename);
        Log.d("setting date is ", "d" + currentDate);
        int imageId = 0;
        switch (exercisename) {
            // in an hour
            case "Walking":
                imageId = R.drawable.walking;
                calorie = 250;
                break;
            case "Swimming":
                imageId = R.drawable.swimming;
                calorie = 600;
                break;
            case "Running":
                imageId = R.drawable.running;
                calorie = 600;
                break;
            case "Weight-lifting":
                imageId = R.drawable.weighlift;
                calorie = 380;
                break;
            case "Bicycling":
                imageId = R.drawable.bicycle;
                calorie = 650;
                break;
            default:
                imageId = R.drawable.other_exercise;
                exercisename = ((EditText)findViewById(R.id.exercise)).getText().toString();
                Log.d("exercise name is ",exercisename);
                calorie = Integer.parseInt(((EditText)findViewById(R.id.calorie)).getText().toString());
                Log.d("calorie is ", String.valueOf(calorie));
                break;
        }

        User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);

//        Exercise exercise = new Exercise(username, exercisename, startTime, endTime, imageId, date_string);


        Exercise exercise = new Exercise(username, exercisename, startTime, endTime, imageId, currentDate, calorie);

        MainActivity.exerciseRoomDatabase.ExerciseDao().addExercise(exercise);
        Intent intent = new Intent(this, DailyPlan.class);
        intent.putExtra("username", username);
        startActivity(intent);

        //set notification
        SetNotification setter = new SetNotification();
<<<<<<< HEAD
        setter.setWithTime(this, starthour, startmin, 0,  exercisename);
=======
        setter.setWithTime(this, day, month, year, starthour, startmin, 0, exercisename);
>>>>>>> 55cc14d621fd1010827a5b0518d12df641ad7de8
    }

}
