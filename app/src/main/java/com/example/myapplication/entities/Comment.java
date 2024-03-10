package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.myapplication.entities.User;
/**
 * Entity class representing a comment.
 */
@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private String id;
    private User author;
    private String content;
    /**
     * Constructor to create a new Comment object.
     * @param author The user who authored the comment.
     * @param content The content of the comment.
     */
    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
    }
    /**
     * Getter method to retrieve the author of the comment.
     * @return The author of the comment.
     */
    public User getAuthor() {
        return author;
    }
    /**
     * Setter method to set the author of the comment.
     * @param author The user who authored the comment.
     */
    public void setAuthor(User author) {
        this.author = author;
    }
    /**
     * Getter method to retrieve the content of the comment.
     * @return The content of the comment.
     */
    public String getContent() {
        return content;
    }
    /**
     * Setter method to set the content of the comment.
     * @param content The content of the comment.
     */
    public void setContent(String content) {
        this.content = content;
    }
}