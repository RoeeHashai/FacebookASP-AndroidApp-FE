package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.adapters.CommentsListAdapter;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Activity for displaying comments of a post and allowing users to add comments.
 */
public class CommentsPageActivity extends AppCompatActivity {

    private Post currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page_activity);
        // Get the position of the current post from the intent
        int position = getIntent().getIntExtra("CURRENT_POST", -1);
        currentPost = PostListSrc.getInstance(this).getPosts().get(position);
        // Initialize RecyclerView for displaying comments
        RecyclerView lstComments = findViewById(R.id.lstComments);
        final CommentsListAdapter adapter = new CommentsListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));
        // Load and display all comments for the current post
        setAllComment(adapter);
        // Set click listener for the button to add a new comment
        ImageButton addCommentBT = findViewById(R.id.addCommentBT);
        addCommentBT.setOnClickListener(v -> {
            EditText contentView = findViewById(R.id.commentContentBox);
            String content = contentView.getText().toString();
            if (content.length() != 0) {
                currentPost.addComment(UserListSrc.getInstance(this).getActiveUser(), content);
            }
            // Clear the content of the comment box after adding the comment
            contentView.setText("");
            // Update the displayed comments
            setAllComment(adapter);
        });
    }

    /**
     * Sets all comments for the current post in the adapter.
     * @param adapter The adapter for displaying comments.
     */
    private void setAllComment(CommentsListAdapter adapter) {
        List<Comment> comments = currentPost.getComments();
        adapter.setComment(comments);
    }
}