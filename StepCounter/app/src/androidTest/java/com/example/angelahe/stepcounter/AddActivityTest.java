package com.example.angelahe.stepcounter;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TimePicker;

import com.example.angelahe.stepcounter.Activity.AddExerciseActivity;
import com.example.angelahe.stepcounter.Activity.ChangeProfile;
import com.example.angelahe.stepcounter.Activity.DailyPlan;
import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.SignUp;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;
import com.example.angelahe.stepcounter.Activity.ViewProfile;
import com.example.angelahe.stepcounter.Database.Exercise;
import com.example.angelahe.stepcounter.Database.ExerciseDao;
import com.example.angelahe.stepcounter.Database.ExerciseRoomDatabase;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.Database.UserDao;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddActivityTest extends ActivityTestRule<MainActivity> {
    private ExerciseDao exerciseDao;

    public AddActivityTest(){
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);


    @Rule
    public ActivityTestRule<AddExerciseActivity> addExerciseRule =
            new ActivityTestRule<AddExerciseActivity>(AddExerciseActivity.class,true,false);

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        InstrumentationRegistry.getTargetContext().deleteDatabase("exerciseRoomDatabase");
        userRegisterRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        MainActivity.exerciseRoomDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), ExerciseRoomDatabase.class, "exerciseRoomDatabase").allowMainThreadQueries().build();
        UserDao mUserDao = MainActivity.myAppDatabase.UserDao();
        exerciseDao =  MainActivity.exerciseRoomDatabase.ExerciseDao();
        User user = new User("here", "there", 20 ,130, 168, 3000, 'F');
        mUserDao.addUser(user);
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, ViewProfile.class);
        result.putExtra("username", "here");
        addExerciseRule.launchActivity(result);
    }

    // White box Testing
    @Test
    public void addSwimming(){
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Swimming"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Swimming")));
    }

    // White Box Testing
    @Test
    public void addRunning(){
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Running"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Running")));
    }

    // White Box Testing
    @Test
    public void addWeightLifting(){
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Weight-lifting"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Weight-lifting")));
    }

    // Black Box Testing
    @Test
    public void addBycle(){
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Bicycling"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Bicycling")));
    }

    // Blackbox testing
    @Test
    public void clickButton(){
        // check whether it will go back to the view profile function
        // test whether the button of reset password works
        onView(withId(R.id.button2)).check(matches(allOf( isEnabled(), isClickable()))).perform(
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


    // set the time of blackbox
    public static ViewAction setTime(final int hour, final int minute) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                TimePicker tp = (TimePicker) view;
                tp.setHour(hour);
                tp.setMinute(minute);
            }
            @Override
            public String getDescription() {
                return "Set the passed time into the TimePicker";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }
        };
    }

    @Test
    public void setStartingTime(){
        ViewInteraction numPickerTo = onView(withId(R.id.simpleTimePicker));
        ViewInteraction numPickerEnd = onView(withId(R.id.simpleTimePicker2));
        numPickerTo.perform(setTime(10,23));
        numPickerEnd.perform(setTime(11,23));
    }

    // white box testing
    // database testing
    @Test
    public void databaseUpdate(){
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Bicycling"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Bicycling")));
        ViewInteraction numPickerTo = onView(withId(R.id.simpleTimePicker));
        ViewInteraction numPickerEnd = onView(withId(R.id.simpleTimePicker2));
        numPickerTo.perform(setTime(10,23));
        numPickerEnd.perform(setTime(11,23));
        onView(withId(R.id.button2)).check(matches(allOf( isEnabled(), isClickable()))).perform(
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
        List<Exercise> allExercise= exerciseDao.returnAllExercise("here");
        assertNotNull(allExercise);
        assertEquals(1, allExercise.size());
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



