package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.media.RouteListingPreference;
import android.os.Bundle;
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
import java.util.List;

public class FeedPageActivity extends AppCompatActivity {
    private ImageButton showMenuBt;
    private ImageButton logoutBT;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
        String username = getIntent().getStringExtra("CURRENT_USER");
        currentUser = UserListSrc.getInstance().getUser(username);
        setHeaderDetails();
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));
        List<Post> posts = PostListSrc.getInstance().getPosts();
        adapter.setPosts(posts);
        showMenuBt = findViewById(R.id.menuBT);
        showMenuBt.setOnClickListener(v -> {
            showMenu(v);
        });
        logoutBT = findViewById(R.id.logoutBT);
        logoutBT.setOnClickListener(v -> {
            Intent i = new Intent(this, LogInPageActivity.class);
            startActivity(i);
        });

    }

    private void setHeaderDetails() {
        ImageView profile = findViewById(R.id.profileHeader);
        if (currentUser.getUriProfilePic() == null) {
            profile.setImageResource(currentUser.getIntProfilePic());
        }
        else {
            profile.setImageURI(currentUser.getUriProfilePic());
        }
        TextView displayName = findViewById(R.id.NameHeaderText);
        displayName.setText(currentUser.getDisplayName());
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.nav_drawer_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.show();
    }


}