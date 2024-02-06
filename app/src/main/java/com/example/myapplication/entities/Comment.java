package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.entities.User;

@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private User author;
    private String content;

    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
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
}