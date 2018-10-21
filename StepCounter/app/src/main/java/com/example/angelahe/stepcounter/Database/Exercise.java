package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.angelahe.stepcounter.R;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "exercise_table")

public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int exercise;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "exerciseName")
    final String exerciseName;

    @ColumnInfo(name = "startTime")
    String startTime;

    @ColumnInfo(name ="endTime")
    String endTime;

    @ColumnInfo(name ="imageFile")
    int image;

    @ColumnInfo(name ="calorie")
    int calorie;

    public int getImage() {
        return image;
    }

    public Exercise(String username, String exerciseName, String startTime, String endTime, int image) {
        this.username = username;
        this.exerciseName = exerciseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.image = image;
        switch (exerciseName) {
            // in an hour
            case "Walking":
                this.calorie = 250;
                break;
            case "Swimming":
                this.calorie = 600;
                break;
            case "Running":
                this.calorie = 600;
                break;
            case "Weight-lifting":
                this.calorie = 380;
                break;
            case "Bicycling":
                this.calorie = 650;
                break;
            default:
                this.calorie = 0;
                break;
        }
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
