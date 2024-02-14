package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PostListSrc {
    private static PostListSrc inst;
    private List<Post> posts;

    private PostListSrc(){}

    public static PostListSrc getInstance(Context context) {
        if (inst == null) {
            inst = new PostListSrc();
            inst.initList(context);
        }
        return inst;
    }

    private void initList(Context context){
        if (posts != null)
            return;
        posts = JsonToList.createPostList(context, "posts.json");
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
