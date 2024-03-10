package com.example.myapplication;

import android.util.Base64;
import androidx.room.TypeConverter;

public class BlobTypeConverter {

    @TypeConverter
    public static byte[] fromString(String value) {
        return Base64.decode(value, Base64.DEFAULT);
    }

    @TypeConverter
    public static String toString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
