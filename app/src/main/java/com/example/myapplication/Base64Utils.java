package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Utils {
    public static String uriToBase64(Uri uri) throws IOException {
        InputStream inputStream = MyApplication.context.getContentResolver().openInputStream(uri);
        byte[] bytes = getBytes(inputStream);
        return "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.DEFAULT);
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
        String base64s = base64String.split(",", 2)[1];
        byte[] decodedBytes;
        decodedBytes = Base64.decode(base64s, Base64.DEFAULT);
        File file = File.createTempFile("temp_image", null, MyApplication.context.getCacheDir());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(decodedBytes);
        fos.flush();
        fos.close();
        return Uri.fromFile(file);
    }
}
