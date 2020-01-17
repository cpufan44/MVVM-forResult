package com.example.mvvm_forresult;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


public class Reciever extends BroadcastReceiver {
    private final String CHANNEL_ID = "tasks_notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("param"), Toast.LENGTH_SHORT).show();

        String desc = intent.getStringExtra("param");
        displayNotification(context, desc);

    }

    public void displayNotification(Context context, String description){
        Notification builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle("You have task to do")
                .setContentText(description)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        createNotificationChannel(context);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder);
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "Task notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
