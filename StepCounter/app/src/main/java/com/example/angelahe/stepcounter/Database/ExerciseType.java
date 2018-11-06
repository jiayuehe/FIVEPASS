package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "AllExercises", indices = @Index(value = "name", unique = true))
public class ExerciseType {

    @PrimaryKey(autoGenerate = true)
    public int key;

    @NonNull
    public String name;

    @NonNull
    public int calorie;

    public ExerciseType(String name, int calorie){
        this.name = name;
        this.calorie = calorie;
    }
}
