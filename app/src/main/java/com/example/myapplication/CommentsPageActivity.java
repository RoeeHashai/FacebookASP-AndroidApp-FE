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

public class CommentsPageActivity extends AppCompatActivity {

    private Post currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page_activity);
        int position = getIntent().getIntExtra("CURRENT_POST", -1);
        currentPost = PostListSrc.getInstance().getPosts().get(position);
        RecyclerView lstComments = findViewById(R.id.lstComments);
        final CommentsListAdapter adapter = new CommentsListAdapter(this);
        lstComments.setAdapter(adapter);
        lstComments.setLayoutManager(new LinearLayoutManager(this));
        setAllComment(adapter);
        ImageButton addCommentBT = findViewById(R.id.addCommentBT);
        addCommentBT.setOnClickListener(v -> {
            EditText contentView = findViewById(R.id.commentContentBox);
            String content = contentView.getText().toString();
            if (content.length() != 0) {
                currentPost.addComment(UserListSrc.getInstance().getActiveUser(), content);
            }
            contentView.setText("");
            setAllComment(adapter);
        });
    }

    private void setAllComment(CommentsListAdapter adapter) {
        List<Comment> comments = currentPost.getComments();
        adapter.setComment(comments);
    }
}