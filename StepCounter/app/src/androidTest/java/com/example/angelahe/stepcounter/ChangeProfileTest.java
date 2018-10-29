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

import com.example.angelahe.stepcounter.Activity.ChangeProfile;
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

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChangeProfileTest extends ActivityTestRule<MainActivity> {

    public ChangeProfileTest(){
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);


    @Rule
    public ActivityTestRule<ChangeProfile> changeProfileActivityTestRule =
            new ActivityTestRule<ChangeProfile>(ChangeProfile.class,true,false);

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        userRegisterRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        MainActivity.exerciseRoomDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), ExerciseRoomDatabase.class, "exerciseRoomDatabase").allowMainThreadQueries().build();
        UserDao mUserDao = MainActivity.myAppDatabase.UserDao();
        User user = new User("here", "there", 20 ,130, 168, 3000, 'F');
        mUserDao.addUser(user);
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, ViewProfile.class);
        result.putExtra("username", "here");
        changeProfileActivityTestRule.launchActivity(result);
    }

    @Before
    public void changeValue() throws Exception{
        onView(allOf(withId(R.id.newPassword))).perform(clearText(), typeText("changed"),ViewActions.closeSoftKeyboard());

        onView(withId(R.id.spinnerGender)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Male"))).perform(click());

        onView(withId(R.id.spinnerAge)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("18"))).perform(click());

        onView(withId(R.id.spinnerHeight)).perform(scrollTo()).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("185"))).perform(click());

        onView(withId(R.id.spinnerWeight)).perform(scrollTo()).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("74"))).perform(click());
    }

    @Test
    public void changeProfile(){
        // check whether it will go back to the view profile function
        // test whether the button of reset password works
        onView(withId(R.id.resetProfileButton)).check(matches(allOf( isEnabled(), isClickable()))).perform(
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

//    @Test
//    public void databaseUpdate(){
//
//    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @After
    public void closeDb() throws IOException {
        MainActivity.myAppDatabase.close();
    }
}



