package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

public class SignUp extends AppCompatActivity {
    private Spinner genderSpinner, ageSpinner, weightSpinner, heightSpinner;
    private Button signUpBtn;
    EditText Password;
    EditText Username;
    EditText DailyGoal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        signUpBtn = findViewById(R.id.realSignUpButton);


        signUpBtn = findViewById(R.id.realSignUpButton);
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        ageSpinner = (Spinner) findViewById((R.id.age));
        weightSpinner = (Spinner) findViewById(R.id.weight);
        heightSpinner = (Spinner) findViewById(R.id.body_height);
        DailyGoal = (EditText) findViewById(R.id.dailyGoal);

        signUpBtn.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        final String username = Username.getText().toString();
                        final String password = Password.getText().toString();
                        final char gender = genderSpinner.getSelectedItem().toString().equals("Male") ? 'M' : 'F';
                        final int age = Integer.parseInt(ageSpinner.getSelectedItem().toString());
                        final float weight = Float.parseFloat(weightSpinner.getSelectedItem().toString());
                        final float height = Float.parseFloat(heightSpinner.getSelectedItem().toString());
                        if(DailyGoal.getText().toString().trim().equals("")){
                            Toast.makeText(SignUp.this,"Need daily goal", Toast.LENGTH_SHORT).show();
                        } else {
                            final int dailyGoal = Integer.parseInt(DailyGoal.getText().toString());
                            User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                            if(currentUser == null){
                                User user = new User(username,password,age,weight, height, dailyGoal,gender);
                                MainActivity.myAppDatabase.UserDao().addUser(user);
                                Intent myIntent = new Intent(SignUp.this, HomeActivity.class);
                                myIntent.putExtra("username", username);
                                startActivity(myIntent);
                            } else{
                                Toast.makeText(SignUp.this,"Username taken", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
