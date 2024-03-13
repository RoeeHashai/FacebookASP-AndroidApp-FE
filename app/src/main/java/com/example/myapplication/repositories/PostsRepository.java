package com.example.myapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.myapplication.MyApplication;
import com.example.myapplication.api.PostAPI;
import com.example.myapplication.entities.AppDB;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.PostDao;


import java.util.ArrayList;
import java.util.List;

public class PostsRepository {
    private PostDao dao;
    private PostListData postListData;
    private ProfileListData profileListData;
    private CommentListData commentListData;
    private PostAPI api;

    public PostsRepository() {
        AppDB db = Room.databaseBuilder(MyApplication.context, AppDB.class, "PostsDB")
                .fallbackToDestructiveMigration().build();
        dao = db.postDao();
        postListData = new PostListData();
        profileListData = new ProfileListData();
        commentListData = new CommentListData();
        api = new PostAPI(postListData, profileListData, commentListData, dao);
    }

    class PostListData extends MutableLiveData<List<Post>> {
        public PostListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                postListData.postValue(dao.index());
                //postListData.postValue(postListData.getValue());
            }).start();
        }
    }

    class ProfileListData extends MutableLiveData<List<Post>> {
        public ProfileListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                profileListData.postValue(postListData.getValue());
            }).start();
        }
    }

    class CommentListData extends MutableLiveData<List<Comment>> {
        public CommentListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                commentListData.postValue(commentListData.getValue());
            }).start();
        }
    }

    public void getUserDetails() {
        api.getUserDetailByEmail();
    }

    public LiveData<List<Post>> getAll() {
        return postListData;
    }
    public LiveData<List<Post>> getProfile() {
        return profileListData;
    }

    public LiveData<List<Comment>> getAllComments() {
        return commentListData;
    }

    public void reload() {
        api.get();
    }
    public void reloadComments(String pid) {
        api.getPostComments(pid);
    }

    public void createPost(Post post) {
        api.createPost(post);
    }
    public void createComment(String pid, Comment comment) {
        api.createComment(pid, comment);
    }

    public void getUserPosts(String id) {
        api.getUserPosts(id);
    }
    public void likePost(String pid) {
        api.likePost(pid);
    }
    public void unlikePost(String pid) {
        api.unlikePost(pid);
    }
    public void deletePost(String pid) {
        api.deletePost(pid);
    }
    public void updatePost(String pid, Post post) {
        api.updatePost(pid, post);
    }
}



