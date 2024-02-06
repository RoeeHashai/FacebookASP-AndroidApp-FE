package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapters.CommentsListAdapter;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class CommentsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page_activity);
        RecyclerView lstComments = findViewById(R.id.lstComments);
        final CommentsListAdapter adapter = new CommentsListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));
        List<Comment> comments = new ArrayList<>();
        User myUser = new User("Yatyat", "12345678", "Yatir Gross", R.drawable.profile_image);
        comments.add(new Comment(myUser,"my comment"));
        comments.add(new Comment(myUser,"my comment 2"));
        comments.add(new Comment(myUser,"my comment 3"));
        comments.add(new Comment(myUser,"my comment 4"));
        comments.add(new Comment(myUser,"my comment 5"));
        adapter.setComment(comments);
    }
}