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

import com.example.myapplication.adapters.PostsListAdapter;
import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FeedPageActivity extends AppCompatActivity {

    public ActionBarDrawerToggle toggle;
    //public DrawerLayout drawerLayout;
    public MenuItem logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_page);
       /* drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        FloatingActionButton openMenuBT = findViewById(R.id.openMenuBT);
        openMenuBT.setOnClickListener(v -> {
            this.drawerLayout.open();
        }); */
        RecyclerView lstPosts = findViewById(R.id.lstPosts);
        final PostsListAdapter adapter = new PostsListAdapter(this);
        lstPosts.setAdapter(adapter);
        lstPosts.setLayoutManager(new LinearLayoutManager(this));

        List<Post> posts = new ArrayList<>();
        User myUser = new User("Yatyat", "12345678", "Yatir Gross", R.drawable.profile_image);
        posts.add(new Post(myUser,"Hello World1", R.drawable.facebook_icon));
        posts.add(new Post( myUser,"Hello World2"));
        posts.add(new Post( myUser,"Hello World3", R.drawable.facebook_icon));
        posts.add(new Post( myUser,"Hello World4", R.drawable.facebook_icon));
        adapter.setPosts(posts);
    }
}