package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.entities.User;

public class ProfileActivity extends FeedPageActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton addPost = findViewById(R.id.addPostBT);
        addPost.setVisibility(View.GONE);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER");
        user = UserListSrc.getInstance(this).getUser(userName);
        super.setHeaderDetails(user);
        super.adapter.setPosts(PostListSrc.getInstance(this).getPostsOf(user));
    }
}