package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.BitmapUtils;
import com.example.myapplication.DialogUtils;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Friend;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.viewmodels.PostsViewModel;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private MutableLiveData<UserDetails> user;
    private UsersViewModel usersViewModel;
    private PostsViewModel postsViewModel;
    protected PostsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
        ImageButton addPost = findViewById(R.id.addPostBT);
        addPost.setVisibility(View.GONE);
        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("ID");
        user = new MutableLiveData<>();
        usersViewModel.getUserDetailsById(userId, user);
        user.observe(this, userDetails -> {
            if (userDetails != null) {
                setHeaderDetails(userDetails);
                uploadHisProfil(userDetails);
            }
        });
        usersViewModel.reloadFriends();
        // Set click listeners for menu, logout, and add post buttons
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

    private void uploadHisProfil(UserDetails userDetails) {
        postsViewModel.reloadProfile(userDetails.get_id());
        usersViewModel.getFriends().observe(this, this::setPage);
    }

    private void setPage(List<Friend> friendList) {
        String status;
        if (user.getValue().get_id().equals(MyJWTtoken.getInstance().getUserDetails().getValue().get_id()))
            status = "approved";
        else
            status = isHeIsMyFriend(friendList);
        switch (status) {
            case "approved":
                initAdapter();
                break;
            case "s-pending":
                sPend();
                break;
            case "no":
                notFriend();
                break;
            case "pending":
                pend();
                break;
            default:
                break;
        }
    }

    private void pend() {
        Button sendReqBT = findViewById(R.id.sendFriendReqBT);
        TextView tvReqSent = findViewById(R.id.tvReqSent);
        sendReqBT.setVisibility(View.GONE);
        tvReqSent.setVisibility(View.VISIBLE);
        tvReqSent.setText("This user is waiting your approval!");
        setRefreshUpd();
    }

    private void notFriend() {
        Button sendReqBT = findViewById(R.id.sendFriendReqBT);
        TextView tvReqSent = findViewById(R.id.tvReqSent);
        sendReqBT.setVisibility(View.VISIBLE);
        tvReqSent.setVisibility(View.GONE);
        sendReqBT.setOnClickListener(v -> {
            usersViewModel.sendRequest(user.getValue().get_id());
            sPend();
        });
        setRefreshUpd();
    }

    private void sPend() {
        Button sendReqBT = findViewById(R.id.sendFriendReqBT);
        TextView tvReqSent = findViewById(R.id.tvReqSent);
        sendReqBT.setVisibility(View.GONE);
        tvReqSent.setVisibility(View.VISIBLE);
        tvReqSent.setText("Request sent! waiting for an answer");
        setRefreshUpd();
    }

    private void initAdapter() {
        Button sendReqBT = findViewById(R.id.sendFriendReqBT);
        TextView tvReqSent = findViewById(R.id.tvReqSent);
        sendReqBT.setVisibility(View.GONE);
        tvReqSent.setVisibility(View.GONE);
        // Initialize RecyclerView for displaying posts
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        adapter = new PostsListAdapter(this, postsViewModel, this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            postsViewModel.reloadProfile(user.getValue().get_id());
        });
        postsViewModel.getProfile().observe(this, posts -> {
            adapter.setPosts(posts);
            refreshLayout.setRefreshing(false);
        });
        postsViewModel.reloadProfile(user.getValue().get_id());
    }

    private String isHeIsMyFriend(List<Friend> friendList) {
        String userId = user.getValue().get_id();
        for (int i = 0; i < friendList.size(); i++) {
            if (Objects.equals(userId, friendList.get(i).get_id())) {
                return friendList.get(i).getStatus();
            }
        }
        return getString(R.string.no);
    }

    private void logOut() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Intent i = new Intent(this, LogInPageActivity.class);
        startActivity(i);
        finish();
    }

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
                if (item.getItemId() == R.id.myFriendsItem) {
                    Intent i = new Intent(v.getContext(), FriendsActivity.class);
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

    private void setRefreshUpd() {
        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> {
            usersViewModel.reloadFriends();
            refreshLayout.setRefreshing(false);
        });
    }

    /**
     * Handles the result of selecting an image to attach to a post.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        postsViewModel.reloadProfile(user.getValue().get_id());
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
            adapter.setEditedImage(selectedImageUri);
        }
    }
}