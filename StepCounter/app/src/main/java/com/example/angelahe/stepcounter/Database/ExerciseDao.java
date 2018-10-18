package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    void addExercise(Exercise exercise);

    @Query("SELECT * FROM exercise_table WHERE username = :queryName")
    public List<Exercise> returnAllExercise(String queryName);
}
