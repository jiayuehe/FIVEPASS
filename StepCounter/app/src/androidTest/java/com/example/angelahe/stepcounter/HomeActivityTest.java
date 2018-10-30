package com.example.angelahe.stepcounter;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.angelahe.stepcounter.Activity.DailyPlan;
import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.SignUp;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;
import com.example.angelahe.stepcounter.Activity.ViewProfile;
import com.example.angelahe.stepcounter.Database.ExerciseRoomDatabase;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.Database.UserDao;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest extends ActivityTestRule<MainActivity> {

    public HomeActivityTest(){
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityRule =
            new ActivityTestRule<HomeActivity>(HomeActivity.class,true,false);

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        userRegisterRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        MainActivity.exerciseRoomDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), ExerciseRoomDatabase.class, "exerciseRoomDatabase").allowMainThreadQueries().build();
        UserDao mUserDao = MainActivity.myAppDatabase.UserDao();
        User user = new User("here", "there", 20 ,30, 168, 3000, 'F');
        mUserDao.addUser(user);
    }

    @Test
    public void testAdd() throws InterruptedException {
        // Test what happened if add activity button is clicked
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "here");
        homeActivityRule.launchActivity(result);
        onView(withId(R.id.AddMorePlan)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
        intended(hasComponent(DailyPlan.class.getName()));
    }



    @Test
    public void testSettings() throws InterruptedException {
        // Test what happened if settings is clicked
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "here");
        homeActivityRule.launchActivity(result);
        onView(withId(R.id.Settings)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
        intended(hasComponent(ViewProfile.class.getName()));
    }

    @Test
    public void testHome() throws InterruptedException {
        // Testing what happened if home is clicked
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "here");
        homeActivityRule.launchActivity(result);
        onView(withId(R.id.Settings)).check(matches(allOf( isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click plus button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
        intended(hasComponent(HomeActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @After
    public void closeDb() throws IOException {
        MainActivity.myAppDatabase.close();
    }
}



