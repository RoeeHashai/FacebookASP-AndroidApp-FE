package com.example.myapplication;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Response;

public class ErrorUtils {
    public static ErrorResponse parseError(Response<?> response) {
        Gson gson = new GsonBuilder().create();
        ErrorResponse errorResponse = new ErrorResponse();

        try {
            errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
        } catch (IOException e) {
            Log.e("ErrorUtils", "Error parsing error response", e);
        }

        return errorResponse;
    }
}
