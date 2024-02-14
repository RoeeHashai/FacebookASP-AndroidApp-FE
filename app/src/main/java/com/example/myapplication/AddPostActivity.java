package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entities.Post;
import com.example.myapplication.entities.User;

/**
 * Activity for adding a new post.
 */
public class AddPostActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri selectedImage;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_page);
        // Hide search button (it's not used in this activity)
        findViewById(R.id.searchBT).setVisibility(View.GONE);
        // Get the currently logged-in user
        currentUser = UserListSrc.getInstance(this).getActiveUser();
        // Set header details (profile picture and display name)
        setHeaderDetails();
        // Set click listener for photo picker button
        Button photoPickerBT = findViewById(R.id.postImagePicker);
        photoPickerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        // Set click listener for publish button
        Button publishBT = findViewById(R.id.publishPostBT);
        publishBT.setOnClickListener(v -> {
            if (validPost()) {
                publishPost();
                Intent i = new Intent(this, FeedPageActivity.class);
                startActivity(i);
            }
        });
    }
    /**
     * Publishes the post to the post list.
     */
    private void publishPost() {
        EditText contentView = findViewById(R.id.postContentBox);
        String content = contentView.getText().toString();
        Post newPost;
        if (selectedImage == null) {
            newPost = new Post(currentUser, content);
        }
        else {
            newPost = new Post(currentUser,content, selectedImage);
        }
        PostListSrc.getInstance(this).addPost(newPost);
    }
    /**
     * Validates the post before publishing.
     * @return True if the post is valid, false otherwise.
     */
    private boolean validPost() {
        EditText content = findViewById(R.id.postContentBox);
        if (content.getText().toString().length() < 3 && selectedImage == null) {
            Toast.makeText(this, "Post too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Sets the header details (profile picture and display name) of the current user.
     */
    private void setHeaderDetails() {
        ImageView profile = findViewById(R.id.profileHeader);
        if (currentUser.getUriProfilePic() == null) {
            profile.setImageResource(currentUser.getIntProfilePic());
        } else {
            profile.setImageURI(currentUser.getUriProfilePic());
        }
        TextView displayName = findViewById(R.id.NameHeaderText);
        displayName.setText(currentUser.getDisplayName());
    }

    /**
     * Starts an activity to select a photo for the post.
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
        ImageView postImageView = findViewById(R.id.addPostImage);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                // Photo picked from the gallery
                Uri selectedImageUri = data.getData();
                postImageView.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            } else {
                // Handle camera photo capture
                // The photo is available in the intent's extras as a bitmap
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri selectedImageUri = BitmapUtils.bitmapToUri(this,photo);
                postImageView.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            }
        }
    }
}