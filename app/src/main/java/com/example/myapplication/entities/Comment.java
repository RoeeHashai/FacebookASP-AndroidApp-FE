package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.entities.User;
/**
 * Entity class representing a comment.
 */
@Entity
public class Comment {
    private String id;
    private UserDetails author;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDetails getAuthor() {
        return author;
    }

    public void setAuthor(UserDetails author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}