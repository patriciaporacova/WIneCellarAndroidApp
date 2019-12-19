package com.example.winecellarapp.login;


import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.winecellarapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)


public class LoginActivityTest
{

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signUp()
    {
        onView(withId(R.id.emailEditText)).perform(replaceText("eric.volmer@hotmail.com"));
        onView(withId(R.id.passwordEditText)).perform(replaceText("123456"));
        onView(withId(R.id.passwordLoginButton)).perform(click());
    }
}
