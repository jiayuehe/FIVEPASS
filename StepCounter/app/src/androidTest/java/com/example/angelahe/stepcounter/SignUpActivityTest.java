package com.example.angelahe.stepcounter;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.angelahe.stepcounter.Activity.HomeActivity;
import com.example.angelahe.stepcounter.Activity.MainActivity;
import com.example.angelahe.stepcounter.Activity.SignUp;
import com.example.angelahe.stepcounter.Database.User;
import com.example.angelahe.stepcounter.Database.UserDao;
import com.example.angelahe.stepcounter.Database.UserRoomDatabase;

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
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityTest extends ActivityTestRule<MainActivity>{
    private UserDao mUserDao;
    public  UserRoomDatabase userDatabase;
    private MainActivity mActivity;

    public SignUpActivityTest(){
        super(MainActivity.class);
        mActivity = getActivity(); // launch MainActicity
    }
    @Rule
    public ActivityTestRule<SignUp> signUpRule = new ActivityTestRule<>(SignUp.class,true, false);

    @Before
    public void setUp() throws Exception{
        Intents.init();
        InstrumentationRegistry.getTargetContext().deleteDatabase("userRoomDatabase");
        signUpRule.launchActivity(new Intent());
        MainActivity.myAppDatabase = Room.databaseBuilder(signUpRule.getActivity().getApplicationContext(), UserRoomDatabase.class, "userRoomDatabase").allowMainThreadQueries().build();
        mUserDao = MainActivity.myAppDatabase.UserDao();
        User user = new User("test", "test", 20 ,30, 168, 3000, 'F');
        mUserDao.addUser(user);
    }

    @Test
    public void testSignUpWithTakenUsername() throws InterruptedException {
        // sign up failed due to taken username
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("test"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("test"));
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
        onView(withId(R.id.realSignUpButton)).perform(click());
        intended(hasComponent(SignUp.class.getName()));
    }

    @Test
    public void testSignUpSuccess() throws InterruptedException {
        // sign up successfully
        onView(allOf(withId(R.id.username))).perform(clearText(), typeText("joe"));
        onView(allOf(withId(R.id.password))).perform(clearText(), typeText("joe"));

        // test spinner
//        onView(withId(R.id.gender)).perform(click());
//        onData(allOf(is(instanceOf(String.class)),is("Male"))).perform(click());
//        onView(withId(R.id.gender)).check(matches(withSpinnerText("Male")));

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
        onView(withId(R.id.realSignUpButton)).perform(click());
        Thread.sleep(1000);
        intended(hasComponent(HomeActivity.class.getName()));
    }

    @Test
    public void testSignUpWithoutDailyGoal() throws InterruptedException {
        // fail to sign up due to not entering daily goal
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

//        onView(allOf(withId(R.id.dailyGoal))).perform(scrollTo()).perform(clearText(), typeText("500"));
        //onView(ViewMatchers.withId(R.id.buttonsignup)).perform(click());
        onView(withId(R.id.realSignUpButton)).perform(scrollTo()).check(matches(isDisplayed()));
//        Espresso.registerIdlingResources(new WaitActivityIsResumedIdlingResource(SignUp.class.getName()));
        onView(withId(R.id.realSignUpButton)).perform(click());
        intended(hasComponent(SignUp.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}