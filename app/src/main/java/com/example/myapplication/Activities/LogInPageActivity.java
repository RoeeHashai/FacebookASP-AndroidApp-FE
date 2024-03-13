package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.LoginRequest;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.viewmodels.UsersViewModel;

/**
 * Activity for logging in to the application.
 */
public class LogInPageActivity extends AppCompatActivity {
    private UsersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page);
        // Sign in button click listener
        Button SignInBT = findViewById(R.id.SignInBT);
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        SignInBT.setOnClickListener(v -> {
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
            finish();
        });
        // Log in button click listener
        Button LogInBT = findViewById(R.id.LogInBT);
        LogInBT.setOnClickListener(v -> {
            EditText usernameBox = findViewById(R.id.usernameLogBox);
            EditText passwordBox = findViewById(R.id.passwordLogBox);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(usernameBox.getText().toString());
            loginRequest.setPassword(passwordBox.getText().toString());
            viewModel.login(loginRequest);
            Intent i = new Intent(this, FeedPageActivity.class);
            MyJWTtoken.getInstance().getToken().observe(this, s -> {
                i.putExtra("EMAIL", usernameBox.getText().toString());
                startActivity(i);
                passwordBox.setText("");
                finish();
            });
        });
    }
}