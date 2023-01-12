package com.example.lets_eat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyMain extends AppCompatActivity {
    public final static int MY_NOTIFICATION_ID = 1;
    final private String CHANNEL_ID = "default";
    private NotificationManagerCompat mNotificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManagerCompat = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API 26
            mNotificationManagerCompat.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, "default channel",
                            NotificationManager.IMPORTANCE_DEFAULT));
        }
    }

    public void onSimpleNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        //mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        //mBuilder.setContentText(getResources().getString(R.string.notif_body));

        // MY_NOTIFICATION_ID allows you to update the notification later on.
        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }

    public void onActionNotification(View v) {
        if (!mNotificationManagerCompat.areNotificationsEnabled())
            return;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        //mBuilder.setContentTitle(getResources().getString(R.string.notif_title));
        //mBuilder.setContentText(getResources().getString(R.string.notif_body));


        // notification 누르면 리스트뷰 창으로 연결
        Intent intent = new Intent(this, Notification.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this); stackBuilder.addParentStack(Notification.class); stackBuilder.addNextIntent(intent);
        PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT); mBuilder.setContentIntent(pIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());



        //mNotificationManagerCompat.notify(MY_NOTIFICATION_ID, mBuilder.build());
    }


}
