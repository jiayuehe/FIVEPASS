package com.example.angelahe.stepcounter.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.R;

public class UserRegisterActivity extends AppCompatActivity {
    // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    EditText Password;
    EditText Username;
    Button   mButton;
    Button   mButtonTwo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mButton = (Button) findViewById(R.id.buttonsignup);
        mButtonTwo = (Button) findViewById(R.id.buttonlogin);
        Username = (EditText) findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.password);


        // clicking sign up will direct the user to another page
        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent  = new Intent(UserRegisterActivity.this, SignUp.class);
                        startActivity(intent);

                    }
                }
        );

        mButtonTwo.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        final String username = Username.getText().toString();
                        final String password = Password.getText().toString();
                        Log.d("Currentusername","CurrentUsername is " + username);
                        User currentUser = MainActivity.myAppDatabase.UserDao().returnCurrentUser(username);
                        if(currentUser == null || !currentUser.checkPassword(password)){
                            Toast.makeText(UserRegisterActivity.this,"Log in failed", Toast.LENGTH_SHORT).show();
                        } else{
                            Intent myIntent = new Intent(UserRegisterActivity.this, HomeActivity.class);
                            myIntent.putExtra("username", username);
                            startActivity(myIntent);
                        }

                    }
                });


    }

}
