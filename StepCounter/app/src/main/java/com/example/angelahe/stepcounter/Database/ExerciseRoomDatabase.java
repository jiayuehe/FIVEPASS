package com.example.angelahe.stepcounter.Database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

@Database(entities = {Exercise.class}, version = 2)
public abstract class ExerciseRoomDatabase extends RoomDatabase {
    public abstract ExerciseDao ExerciseDao();

}