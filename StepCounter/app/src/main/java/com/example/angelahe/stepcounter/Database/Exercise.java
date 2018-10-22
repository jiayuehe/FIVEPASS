package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

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

    public int getImage() {
        return image;
    }

    public Exercise(String username, String exerciseName, String date, String startTime, String endTime, int image) {
        this.username = username;
        this.date = date;
        this.exerciseName = exerciseName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.image = image;
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
