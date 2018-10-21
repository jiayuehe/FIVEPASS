package com.example.angelahe.stepcounter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.angelahe.stepcounter.R;

public class SignUp extends AppCompatActivity {
    private Spinner genderSpinner, ageSpinner;
    private Button signUpBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        signUpBtn = findViewById(R.id.signUpButton);
        signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name =
                    }
                }
        );
    }

    public void addGenderItems(){
        genderSpinner = (Spinner) findViewById(R.id.gender);

    }

}
