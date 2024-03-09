package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Singleton class responsible for managing the list of posts in the application.
 */
public class PostListSrc {
    private static PostListSrc inst;
    private List<Post> posts;

    private PostListSrc(){}

    /**
     * Retrieves the singleton instance of the PostListSrc class.
     *
     * @param context The context of the application.
     * @return The singleton instance of PostListSrc.
     */
    public static PostListSrc getInstance(Context context) {
        if (inst == null) {
            inst = new PostListSrc();
            inst.initList(context);
        }
        return inst;
    }

    /**
     * Initializes the list of posts by loading data from a JSON file.
     *
     * @param context The context of the application.
     */
    private void initList(Context context){
        if (posts != null)
            return;
        //posts = JsonToList.createPostList(context, "posts.json");
    }

    /**
     * Retrieves the list of posts.
     *
     * @return The list of posts.
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * Adds a new post to the list.
     *
     * @param post The post to add.
     */
    public void addPost(Post post) {
        posts.add(post);
    }

    public List<Post> getPostsOf (User user) {
        List<Post> userPosts = new ArrayList<>();
        for (Post post : this.posts) {
            if (post.getAuthor().equals(user)) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }
}
