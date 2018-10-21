package com.example.angelahe.stepcounter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.example.angelahe.stepcounter.R;

public class SignUp extends AppCompatActivity {
    private Spinner genderSpinner, ageSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
    }

    public void addGenderItems(){
        genderSpinner = (Spinner) findViewById(R.id.gender);

    }
}
