package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.DialogUtils;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.adapters.FriendsListAdapter;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private UsersViewModel usersViewModel;
    private MutableLiveData<List<Friend>> friends;
    private FriendsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        setHeaderDetails(MyJWTtoken.getInstance().getUserDetails().getValue());
        // Initialize RecyclerView for displaying posts
        RecyclerView lstFriends = findViewById(R.id.lstPosts);
        adapter = new FriendsListAdapter(this, usersViewModel);
        lstFriends.setAdapter(adapter);
        lstFriends.setLayoutManager(new LinearLayoutManager(this));
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            usersViewModel.reloadFriends();
        });
        usersViewModel.getFriends().observe(this, friends -> {
            adapter.setFriends(friends);
            refreshLayout.setRefreshing(false);
        });
        usersViewModel.reloadFriends();
        // Set click listeners for menu, logout, and add post buttons
        ImageButton addPost = findViewById(R.id.addPostBT);
        addPost.setVisibility(View.GONE);
        ImageButton showMenuBt = findViewById(R.id.menuBT);
        showMenuBt.setOnClickListener(v -> {
            showMenu(v);
        });
        ImageButton logoutBT = findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(v -> {
            MyJWTtoken.getInstance().forget();
            logOut();
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

    private void logOut() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Intent i = new Intent(this, LogInPageActivity.class);
        startActivity(i);
        finish();
    }

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
                    String id = MyJWTtoken.getInstance().getUserDetails().getValue().get_id();
                    i.putExtra("ID", id);
                    startActivity(i);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.editUserItem) {
                    Intent i = new Intent(v.getContext(), EditUserActivity.class);
                    startActivity(i);
                    finish();
                    return true;
                }
                if (item.getItemId() == R.id.deleteUserItem) {
                    DialogUtils.showAreYouSureDialog(v.getContext(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usersViewModel.deleteUser();
                            logOut();
                        }
                    });
                    return true;
                }
                if (item.getItemId() == R.id.homeItem) {
                    Intent i = new Intent(v.getContext(), FeedPageActivity.class);
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    private void changeNightMood() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}