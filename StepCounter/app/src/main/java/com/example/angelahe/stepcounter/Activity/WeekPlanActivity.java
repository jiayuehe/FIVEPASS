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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class WeekPlanActivity extends AppCompatActivity {
        private FloatingActionButton addExerciseButton;
        private String username;
        private List<Exercise> allExercise;
        Button checkbutton;
        Button weeklyPlan;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_week_plan);
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            allExercise = MainActivity.exerciseRoomDatabase.ExerciseDao().returnAllExercise(username);

            ListView dayOne = (ListView) findViewById(R.id.dayOne);
            ListView dayTwo = (ListView) findViewById(R.id.dayTwo);
            ListView dayThree = (ListView) findViewById(R.id.dayThree);
            final WeekPlanActivity.CustomerAdaptar customerAdaptar = new CustomerAdaptar();
            final WeekPlanActivity.DayTwoAdapter dayTwoAdap = new DayTwoAdapter();
            final WeekPlanActivity.DayThreeAdapter dayThreeAdapter = new DayThreeAdapter();

            if(dayOne == null){
                Log.e("cannot find", "not found");
            } else {
                dayOne.setAdapter(customerAdaptar);
            }
            dayTwo.setAdapter(dayTwoAdap);
            dayThree.setAdapter(dayThreeAdapter);
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
//
//                GregorianCalendar gc = new GregorianCalendar();
//                gc.add(Calendar.DATE, 1);
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                String currentDate = sdf.format(gc);

               // if (allExercise.get(position).getDate().equals(currentDate)) {
                    imageView.setImageResource(allExercise.get(position).getImage());
                    textView.setText(allExercise.get(position).getExerciseName());
                    textViewDes.setText(allExercise.get(position).getDate() + "\n" + allExercise.get(position).getStartTime() + " - " + allExercise.get(position).getEndTime());

                //}
                return convertView;
            }
        }

    class DayTwoAdapter extends BaseAdapter {

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
//
//            GregorianCalendar gc = new GregorianCalendar();
//            gc.add(Calendar.DATE, 2);
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//            String currentDate = sdf.format(gc);

           // if (allExercise.get(position).getDate().equals(currentDate)) {
                imageView.setImageResource(allExercise.get(position).getImage());
                textView.setText(allExercise.get(position).getExerciseName());
                textViewDes.setText(allExercise.get(position).getDate() + "\n" + allExercise.get(position).getStartTime() + " - " + allExercise.get(position).getEndTime());

          // }
            return convertView;
        }
    }

    class DayThreeAdapter extends BaseAdapter {

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

//            GregorianCalendar gc = new GregorianCalendar();
//            gc.add(Calendar.DATE, 3);
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//            String currentDate = sdf.format(gc);

            //if (allExercise.get(position).getDate().equals(currentDate)) {
                imageView.setImageResource(allExercise.get(position).getImage());
                textView.setText(allExercise.get(position).getExerciseName());
                textViewDes.setText(allExercise.get(position).getDate() + "\n" + allExercise.get(position).getStartTime() + " - " + allExercise.get(position).getEndTime());

           // }
            return convertView;
        }
    }

}

