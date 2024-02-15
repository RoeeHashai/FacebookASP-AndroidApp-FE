package com.example.myapplication;

import com.example.myapplication.R;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PostTest {

    private Post post;
    private User author;
    private String content;
    private Calendar date;
    private int picResource;
    private List<Comment> comments;

    @Before
    public void setUp() {
        author = new User("username", "password", "name", 0); // Example user
        content = "This is a post.";
        date = Calendar.getInstance();
        date.set(2024,Calendar.FEBRUARY,15,10,30);
        picResource = R.drawable.facebook_icon; // Example image resource ID
        comments = new ArrayList<>();
        post = new Post(author, content, 0, date, picResource, comments);
    }

    @Test
    public void testGetAuthor() {
        assertEquals(author, post.getAuthor());
    }

    @Test
    public void testGetContent() {
        assertEquals(content, post.getContent());
    }

    @Test
    public void testGetLikes() {
        assertEquals(0, post.getLikes());
    }

    @Test
    public void testGetDate() {
        assertEquals("15.02.2024 10:30", post.getDate()); // Adjust date and time format as per your requirements
    }

    @Test
    public void testAddComment() {
        User commenter = new User("Alice1234", "password", "Alice", 0); // Example commenter
        String commentContent = "This is a comment.";
        post.addComment(commenter, commentContent);
        assertEquals(1, post.getComments().size());
    }

    @Test
    public void testAddLikedUser() {
        User liker = new User("Alice1234", "password", "Alice", 0); // Example liker
        post.addLikedUser(liker);
        assertTrue(post.isUserLiked(liker));
    }

    @Test
    public void testRemoveLikedUser() {
        User liker = new User("Alice1234", "password", "Alice", 0); // Example liker
        post.addLikedUser(liker);
        post.removeLikedUser(liker);
        assertFalse(post.isUserLiked(liker));
    }
}
