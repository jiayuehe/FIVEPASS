package com.example.angelahe.stepcounter.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.ExerciseType;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

import java.util.Calendar;
import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {

    private static final int REQUEST_ALARM = 1;
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
    private List<String> types;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (ContextCompat.checkSelfPermission(AddExerciseActivity.this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
        //      != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        //}

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

        // read exercise from database
        types = MainActivity.exerciseTypeDatabase.ExerciseTypeDao().getAllExercise();
        arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_textview, types);
        spinner.setAdapter(arrayAdapter);

        // Set default date in date picker
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String temp = month + 1 + "/" + day + "/" + year;
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
                date_string = month + 1 + "/" + dayOfMonth + "/" + year;
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
                exercisename = ((EditText) findViewById(R.id.exercise)).getText().toString();
                Log.d("exercise name is ", exercisename);
                calorie = Integer.parseInt(((EditText) findViewById(R.id.calorie)).getText().toString());
                Log.d("calorie is ", String.valueOf(calorie));
                break;
        }

        // update exercise list
        if (imageId == R.drawable.other_exercise){
            Log.d("exercise name ", exercisename);
            ExerciseType newType = new ExerciseType(exercisename, calorie);
            MainActivity.exerciseTypeDatabase.ExerciseTypeDao().addExercise(newType);
            types.add(exercisename);
            arrayAdapter.notifyDataSetChanged();
        }


        User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);

        Exercise exercise = new Exercise(username, exercisename, startTime, endTime, imageId, currentDate, calorie);
        MainActivity.exerciseRoomDatabase.ExerciseDao().addExercise(exercise);

        if(ContextCompat.checkSelfPermission(AddExerciseActivity.this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(AddExerciseActivity.this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            SetNotification setter = new SetNotification();
            setter.setWithTime(AddExerciseActivity.this, day, month, year, starthour, startmin, 0, exercisename, username);
        } else {
            Toast.makeText(AddExerciseActivity.this, "Permission Denied, not able to set the alarm", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(AddExerciseActivity.this, DailyPlan.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


    public void requestPermission() {
        new AlertDialog.Builder(this).setTitle("Permission needed")
                .setMessage("This permission is needed to set alarm")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(AddExerciseActivity.this,
                                new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, REQUEST_ALARM);
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
        if(requestCode == REQUEST_ALARM){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
