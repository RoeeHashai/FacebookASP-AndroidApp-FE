package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.entities.User;

public class LogInPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page);
        Button SignInBT = findViewById(R.id.SignInBT);
        SignInBT.setOnClickListener(v -> {
            Intent i = new Intent(this, SignInPageActivity.class);
            startActivity(i);
        });
        Button LogInBT = findViewById(R.id.LogInBT);
        LogInBT.setOnClickListener(v -> {
            if (validUser()) {
                Intent i = new Intent(this, FeedPageActivity.class);
                EditText usernameBox = findViewById(R.id.usernameLogBox);
                String user = usernameBox.getText().toString();
                i.putExtra("CURRENT_USER", user);
                startActivity(i);
            }
        });
    }

    private boolean validUser() {
        EditText usernameBox = findViewById(R.id.usernameLogBox);
        EditText passwordBox = findViewById(R.id.passwordLogBox);
        User thisUser = UserListSrc.getInstance().getUser(usernameBox.getText().toString());
        if (thisUser == null){
            Toast.makeText(this, "no user in this Email. try again", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (thisUser.isMachingPassword(passwordBox.getText().toString())){
            return true;
        }
        Toast.makeText(this, "wrong password. try again", Toast.LENGTH_SHORT).show();
        return false;
    }
}