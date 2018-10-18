package com.example.angelahe.stepcounter.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table WHERE username = :queryName")
    public User returnCurrentUser(String queryName);

    @Query("SELECT * FROM user_table WHERE username = :queryName")
    public int returnBdge(String queryName);

    @Query("SELECT * FROM user_table")
    public List<User> getAllUsers();
}
