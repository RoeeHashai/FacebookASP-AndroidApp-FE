package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.repositories.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel {

    private PostsRepository repository;
    private LiveData<List<Post>> posts;
    private LiveData<List<Post>> profile;
    private LiveData<List<Comment>> comments;

    public PostsViewModel() {
        repository = new PostsRepository();
        posts = repository.getAll();
        comments = repository.getAllComments();
        profile = repository.getProfile();
    }

    public LiveData<List<Post>> get() {
        return posts;
    }
    public LiveData<List<Post>> getProfile() {
        return profile;
    }
    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public void getUserDetails() {
        repository.getUserDetails();
    }

    public void reload() {
        repository.reload();
    }
    public void reloadComments(String pid) {
        repository.reloadComments(pid);
    }
    public void reloadProfile(String id) {
        repository.getUserPosts(id);
    }

    public void createPost(Post post) {
        repository.createPost(post);
        reload();
    }

    public void createComment(String pid, Comment comment) {
        repository.createComment(pid, comment);
        reloadComments(pid);
    }
    public void likePost(String pid){
        repository.likePost(pid);
    }
    public void unlikePost(String pid){
        repository.unlikePost(pid);
    }
    public void deletePost(String pid){
        repository.deletePost(pid);
    }
    public void updatePost(String pid, Post post){
        repository.updatePost(pid, post);
    }
    public void getUserPosts(String id) {
        repository.getUserPosts(id);
    }
}
