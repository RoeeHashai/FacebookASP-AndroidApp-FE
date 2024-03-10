package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.BitmapUtils;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.PostListSrc;
import com.example.myapplication.R;
import com.example.myapplication.UserDetails;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewmodels.PostsViewModel;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;

/**
 * Activity for displaying the main feed page with posts.
 */
public class FeedPageActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton showMenuBt;
    private ImageButton logoutBT;
    private ImageButton addPostBT;
    private PostsViewModel postsViewModel;
    protected PostsListAdapter adapter;
    public LiveData<UserDetails> currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(getIntent().getStringExtra("EMAIL"));
        MyJWTtoken.getInstance().setUserDetails(userDetails);
        currentUser = MyJWTtoken.getInstance().getUserDetails();
        postsViewModel.getUserDetails();
        MyJWTtoken.getInstance().getUserDetails().observe(this, new Observer<UserDetails>() {
            @Override
            public void onChanged(UserDetails userDetails) {
                if (userDetails.get_id() != null) {
                    setHeaderDetails(userDetails);
                }
            }
        });
        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            postsViewModel.reload();
        });
        postsViewModel.get().observe(this, posts -> {
            adapter.setPosts(posts);
            refreshLayout.setRefreshing(false);
        });

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
            startActivity(i);
        });

    }

    /**
     * Sets header details such as profile image and display name.
     */
    protected void setHeaderDetails(UserDetails userDetails) {
        ImageView profile = findViewById(R.id.profileHeader);
        try {
            profile.setImageURI(Base64Utils.base64ToUri(userDetails.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextView displayName = findViewById(R.id.NameHeaderText);
        displayName.setText(userDetails.getName());
    }

    /**
     * Displays the menu popup.
     *
     * @param v The view associated with the menu button.
     */
    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.nav_drawer_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.darkMoodItem) {
                    changeNightMood();
                    return true;
                }
                if (item.getItemId() == R.id.profileItem) {
                    Intent i = new Intent(v.getContext(), ProfileActivity.class);
                    String user = currentUser.getValue().getEmail();
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
            //PostListSrc.getInstance(this).getPosts().get(adapter.getPosOfEditedImage()).setUriPic(selectedImageUri);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Changes the night mode (dark mode).
     */
    private void changeNightMood() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}