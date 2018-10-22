package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

public class SignUp extends AppCompatActivity {
    private Spinner genderSpinner, heightSpinner, wightSpinnner, ageSpinner;
    private Button signUpBtn;
    private EditText usernameText;
    private EditText passwordText;
    private EditText goalText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usernameText = (EditText) findViewById(R.id.username);
                        passwordText = (EditText) findViewById(R.id.password);
                        genderSpinner = (Spinner) findViewById(R.id.gender);
                        heightSpinner = (Spinner) findViewById(R.id.body_height);
                        wightSpinnner = (Spinner) findViewById(R.id.weight);
                        ageSpinner = (Spinner) findViewById(R.id.age);
                        goalText = (EditText) findViewById(R.id.dailyGoal);

                        String username = usernameText.getText().toString();
                        String password = passwordText.getText().toString();
                        char gender = genderSpinner.getSelectedItem().toString().toLowerCase().charAt(0);
                        int age = Integer.valueOf(ageSpinner.getSelectedItem().toString());
                        int weight = Integer.valueOf(ageSpinner.getSelectedItem().toString());
                        int height = Integer.valueOf(ageSpinner.getSelectedItem().toString());
                        int dailyGaol = Integer.valueOf(goalText.getText().toString());


                        User newUser = new User(username, password, age, weight, height, dailyGaol, gender);
                        MainActivity.myAppDatabase.UserDao().addUser(newUser);
                        Intent myIntent = new Intent(SignUp.this, HomeActivity.class);
                        myIntent.putExtra("username", username);
                        startActivity(myIntent);
                    }
                }
        );
    }

//    public void addGenderItems(){
//        genderSpinner = (Spinner) findViewById(R.id.gender);
//
//    }

}
