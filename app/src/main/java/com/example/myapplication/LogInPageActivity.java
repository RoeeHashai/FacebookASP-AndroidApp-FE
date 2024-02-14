package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.entities.User;

/**
 * Activity for logging in to the application.
 */
public class LogInPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page);
        // Sign in button click listener
        Button SignInBT = findViewById(R.id.SignInBT);
        SignInBT.setOnClickListener(v -> {
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        });
        // Log in button click listener
        Button LogInBT = findViewById(R.id.LogInBT);
        LogInBT.setOnClickListener(v -> {
            // Validate user credentials and navigate to feed page if valid
            if (validUser()) {
                Intent i = new Intent(this, FeedPageActivity.class);
                EditText usernameBox = findViewById(R.id.usernameLogBox);
                User user = UserListSrc.getInstance(this).getUser(usernameBox.getText().toString());
                UserListSrc.getInstance(this).setActiveUser(user);
                startActivity(i);
                usernameBox.setText("");
                EditText passwordBox = findViewById(R.id.passwordLogBox);
                passwordBox.setText("");
            }
        });
    }

    /**
     * Validates the entered user credentials.
     *
     * @return True if the user credentials are valid, otherwise false.
     */
    private boolean validUser() {
        EditText usernameBox = findViewById(R.id.usernameLogBox);
        EditText passwordBox = findViewById(R.id.passwordLogBox);
        User thisUser = UserListSrc.getInstance(this).getUser(usernameBox.getText().toString());
        // Check if the user exists
        if (thisUser == null){
            Toast.makeText(this, "no user in this Email. try again", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check if the entered password matches the user's password
        if (thisUser.isMachingPassword(passwordBox.getText().toString())){
            return true;
        }
        Toast.makeText(this, "wrong password. try again", Toast.LENGTH_SHORT).show();
        return false;
    }
}