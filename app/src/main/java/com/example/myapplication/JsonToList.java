package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Utility class to convert JSON data to lists of entities.
 */
public class JsonToList {
    /**
     * Create a list of users from a JSON file.
     *
     * @param context      The context of the application.
     * @param jsonFilePath The path to the JSON file containing user data.
     * @return A list of User objects parsed from the JSON file.
     */
    public static List<User> createUserList(Context context, String jsonFilePath) {
        List<User> userList = new ArrayList<>();
        try {
            // Load JSON array from file
            JSONArray jsonArray = loadJsonArray(context, jsonFilePath);
            JSONObject jsonUser = null;
            if (jsonArray != null) {
                jsonUser = jsonArray.getJSONObject(0);
            }
            String name = jsonUser.getString("name");
            String password = jsonUser.getString("password");
            String userName = jsonUser.getString("email");
            User user = new User(userName, password, name, R.drawable.roee_hashai);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(1);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.talya_rubinstein);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(2);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.yatir_gross);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(3);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.natalya_gross);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(4);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.shira_goshen);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(5);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.tal_ziv);
            userList.add(user);

            jsonUser = jsonArray.getJSONObject(6);
            name = jsonUser.getString("name");
            password = jsonUser.getString("password");
            userName = jsonUser.getString("email");
            user = new User(userName, password, name, R.drawable.yaara_sir);
            userList.add(user);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    /**
     * Create a list of posts from a JSON file.
     *
     * @param context      The context of the application.
     * @param jsonFilePath The path to the JSON file containing post data.
     * @return A list of Post objects parsed from the JSON file.
     */
    public static List<Post> createPostList(Context context, String jsonFilePath) {
        List<Post> postList = new ArrayList<>();
        try {
            JSONArray jsonArray = loadJsonArray(context, jsonFilePath);
            JSONObject jsonPost = null;
            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(0);
            }
            String author = jsonPost.getString("author");
            String content = jsonPost.getString("content");
            String dateString = jsonPost.getString("date");
            Calendar date = strToCldr(dateString);
            int likes = jsonPost.getInt("likes");
            JSONArray commentsJson = jsonPost.getJSONArray("comments");
            List<Comment> comments = getComments(commentsJson, context);
            User user = UserListSrc.getInstance(context).getUser(author);
            Post post = new Post(user, content, likes, date, R.drawable.post1, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(1);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post2, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(2);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post3, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(3);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post4, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(4);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post5, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(5);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post6, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(6);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post7, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(7);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post8, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(8);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, R.drawable.post9, comments);
            postList.add(post);

            if (jsonArray != null) {
                jsonPost = jsonArray.getJSONObject(9);
            }
            author = jsonPost.getString("author");
            content = jsonPost.getString("content");
            dateString = jsonPost.getString("date");
            date = strToCldr(dateString);
            likes = jsonPost.getInt("likes");
            commentsJson = jsonPost.getJSONArray("comments");
            comments = getComments(commentsJson, context);
            user = UserListSrc.getInstance(context).getUser(author);
            post = new Post(user, content, likes, date, 0, comments);
            postList.add(post);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return postList;
    }

    /**
     * Load a JSON array from a file.
     *
     * @param context  The context of the application.
     * @param filename The name of the JSON file.
     * @return A JSONArray object containing the data from the file.
     */
    public static JSONArray loadJsonArray(Context context, String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            return new JSONArray(jsonString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Convert a JSONArray of comments to a list of Comment objects.
     *
     * @param jsonArray The JSONArray containing comment data.
     * @param context   The context of the application.
     * @return A list of Comment objects parsed from the JSONArray.
     */
    private static List<Comment> getComments(JSONArray jsonArray, Context context) {
        List<Comment> commentList = new ArrayList<>();
        try {
            JSONObject jsonComment = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonComment = jsonArray.getJSONObject(i);
                String author = jsonComment.getString("author");
                String content = jsonComment.getString("content");
                User user = UserListSrc.getInstance(context).getUser(author);
                commentList.add(new Comment(user, content));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return commentList;
    }

    /**
     * Convert a string representation of date to a Calendar object.
     *
     * @param dateString The string representation of the date.
     * @return A Calendar object representing the date.
     */
    private static Calendar strToCldr(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            // Parse the string into a Date object
            Date date = dateFormat.parse(dateString);

            // Convert Date to Calendar
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);
            }
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}