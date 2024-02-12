package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.entities.User;

public class SignInPageActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);
        Button photoPickerBT = findViewById(R.id.photoPickerBT);
        photoPickerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        Button signInBT = findViewById(R.id.signMeInBT);
        signInBT.setOnClickListener( v -> {
            if(checkValid()) {
                signInThisUser();
                Intent i = new Intent(this, LogInPageActivity.class);
                startActivity(i);
            }
        });
    }

    private void signInThisUser() {
        EditText emailV = findViewById(R.id.userNameSignBox);
        EditText passwordV = findViewById(R.id.passSignBox);
        EditText nameV = findViewById(R.id.displayNameBox);
        ImageView profileV = findViewById(R.id.ivProfileView);
        String email = emailV.getText().toString();
        String password = passwordV.getText().toString();
        String name = nameV.getText().toString();
        User newUser;
        if (selectedImage != null) {
            newUser = new User(email, password, name, selectedImage);
        }
        else {
            newUser = new User(email, password, name, R.drawable.facebook_icon);
        }
        UserListSrc.getInstance().addUser(newUser);
    }

    private boolean checkValid() {
        EditText email = findViewById(R.id.userNameSignBox);
        EditText password1 = findViewById(R.id.passSignBox);
        EditText password2 = findViewById(R.id.passVrfySignBox);
        EditText name = findViewById(R.id.displayNameBox);
        if (!isValidEmail(email.getText())) {
            Toast.makeText(this,"invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (UserListSrc.getInstance().getUser(email.getText().toString()) != null) {
            Toast.makeText(this,"this email already exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password1.getText().length() < 8) {
            Toast.makeText(this,"too short password (at least 8)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password1.getText().toString().equals(password2.getText().toString())) {
            Toast.makeText(this,"the passwords aren't equal", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.getText().toString().length() < 2) {
            Toast.makeText(this,"name too short (at least 2 character)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void selectPhoto() {
        // Intent to pick an image from the gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Intent to capture a photo from the camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a chooser intent to present the user with options
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        // Start the activity based on the user's choice
        startActivityForResult(chooserIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileView = findViewById(R.id.ivProfileView);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                profileView.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            } else {
                // Handle camera photo capture
                // The photo is available in the intent's extras as a bitmap
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profileView.setImageURI(BitmapUtils.bitmapToUri(this,photo));
            }
        }
    }
}