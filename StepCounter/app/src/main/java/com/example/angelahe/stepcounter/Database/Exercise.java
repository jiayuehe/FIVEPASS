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

    @ColumnInfo(name = "date") // "dd/mm/yyyy"
    String date;

    @ColumnInfo(name = "startTime")
    String startTime;

    @ColumnInfo(name ="endTime")
    String endTime;

    @ColumnInfo(name ="imageFile")
    int image;

    @ColumnInfo(name ="calorie")
    public int calorie;


    public int getImage() {
        return image;
    }


    public Exercise(String username, String exerciseName, String startTime, String endTime, int image,
                    String date, int calorie) {
        this.username = username;
        this.date = date;
        this.exerciseName = exerciseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.image = image;
        this.date = date;
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
                this.calorie = calorie;
                break;
        }
        // get interval of exercise time
        String[] start = startTime.split(":");
        String[] end = endTime.split(":");
        double hours = 0.0;
        if(Integer.parseInt(end[0]) >= Integer.parseInt(start[0])){
            hours = (double)(Integer.parseInt(end[0]) - Integer.parseInt(start[0])) + (double)(Integer.parseInt(end[1]) - Integer.parseInt(start[1]))/60.0;
        } else {
            hours = (double)(Integer.parseInt(end[0])+24 - Integer.parseInt(start[0])) + (double)(Integer.parseInt(end[1]) - Integer.parseInt(start[1]))/60.0;
        }
        this.calorie = (int)(hours * this.calorie);
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getDate(){return date;}

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

}
