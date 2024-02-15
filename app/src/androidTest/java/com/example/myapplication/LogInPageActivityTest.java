package com.example.myapplication;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LogInPageActivityTest {

    @Rule
    public ActivityScenarioRule<LogInPageActivity> activityRule =
            new ActivityScenarioRule<>(LogInPageActivity.class);

    @Test
    public void testLogInWithValidCredentials() {
        // Type correct username and password
        onView(withId(R.id.usernameLogBox))
                .perform(replaceText("yatir.gross@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogBox))
                .perform(replaceText("3333"), closeSoftKeyboard());
        // Add a delay before clicking the log in button
        try {
            Thread.sleep(4000); // Adjust the delay time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click the log in button
        onView(withId(R.id.LogInBT)).perform(click());

        // Check if the FeedPageActivity is displayed
        onView(withId(R.id.feedPage)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogInWithInvalidCredentials() {
        // Type incorrect username and password
        onView(withId(R.id.usernameLogBox))
                .perform(replaceText("yatir.gross@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordLogBox))
                .perform(replaceText("11111111"), closeSoftKeyboard());

        // Click the log in button
        onView(withId(R.id.LogInBT)).perform(click());

        // Check if stay in LogIn
        onView(withId(R.id.logInPage)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignInButton() {
        // Click the log in button
        onView(withId(R.id.SignInBT)).perform(click());
        // Check if stay in LogIn
        onView(withId(R.id.signInPage)).check(matches(isDisplayed()));
    }
}
