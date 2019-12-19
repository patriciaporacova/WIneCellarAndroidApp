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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
public class HumidityTest
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
    public void humidityTest()
    {
        ViewInteraction bottomNavigationItemView = onView(allOf(withId(R.id.subnavbar_humidity), withContentDescription("Humidity"),
                childAtPosition(childAtPosition(withId(R.id.bottom_navigation), 0), 2), isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.changePeriod), withText("Change"), childAtPosition(
                childAtPosition(withId(R.id.fragment_container), 0), 6), isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatTextView = onView(allOf(withId(R.id.txtCancel), withText("Cancel"), childAtPosition(
                childAtPosition(withClassName(is("android.widget.FrameLayout")), 2), 2), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction bottomNavigationItemView2 = onView(allOf(withId(R.id.subnavbar_home), withContentDescription("Home"),
                childAtPosition(childAtPosition(withId(R.id.bottom_navigation), 0), 0),
                isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction cardView = onView(allOf(withId(R.id.cardHumidity), childAtPosition(
                childAtPosition(withClassName(is("android.widget.LinearLayout")), 1), 1), isDisplayed()));
        cardView.perform(click());

        onView(withId(R.id.start_date_humidity)).check(matches(isDisplayed()));

        onView(withId(R.id.end_date_humidity)).check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton = onView(allOf(withContentDescription("Open navigation drawer"),
                childAtPosition(allOf(withId(R.id.toolbar), childAtPosition(withId(R.id.toolbarOrange), 0)), 1),
                isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(allOf(childAtPosition(allOf(withId(R.id.design_navigation_view), childAtPosition(
                withId(R.id.nav_view), 0)), 2), isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction linearLayout = onView(allOf(childAtPosition(allOf(withId(R.id.recycler_view), childAtPosition(
                withId(android.R.id.list_container), 0)), 4), isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatEditText = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1)));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1)));
        appCompatEditText2.perform(scrollTo(), replaceText("15"));

        ViewInteraction appCompatEditText3 = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1), isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(allOf(withId(android.R.id.button1), withText("OK"), childAtPosition(childAtPosition(
                withId(R.id.buttonPanel), 0), 3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction linearLayout2 = onView(allOf(childAtPosition(allOf(withId(R.id.recycler_view), childAtPosition(
                withId(android.R.id.list_container), 0)), 5), isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction appCompatEditText4 = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1)));
        appCompatEditText4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1)));
        appCompatEditText5.perform(scrollTo(), replaceText("70"));

        ViewInteraction appCompatEditText6 = onView(allOf(withId(android.R.id.edit), childAtPosition(childAtPosition(
                withClassName(is("android.widget.ScrollView")), 0), 1), isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(allOf(withId(android.R.id.button1), withText("OK"), childAtPosition(
                childAtPosition(withId(R.id.buttonPanel), 0), 3)));
        appCompatButton2.perform(scrollTo(), click());
    }
}
