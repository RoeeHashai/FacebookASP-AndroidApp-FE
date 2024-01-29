package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page);
        Button LogInBT = findViewById(R.id.LogInBT);
        LogInBT.setOnClickListener(v -> {
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        });
    }
}