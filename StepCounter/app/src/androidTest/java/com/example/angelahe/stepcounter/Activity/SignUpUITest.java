package com.example.angelahe.stepcounter.Activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.angelahe.stepcounter.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SignUpUITest {

    private SignUp mSignUpActivity;
    public ActivityTestRule<SignUp> signUpRule = new ActivityTestRule<>(SignUp.class,true, false);

    @Before
    public void setUp() throws Exception {
        signUpRule.launchActivity(new Intent());
        mSignUpActivity = signUpRule.getActivity();
    }


    @Test
    public void testUIUsernameEdit(){
        EditText username = mSignUpActivity.findViewById(R.id.username);
        assertNotNull(username);
        assertEquals(username.getHint(), "Name");
        TextView usernameText = mSignUpActivity.findViewById(R.id.usernameText);
        assertEquals(usernameText.getText(), "Username");
    }

    @Test
    public void testUIPassword(){
        EditText password = mSignUpActivity.findViewById(R.id.password);
        assertNotNull(password);
        assertEquals(password.getHint(), "Password");
        TextView passwordText = mSignUpActivity.findViewById(R.id.passwordText);
        assertEquals(passwordText.getText(), "Password");
    }

    @Test
    public void testUIGender(){
        Spinner genderSpinner = mSignUpActivity.findViewById(R.id.gender);
        assertEquals(2,genderSpinner.getCount());
        assertEquals("gender", genderSpinner.getPrompt().toString().toLowerCase());
    }

    @Test
    public void testUIAge(){
        Spinner ageSpinner = mSignUpActivity.findViewById(R.id.age);
        assertEquals(86, ageSpinner.getCount());
        assertEquals("age", ageSpinner.getPrompt().toString().toLowerCase());
    }

    @Test
    public void testUIHeight(){
        mSignUpActivity = signUpRule.getActivity();

        Spinner heightSpinner = mSignUpActivity.findViewById(R.id.body_height);
        assertEquals(15, heightSpinner.getCount());
        assertEquals("height", heightSpinner.getPrompt().toString().toLowerCase());
    }

    @Test
    public void testUIWeight(){
        TextView weightText = mSignUpActivity.findViewById(R.id.withText);

        Spinner weightSpinner = mSignUpActivity.findViewById(R.id.weight);
        assertEquals(42, weightSpinner.getCount());
    }

    @Test
    public void testUIDialyGoal(){
        TextView daliyGoalText = mSignUpActivity.findViewById(R.id.dailyGoalText);
        assertEquals("Daily Goal", daliyGoalText.getText());
        EditText dailyGoal = mSignUpActivity.findViewById(R.id.dailyGoal);
        assertEquals(InputType.TYPE_CLASS_NUMBER, dailyGoal.getInputType());

    }

    @After
    public void tearDown() throws Exception {
    }
}