package com.example.myapplication.api;

import com.example.myapplication.Comments;
import com.example.myapplication.Friends;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.JWT;
import com.example.myapplication.LoginRequest;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface WebServiceAPI {
    // Create a new user
    @POST("users")
    Call<Void> createUser(@Body User user);

    // Generate JWT token for user authentication
    @POST("tokens")
    Call<JWT> generateToken(@Body LoginRequest loginRequest);

    // Get posts feed
    @GET("posts")
    Call<List<Post>> getPostsFeed(@Header("Authorization") String authToken);

    // Create a new post
    @POST("users/{id}/posts")
    Call<Void> createPost(@Header("Authorization") String authToken, @Path("id") String userId, @Body Post post);

    // Update a post
    @PATCH("users/{id}/posts/{pid}")
    Call<Void> updatePost(@Header("Authorization") String authToken, @Path("id") String userId,
                          @Path("pid") String postId, @Body Post post);

    // Delete a post
    @DELETE("users/{id}/posts/{pid}")
    Call<Void> deletePost(@Header("Authorization") String authToken, @Path("id") String userId, @Path("pid") String postId);

    // Get user details by ID
    @GET("users/{id}")
    Call<UserDetails> getUserDetails(@Header("Authorization") String authToken, @Path("id") String userId);

    // Get user details by Email
    @GET("users/{email}")
    Call<UserDetails> getUserDetailsByEmail(@Header("Authorization") String authToken, @Path("email") String email);

    // Update user details
    @PATCH("users/{id}")
    Call<Void> updateUserDetails(@Header("Authorization") String authToken, @Path("id") String userId, @Body UserDetails user);

    // Delete user
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Header("Authorization") String authToken, @Path("id") String userId);

    // Get user's posts
    @GET("users/{id}/posts")
    Call<List<Post>> getUserPosts(@Header("Authorization") String authToken, @Path("id") String userId);

    // Get user's friends
    @GET("users/{id}/friends")
    Call<Friends> getUserFriends(@Header("Authorization") String authToken, @Path("id") String userId);

    // Add a friend
    @POST("users/{id}/friends")
    Call<Void> addFriend(@Header("Authorization") String authToken, @Path("id") String userId);

    // Approve friend request
    @PATCH("users/{id}/friends/{fid}")
    Call<Void> handleFriendRequest(@Header("Authorization") String authToken, @Path("id") String userId, @Path("fid") String friendId);

    // Delete friend or friend request
    @DELETE("users/{id}/friends/{fid}")
    Call<Void> deleteFriend(@Header("Authorization") String authToken, @Path("id") String userId, @Path("fid") String friendId);

    @POST("users/{id}/posts/{pid}/likes")
    Call<Void> likePost(@Header("Authorization") String authToken, @Path("id") String userId, @Path("pid") String PostId);

    @DELETE("users/{id}/posts/{pid}/likes")
    Call<Void> unlikePost(@Header("Authorization") String authToken, @Path("id") String userId, @Path("pid") String PostId);

    @GET("users/{id}/posts/{pid}/comments")
    Call<Comments> getPostComments(@Header("Authorization") String authToken, @Path("id") String userId, @Path("pid") String PostId);

    @POST("users/{id}/posts/{pid}/comments")
    Call<Void> createComment(@Header("Authorization") String authToken, @Path("id") String userId, @Path("pid") String PostId, @Body Comment comment);
}
