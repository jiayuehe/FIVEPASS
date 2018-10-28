package com.example.angelahe.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.UserRegisterActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class,true, false);
    private int splashScreenWaitingTime = 3000;

    @Before
    public void setUp() throws Exception{
        Intents.init();
    }

    @Test
    public void viewSplashFirstTime_NavigateToTutorialAfter1000ms() throws InterruptedException {
        mainActivityRule.launchActivity(new Intent());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.registerIdlingResources(new WaitActivityIsResumedIdlingResource(UserRegisterActivity.class.getName()));
        intended(hasComponent(UserRegisterActivity.class.getName()));
    }

    @After
    public void tearDown() {
       Intents.release();
    }
}
