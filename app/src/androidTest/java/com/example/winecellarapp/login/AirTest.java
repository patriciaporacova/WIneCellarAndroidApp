package com.example.winecellarapp.login;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.winecellarapp.R;
import com.example.winecellarapp.views.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AirTest
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position)
    {

        return new TypeSafeMatcher<View>()
        {
            @Override
            public void describeTo(Description description)
            {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view)
            {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void airTest()
    {
        ViewInteraction bottomNavigationItemView = onView(allOf(withId(R.id.subnavbar_air), withContentDescription("Air quality"),
                childAtPosition(childAtPosition(withId(R.id.bottom_navigation), 0), 3), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.changePeriodAir), withText("Change"), childAtPosition(
                childAtPosition(withId(R.id.fragment_container), 0), 6), isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatTextView = onView(allOf(withId(R.id.txtCancel), withText("Cancel"), childAtPosition(childAtPosition(
                withClassName(is("android.widget.FrameLayout")), 2), 2), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(allOf(withId(R.id.subnavbar_home), withContentDescription("Home"), childAtPosition(
                childAtPosition(withId(R.id.bottom_navigation), 0), 0), isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction cardView = onView(allOf(withId(R.id.cardAir), childAtPosition(childAtPosition(
                withClassName(is("android.widget.LinearLayout")), 2), 0), isDisplayed()));
        cardView.perform(click());

        onView(withId(R.id.start_date_Air)).check(matches(isDisplayed()));

        onView(withId(R.id.end_date_air)).check(matches(isDisplayed()));

    }
}
