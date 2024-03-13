package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.PostListSrc;
import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.adapters.CommentsListAdapter;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewmodels.PostsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Activity for displaying comments of a post and allowing users to add comments.
 */
public class CommentsPageActivity extends AppCompatActivity {

    private String currentPost;
    private PostsViewModel postsViewModel;
    protected CommentsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page_activity);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        // Get the position of the current post from the intent
        currentPost = getIntent().getStringExtra("POST");
        // Initialize RecyclerView for displaying comments
        RecyclerView lstComments = findViewById(R.id.lstComments);
        adapter = new CommentsListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));
        SwipeRefreshLayout refreshLayout = findViewById(R.id.commentsRefreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            postsViewModel.reloadComments(currentPost);
        });
        postsViewModel.getComments().observe(this, comments -> {
            adapter.setComment(comments);
            refreshLayout.setRefreshing(false);
        });
        ImageButton addCommentBT = findViewById(R.id.addCommentBT);
        addCommentBT.setOnClickListener(v -> {
            EditText contentView = findViewById(R.id.commentContentBox);
            String content = contentView.getText().toString();
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setAuthor(MyJWTtoken.getInstance().getUserDetails().getValue());
            if (!content.isEmpty()) {
                postsViewModel.createComment(currentPost, comment);
            }
            // Clear the content of the comment box after adding the comment
            contentView.setText("");
        });
        postsViewModel.reloadComments(currentPost);
        FloatingActionButton backBT = findViewById(R.id.backToFeedBT);
        backBT.setOnClickListener(v -> {
            finish();
        });
    }
}