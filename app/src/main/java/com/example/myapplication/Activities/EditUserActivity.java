package com.example.myapplication.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Base64Utils;
import com.example.myapplication.BitmapUtils;
import com.example.myapplication.MyJWTtoken;
import com.example.myapplication.R;
import com.example.myapplication.entities.UserDetails;
import com.example.myapplication.viewmodels.UsersViewModel;

import java.io.IOException;

public class EditUserActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri selectedImage;
    private UsersViewModel usersViewModel;
    EditText etName;
    ImageView ivImage;
    Button photoPicker;
    Button makeChangeBT;
    Button backToFeedBT;
    UserDetails user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setDetails();
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        photoPicker.setOnClickListener(v -> {
            selectPhoto();
        });
        makeChangeBT.setOnClickListener(v -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setName(etName.getText().toString());
            if (selectedImage != null) {
                try {
                    userDetails.setImage(Base64Utils.uriToBase64(selectedImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                userDetails.setImage(MyJWTtoken.getInstance().getUserDetails().getValue().getImage());
            }
            usersViewModel.editUser(userDetails);
        });
        backToFeedBT.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), FriendsActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void initViews() {
        etName = findViewById(R.id.editNameBox);
        ivImage = findViewById(R.id.ivEditProfileImage);
        photoPicker = findViewById(R.id.editProfileImagePicker);
        makeChangeBT = findViewById(R.id.editUserBT);
        backToFeedBT = findViewById(R.id.goBackFromEditBT);
        user = MyJWTtoken.getInstance().getUserDetails().getValue();
    }

    private void setDetails() {
        if (user != null) {
            etName.setText(user.getName());
        }
        try {
            ivImage.setImageURI(Base64Utils.base64ToUri(user.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                ivImage.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            } else {
                // Handle camera photo capture
                // The photo is available in the intent's extras as a bitmap
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri selectedImageUri = BitmapUtils.bitmapToUri(this, photo);
                ivImage.setImageURI(selectedImageUri);
                selectedImage = selectedImageUri;
            }
        }
    }
}