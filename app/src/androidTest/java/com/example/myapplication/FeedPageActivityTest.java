package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.myapplication.Activities.LogInPageActivity;

@RunWith(AndroidJUnit4.class)
public class FeedPageActivityTest {

    @Rule
    public ActivityScenarioRule<LogInPageActivity> loginPageActivityRule =
            new ActivityScenarioRule<>(LogInPageActivity.class);

    @Before
    public void setUp() {
        // Perform login action
        Espresso.onView(ViewMatchers.withId(R.id.usernameLogBox))
                .perform(ViewActions.replaceText("yatir.gross@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.passwordLogBox))
                .perform(ViewActions.replaceText("3333"));
        Espresso.onView(ViewMatchers.withId(R.id.LogInBT)).perform(ViewActions.click());
    }

    @Test
    public void testAddPostButton() {
        // Verify that AddPostActivity is launched
        onView(withId(R.id.addPostBT)).perform(click());

        onView(withId(R.id.addPostPage)).check(matches(isDisplayed()));
    }

    @Test
    public void testMenuButton() {
        // Click the menu button
        onView(withId(R.id.menuBT)).perform(click());
        // Verify that the popup menu is displayed
        Espresso.onView(withText("dark / light")).check(matches(isDisplayed()));
    }

    @Test
    public void testLogoutButton() {
        // Verify that log-out is launched
        onView(withId(R.id.logoutBT)).perform(click());

        onView(withId(R.id.logInPage)).check(matches(isDisplayed()));
    }
}
