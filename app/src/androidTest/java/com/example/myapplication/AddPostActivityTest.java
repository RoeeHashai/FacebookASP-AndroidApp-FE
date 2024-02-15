package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.myapplication.entities.Post;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AddPostActivityTest {

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
        Espresso.onView(ViewMatchers.withId(R.id.addPostBT)).perform(ViewActions.click());
    }

    @Test
    public void testPublishButton() {
        String postContent = "Test post content";
        // Enter post content
        Espresso.onView(ViewMatchers.withId(R.id.postContentBox))
                .perform(ViewActions.replaceText(postContent));

        // Click the publish button
        Espresso.onView(ViewMatchers.withId(R.id.publishPostBT)).perform(ViewActions.click());

        // Verify that the FeedPageActivity is launched
        onView(withId(R.id.feedPage)).check(matches(isDisplayed()));

        // Get the newly added post
        List<Post> posts = PostListSrc.getInstance(ApplicationProvider.getApplicationContext()).getPosts();

        // Check that the post list contains the newly added post
        assertTrue(posts.size() > 0);

        // Check that the content of the added post matches the entered content
        assertEquals(posts.get(posts.size() - 1).getContent(), postContent);
    }
}
