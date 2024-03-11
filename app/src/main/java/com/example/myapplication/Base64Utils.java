package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.example.myapplication.entities.Post;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Base64Utils {
    private static final String preBase64 = "data:image/jpeg;base64,";

    public static String uriToBase64(Uri uri) throws IOException {
        InputStream inputStream = MyApplication.context.getContentResolver().openInputStream(uri);
        byte[] bytes = getBytes(inputStream);
        return preBase64 + (Base64.encodeToString(bytes, Base64.DEFAULT)).replace("\n", "");
    }

    private static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Uri base64ToUri(String base64String) throws IOException {
        String base64s = base64String.split(",")[1];
        byte[] decodedBytes;
        decodedBytes = Base64.decode(base64s, Base64.DEFAULT);
        File file = File.createTempFile("temp_image", null, MyApplication.context.getCacheDir());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(decodedBytes);
        fos.flush();
        fos.close();
        return Uri.fromFile(file);
    }

    public static String compressBase64Image(String image) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtils.uriToBitmap(MyApplication.context, Base64Utils.base64ToUri(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Compress bitmap to reduce size
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);

        // Convert compressed bitmap to byte array
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        // Encode byte array to Base64 string
        image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return preBase64 + image.replace("\n", "");
    }

    public static List<Post> compressAll(List<Post> posts) {
        for (Post post : posts) {
            if (!post.getImage().isEmpty()) {
                post.setImage(Base64Utils.compressBase64Image(post.getImage()));
            }
            post.getAuthor().setImage(Base64Utils.compressBase64Image(post.getAuthor().getImage()));
        }
        return posts;
    }
}
