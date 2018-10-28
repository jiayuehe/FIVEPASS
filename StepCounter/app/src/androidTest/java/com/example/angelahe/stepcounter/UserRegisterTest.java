package com.example.angelahe.stepcounter;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.SignUp;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserRegisterTest {
    @Rule
    public ActivityTestRule<UserRegisterActivity> userRegisterRule = new ActivityTestRule<>(UserRegisterActivity.class,true, false);

    @Before
    public void setUp() throws Exception{
        Intents.init();

    }

    @Test
    public void linkToSetUp() throws InterruptedException {
        userRegisterRule.launchActivity(new Intent());
        onView(allOf(withId(R.id.buttonsignup), withText("Sign Up")));
        onView(allOf(withId(R.id.buttonlogin), withText("Log In")));
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("jiayuehe"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("jiayuehe"));
        //onView(ViewMatchers.withId(R.id.buttonsignup)).perform(click());
        onView(withId(R.id.buttonsignup)).check(matches(isDisplayed()));
        Espresso.registerIdlingResources(new WaitActivityIsResumedIdlingResource(SignUp.class.getName()));
        intended(hasComponent(SignUp.class.getName()));
    }

//    @Test
//    public void linktoLogIn() throws InterruptedException {
//       // userRegisterRule.launchActivity(new Intent());
//        //onView.
//    }


    @After
    public void tearDown() {
        Intents.release();
    }
}

