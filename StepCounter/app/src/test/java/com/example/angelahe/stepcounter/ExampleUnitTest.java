package com.example.angelahe.stepcounter;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.Database.UserDao;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    /*
    private UserDao mUserDao;
    private UserRoomDatabase mDb;

    @Before
    public void initDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.databaseBuilder(getApplicationContext(),
                UserRoomDatabase.class, "BuildNewDataBase").build();
        mUserDao = mDb.getUserDao();
    }


    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = TestUtil.createUser(3);
        user.setName("george");
        mUserDao.insert(user);
        List<User> byName = mUserDao.findUsersByName("george");
        assertThat(byName.get(0), equalTo(user));
    }
    */
}