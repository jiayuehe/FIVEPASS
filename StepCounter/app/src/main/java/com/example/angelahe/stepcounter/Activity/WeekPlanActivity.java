package com.example.angelahe.stepcounter.Activity;

import android.annotation.SuppressLint;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.GregorianCalendar;
import java.util.List;

public class WeekPlanActivity extends AppCompatActivity {
        private FloatingActionButton addExerciseButton;
        private String username;
        private List<Exercise> allExercise;
        Button checkbutton;
        Button weeklyPlan;

        @SuppressLint("SimpleDateFormat")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_week_plan);
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            allExercise = MainActivity.exerciseRoomDatabase.ExerciseDao().returnAllExercise(username);

            Date currDate = Calendar.getInstance().getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            currDate = calendar.getTime();

            calendar.add(Calendar.DATE, 1);
            Date tomorrow = calendar.getTime();

            calendar.add(Calendar.DATE, 1);
            Date day_two = calendar.getTime();

            calendar.add(Calendar.DATE, 1);
            Date day_three = calendar.getTime();

            List<Exercise> day1 = new ArrayList<>();
            List<Exercise> day2 = new ArrayList<>();
            List<Exercise> day3 = new ArrayList<>();

            Log.e("currDate", new SimpleDateFormat("MM/dd/yyyy").format(currDate));
            Log.e("tomorrow date", String.valueOf(tomorrow));
            Log.e("day_two date", String.valueOf(day_two));
            Log.e("day_three date", String.valueOf(day_three));

            for(Exercise item : allExercise) {
                try {
                    Date d = new SimpleDateFormat("MM/dd/yyyy").parse(item.getDate());
                    Log.e("item date", String.valueOf(d));

                    if(d.compareTo(tomorrow) == 0)
                        day1.add(item);
                    else if(d.compareTo(day_two) == 0)
                        day2.add(item);
                    else if(d.compareTo(day_three) == 0)
                        day3.add(item);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Log.e("Num items", String.valueOf(allExercise.size()));

            ListView dayOne = (ListView) findViewById(R.id.dayOne);
            ListView dayTwo = (ListView) findViewById(R.id.dayTwo);
            ListView dayThree = (ListView) findViewById(R.id.dayThree);
            final WeekPlanActivity.CustomerAdaptar customerAdaptar = new CustomerAdaptar(day1);
            final WeekPlanActivity.DayTwoAdapter dayTwoAdap = new DayTwoAdapter(day2);
            final WeekPlanActivity.DayThreeAdapter dayThreeAdapter = new DayThreeAdapter(day3);

            dayOne.setAdapter(customerAdaptar);
            Utility.setListViewHeightBasedOnChildren(dayOne);
            dayTwo.setAdapter(dayTwoAdap);
            Utility.setListViewHeightBasedOnChildren(dayTwo);
            dayThree.setAdapter(dayThreeAdapter);
            Utility.setListViewHeightBasedOnChildren(dayThree);
        }

        class CustomerAdaptar extends BaseAdapter {

            private List<Exercise> dayOneExercise;

            CustomerAdaptar(List<Exercise> dayOneExercise) {this.dayOneExercise = dayOneExercise;}

            @Override
            public int getCount() {
                if(dayOneExercise != null)
                    return dayOneExercise.size();
                return 0;
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
                Button videoButton = (Button) convertView.findViewById(R.id.video_button);
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WeekPlanActivity.this, YouTubeVideo.class);
                        Exercise currExercise = allExercise.get(position);
                        String exerciseName = currExercise.getExerciseName();
                        intent.putExtra("exerciseName", exerciseName);
                        startActivity(intent);
                    }
                });
                checkedButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Exercise currentExercise = allExercise.get(position);
                        java.util.Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = sdf.format(currentTime);
                        Log.d("currentDate is ", currentDate);
                        Log.d("exercise date is ", currentExercise.getDate());

                        if(!currentExercise.getDate().equals(currentDate)){
                            Toast.makeText(WeekPlanActivity.this,"You cannot check this", Toast.LENGTH_SHORT).show();
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
                                    startActivity(new Intent(WeekPlanActivity.this,Congratulations.class));
                                }
                                Log.e("currentDays", String.valueOf(currentDays));
                                MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                                Toast.makeText(WeekPlanActivity.this,"Congratulations!", Toast.LENGTH_SHORT).show();
                            }

                            notifyDataSetChanged();
                        }
                    }
                });

                imageView.setImageResource(dayOneExercise.get(position).getImage());
                textView.setText(dayOneExercise.get(position).getExerciseName());
                textViewDes.setText(dayOneExercise.get(position).getDate() + "\n" + dayOneExercise.get(position).getStartTime() + " - " + dayOneExercise.get(position).getEndTime());


                return convertView;
            }
        }

        class DayTwoAdapter extends BaseAdapter {

            private List<Exercise> dayTwoExercise;

            DayTwoAdapter(List<Exercise> dayTwoExercise) {this.dayTwoExercise = dayTwoExercise;}

            @Override
            public int getCount() {
                if(dayTwoExercise != null)
                    return dayTwoExercise.size();
                return 0;
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
                Button videoButton = (Button) convertView.findViewById(R.id.video_button);
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WeekPlanActivity.this, YouTubeVideo.class);
                        Exercise currExercise = allExercise.get(position);
                        String exerciseName = currExercise.getExerciseName();
                        intent.putExtra("exerciseName", exerciseName);
                        startActivity(intent);
                    }
                });
                checkedButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Exercise currentExercise = allExercise.get(position);
                        java.util.Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = sdf.format(currentTime);
                        Log.d("currentDate is ", currentDate);
                        Log.d("exercise date is ", currentExercise.getDate());

                        if(!currentExercise.getDate().equals(currentDate)){
                            Toast.makeText(WeekPlanActivity.this,"You cannot check this", Toast.LENGTH_SHORT).show();
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
                                    startActivity(new Intent(WeekPlanActivity.this,Congratulations.class));
                                }
                                Log.e("currentDays", String.valueOf(currentDays));
                                MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                                Toast.makeText(WeekPlanActivity.this,"Congratulations!", Toast.LENGTH_SHORT).show();
                            }

                            notifyDataSetChanged();
                        }
                    }
                });

                imageView.setImageResource(dayTwoExercise.get(position).getImage());
                textView.setText(dayTwoExercise.get(position).getExerciseName());
                textViewDes.setText(dayTwoExercise.get(position).getDate() + "\n" + dayTwoExercise.get(position).getStartTime() + " - " + dayTwoExercise.get(position).getEndTime());


                return convertView;
            }
        }

        class DayThreeAdapter extends BaseAdapter {

            private List<Exercise> dayThreeExercise;

            DayThreeAdapter(List<Exercise> dayThreeExercise) {this.dayThreeExercise = dayThreeExercise;}

            @Override
            public int getCount() {
                if(dayThreeExercise != null)
                    return dayThreeExercise.size();
                return 0;
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
                Button videoButton = (Button) convertView.findViewById(R.id.video_button);
                videoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WeekPlanActivity.this, YouTubeVideo.class);
                        Exercise currExercise = allExercise.get(position);
                        String exerciseName = currExercise.getExerciseName();
                        intent.putExtra("exerciseName", exerciseName);
                        startActivity(intent);
                    }
                });
                checkedButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Exercise currentExercise = allExercise.get(position);
                        java.util.Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        String currentDate = sdf.format(currentTime);
                        Log.d("currentDate is ", currentDate);
                        Log.d("exercise date is ", currentExercise.getDate());

                        if(!currentExercise.getDate().equals(currentDate)){
                            Toast.makeText(WeekPlanActivity.this,"You cannot check this", Toast.LENGTH_SHORT).show();
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
                                    startActivity(new Intent(WeekPlanActivity.this,Congratulations.class));
                                }
                                Log.e("currentDays", String.valueOf(currentDays));
                                MainActivity.myAppDatabase.UserDao().updateUser(currentUser);
                                Toast.makeText(WeekPlanActivity.this,"Congratulations!", Toast.LENGTH_SHORT).show();
                            }

                            notifyDataSetChanged();
                        }
                    }
                });

                imageView.setImageResource(dayThreeExercise.get(position).getImage());
                textView.setText(dayThreeExercise.get(position).getExerciseName());
                textViewDes.setText(dayThreeExercise.get(position).getDate() + "\n" + dayThreeExercise.get(position).getStartTime() + " - " + dayThreeExercise.get(position).getEndTime());

                return convertView;
            }
        }


}

