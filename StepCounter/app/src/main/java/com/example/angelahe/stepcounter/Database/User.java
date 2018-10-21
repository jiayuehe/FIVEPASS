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

    @ColumnInfo(name = "badge")
    public int badge;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "gender")
    public char gender;

    @ColumnInfo(name = "weight")
    public float weight;

    @ColumnInfo(name = "height")
    public float height;

    @ColumnInfo(name = "daily goal")
    public int dailyGoal;

    @ColumnInfo(name = "calorie")
    public int calorie;

    public int getAge() {
        return age;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public int getDailyGoal() {
        return dailyGoal;
    }

    public int getCalorie() {
        return calorie;
    }

    public int getBadge() {
        return badge;
    }

    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.badge = 0;
        this.calorie = 0;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setDailyGoal(int dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public User(String mUserName, String mPassword, int age, float weight, float height, int dailyGoal, char gender) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.badge = 0;
        this.calorie = 0;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.dailyGoal = dailyGoal;
        this.gender = gender;
    }

    public void addOne(){
        ++ this.badge;
    }

    public void setZero(){
        this.badge = 0;
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

    public void addExercise(int calorie){
        this.calorie += calorie;
    }

}