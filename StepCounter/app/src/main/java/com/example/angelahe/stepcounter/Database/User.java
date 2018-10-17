package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    public String mUserName;

    @ColumnInfo(name = "password")
    public String mPassword;

    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    @NonNull
    public void setmPassword(String password){
        this.mPassword = password;
    }

    @NonNull
    public boolean checkPassword(@NonNull String password){
        if(password.equals(mPassword)){
            return true;
        } else{
            return false;
        }
    }

}