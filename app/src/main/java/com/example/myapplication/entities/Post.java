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
/**
 * Entity class representing a post.
 */
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

    // Constructors

    /**
     * Constructor to create a new Post object with a resource ID of the image.
     * @param author The user who authored the post.
     * @param content The content of the post.
     * @param pic The resource ID of the post image.
     */
    public Post(User author, String content, int pic) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.intPic = pic;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    /**
     * Constructs a new Post object with the specified author, content, likes, date, image, and comments.
     *
     * @param author   The user who authored the post.
     * @param content  The content of the post.
     * @param likes    The number of likes for the post.
     * @param date     The date and time when the post was created.
     * @param pic      The resource ID of the post image.
     * @param comments The list of comments on the post.
     */
    public Post(User author, String content, int likes, Calendar date, int pic, List<Comment> comments) {
        this.author = author;
        this.content = content;
        this.likes = likes;
        this.likedUsers = new HashSet<>();
        this.intPic = pic;
        this.date = date;
        this.comments = comments;
    }

    /**
     * Constructor to create a new Post object with a URI of the image.
     * @param author The user who authored the post.
     * @param content The content of the post.
     * @param pic The URI of the post image.
     */
    public Post(User author, String content, Uri pic) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.uriPic = pic;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    /**
     * Constructor to create a new Post object with no image.
     * @param author The user who authored the post.
     * @param content The content of the post.
     */
    public Post(User author, String content) {
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.likedUsers = new HashSet<>();
        this.intPic = 0;
        this.date = Calendar.getInstance();
        this.comments = new ArrayList<>();
    }

    // Getters and setters

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


    /**
     * Add a new comment to the post.
     * @param author The user who authored the comment.
     * @param content The content of the comment.
     */
    public void addComment(User author, String content) {
        this.comments.add(new Comment(author, content));
    }
    /**
     * Add a user to the list of users who liked the post.
     * @param user The user who liked the post.
     */
    public void addLikedUser(User user) {
        this.likedUsers.add(user);
    }
    /**
     * Remove a user from the list of users who liked the post.
     * @param user The user whose like is to be removed.
     */
    public void removeLikedUser(User user) {
        this.likedUsers.remove(user);
    }

    /**
     * Check if a user has liked the post.
     * @param user The user to check.
     * @return True if the user has liked the post, false otherwise.
     */
    public boolean isUserLiked(User user) {
        return this.likedUsers.contains(user);
    }
}
