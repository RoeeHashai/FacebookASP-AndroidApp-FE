package com.example.myapplication;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PostListSrc {
    private static PostListSrc inst;
    private List<Post> posts;

    private PostListSrc(){}

    public static PostListSrc getInstance() {
        if (inst == null) {
            inst = new PostListSrc();
            inst.initList();
        }
        return inst;
    }

    private void initList(){
        if (posts != null)
            return;
        posts = new ArrayList<>();
        User myUser = UserListSrc.getInstance().getUser("yatir@gmail.com");
        posts.add(new Post(myUser,"Hello World1", R.drawable.profile_image));
        posts.add(new Post( myUser,"Hello World2"));
        posts.add(new Post( myUser,"Hello World3", R.drawable.facebook_icon));
        posts.add(new Post( myUser,"Hello World4", R.drawable.account_circle_24px));
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
