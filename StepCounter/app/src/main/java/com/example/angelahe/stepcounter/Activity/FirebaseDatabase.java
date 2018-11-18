package com.example.angelahe.stepcounter.Activity;


import com.firebase.client.Firebase;

public class FirebaseDatabase extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
