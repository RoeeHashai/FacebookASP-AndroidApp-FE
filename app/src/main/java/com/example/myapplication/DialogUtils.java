package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogUtils {

    public static void showAreYouSureDialog(Context context, DialogInterface.OnClickListener yesListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.are_you_sure, null);
        builder.setView(dialogView);

        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
        Button yesButton = dialogView.findViewById(R.id.dialog_button_yes);
        Button noButton = dialogView.findViewById(R.id.dialog_button_no);

        messageTextView.setText("Are you sure?");

        final AlertDialog dialog = builder.create();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesListener != null) {
                    yesListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
                dialog.dismiss(); // Close the dialog
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Close the dialog
            }
        });

        dialog.show();
    }
}
