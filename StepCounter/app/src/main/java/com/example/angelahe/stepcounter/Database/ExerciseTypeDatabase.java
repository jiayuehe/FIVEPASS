package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = ExerciseType.class, version = 1)
public abstract class ExerciseTypeDatabase extends RoomDatabase{
    public abstract ExerciseTypeDao ExerciseTypeDao();
}
