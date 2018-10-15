package com.example.angelahe.stepcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserRegister extends AppCompatActivity {
    // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }


    public void countStep(View view) {
        Intent myIntent = new Intent(this, HomeActivity.class);
        //myIntent.putExtra("key", ); //Optional parameters
        this.startActivity(myIntent);
    }

}
