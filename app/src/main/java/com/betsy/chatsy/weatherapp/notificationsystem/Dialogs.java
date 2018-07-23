package com.betsy.chatsy.weatherapp.notificationsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Dialogs {

    public static AlertDialog.Builder alertDialog(Context c, String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder;
    }

}
