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
import android.widget.EditText;
import android.widget.TextView;

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
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsTest extends ActivityTestRule<MainActivity> {

    private ViewProfile viewProfile;

    public SettingsTest(){
        super(MainActivity.class);
    }

//    @Rule
//    public TestRule chain = RuleChain
//            .outerRule(new ActivityTestRule<UserRegisterActivity>())

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);


    @Rule
    public ActivityTestRule<ViewProfile> viewProfileActivityTestRule =
            new ActivityTestRule<ViewProfile>(ViewProfile.class,true,false);

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

        viewProfileActivityTestRule.launchActivity(new Intent());

        viewProfile = viewProfileActivityTestRule.getActivity();

    }

    @Test
    public void basicLayoutTest(){
        // test the basic layout of the test file
        onView(allOf(withId(R.id.currUsername), withText("here")));
        onView(allOf(withId(R.id.currGoal), withText("3000")));
        onView(allOf(withId(R.id.currGender), withText('F')));
        onView(allOf(withId(R.id.currAge), withText("20")));
        onView(allOf(withId(R.id.currWeight), withText("130")));
        onView(allOf(withId(R.id.currHeight), withText("150")));
    }

    @Test
    public void changeProfile(){
        // set the username as extra
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent result = new Intent(targetContext, ViewProfile.class);
        result.putExtra("username", "here");
        viewProfileActivityTestRule.launchActivity(result);

        // test whether the button of reset password works
        onView(withId(R.id.goToProfileSetting)).check(matches(allOf( isEnabled(), isClickable()))).perform(
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
        intended(hasComponent(ChangeProfile.class.getName()));
    }

    @Test
    public void testUIUsernameDisplay() {
        TextView text = viewProfile.findViewById(R.id.usernameText);
        EditText username = viewProfile.findViewById(R.id.currUsername);
        assertEquals(text.getText(), "Username: ");
        assertNotNull(username);
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



