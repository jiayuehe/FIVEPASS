package com.example.angelahe.stepcounter;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.EditText;

import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.SignUp;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.Database.UserDao;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserRegisterTest extends ActivityTestRule<MainActivity> {

    public UserRegisterTest(){
        super(MainActivity.class);
    }

    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);
    UserRegisterActivity mActivity;

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        userRegisterRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(userRegisterRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        UserDao mUserDao = MainActivity.myAppDatabase.UserDao();
        User user = new User("here", "there", 20 ,30, 168, 3000, 'F');
        mUserDao.addUser(user);

        mActivity = userRegisterRule.getActivity();
    }
    @Test
    public void testUsernameEdit(){
        EditText username = mActivity.findViewById(R.id.username);
        assertNotNull(username);
        assertEquals(username.getHint(), "USERNAME");
    }
    @Test
    public void testPasswordEdit(){
        EditText password = mActivity.findViewById(R.id.password);
        assertNotNull(password);
        assertEquals(password.getHint(), "PASSWORD");
    }
    @Test
    public void testSignUpBtn(){
        Button btn = mActivity.findViewById(R.id.buttonsignup);
        assertNotNull(btn);
        assertEquals(btn.getText().toString(), "Sign Up");
    }
    @Test
    public void testLogInBtn(){
        Button btn = mActivity.findViewById(R.id.buttonlogin);
        assertNotNull(btn);
        assertEquals(btn.getText().toString(), "Log In");
    }

    @Test
    public void linkToSignUp() throws InterruptedException {
        // sign up successful∂
        onView(allOf(withId(R.id.buttonsignup), withText("Sign Up")));
        onView(allOf(withId(R.id.buttonlogin), withText("Log In")));
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("jiayuehe"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("jiayuehe"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonsignup)).perform(click());
        intended(hasComponent(SignUp.class.getName()));
    }


    @Test
    public void linkToSignUpUsernameTaken() throws InterruptedException {
        // sign up Username Taken
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("here"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("there"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonsignup)).perform(click());
        intended(hasComponent(UserRegisterActivity.class.getName()));
    }

    @Test
    public void linktoLogInSuccessful() throws InterruptedException {
        // log in successfull
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("here"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("there"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonlogin)).perform(click());
        Thread.sleep(1000);
        intended(hasComponent(HomeActivity.class.getName()));
    }

    @Test
    public void linktoLogInFailure() throws InterruptedException {
        // log in with unregistered username
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("jiayuehe"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("abcdefg") ,ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonlogin)).perform(click());
        intended(hasComponent(UserRegisterActivity.class.getName()));
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

