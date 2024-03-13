package com.example.myapplication.Activities;

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

import com.example.myapplication.Activities.LogInPageActivity;
import com.example.myapplication.Base64Utils;
import com.example.myapplication.BitmapUtils;
import com.example.myapplication.R;
import com.example.myapplication.UserListSrc;
import com.example.myapplication.entities.User;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;

/**
 * Activity responsible for signing in a new user.
 */
public class SignInPageActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public Uri selectedImage;
    private UsersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page);
        viewModel = new UsersViewModel();
        // Button to pick a profile photo
        Button photoPickerBT = findViewById(R.id.photoPickerBT);
        photoPickerBT.setOnClickListener(v -> selectPhoto());
        // Button to sign in the user
        Button signInBT = findViewById(R.id.signMeInBT);
        signInBT.setOnClickListener(v -> {
            if (checkValid()) {
                signInThisUser();
            }
        });
        Button goToLogInBT = findViewById(R.id.goToLogInBT);
        goToLogInBT.setOnClickListener(v -> {
            Intent i = new Intent(this, LogInPageActivity.class);
            startActivity(i);
            finish();
        });
    }

    /**
     * Signs in the user by creating a new user object and adding it to the user list.
     */
    private void signInThisUser() {
        EditText emailV = findViewById(R.id.userNameSignBox);
        EditText passwordV = findViewById(R.id.passSignBox);
        EditText nameV = findViewById(R.id.displayNameBox);
        ImageView profileV = findViewById(R.id.ivProfileView);
        String email = emailV.getText().toString();
        String password = passwordV.getText().toString();
        String name = nameV.getText().toString();
        User newUser;
        // Create a new user object based on the entered details
        if (selectedImage != null) {
            try {
                newUser = new User(email, password, name, Base64Utils.uriToBase64(selectedImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            viewModel.createUser(newUser);
        }
    }

    /**
     * Checks if the entered user details are valid.
     *
     * @return True if the user details are valid, false otherwise.
     */
    private boolean checkValid() {
        EditText email = findViewById(R.id.userNameSignBox);
        EditText password1 = findViewById(R.id.passSignBox);
        EditText password2 = findViewById(R.id.passVrfySignBox);
        EditText name = findViewById(R.id.displayNameBox);
        ImageView profile = findViewById(R.id.ivProfileView);
        // Validate email
        if (!isValidEmail(email.getText())) {
            Toast.makeText(this, "invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validate password length
        else {
            if (password1.getText().length() < 8) {
                Toast.makeText(this, "too short password (at least 8)", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Validate password match
            else {
                if (!password1.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(this, "the passwords aren't equal", Toast.LENGTH_SHORT).show();
                    return false;
                }
                // Validate name length
                else {
                    if (name.getText().toString().length() < 2) {
                        Toast.makeText(this, "name too short (at least 2 character)", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // Validate if profile picture is selected
                    else {
                        if (selectedImage == null) {
                            Toast.makeText(this, "must pick a profile picture", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the given email address is valid.
     *
     * @param target The email address to validate.
     * @return True if the email address is valid, false otherwise.
     */
    private boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Opens the gallery or camera to select a profile photo.
     */

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
                Uri selectedImageUri = BitmapUtils.bitmapToUri(this, photo);
                profileView.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            }
        }
    }
}