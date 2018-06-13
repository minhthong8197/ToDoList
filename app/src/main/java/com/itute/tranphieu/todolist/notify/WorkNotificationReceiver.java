package com.itute.tranphieu.todolist.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.itute.tranphieu.todolist.MainActivity;
import com.itute.tranphieu.todolist.R;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class WorkNotificationReceiver extends BroadcastReceiver {

    Context context;
    String title, description;

    @Override
    public void onReceive(Context context, Intent intent) {
        description = intent.getStringExtra("description");
        title = intent.getStringExtra("title");
        this.notify(context, title, description);
    }

    private void notify(Context context, String title, String description) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher)//xai tammmmmmmmmmmmmmm
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        //noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(new Random(System.currentTimeMillis()).nextInt(), noti);
        pIntent.cancel();
    }
}
