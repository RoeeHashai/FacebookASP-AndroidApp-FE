package com.example.myapplication;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public static List<String> fromString(String value) {
        if (value == null || value.isEmpty()) {
            return new ArrayList<>();
        } else {
            String[] splitValues = value.split(",");
            List<String> list = new ArrayList<>(splitValues.length);
            for (String splitValue : splitValues) {
                list.add(splitValue);
            }
            return list;
        }
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        StringBuilder value = new StringBuilder();
        for (String item : list) {
            value.append(item).append(",");
        }
        return value.toString();
    }
}

