package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExerciseTypeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExercise(ExerciseType exercise);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addAll(List<ExerciseType> exercises);

    @Query("SELECT name FROM AllExercises")
    public List<String> getAllExercise();

    @Query("SELECT calorie FROM AllExercises WHERE name = :queryName")
    public int getCalorie(String queryName);
}
