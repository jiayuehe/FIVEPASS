package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.widget.Toast;
>>>>>>> 55cc14d621fd1010827a5b0518d12df641ad7de8

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

public class SignUp extends AppCompatActivity {
<<<<<<< HEAD
    private Spinner genderSpinner, heightSpinner, wightSpinnner, ageSpinner;
    private Button signUpBtn;
    private EditText usernameText;
    private EditText passwordText;
    private EditText goalText;
=======
    private Spinner genderSpinner, ageSpinner, weightSpinner, heightSpinner;
    private Button signUpBtn;
    EditText Password;
    EditText Username;
    EditText DailyGoal;

>>>>>>> 55cc14d621fd1010827a5b0518d12df641ad7de8

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
<<<<<<< HEAD
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
=======
>>>>>>> 55cc14d621fd1010827a5b0518d12df641ad7de8

        signUpBtn = findViewById(R.id.realSignUpButton);


        signUpBtn = findViewById(R.id.realSignUpButton);
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        ageSpinner = (Spinner) findViewById((R.id.age));
        weightSpinner = (Spinner) findViewById(R.id.body_height);
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
                });
    }

}
