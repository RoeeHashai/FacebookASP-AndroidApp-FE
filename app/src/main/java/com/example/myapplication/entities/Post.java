package com.example.myapplication.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private User author;
    private String content;
    private int likes;
    private Set<User> likedUsers;
    private Calendar date;
    private int intPic;
    private Uri uriPic;
    private List<Comment> comments;

    public Post(User author, String content, int pic) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.intPic = pic;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    public Post(User author, String content, Uri pic) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.uriPic = pic;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    public Post(User author, String content) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.intPic = 0;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    public String getDate() {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            return dateFormat.format(date.getTime());
        }
        return "";
    }

    public List<Comment> getComments() {
        return comments;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getIntPic() {
        return intPic;
    }

    public Uri getUriPic() {
        return uriPic;
    }

    public void setIntPic(int intPic) {
        this.intPic = intPic;
        this.uriPic = null;
    }

    public void setUriPic(Uri uriPic) {
        this.uriPic = uriPic;
        this.intPic = 0;
    }

    public void addComment(User author, String content) {
        this.comments.add(new Comment(author, content));
    }

    public void addLikedUser(User user) {
        this.likedUsers.add(user);
    }
    public void removeLikedUser(User user) {
        this.likedUsers.remove(user);
    }
    public boolean isUserLiked(User user) {
        return this.likedUsers.contains(user);
    }
}
