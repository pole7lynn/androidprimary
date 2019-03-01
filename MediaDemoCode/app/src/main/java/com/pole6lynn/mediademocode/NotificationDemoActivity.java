package com.pole6lynn.mediademocode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class NotificationDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mSendNoticesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_demo_activity);
        mSendNoticesBtn = findViewById(R.id.send_notification);
        mSendNoticesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification:
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        this, 0, intent, 0);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(
                        Context.NOTIFICATION_SERVICE);
                Notification mNotification = new NotificationCompat.Builder(this)
                        .setContentTitle("This is title")
                        .setContentText("This is content.")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(Uri.fromFile(new File("")))
                        .setVibrate(new long[]{0, 1000, 1000, 1000})
                        .setLights(Color.GREEN, 1000, 1000)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(
                                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .build();
                mNotificationManager.notify(0, mNotification);
                break;
            default:
                break;
        }
    }
}
