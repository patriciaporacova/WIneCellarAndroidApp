package com.example.winecellarapp.login;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.winecellarapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class CreateAccountTest
{
    @Rule
    public ActivityTestRule<CreateAccount> activityRule = new ActivityTestRule<>(CreateAccount.class);


    @Test
    public void createNewUser()
    {
        onView(withId(R.id.nameEditText)).perform(replaceText("Eric"));
        onView(withId(R.id.emailEditText)).perform(replaceText("something@gmail.com"));
        onView(withId(R.id.passwordEditText)).perform(replaceText("123456"));
        onView(withId(R.id.confirmPasswordEditText)).perform(replaceText("123456"));
        onView(withId(R.id.confirmPasswordEditText)).check(ViewAssertions.matches(withText("123456")));
        onView(withId(R.id.createUserButton)).perform(click());
        closeSoftKeyboard();

    }
}
