package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    @ColumnInfo(name = "step")
    public float dailySteps;

    @ColumnInfo(name = "calorieConsumption")
    public int calorieConsumptioon;

    @ColumnInfo(name = "calorie_day1")
    public int day1;

    @ColumnInfo(name = "calorie_day2")
    public int day2;

    @ColumnInfo(name = "calorie_day3")
    public int day3;

    @ColumnInfo(name = "workout_goal")
    public String workoutGoal;

    public String getmUserName() {return mUserName;}

    public String getmPassword() {return mPassword;}

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

    public char getGender() {return gender;}

    public int getCalorie() {
        return calorieConsumptioon;
    }

    public int getBadge() {
        return badge;
    }

    public String getWorkoutGoal() { return workoutGoal; }

    public void setPassword(String password) {this.mPassword = password;}
    @Ignore
    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.badge = 0;
        this.calorieConsumptioon = 0;
        this.dailySteps = 0;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setDailySteps(float steps) {
        this.dailySteps = steps;
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

    public void setGender(char gender) {this.gender = gender;}

    public void setCalorie(int calorie) {
        this.calorieConsumptioon = calorie;
    }

    public User(String mUserName, String mPassword, int age, float weight, float height, int dailyGoal, char gender) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
        this.badge = 0;
        this.calorieConsumptioon = 0;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.dailyGoal = dailyGoal;
        this.gender = gender;
        this.day1 = 0;
        this.day2 = 0;
        this.day3 = 0;
    }



    public int getCalorieConsumptioon() {
        return calorieConsumptioon;
    }

    public void addOne(){
        ++ this.badge;
    }

    public void setZero(){
        this.badge = 0;
    }

    public List<Integer> getHistory(){
        List<Integer> history = new ArrayList<Integer>();
        history.add(day1);
        history.add(day2);
        history.add(day3);
        return history;
    }

    public void updateHistory(int newCalorie){
        day1 = day2;
        day2 = day3;
        day3 = newCalorie;
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