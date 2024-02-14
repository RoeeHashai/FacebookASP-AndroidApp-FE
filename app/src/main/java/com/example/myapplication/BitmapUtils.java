package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {

    public static Uri bitmapToUri(Context context, Bitmap bitmap) {
        // Get the directory for storing images
        File imagesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create a file to save the bitmap
        File imageFile = new File(imagesDirectory, "image_" + System.currentTimeMillis() + ".jpg");

        // Save the bitmap to the file
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the Uri of the saved file
        return Uri.fromFile(imageFile);
    }
}

