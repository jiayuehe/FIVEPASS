package com.example.angelahe.stepcounter;

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
import android.util.Log;
import android.view.View;

import com.example.angelahe.stepcounter.Activity.AddExerciseActivity;
import com.example.angelahe.stepcounter.Activity.DailyPlan;
import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;
import com.example.angelahe.stepcounter.Activity.ViewProfile;
import com.example.angelahe.stepcounter.Database.Exercise;
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
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DailyPlanTest extends ActivityTestRule<MainActivity>{
    private UserDao mUserDao;
    public  UserRoomDatabase userDatabase;
    private MainActivity mActivity;

    public DailyPlanTest(){
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);

    @Rule
    public ActivityTestRule<DailyPlan> dailyPlanRule = new ActivityTestRule<>(DailyPlan.class,true, false);

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        InstrumentationRegistry.getTargetContext().deleteDatabase("exerciseRoomDatabase");
        userRegisterRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        MainActivity.exerciseRoomDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), ExerciseRoomDatabase.class, "exerciseRoomDatabase").allowMainThreadQueries().build();
        UserDao mUserDao = MainActivity.myAppDatabase.UserDao();

        User user = new User("test", "test", 20 ,30, 168, 3000, 'F');
        mUserDao.addUser(user);
        Exercise exercise = new Exercise("test", "Walking", "10:30", "11:30", R.drawable.walking, "10/10/2018", 250);
        MainActivity.exerciseRoomDatabase.ExerciseDao().addExercise(exercise);
    }

    @Test
    public void testAddExerciseButton() throws InterruptedException {
        // test if it navigates to AddExerciseActivity
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "test");
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("test"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("test"),ViewActions.closeSoftKeyboard());
        dailyPlanRule.launchActivity(result);
        onView(withId(R.id.addExerciseButton)).perform(click());
        intended(hasComponent(AddExerciseActivity.class.getName()));
    }
    @Test
    public void testCompleteExercise() throws InterruptedException {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "test");
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("test"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("test"),ViewActions.closeSoftKeyboard());
        dailyPlanRule.launchActivity(result);
        onView(withId(R.id.check_button)).perform(click());
        intended(hasComponent(DailyPlan.class.getName()));
    }
    @Test
    public void testSettings() throws InterruptedException {
        // Test what happened if settings is clicked
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, HomeActivity.class);
        result.putExtra("username", "test");
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("test"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("test"),ViewActions.closeSoftKeyboard());
        dailyPlanRule.launchActivity(result);
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
        result.putExtra("username", "test");
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("test"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("test"),ViewActions.closeSoftKeyboard());
        dailyPlanRule.launchActivity(result);
        onView(withId(R.id.Home)).check(matches(allOf( isEnabled(), isClickable()))).perform(
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
}