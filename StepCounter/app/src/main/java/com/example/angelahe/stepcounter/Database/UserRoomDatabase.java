package com.example.angelahe.stepcounter.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {User.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao UserDao();

}
