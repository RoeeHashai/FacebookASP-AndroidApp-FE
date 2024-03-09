package com.example.myapplication;

import android.net.Uri;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.myapplication.Activities.SignInPageActivity;

@RunWith(AndroidJUnit4.class)
public class SignInPageActivityTest {

    @Rule
    public ActivityScenarioRule<SignInPageActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SignInPageActivity.class);

    @Test
    public void testSignInWithValidCredentials() {
        // Enter valid user details
        onView(withId(R.id.userNameSignBox))
                .perform(ViewActions.replaceText("example@gmail.com"));
        onView(withId(R.id.passSignBox))
                .perform(ViewActions.replaceText("12345678"));
        onView(withId(R.id.passVrfySignBox))
                .perform(ViewActions.replaceText("12345678"));
        onView(withId(R.id.displayNameBox))
                .perform(ViewActions.replaceText("Name"));

        Uri mockUri = Uri.parse("content://com.example.provider/data/1");

        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.selectedImage = Uri.parse("android.resource://com.example.myapplication/" + R.drawable.facebook_icon);
        });

        // Click the sign-in button
        Espresso.onView(ViewMatchers.withId(R.id.signMeInBT)).perform(ViewActions.click());

        // Check if the LogInPageActivity is displayed after signing in
        Espresso.onView(withId(R.id.logInPage)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignInWithInvalidCredentials() {

        // Enter invalid user details
        onView(withId(R.id.userNameSignBox))
                .perform(ViewActions.replaceText("invalidemail"));
        onView(withId(R.id.passSignBox))
                .perform(ViewActions.replaceText("short"));
        onView(withId(R.id.passVrfySignBox))
                .perform(ViewActions.replaceText("short"));
        onView(withId(R.id.displayNameBox))
                .perform(ViewActions.replaceText("T")); // Name less than 2 characters

        // Click the sign-in button
        onView(withId(R.id.signMeInBT)).perform(click());

        try {
            Thread.sleep(4000); // Adjust the delay time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.signInPage)).check(matches(isDisplayed()));
    }
}
