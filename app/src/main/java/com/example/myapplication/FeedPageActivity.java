package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RouteListingPreference;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.adapters.CommentsListAdapter;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Comment;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/**
 * Activity for displaying the main feed page with posts.
 */
public class FeedPageActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton showMenuBt;
    private ImageButton logoutBT;
    private ImageButton addPostBT;
    protected PostsListAdapter adapter;
    public User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
        // Get the active user from the UserListSrc
        currentUser = UserListSrc.getInstance(this).getActiveUser();
        UserListSrc.getInstance(this).setActiveUser(currentUser);
        // Set up header details (profile image and display name)
        setHeaderDetails(currentUser);
        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        // Load and display all posts
        List<Post> posts = PostListSrc.getInstance(this).getPosts();
        adapter.setPosts(posts);
        // Set click listeners for menu, logout, and add post buttons
        showMenuBt = findViewById(R.id.menuBT);
        showMenuBt.setOnClickListener(v -> {
            showMenu(v);
        });
        logoutBT = findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            UserListSrc.getInstance(this).setActiveUser(null);
            Intent i = new Intent(this, LogInPageActivity.class);
            startActivity(i);
        });
        addPostBT = findViewById(R.id.addPostBT);
        addPostBT.setOnClickListener(v -> {
            Intent i = new Intent(this, AddPostActivity.class);
            String user = currentUser.getUserName();
            i.putExtra("CURRENT_USER", user);
            startActivity(i);
        });

    }

    /**
     * Sets header details such as profile image and display name.
     */
    protected void setHeaderDetails(User user) {
        ImageView profile = findViewById(R.id.profileHeader);
        if (user.getUriProfilePic() == null) {
            profile.setImageResource(user.getIntProfilePic());
        }
        else {
            profile.setImageURI(user.getUriProfilePic());
        }
        TextView displayName = findViewById(R.id.NameHeaderText);
        displayName.setText(user.getDisplayName());
    }

    /**
     * Displays the menu popup.
     * @param v The view associated with the menu button.
     */
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.nav_drawer_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.darkMoodItem) {
                    changeNightMood();
                    return true;
                }
                if (item.getItemId() == R.id.profileItem) {
                    Intent i = new Intent(v.getContext(), ProfileActivity.class);
                    String user = currentUser.getUserName();
                    i.putExtra("USER", user);
                    startActivity(i);
                    return true;
                }
                if (item.getItemId() == R.id.homeItem) {
                    Intent i = new Intent(v.getContext(), FeedPageActivity.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    /**
     * Handles the result of selecting an image to attach to a post.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                selectedImageUri = data.getData();
            } else {
                // Handle camera photo capture
                // The photo is available in the intent's extras as a bitmap
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                selectedImageUri = BitmapUtils.bitmapToUri(this, photo);
            }
            PostListSrc.getInstance(this).getPosts().get(adapter.getPosOfEditedImage()).setUriPic(selectedImageUri);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Changes the night mode (dark mode).
     */
    private void changeNightMood() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}