package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import com.example.angelahe.stepcounter.R;
import com.example.angelahe.stepcounter.Database.User;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener {

    User currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        currUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
        System.out.println("get username in viewProfile--------" + username);
        System.out.println("get currGoal in viewProfile--------" + currUser.getDailyGoal());

        try {
            EditText userNameText = (EditText) findViewById(R.id.currUsername);
            userNameText.setEnabled(false);
            userNameText.setText(currUser.getmUserName());

            EditText dailyGoal = (EditText) findViewById(R.id.currGoal);
            dailyGoal.setEnabled(false);
            dailyGoal.setText(Integer.toString(currUser.getDailyGoal()));

            EditText gender = (EditText) findViewById(R.id.currGender);
            gender.setEnabled(false);
            gender.setText(Character.toString(currUser.getGender()));

            EditText age = (EditText) findViewById(R.id.currAge);
            age.setEnabled(false);
            age.setText(Integer.toString(currUser.getAge()));

            EditText height = (EditText) findViewById(R.id.currHeight);
            height.setEnabled(false);
            height.setText(Integer.toString((int)currUser.getHeight()));

            EditText weight = (EditText) findViewById(R.id.currWeight);
            weight.setEnabled(false);
            weight.setText(Integer.toString((int)currUser.getWeight()));


        } catch (Exception e) {
            Log.e("ViewProfile error: ", e.getMessage());
        }

        Button goToProfileSetting = (Button) findViewById(R.id.goToProfileSetting);
        goToProfileSetting.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ViewProfile.this, ChangeProfile.class);
        intent.putExtra("username", currUser.getmUserName());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewProfile.this, HomeActivity.class);
        intent.putExtra("username", currUser.getmUserName());
        startActivity(intent);
    }
}
