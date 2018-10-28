package com.example.angelahe.stepcounter;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Database.ExerciseRoomDatabase;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {
    UserRoomDatabase myAppDatabase;
    ExerciseRoomDatabase exerciseRoomDatabase;

    @Before
    public void prepareDatabase(){

    }

    public HomeActivityTest(){
    }

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class, true, false){
        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
        }
    };

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule(HomeActivity.class);

    @Test
    public void testStepFunction(){
        // Test Basic Layout
//        //from ActivityA, click the button which starts the ActivityB
//        onView(withText("ClickMe")).perform(click());
        onView(withId(R.id.tv_info)).check(matches(notNullValue()));
        onView(withId(R.id.tv_info)).check(matches(withText(containsString("Steps"))));
    }

}
