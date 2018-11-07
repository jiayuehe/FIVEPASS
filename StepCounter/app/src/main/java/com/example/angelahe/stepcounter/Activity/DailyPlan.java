package com.example.angelahe.stepcounter.Activity;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DailyPlan extends AppCompatActivity {

    private FloatingActionButton addExerciseButton;
    private String username;
    private List<Exercise> allExercise;
    Button checkbutton;
    Button weeklyPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_plan);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        allExercise = MainActivity.exerciseRoomDatabase.ExerciseDao().returnAllExercise(username);

        Log.d("What about here", "Is it here?");
        if (allExercise == null) {
            Log.d("CurrentExerciseisnull?", "No Exercise List");
        } else {
            ListView listView = (ListView) findViewById(R.id.listView);

            final CustomerAdaptar customerAdaptar = new CustomerAdaptar();
            listView.setAdapter(customerAdaptar);
        }

        addExerciseButton = findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseOptions();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavigationBar2);
        bottomNavigationView.setSelectedItemId(R.id.AddMorePlan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        Intent intent = new Intent(DailyPlan.this, HomeActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        break;
                    case R.id.AddMorePlan:
                        break;
                    case R.id.Settings:
                        Intent intent1 = new Intent(DailyPlan.this, ViewProfile.class);
                        intent1.putExtra("username", username);
                        startActivity(intent1);
                        break;
                    case R.id.Map:
                        Intent intent2 = new Intent(DailyPlan.this, MapsActivity.class);
                        intent2.putExtra("username", username);
                        startActivity(intent2);
                        break;
                }

                return false;
            }
        });

        weeklyPlan = findViewById(R.id.title_weekly_plan);
        weeklyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWeeklyPlan();
            }
        });
    }

    private void openWeeklyPlan() {
        Intent intent = new Intent(this, WeekPlanActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    class CustomerAdaptar extends BaseAdapter {

        @Override
        public int getCount() {
            return allExercise.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customer_layout, null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.walking);
            TextView textView = (TextView) convertView.findViewById(R.id.name);
            TextView textViewDes = (TextView) convertView.findViewById(R.id.startingDate);
            Button checkedButton = (Button) convertView.findViewById(R.id.check_button);
            checkedButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Exercise currentExercise = allExercise.get(position);
                    java.util.Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String currentDate = sdf.format(currentTime);
                    Log.d("currentDate is ", currentDate);
                    Log.d("exercise date is ", currentExercise.getDate());
                    if(!currentExercise.getDate().equals(currentDate)){
                        Toast.makeText(DailyPlan.this,"You cannot check this", Toast.LENGTH_SHORT).show();
                    } else{
                        int calorieConsumption = currentExercise.calorie;
                        User currentUser =  MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        int currentCal = currentUser.getCalorieConsumptioon();
                        currentUser.setCalorie(currentUser.getCalorie() + calorieConsumption);
                        Log.e("Adding calorie: ",""+calorieConsumption);

                        // update user in database
                        MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                        MainActivity.exerciseRoomDatabase.ExerciseDao().deleteExercise(currentExercise);
                        allExercise.remove(position);

                        // check if the user completes the daily goal for the first time
                        if(currentCal < currentUser.getDailyGoal() && currentUser.getCalorieConsumptioon() > currentUser.getDailyGoal()){
                            currentUser.addOne();
                            int currentDays = currentUser.getBadge();
                            if(currentDays != 0 && currentDays % 7 == 0){
                                startActivity(new Intent(DailyPlan.this,Congratulations.class));
                            }
                            Log.e("currentDays", String.valueOf(currentDays));
                            MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                            Toast.makeText(DailyPlan.this,"Congratulations!", Toast.LENGTH_SHORT).show();
                        }

                        notifyDataSetChanged();
                    }
                }
            });

            imageView.setImageResource(allExercise.get(position).getImage());
            textView.setText(allExercise.get(position).getExerciseName());
            textViewDes.setText(allExercise.get(position).getDate() + "\n" + allExercise.get(position).getStartTime() + " - " + allExercise.get(position).getEndTime());
            return convertView;
        }
    }

    public void openExerciseOptions() {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
