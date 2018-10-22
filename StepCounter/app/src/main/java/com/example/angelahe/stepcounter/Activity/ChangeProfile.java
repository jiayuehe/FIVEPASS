package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.*;

import com.example.angelahe.stepcounter.R;
import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.User;

public class ChangeProfile extends AppCompatActivity {

    User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        currUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);

        EditText usernameText = (EditText) findViewById(R.id.newUsername);
        usernameText.setEnabled(false);
        usernameText.setText(currUser.getmUserName());

        EditText passwordText = (EditText) findViewById(R.id.newPassword);
        passwordText.setText(currUser.getmPassword());

        final Spinner spinnerDailyGoal = (Spinner) findViewById(R.id.spinnerDailyGoal);
        final Spinner spinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        final Spinner spinnerWeight = (Spinner) findViewById(R.id.spinnerWeight);
        final Spinner spinnerHeight = (Spinner) findViewById(R.id.spinnerHeight) ;
        final Spinner spinnerGender = (Spinner) findViewById(R.id.spinnerGender) ;

        List goal = new ArrayList<Integer>();
        goal.add("Daily Goal (Cal)");
        for (int i = 100; i <=1500; i += 20) {
            goal.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterGoal = new ArrayAdapter<String>(ChangeProfile.this, R.layout.spinner_item_textview, goal) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerDailyGoal.setAdapter(adapterGoal);

        List age = new ArrayList<Integer>();
        age.add("Age");
        for (int i = 1; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(ChangeProfile.this, R.layout.spinner_item_textview, age) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerAge.setAdapter(adapterAge);

        List weight = new ArrayList<Integer>();
        weight.add("Weight");
        for (int i = 30; i <= 110; i++) {
            weight.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterWeight = new ArrayAdapter<String>(ChangeProfile.this, R.layout.spinner_item_textview, weight) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerWeight.setAdapter(adapterWeight);

        List height = new ArrayList<Integer>();
        height.add("Height");
        for (int i = 100; i <= 220; i++) {
            height.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterHeight = new ArrayAdapter<String>(ChangeProfile.this, R.layout.spinner_item_textview, height) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerHeight.setAdapter(adapterHeight);

        List<String> gender = new ArrayList<String>() {
            {
                add("Gender");
                add("male");
                add("female");
            }
        };
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(ChangeProfile.this, R.layout.spinner_item_textview, gender) {
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                return true;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };
        spinnerGender.setAdapter(adapterGender);

        Button resetProfileButton = (Button) findViewById(R.id.resetProfileButton);
        resetProfileButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        EditText userNameText = findViewById(R.id.newUsername);
//                        String newUserName = userNameText.getText().toString();
//                        currUser.setUserName(newUserName);


                        EditText password = findViewById(R.id.newPassword);
                        String newPassword = password.getText().toString();
                        currUser.setPassword(newPassword);

                        Spinner dailyGoal = findViewById(R.id.spinnerDailyGoal);
                        if(dailyGoal.getSelectedItemPosition() > 0) {
                            int newDailyGoal = Integer.parseInt(dailyGoal.getSelectedItem().toString());
                            currUser.setDailyGoal(newDailyGoal);
                        }

                        Spinner gender = findViewById(R.id.spinnerGender);
                        if(gender.getSelectedItemPosition() > 0) {
                            char newGender = gender.getSelectedItem().toString().charAt(0);
                            currUser.setGender(newGender);
                        }

                        Spinner age = findViewById(R.id.spinnerAge);
                        if(age.getSelectedItemPosition() > 0 ) {
                            int newAge = Integer.parseInt(age.getSelectedItem().toString());
                            currUser.setAge(newAge);
                        }

                        Spinner height = findViewById(R.id.spinnerHeight);
                        if(height.getSelectedItemPosition() > 0) {
                            int newHeight = Integer.parseInt(height.getSelectedItem().toString());
                            currUser.setHeight(newHeight);
                        }

                        Spinner weight = findViewById(R.id.spinnerWeight);
                        if(weight.getSelectedItemPosition() > 0) {
                            int newWeight = Integer.parseInt(weight.getSelectedItem().toString());
                            currUser.setWeight(newWeight);
                        }

                        MainActivity.myAppDatabase.UserDao().updateUser(currUser);

                        System.out.println("=============================");
                        System.out.println(currUser);
                        Intent intent = new Intent(ChangeProfile.this, ViewProfile.class);
                        intent.putExtra("username", currUser.getmUserName());
                        startActivity(intent);

                    }
                }
        );

    }


}
