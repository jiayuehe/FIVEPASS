package com.example.angelahe.stepcounter;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.angelahe.stepcounter.Activity.SignUp;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityTest {
    @Rule
    public ActivityTestRule<SignUp> signUpRule = new ActivityTestRule<>(SignUp.class,true, false);

    @Before
    public void setUp() throws Exception{
        Intents.init();

    }

    @Test
    public void testSignUp() throws InterruptedException {
        signUpRule.launchActivity(new Intent());
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("joe"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("joe"));
        // test spinner
        onView(withId(R.id.gender)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Male"))).perform(click());
        onView(withId(R.id.gender)).check(matches(withSpinnerText("Male")));

        onView(withId(R.id.age)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("18"))).perform(click());
        onView(withId(R.id.age)).check(matches(withSpinnerText("18")));

        onView(withId(R.id.body_height)).perform(scrollTo()).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("185"))).perform(click());
        onView(withId(R.id.body_height)).check(matches(withSpinnerText("185")));

        onView(withId(R.id.weight)).perform(scrollTo()).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("74"))).perform(click());
        onView(withId(R.id.weight)).check(matches(withSpinnerText("74")));

        onView(allOf(withId(R.id.dailyGoal))).perform(scrollTo()).perform(clearText(), typeText("500"));
        //onView(ViewMatchers.withId(R.id.buttonsignup)).perform(click());
        onView(withId(R.id.realSignUpButton)).perform(scrollTo()).check(matches(isDisplayed()));
//        Espresso.registerIdlingResources(new WaitActivityIsResumedIdlingResource(SignUp.class.getName()));
//        intended(hasComponent(SignUp.class.getName()));
    }


    @After
    public void tearDown() {
        Intents.release();
    }
}